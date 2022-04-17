package com.stelmah.examples.postgresqlreactive.repo;

import com.stelmah.examples.postgresqlreactive.domain.Book;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface BookRepo extends ReactiveCrudRepository<Book, Long> {
}
