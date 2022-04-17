package com.stelmah.examples.postgresqlreactive.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookVo {

	private Long id;
	private String name;
	private String author;

}
