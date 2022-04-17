package com.stelmah.examples.postgresqlreactive.controller;

import com.stelmah.examples.postgresqlreactive.service.BookService;
import com.stelmah.examples.postgresqlreactive.vo.BookVo;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebFluxTest(BookController.class)
public class BookControllerTests {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private BookService bookService;

	private final BookVo bookVo1 = new BookVo(1L, "name", "author");
	private final BookVo bookVo2 = new BookVo(2L, "name2", "author2");

	@Test
	void create() {
		Mockito.when(bookService.create(Mockito.any())).thenReturn(Mono.just(bookVo1));

		var requestVo = new BookVo();

		webTestClient.post()
				.uri("/books")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)
				.body(Mono.just(requestVo), BookVo.class)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody(BookVo.class)
				.value(vo -> vo, Matchers.equalTo(bookVo1));
	}

	@Test
	void findById() {
		Mockito.when(bookService.findById(Mockito.any())).thenReturn(Mono.just(bookVo1));

		webTestClient.get()
				.uri("/books/1")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody(BookVo.class)
				.value(vo -> vo, Matchers.equalTo(bookVo1));
	}

	@Test
	void findAll() {
		Mockito.when(bookService.findAll()).thenReturn(Flux.just(bookVo1, bookVo2));

		webTestClient.get()
				.uri("/books")
				.accept(MediaType.TEXT_EVENT_STREAM)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBodyList(BookVo.class)
				.value(List::size, Matchers.equalTo(2))
				.value(vo -> vo.get(0), Matchers.equalTo(bookVo1))
				.value(vo -> vo.get(1), Matchers.equalTo(bookVo2));
	}

	@Test
	void findAllWithDelay() {
		Mockito.when(bookService.findAllWithDelay())
				.thenReturn(Flux.just(bookVo1, bookVo2).delayElements(Duration.ofMillis(1000)));

		webTestClient.get()
				.uri("/books/with-delay")
				.accept(MediaType.TEXT_EVENT_STREAM)
				.exchange()
				.expectStatus()
				.isOk()
				.expectBodyList(BookVo.class)
				.value(List::size, Matchers.equalTo(2))
				.value(vo -> vo.get(0), Matchers.equalTo(bookVo1))
				.value(vo -> vo.get(1), Matchers.equalTo(bookVo2));
	}

}
