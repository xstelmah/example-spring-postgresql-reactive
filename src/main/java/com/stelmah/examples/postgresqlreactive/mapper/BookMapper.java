package com.stelmah.examples.postgresqlreactive.mapper;

import com.stelmah.examples.postgresqlreactive.domain.Book;
import com.stelmah.examples.postgresqlreactive.vo.BookVo;
import org.mapstruct.BeanMapping;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookMapper {

	@BeanMapping(ignoreByDefault = true)
	@Mapping(source = "id", target = "id")
	@Mapping(source = "name", target = "name")
	@Mapping(source = "author", target = "author")
	BookVo toVo(Book entity);

	@InheritInverseConfiguration(name = "toVo")
	@BeanMapping(ignoreByDefault = true)
	@Mapping(target = "id", ignore = true)
	Book fromVo(BookVo vo);

}
