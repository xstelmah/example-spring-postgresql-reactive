package com.stelmah.examples.postgresqlreactive.controller;

import com.stelmah.examples.postgresqlreactive.service.BookService;
import com.stelmah.examples.postgresqlreactive.vo.BookVo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

	private final BookService bookService;

	@PostMapping
	public Mono<BookVo> create(@RequestBody BookVo bookVo) {
		return bookService.create(bookVo);
	}

	@GetMapping("/{id}")
	public Mono<BookVo> findById(@PathVariable Long id) {
		return bookService.findById(id);
	}

	@GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<BookVo> findAll() {
		return bookService.findAll();
	}

	@GetMapping(path = "/with-delay", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<BookVo> findAllWithDelay() {
		return bookService.findAllWithDelay();
	}

}
