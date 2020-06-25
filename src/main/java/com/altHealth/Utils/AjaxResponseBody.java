package com.altHealth.Utils;

import java.util.List;

import com.altHealth.entity.Views;
import com.fasterxml.jackson.annotation.JsonView;

public class AjaxResponseBody {
	
	@JsonView(Views.Public.class)
	String msg;

	@JsonView(Views.Public.class)
	String status;
	
	@JsonView(Views.Public.class)
	Object result;
	
	@JsonView(Views.Public.class)
	List<Object> resultList;
	
	@JsonView(Views.Public.class)
	List<String> idTags;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public List<Object> getResultList() {
		return resultList;
	}

	public void setResultList(List<Object> resultList) {
		this.resultList = resultList;
	}

	public List<String> getIdTags() {
		return idTags;
	}

	public void setIdTags(List<String> idTags) {
		this.idTags = idTags;
	}

}
