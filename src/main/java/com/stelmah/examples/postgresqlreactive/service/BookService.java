package com.stelmah.examples.postgresqlreactive.service;

import com.stelmah.examples.postgresqlreactive.vo.BookVo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookService {

	Mono<BookVo> create(BookVo book);

	Mono<BookVo> findById(Long id);

	Flux<BookVo> findAll();

	Flux<BookVo> findAllWithDelay();

}
