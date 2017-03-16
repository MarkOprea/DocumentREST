package com.document.domain;

import java.io.Serializable;

import javax.persistence.Entity;

public class SearchCriteria implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5474704433187825976L;
	private String authorFirstName;
	private String authorLastName;
	private String fileName;
	private String fileDescription;
	
	public String getAuthorFirstName() {
		return authorFirstName;
	}
	public String getAuthorLastName() {
		return authorLastName;
	}
	public void setAuthorFirstName(String authorFirstName) {
		this.authorFirstName = authorFirstName;
	}
	public void setAuthorLastName(String authorLastName) {
		this.authorLastName = authorLastName;
	}
	public String getFileName() {
		return fileName;
	}
	public String getFileDescription() {
		return fileDescription;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public void setFileDescription(String fileDescription) {
		this.fileDescription = fileDescription;
	}
	

	
}
