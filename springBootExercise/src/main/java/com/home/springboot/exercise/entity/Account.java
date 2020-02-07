package com.home.springboot.exercise.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Account {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Long id;
	
	private String name;
	
	@ManyToOne
	@JoinColumn(name = "currency_id")
	private Currency currency;
	
	@ManyToOne
	@JoinColumn(name = "balance_id")
	private Money balance;
	
	private Boolean treasury;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Currency getCurrency() {
		return currency;
	}

	public void setCurrency(Currency currency) {
		this.currency = currency;
	}

	public Money getBalance() {
		return balance;
	}

	public void setBalance(Money balance) {
		this.balance = balance;
	}

	public Boolean getTreasury() {
		return treasury;
	}

	public void setTreasury(Boolean treasury) {
		this.treasury = treasury;
	}
	

}
