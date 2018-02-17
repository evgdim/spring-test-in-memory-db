package com.gihub.evgdim.test;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class SpringMemDbTestApplicationTests {
	@Autowired
	private JdbcTemplate jdbc;
	@Test
	public void simpleCheckData() {
		Long cnt = this.jdbc.queryForObject("select count(*) from person", Long.class);
		Assertions.assertThat(cnt).isEqualTo(1);
	}

}
