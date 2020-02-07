package com.home.springboot.exercise.dto;

public class TransferDto {

	private AccountDto accountDtoOrigin;
	private AccountDto accountDtoDestination;
	private Double amount;

	public AccountDto getAccountDtoOrigin() {
		return accountDtoOrigin;
	}

	public void setAccountDtoOrigin(AccountDto accountDtoOrigin) {
		this.accountDtoOrigin = accountDtoOrigin;
	}

	public AccountDto getAccountDtoDestination() {
		return accountDtoDestination;
	}

	public void setAccountDtoDestination(AccountDto accountDtoDestination) {
		this.accountDtoDestination = accountDtoDestination;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
