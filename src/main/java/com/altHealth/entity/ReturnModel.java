package com.altHealth.entity;

import java.util.List;
import java.util.stream.Collectors;

public class ReturnModel {

	Object entity;
	List<String> errorList;
	List<String> idTags;
	
	
	public ReturnModel() {
	}

	public ReturnModel(Object entity, List<String> errorList) {
		this.entity = entity;
		this.errorList = errorList;
	}

	public Object getEntity() {
		return entity;
	}

	public void setEntity(Object entity) {
		this.entity = entity;
	}

	public List<String> getErrorList() {
		return errorList;
	}

	public void setErrorList(List<String> errorList) {
		this.errorList = errorList;
	}
	
	public String getStringErrorList() {
		String errors = "";
		for(String e : errorList) {
			errors = errors + e + ", ";
		}
		return errors;
	}

	public List<String> getIdTags() {
		List<String> uniqueTags = idTags.stream().distinct().collect(Collectors.toList());
		
		return uniqueTags;
	}

	public void setIdTags(List<String> idTags) {
		this.idTags = idTags;
	}
	
}
