package com.taklip.yoda;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.taklip.yoda.mapper.ContentMapper;
import com.taklip.yoda.model.Content;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContentMapperTest {
	@Autowired
	private ContentMapper contentMapper;

	@Test
	public void selectByContentIdTest() {
		Content content = contentMapper.getById(2);
		assertThat(content.getTitle()).isEqualTo("taklip");
	}
}