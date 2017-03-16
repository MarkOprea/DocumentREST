package com.document.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.document.domain.Document;
import com.document.domain.DocumentException;
import com.document.domain.DocumentMeta;

@Service("DocumentService")
public class DocumentServiceImpl implements DocumentService 
{
	
	private static final Logger log = LoggerFactory.getLogger(DocumentServiceImpl.class);
	@Autowired
	private DocumentMetaService documentMetaService;
	
	private Path datastorePath;
	
	@Autowired
    public DocumentServiceImpl(@Value("${com.document.DocumentStore}") String datastoreLocation) {
        datastorePath = Paths.get(datastoreLocation);
    }

	public Document getDocument(Long documentID) 
	{
		Document results= null;
		
		//Retrieve metadata and make sure it is there
		DocumentMeta documentMeta= documentMetaService.getDocumentMeta(documentID);
		if(documentMeta!= null)
		{
			results= new Document();
			results.setDocumentMeta(documentMeta);
			
			//get the file contents and add it to the object
			InputStream documentContents;
			try {
				documentContents = Files.newInputStream(datastorePath.resolve(documentMeta.getName()));
			} catch (IOException e) {
				log.error("Failed to Read the Document with ID: "+documentID,e);
				throw new DocumentException("Failed to Read the Document with ID: "+documentID,e);
			} 
			
			results.setDocumentContents(documentContents);
		}
		return results;
	}

	@Transactional
	public Document uploadDocument(Document document) 
	{
		//the path where the file will be saved needs to be set.
		document.getDocumentMeta().setLocation(datastorePath.resolve(document.getDocumentMeta().getName()).toString());
		
		//insert the metaData
		DocumentMeta associatedMeta=documentMetaService.insertDocumentMeta(document.getDocumentMeta());
		try {
			Files.copy(document.getDocumentContents(), datastorePath.resolve(associatedMeta.getName()));
		} catch (IOException e) {
			log.error("Failed to Store the Document to the datastore location.",e);
			throw new DocumentException("Failed to Store the Document to the datastore location.",e);
		}
		
		//make a new object and only add the metadata. returning the Input stream to the user would cause a lot of unnecessary network traffic.
		Document results= new Document();
		results.setDocumentMeta(associatedMeta);
		return results;
	}

}
