package com.document.web;


import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.document.domain.Document;
import com.document.domain.DocumentException;
import com.document.domain.DocumentMeta;
import com.document.domain.SearchCriteria;
import com.document.service.DocumentMetaService;
import com.document.service.DocumentService;

@RestController
public class DocumentMetaController 
{
	private static final Logger log = LoggerFactory.getLogger(DocumentMetaController.class);
	@Value("${com.document.DocumentStore}")
	private String datastorePath;
	
	@Autowired
	private DocumentMetaService documentMetaService;
	@Autowired
	private DocumentService documentService;

	
	@RequestMapping(value = "/Document/{documentID}/DocumentMeta", method= RequestMethod.GET )
    public DocumentMeta getDocumentMeta(@PathVariable(value="documentID") Long documentID) 
	{
		DocumentMeta result = documentMetaService.getDocumentMeta(documentID);
        return result;
    }
	
	@RequestMapping(value = "/Document/{documentID}", method= RequestMethod.GET )
    public void getDocument(@PathVariable(value="documentID") Long documentID, HttpServletResponse response) 
	{
		Document returnDocument= documentService.getDocument(documentID);
		
		if(returnDocument!=null)
		{
			response.addHeader("Content-disposition", "attachment;filename="+returnDocument.getDocumentMeta().getName());
			try {
				IOUtils.copy(returnDocument.getDocumentContents(), response.getOutputStream());
				response.flushBuffer();
			} catch (IOException e) {
				log.error("An error occured when trying to get DOcument with ID: "+documentID,e);
				throw new DocumentException("An error occured when trying to get DOcument with ID: "+documentID,e);
			}
		}
		else
		{
			response.setStatus(404);
		}
    }
	
	@RequestMapping(value = "/Document", method= RequestMethod.POST )
    public Document putDocument(@RequestPart(value = "json") DocumentMeta documentMeta,
            @RequestParam(value = "data", required = true) MultipartFile file) 
	{
		Document newDocument= new Document();
		newDocument.setDocumentMeta(documentMeta);
		try {
			
			newDocument.setDocumentContents(file.getInputStream());
		} catch (IOException e) {
			log.error("Failed to read the attached document.",e);
			throw new DocumentException("Failed to read the attached document.",e);
		}
		
		Document result=documentService.uploadDocument(newDocument);
        return result;
    }
	
	@RequestMapping(value = "/Document/Search", method= RequestMethod.POST )
    public Long[] searchForDocumentIDs(@RequestBody SearchCriteria searchCriteria) 
	{
		List<Long> results = documentMetaService.searchDocumentMeta(searchCriteria);
        return results.toArray(new Long[]{});
    }
	
	
	
	@ExceptionHandler(RuntimeException.class)
    public ResponseEntity handleStorageFileNotFound(RuntimeException e) {
		log.error("An error occured:",e);
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
