
package com.document.service;

import java.util.List;

import com.document.domain.DocumentMeta;
import com.document.domain.SearchCriteria;


public interface DocumentMetaService 
{
	
	/**
	 * Retrieves a document object along with the associated author based on ID.
	 * @param documentID
	 * @return Document
	 */
	public DocumentMeta getDocumentMeta(Long documentID);
	
	
	
	/**
	 * Inserts a new Document. It will try to match to existing authors before inserting a new one. Returns if it was successful or not.
	 * @param document
	 * @return DocumentMeta
	 */
	public DocumentMeta insertDocumentMeta(DocumentMeta document);
	
	/**
	 * searches for a list of IDs that satisfy the Crieteria
	 * @param document
	 * @return List<Long> 
	 */
	public List<Long> searchDocumentMeta(SearchCriteria searchCriteria);
	

}
