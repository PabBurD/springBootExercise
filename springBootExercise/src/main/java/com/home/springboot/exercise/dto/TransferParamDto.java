package com.home.springboot.exercise.dto;

public class TransferParamDto {

	private Long originAccountId;
	private Long destinationAccountId;
	private Double amount;

	public Long getOriginAccountId() {
		return originAccountId;
	}

	public void setOriginAccountId(Long originAccountId) {
		this.originAccountId = originAccountId;
	}

	public Long getDestinationAccountId() {
		return destinationAccountId;
	}

	public void setDestinationAccountId(Long destinationAccountId) {
		this.destinationAccountId = destinationAccountId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
