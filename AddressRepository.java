package com.example.demo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long>{
	Optional<Address> findById(long id);
}

