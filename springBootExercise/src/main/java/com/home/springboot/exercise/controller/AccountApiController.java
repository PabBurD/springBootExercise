package com.home.springboot.exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.home.springboot.exercise.dto.AccountDto;
import com.home.springboot.exercise.dto.TransferDto;
import com.home.springboot.exercise.dto.TransferParamDto;
import com.home.springboot.exercise.interfaces.AccountApiService;

@RestController
@RequestMapping("/accountapi")
public class AccountApiController {

	@Autowired
	private AccountApiService accountApiService;

	@GetMapping("/account/{id}")
	public ResponseEntity<AccountDto> findAccount(@PathVariable Long id) {

		ResponseEntity<AccountDto> response = null;

		AccountDto accountDto = this.accountApiService.findAccount(id);

		if (accountDto != null) {
			response = new ResponseEntity<>(accountDto, HttpStatus.OK);
		} else {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return response;

	}

	@GetMapping("/account")
	public ResponseEntity<AccountDto> createAccount(@RequestBody AccountDto body) {

		ResponseEntity<AccountDto> response = null;

		AccountDto accountDto = this.accountApiService.createAccount(body);

		if (accountDto != null) {
			response = new ResponseEntity<>(accountDto, HttpStatus.CREATED);
		} else {
			response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return response;

	}

	@GetMapping("/account/{id}")
	public ResponseEntity<AccountDto> updateAccount(@PathVariable Long id, @RequestBody AccountDto body) {

		ResponseEntity<AccountDto> response = null;

		AccountDto accountDto = this.accountApiService.updateAccount(id, body);

		if (accountDto != null) {
			response = new ResponseEntity<>(accountDto, HttpStatus.ACCEPTED);
		} else {
			response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return response;

	}

	@GetMapping("/transfer")
	public ResponseEntity<TransferDto> doMoneyTransfer(@RequestBody TransferParamDto body) {

		ResponseEntity<TransferDto> response = null;

		TransferDto transferDto = this.accountApiService.doMoneyTransfer(body);

		if (transferDto != null) {
			response = new ResponseEntity<>(transferDto, HttpStatus.ACCEPTED);
		} else {
			response = new ResponseEntity<>(HttpStatus.LOCKED);
		}

		return response;

	}

}
