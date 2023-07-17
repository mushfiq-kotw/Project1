package com.example.demo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface UserDetailRepository extends CrudRepository<UserDetail, Long> {
	Optional<UserDetail> findById(long id);

}

