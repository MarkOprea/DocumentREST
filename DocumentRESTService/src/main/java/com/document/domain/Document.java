package com.document.domain;

import java.io.InputStream;
import java.io.Serializable;

public class Document implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -8596002803769889865L;
	private DocumentMeta documentMeta;
	private InputStream documentContents;


	public InputStream getDocumentContents() {
		return documentContents;
	}

	public void setDocumentContents(InputStream documentContents) {
		this.documentContents = documentContents;
	}

	public DocumentMeta getDocumentMeta() {
		return documentMeta;
	}

	public void setDocumentMeta(DocumentMeta documentMeta) {
		this.documentMeta = documentMeta;
	}

}
