package com.document.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.jpa.criteria.predicate.BooleanExpressionPredicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.document.dao.AuthorDAO;
import com.document.dao.DocumentMetaDAO;
import com.document.domain.Author;
import com.document.domain.DocumentMeta;
import com.document.domain.QAuthor;
import com.document.domain.QDocumentMeta;
import com.document.domain.SearchCriteria;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

@Service("DocumentMetaService")
public class DocumentMetaServiceImpl implements DocumentMetaService
{
	@Autowired
	private DocumentMetaDAO documentMetaDAO;
	@Autowired
	private AuthorDAO authorDAO;
	

	public DocumentMeta getDocumentMeta(Long documentID)
	{
		
		return documentMetaDAO.findOne(documentID);
	}

	public DocumentMeta insertDocumentMeta(DocumentMeta document)
	{
		List<Author> matchedAuthors=authorDAO.findByLastNameAndFirstName(document.getAuthor().getLastName(),document.getAuthor().getFirstName());
		
		if(matchedAuthors.isEmpty())
		{
			authorDAO.save(document.getAuthor());
		}
		else 
		{
			document.getAuthor().setAutorID(matchedAuthors.get(0).getAutorID());
		}
		
		
		return documentMetaDAO.save(document);
	}
	
	public List<Long> searchDocumentMeta(SearchCriteria searchCriteria)
	{
		
		//Retrieve all records that match at least one of the criteria
		Iterable<DocumentMeta> list= documentMetaDAO.findAll(processSearchCriteriaPredicate(searchCriteria));
		
		ArrayList<Long> results = new ArrayList<Long>();
		
		for(Iterator<DocumentMeta> documentIter=list.iterator(); documentIter.hasNext(); )
		{
			results.add(documentIter.next().getDocumentID());
		}
		
		
		return results;
	}
	
	private Predicate processSearchCriteriaPredicate(SearchCriteria searchCriteria)
	{
		Predicate predicate;
		
		QDocumentMeta documentMeta= QDocumentMeta.documentMeta;
		BooleanExpression isNotNull= documentMeta.isNotNull();
		BooleanExpression fileNameMatch = documentMeta.name.like(searchCriteria.getFileName());
		BooleanExpression fileDescriptionMatch = documentMeta.description.like(searchCriteria.getFileDescription());
		BooleanExpression authorFirstNameMatch = documentMeta.author.firstName.equalsIgnoreCase(searchCriteria.getAuthorFirstName());
		BooleanExpression authorLastNameMatch = documentMeta.author.lastName.equalsIgnoreCase(searchCriteria.getAuthorLastName());
		
		predicate=isNotNull.andAnyOf(fileNameMatch, fileDescriptionMatch, authorFirstNameMatch, authorLastNameMatch);
		
		return predicate;
	}
}
