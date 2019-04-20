package com.taklip.yoda;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.taklip.yoda.model.Content;
import com.taklip.yoda.service.ContentService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContentServiceTest {

	@Autowired
	private ContentService contentService;

	@Test
	public void selectByContentIdTest() {
		Content content = contentService.getContent(2l);
		assertThat(content.getTitle()).isEqualTo("taklip");
	}

}
