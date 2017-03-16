package com.document.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.document.domain.Author;

@Repository
public interface AuthorDAO extends CrudRepository<Author, Long>
{
	
	List<Author> findByLastNameAndFirstName(String lastName, String firstName);

}
