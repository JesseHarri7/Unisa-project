package com.altHealth.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.altHealth.entity.Client;

public interface ClientRepo extends CrudRepository<Client, String> {

	List<Client> findClientByCName(String name);
	
    List<Client> findClientByCSurname(String surname);
    
    Client findClientByClientId(String clientId);
    
    @Query(value = "SELECT * FROM tblclient_info", nativeQuery = true)
   	List<Client> findClientAllHistory();
   	
   	@Query(value = "SELECT * FROM tblclient_info WHERE state = 'A'", nativeQuery = true)
   	List<Client> findClientAll();
   	
}
