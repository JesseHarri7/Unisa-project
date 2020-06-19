package com.altHealth.repository;

import org.springframework.data.repository.CrudRepository;

import com.altHealth.entity.Reference;

public interface ReferenceRepo extends CrudRepository<Reference, String> {

	Reference findReferenceByReferenceId(String refId);
}
