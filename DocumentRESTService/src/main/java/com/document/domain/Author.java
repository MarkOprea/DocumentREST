package com.document.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Author implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8964398613891705437L;
	private Long autorID;
	private String firstName;
	private String lastName;
	
	protected Author(){}
	
	@Id
	@Column(name = "authorID")
	@GeneratedValue(strategy=GenerationType.AUTO)
	public Long getAutorID() 
	{
		return autorID;
	}
	
	@Column(name = "firstName", nullable = false)
	public String getFirstName() 
	{
		return firstName;
	}
	
	@Column(name = "lastName", nullable = false)
	public String getLastName() 
	{
		return lastName;
	}

	public void setAutorID(Long autorID) {
		this.autorID = autorID;
	}

	public void setFirstName(String firstName) 
	{
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName) 
	{
		this.lastName = lastName;
	}
	
	

}
