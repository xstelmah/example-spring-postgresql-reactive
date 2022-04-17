package com.stelmah.examples.postgresqlreactive.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("t_book")
@NoArgsConstructor
@AllArgsConstructor
public class Book {

	@Id
	@Column(value = "id")
	private Long id;
	private String name;
	private String author;

}
