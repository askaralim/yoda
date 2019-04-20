package com.taklip.yoda;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.taklip.yoda.service.RedisService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
	@Autowired
	private RedisService redisService;

	@Test
	public void redisTest() {
		String redisTestKey = redisService.get("redisTestKey");
		assertThat(redisTestKey).isEqualTo(null);

		redisService.set("redisTestKey", "1");
		redisTestKey = redisService.get("redisTestKey");
		assertThat(redisTestKey).isEqualTo("1");

		redisService.delete("redisTestKey");
		redisTestKey = redisService.get("redisTestKey");
		assertThat(redisTestKey).isEqualTo(null);
	}
}