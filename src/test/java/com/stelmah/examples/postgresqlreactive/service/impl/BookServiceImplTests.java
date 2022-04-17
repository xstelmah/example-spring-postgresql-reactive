package com.stelmah.examples.postgresqlreactive.service.impl;

import com.stelmah.examples.postgresqlreactive.domain.Book;
import com.stelmah.examples.postgresqlreactive.mapper.BookMapperImpl;
import com.stelmah.examples.postgresqlreactive.repo.BookRepo;
import com.stelmah.examples.postgresqlreactive.vo.BookVo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Objects;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTests {

	@Mock
	private BookRepo bookRepo;

	@Spy
	private BookMapperImpl bookMapper;

	@InjectMocks
	private BookServiceImpl bookService;

	private final Book book1 = new Book(1L, "name1", "author1");
	private final Book book2 = new Book(2L, "name2", "author2");

	@Test
	void create() {
		Mockito.when(bookRepo.save(Mockito.any())).thenReturn(Mono.just(book1));
		var book = bookService.create(new BookVo(0L, "name1", "author1"));
		StepVerifier.create(book)
				.expectNextMatches(
						vo -> Objects.equals(vo.getName(), "name1") && Objects.equals(vo.getAuthor(), "author1"))
				.verifyComplete();
	}

	@Test
	void findById() {
		Mockito.when(bookRepo.findById(Mockito.anyLong())).thenReturn(Mono.just(book1));
		var books = bookService.findById(1L);

		StepVerifier.create(books)
				.expectNextMatches(
						vo -> Objects.equals(vo.getName(), "name1") && Objects.equals(vo.getAuthor(), "author1"))
				.verifyComplete();
	}

	@Test
	void findAll() {
		Mockito.when(bookRepo.findAll()).thenReturn(Flux.just(book1, book2));
		var books = bookService.findAll();

		StepVerifier.create(books)
				.expectNextMatches(
						vo -> Objects.equals(vo.getName(), "name1") && Objects.equals(vo.getAuthor(), "author1"))
				.expectNextMatches(
						vo -> Objects.equals(vo.getName(), "name2") && Objects.equals(vo.getAuthor(), "author2"))
				.verifyComplete();
	}

	@Test
	void findAllWithDelay() {
		Mockito.when(bookRepo.findAll()).thenReturn(Flux.just(book1, book2));
		var books = bookService.findAllWithDelay();

		StepVerifier.create(books)
				.expectNextMatches(
						vo -> Objects.equals(vo.getName(), "name1") && Objects.equals(vo.getAuthor(), "author1"))
				.expectNextMatches(
						vo -> Objects.equals(vo.getName(), "name2") && Objects.equals(vo.getAuthor(), "author2"))
				.verifyComplete();
	}

}