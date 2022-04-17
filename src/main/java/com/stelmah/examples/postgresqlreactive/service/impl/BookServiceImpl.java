package com.stelmah.examples.postgresqlreactive.service.impl;

import com.stelmah.examples.postgresqlreactive.mapper.BookMapper;
import com.stelmah.examples.postgresqlreactive.repo.BookRepo;
import com.stelmah.examples.postgresqlreactive.service.BookService;
import com.stelmah.examples.postgresqlreactive.vo.BookVo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class BookServiceImpl implements BookService {

	private final BookRepo bookRepo;
	private final BookMapper bookMapper;

	@Override
	public Mono<BookVo> create(BookVo bookVo) {
		var book = bookMapper.fromVo(bookVo);

		var savedBook = bookRepo.save(book);

		log.info("Created book with id {}", book.getId());
		return savedBook.map(bookMapper::toVo);
	}

	@Override
	public Mono<BookVo> findById(Long id) {
		return bookRepo.findById(id).map(bookMapper::toVo);
	}

	@Override
	public Flux<BookVo> findAll() {
		return bookRepo.findAll().map(bookMapper::toVo);
	}

	@Override
	public Flux<BookVo> findAllWithDelay() {
		return bookRepo.findAll().delayElements(Duration.ofMillis(1000)).map(bookMapper::toVo);
	}

}
