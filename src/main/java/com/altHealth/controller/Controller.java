package com.altHealth.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface Controller<E, ID> {

		//Find by id
		@RequestMapping(value = "{id}", method = RequestMethod.GET)
		public E findById(@PathVariable ID id);
		
		//create
		@RequestMapping(value = "create", method = RequestMethod.POST)
		@ResponseStatus(HttpStatus.CREATED)
		public E create(@RequestBody E entity);
		
		//update
		@RequestMapping(value = "update", method = RequestMethod.PUT)
		@ResponseStatus(HttpStatus.OK)
		public void update(@RequestBody E entity);
		
		//Find All
		@RequestMapping(value = "findAll", method = RequestMethod.GET)
		public List<E> findAll();
		
		//Find All History
		@RequestMapping(value = "findAllHistory", method = RequestMethod.GET)
		public List<E> findAllHistory();
		
		//delete
		@RequestMapping(value = "delete/{id}", method = {RequestMethod.GET, RequestMethod.DELETE})
		@ResponseStatus(HttpStatus.OK)
		public void delete(@PathVariable("id") ID id);
	
}
