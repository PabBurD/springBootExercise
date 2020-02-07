package com.home.springboot.exercise.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import com.home.springboot.exercise.dto.AccountDto;
import com.home.springboot.exercise.dto.TransferDto;
import com.home.springboot.exercise.dto.TransferParamDto;
import com.home.springboot.exercise.entity.Account;
import com.home.springboot.exercise.repository.AccountRepository;


@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class AccountApiControllerCompTest {
	
	@Autowired
	private AccountApiController accountApiController;
	
	@Autowired
	private AccountRepository accountRepository;
	
	@Test
	@Sql({ "/scripts/account.sql" })
	public void testFindAccountSuccess() {

		Long id = 1L;

		ResponseEntity<AccountDto> response = this.accountApiController.findAccount(id);

		assertNotNull(response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());

	}

	@Test
	@Sql({ "/scripts/account.sql" })
	public void testFindAccountNotFound() {

		Long id = 2L;

		ResponseEntity<AccountDto> response = this.accountApiController.findAccount(id);

		assertNull(response.getBody());
		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

	}

	@Test
	public void testCreateAccountSuccess() {
		AccountDto accountDto = new AccountDto();

		accountDto.setBalance(1000.0);
		accountDto.setCurrency("EUR");
		accountDto.setName("account_test");
		accountDto.setTreasury(Boolean.FALSE);

		ResponseEntity<AccountDto> response = this.accountApiController.createAccount(accountDto);

		assertNotNull(response.getBody());
		assertEquals(Long.valueOf(1L), response.getBody().getId());
		assertEquals(accountDto.getCurrency(), response.getBody().getCurrency());
		assertEquals(accountDto.getName(), response.getBody().getName());
		assertEquals(accountDto.getTreasury(), response.getBody().getTreasury());
		assertEquals(HttpStatus.CREATED, response.getStatusCode());

	}

	@Test
	public void testCreateAccountFailed() {
		AccountDto accountDto = new AccountDto();

		accountDto.setBalance(1000.0);
		accountDto.setCurrency("WRONG");
		accountDto.setName("account_test");
		accountDto.setTreasury(Boolean.FALSE);

		ResponseEntity<AccountDto> response = this.accountApiController.createAccount(accountDto);

		assertNull(response.getBody());
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

	}

	@Test
	@Sql({ "/scripts/account.sql" })
	public void testUpdateAccountSuccess() {

		AccountDto accountDto = new AccountDto();

		Long id = 1L;
		accountDto.setId(id);
		accountDto.setBalance(9999.0);
		accountDto.setCurrency("USD");
		accountDto.setName("account_test_updated");
		accountDto.setTreasury(Boolean.FALSE);

		ResponseEntity<AccountDto> response = this.accountApiController.updateAccount(id, accountDto);

		assertNotNull(response.getBody());
		assertEquals(Long.valueOf(1L), response.getBody().getId());
		assertEquals(accountDto.getCurrency(), response.getBody().getCurrency());
		assertEquals(accountDto.getName(), response.getBody().getName());
		assertEquals(Boolean.TRUE, response.getBody().getTreasury());
		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());

	}

	@Test
	@Sql({ "/scripts/account.sql" })
	public void testUpdateAccountFailed() {
		AccountDto accountDto = new AccountDto();

		Long id = 2L;
		accountDto.setId(id);
		accountDto.setBalance(9999.0);
		accountDto.setCurrency("USD");
		accountDto.setName("account_test_updated");
		accountDto.setTreasury(Boolean.FALSE);

		ResponseEntity<AccountDto> response = this.accountApiController.updateAccount(id, accountDto);

		assertNull(response.getBody());
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	@Sql({ "/scripts/transfer.sql" })
	public void testDoMoneyTransferSuccess() {

		TransferParamDto transferParam = new TransferParamDto();

		Long idOrigin = 1L;
		Long idDest = 2L;
		Double amount = 100.0;

		transferParam.setOriginAccountId(idOrigin);
		transferParam.setDestinationAccountId(idDest);
		transferParam.setAmount(amount);

		Account origin = this.accountRepository.findById(idOrigin).orElse(null);
		Account destination = this.accountRepository.findById(idDest).orElse(null);

		ResponseEntity<TransferDto> response = this.accountApiController.doMoneyTransfer(transferParam);

		assertNotNull(response.getBody());
		assertEquals(Double.valueOf(origin.getBalance().getBalance() - amount),
				response.getBody().getAccountDtoOrigin().getBalance());
		assertEquals(Double.valueOf(destination.getBalance().getBalance() + amount),
				response.getBody().getAccountDtoDestination().getBalance());
		assertEquals(Double.valueOf(amount), response.getBody().getAmount());
		assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());

	}
	
	@Test
	public void testDoMoneyTransferFailed() {
		
		TransferParamDto transferParam = new TransferParamDto();

		Long idOrigin = 1L;
		Long idDest = 2L;
		Double amount = 100.0;

		transferParam.setOriginAccountId(idOrigin);
		transferParam.setDestinationAccountId(idDest);
		transferParam.setAmount(amount);

		ResponseEntity<TransferDto> response = this.accountApiController.doMoneyTransfer(transferParam);

		assertNull(response.getBody());
		assertEquals(HttpStatus.LOCKED, response.getStatusCode());
		
	}

}
