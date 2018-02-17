package com.gihub.evgdim.test;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.DatabasePopulatorUtils;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.jndi.JndiTemplate;

@Configuration
public class DatabaseConfig {
	@Autowired
	private Environment env;
	
	@Bean
	@Profile("!test")
	public DataSource dataSource() throws NamingException {
		return (DataSource) new JndiTemplate().lookup(env.getProperty("jdbc.url"));
	}
	
	@Bean
	@Profile("test")
	public DataSource testDataSource() {
		DataSource dataSource = DataSourceBuilder.create()
				.driverClassName("org.h2.Driver")
				.url("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;Mode=Oracle")
				.username("sa")
				.password("sa")
				.build();
		 DatabasePopulatorUtils.execute(databasePopulator(), dataSource);
		 return dataSource;
	}

	private DatabasePopulator databasePopulator() {
	    final ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
	    populator.addScript(new ClassPathResource("schema-h2.sql"));
	    populator.addScript(new ClassPathResource("data-h2.sql"));
	    return populator;
	}
}