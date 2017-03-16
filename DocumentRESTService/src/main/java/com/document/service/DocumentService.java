package com.document.service;

import com.document.domain.Document;

public interface DocumentService 
{
	
	/**
	 * @param documentID
	 * @return the document with the given ID
	 */
	public Document getDocument(Long documentID);
	
	/**
	 * @param document
	 * @return the uploaded document object without the file
	 */
	public Document uploadDocument(Document document);

}
