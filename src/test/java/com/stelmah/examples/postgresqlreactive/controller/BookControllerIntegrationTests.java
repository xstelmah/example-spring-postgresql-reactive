package com.stelmah.examples.postgresqlreactive.controller;

import com.stelmah.examples.postgresqlreactive.PostgresqlReactiveApplication;
import com.stelmah.examples.postgresqlreactive.vo.BookVo;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@SpringBootTest(classes = PostgresqlReactiveApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookControllerIntegrationTests {

	@Autowired
	private WebTestClient webTestClient;

	@Test
	// Rollback doesn't work now. https://stackoverflow.com/questions/46729849/transactions-in-spring-boot-testing-not-rolled-back
	@Rollback
	@Order(0)
	void create() {
		var requestVo = new BookVo(null, "name", "author");

		webTestClient.post()
				.uri("/books")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(requestVo), BookVo.class)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody(BookVo.class)
				.value(BookVo::getName, Matchers.equalTo(requestVo.getName()))
				.value(BookVo::getAuthor, Matchers.equalTo(requestVo.getAuthor()))
				.value(BookVo::getId, Matchers.notNullValue());
	}

	@Test
	@Order(1)
	void findById() {
		webTestClient.get()
				.uri("/books/1")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody(BookVo.class)
				.value(BookVo::getId, Matchers.equalTo(1L))
				.value(BookVo::getName, Matchers.startsWith("name"))
				.value(BookVo::getAuthor, Matchers.startsWith("author"));
	}

	@Test
	@Order(2)
	void findAll() {
		webTestClient.get()
				.uri("/books")
				.accept(MediaType.TEXT_EVENT_STREAM)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBodyList(BookVo.class)
				.value(List::size, Matchers.equalTo(11));  // 10 from flyway + 1 from create test.
	}

	@Test
	@Timeout(value = 20)
	@Order(3)
	void findAllWithDelay() {
		var webTestClientWithoutTimeout = webTestClient.mutate().responseTimeout(Duration.ofSeconds(20)).build();
		webTestClientWithoutTimeout.get()
				.uri("/books/with-delay")
				.accept(MediaType.TEXT_EVENT_STREAM)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBodyList(BookVo.class)
				.value(List::size, Matchers.equalTo(11)); // 10 from flyway + 1 from create test.
	}

}
