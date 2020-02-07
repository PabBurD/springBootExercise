package com.home.springboot.exercise.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.home.springboot.exercise.entity.Currency;

@Repository
public interface CurrencyRepository extends CrudRepository<Currency, String> {

}
