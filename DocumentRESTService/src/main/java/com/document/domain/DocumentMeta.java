package com.document.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class DocumentMeta implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2448811573489341660L;
	private Long documentID;
	private Author author;
	private String name;
	private String location;
	private String description;
	private Date uploadTImestamp;
	
	public DocumentMeta() {}
	
	@Id
	@Column(name = "documentID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getDocumentID() 
	{
		return documentID;
	}
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "authorID", nullable = false)
	public Author getAuthor() 
	{
		return author;
	}
	
	@Column(name = "name", nullable = false)
	public String getName() 
	{
		return name;
	}
	
	@Column(name = "location", nullable = false)
	public String getLocation() 
	{
		return location;
	}
	
	@Column(name = "description")
	public String getDescription() 
	{
		return description;
	}
	
	@JsonIgnore
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "uploadTImestamp", nullable = false)
	public Date getUploadTImestamp() 
	{
		return uploadTImestamp;
	}
	
	public void setDocumentID(Long documentID) 
	{
		this.documentID = documentID;
	}
	public void setAuthor(Author author) 
	{
		this.author = author;
	}
	public void setName(String name) 
	{
		this.name = name;
	}
	public void setLocation(String location) 
	{
		this.location = location;
	}
	public void setDescription(String description) 
	{
		this.description = description;
	}
	public void setUploadTImestamp(Date uploadTImestamp) 
	{
		this.uploadTImestamp = uploadTImestamp;
	}
	
	

}
