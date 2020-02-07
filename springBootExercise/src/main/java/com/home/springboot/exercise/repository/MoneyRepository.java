package com.home.springboot.exercise.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.home.springboot.exercise.entity.Money;

@Repository
public interface MoneyRepository extends CrudRepository<Money, Long> {

}
