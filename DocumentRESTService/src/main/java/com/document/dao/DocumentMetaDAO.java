package com.document.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


import com.document.domain.DocumentMeta;
import com.querydsl.core.types.Predicate;

/* we just let spring create a repository for us and use it
 * the code generation of Querydsl is also used here. the classes are generated trough a maven external tool command.
 * I added the classes to the source path for convenience. in general you would leave maven to add the classes
 * to the right classpath when the build is made.
 */
@Repository
public interface DocumentMetaDAO extends CrudRepository<DocumentMeta, Long>, JpaRepository<DocumentMeta, Long>, QueryDslPredicateExecutor 
{
	
}
