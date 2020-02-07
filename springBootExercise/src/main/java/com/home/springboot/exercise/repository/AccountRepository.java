package com.home.springboot.exercise.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.home.springboot.exercise.entity.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

}
