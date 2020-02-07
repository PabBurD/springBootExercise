package com.home.springboot.exercise.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.home.springboot.exercise.dto.AccountDto;
import com.home.springboot.exercise.dto.TransferDto;
import com.home.springboot.exercise.dto.TransferParamDto;
import com.home.springboot.exercise.entity.Account;
import com.home.springboot.exercise.entity.Currency;
import com.home.springboot.exercise.entity.Money;
import com.home.springboot.exercise.interfaces.AccountApiService;
import com.home.springboot.exercise.mapper.AccountMapper;
import com.home.springboot.exercise.repository.AccountRepository;
import com.home.springboot.exercise.repository.CurrencyRepository;
import com.home.springboot.exercise.repository.MoneyRepository;


@RunWith(MockitoJUnitRunner.class)
public class AccountApiServiceImplTest {
	
	@InjectMocks
	private AccountApiService accountApiService = new AccountApiServiceImpl();
	
	@Mock
	private AccountRepository accountRepository;

	@Mock
	private CurrencyRepository currencyRepository;

	@Mock
	private MoneyRepository moneyRepository;

	@Mock
	private AccountMapper accountMapper;
	
	@Test
	public void testFindAccountSuccess() {

		// MOCKS

		Long id = 1L;
		Account account = new Account();
		Money balance = new Money();
		Currency currency = new Currency();

		balance.setBalance(100.0);
		currency.setId("EUR");

		account.setId(id);
		account.setName("account_test");
		account.setCurrency(currency);
		account.setBalance(balance);
		account.setTreasury(Boolean.TRUE);

		Mockito.when(accountRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(account));

		AccountDto accountDto = new AccountDto();

		accountDto.setId(account.getId());
		accountDto.setBalance(account.getBalance().getBalance());
		accountDto.setCurrency(account.getCurrency().getId());
		accountDto.setName(account.getName());
		accountDto.setTreasury(account.getTreasury());

		Mockito.when(accountMapper.convertToDto(ArgumentMatchers.any())).thenReturn(accountDto);

		// TEST
		AccountDto response = this.accountApiService.findAccount(id);

		// VERIFIES

		assertNotNull(response);
		assertEquals(accountDto.getId(), response.getId());
		assertEquals(accountDto.getBalance(), response.getBalance());
		assertEquals(accountDto.getCurrency(), response.getCurrency());
		assertEquals(accountDto.getName(), response.getName());
		assertEquals(accountDto.getTreasury(), response.getTreasury());

	}
	
	@Test
	public void testFindAccountNotFound() {

		// MOCKS

		Long id = 2L;

		// TEST
		AccountDto response = this.accountApiService.findAccount(id);

		// VERIFIES

		assertNull(response);

	}

	@Test
	public void testCreateAccountSuccess() {

		// MOCKS

		Long id = 1L;
		Account account = new Account();
		Money balance = new Money();
		Currency currency = new Currency();

		balance.setBalance(100.0);
		currency.setId("EUR");

		Mockito.when(this.currencyRepository.findById(ArgumentMatchers.anyString())).thenReturn(Optional.of(currency));
		Mockito.when(this.moneyRepository.save(ArgumentMatchers.any())).thenReturn(balance);

		account.setId(id);
		account.setName("account_test");
		account.setCurrency(currency);
		account.setBalance(balance);
		account.setTreasury(Boolean.TRUE);

		Mockito.when(accountRepository.save(ArgumentMatchers.any())).thenReturn(account);
		
		Mockito.when(accountMapper.convertToEntity(ArgumentMatchers.any())).thenReturn(account);

		AccountDto accountDto = new AccountDto();

		accountDto.setId(account.getId());
		accountDto.setBalance(account.getBalance().getBalance());
		accountDto.setCurrency(account.getCurrency().getId());
		accountDto.setName(account.getName());
		accountDto.setTreasury(account.getTreasury());

		Mockito.when(accountMapper.convertToDto(ArgumentMatchers.any())).thenReturn(accountDto);

		AccountDto accountParamDto = new AccountDto();

		accountParamDto.setId(null);
		accountParamDto.setBalance(account.getBalance().getBalance());
		accountParamDto.setCurrency(account.getCurrency().getId());
		accountParamDto.setName(account.getName());
		accountParamDto.setTreasury(account.getTreasury());

		// TEST
		AccountDto response = this.accountApiService.createAccount(accountParamDto);

		// VERIFIES

		assertNotNull(response);
		assertEquals(accountDto.getId(), response.getId());
		assertEquals(accountDto.getBalance(), response.getBalance());
		assertEquals(accountDto.getCurrency(), response.getCurrency());
		assertEquals(accountDto.getName(), response.getName());
		assertEquals(accountDto.getTreasury(), response.getTreasury());

	}

	@Test
	public void testCreateAccountCurrencyNotFound() {

		// MOCKS
		Currency currency = new Currency();
		Money balance = new Money();
		balance.setBalance(100.0);

		currency.setId("WRONG");

		AccountDto accountParamDto = new AccountDto();

		accountParamDto.setId(null);
		accountParamDto.setBalance(balance.getBalance());

		// TEST
		AccountDto response = this.accountApiService.createAccount(accountParamDto);

		// VERIFIES

		assertNull(response);

	}

	@Test
	public void testUpdateAccountSuccess() {

		// MOCKS

		Long id = 1L;
		Account account = new Account();
		Money balance = new Money();
		Currency currency = new Currency();

		balance.setBalance(100.0);
		currency.setId("EUR");

		account.setId(id);
		account.setName("account_test");
		account.setCurrency(currency);
		account.setBalance(balance);
		account.setTreasury(Boolean.TRUE);

		Mockito.when(accountRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(account));
		Mockito.when(this.currencyRepository.findById(ArgumentMatchers.anyString())).thenReturn(Optional.of(currency));

		AccountDto accountParamDto = new AccountDto();

		accountParamDto.setId(account.getId());
		accountParamDto.setBalance(200.0);
		accountParamDto.setCurrency("USD");
		accountParamDto.setName("updatedName");

		account.getBalance().setBalance(accountParamDto.getBalance());
		account.getCurrency().setId(accountParamDto.getCurrency());
		account.setName(accountParamDto.getName());

		Mockito.when(accountMapper.convertToEntity(ArgumentMatchers.any())).thenReturn(account);

		AccountDto accountDto = new AccountDto();

		accountDto.setId(account.getId());
		accountDto.setBalance(account.getBalance().getBalance());
		accountDto.setCurrency(account.getCurrency().getId());
		accountDto.setName(account.getName());
		accountDto.setTreasury(account.getTreasury());

		Mockito.when(accountMapper.convertToDto(ArgumentMatchers.any())).thenReturn(accountDto);

		// TEST
		AccountDto response = this.accountApiService.updateAccount(id, accountParamDto);

		// VERIFIES

		assertNotNull(response);
		assertEquals(accountDto.getId(), response.getId());
		assertEquals(accountDto.getBalance(), response.getBalance());
		assertEquals(accountDto.getCurrency(), response.getCurrency());
		assertEquals(accountDto.getName(), response.getName());
		assertEquals(accountDto.getTreasury(), response.getTreasury());

	}

	@Test
	public void testUpdateAccountNotFound() {

		// MOCKS

		Long id = 2L;
		Currency currency = new Currency();
		currency.setId("EUR");

		AccountDto accountParamDto = new AccountDto();

		accountParamDto.setId(id);
		accountParamDto.setBalance(200.0);
		accountParamDto.setCurrency("USD");

		Mockito.when(this.currencyRepository.findById(ArgumentMatchers.anyString())).thenReturn(Optional.of(currency));

		// TEST
		AccountDto response = this.accountApiService.updateAccount(id, accountParamDto);

		// VERIFIES

		assertNull(response);

	}

	@Test
	public void testUpdateCurrencyNotFound() {

		// MOCKS

		Long id = 2L;
		Currency currency = new Currency();
		currency.setId("WRONG");

		Account account = new Account();
		Money balance = new Money();

		balance.setBalance(100.0);

		account.setId(id);
		account.setName("account_test");
		account.setCurrency(currency);
		account.setBalance(balance);
		account.setTreasury(Boolean.TRUE);

		Mockito.when(accountRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(account));

		AccountDto accountParamDto = new AccountDto();

		accountParamDto.setId(id);
		accountParamDto.setBalance(200.0);
		accountParamDto.setCurrency("USD");

		// TEST
		AccountDto response = this.accountApiService.updateAccount(id, accountParamDto);

		// VERIFIES

		assertNull(response);

	}

	@Test
	public void testdoMoneyTransferSuccess() {

		// MOCKS

		Long idOrigin = 1L;
		Long idDest = 2L;
		Double amount = 50.0;
		
		TransferParamDto transferParamDto = new TransferParamDto();
		
		transferParamDto.setOriginAccountId(idOrigin);
		transferParamDto.setDestinationAccountId(idDest);
		transferParamDto.setAmount(amount);
		
		Account accountOrigin = new Account();
		Account accountDest = new Account();
		Money balanceOrigin = new Money();
		Money balanceDest = new Money();
		
		balanceOrigin.setBalance(100.0);
		balanceDest.setBalance(500.0);
		
		Currency currency = new Currency();
		
		currency.setId("EUR");

		accountOrigin.setId(idOrigin);
		accountOrigin.setName("account_origin");
		accountOrigin.setCurrency(currency);
		accountOrigin.setBalance(balanceOrigin);
		accountOrigin.setTreasury(Boolean.TRUE);
		
		accountDest.setId(idDest);
		accountDest.setName("account_dest");
		accountDest.setCurrency(currency);
		accountDest.setBalance(balanceDest);
		accountDest.setTreasury(Boolean.FALSE);

		Mockito.when(accountRepository.findById(idOrigin)).thenReturn(Optional.of(accountOrigin));
		Mockito.when(accountRepository.findById(idDest)).thenReturn(Optional.of(accountDest));

		AccountDto accountOriginDto = new AccountDto();

		accountOriginDto.setId(accountOrigin.getId());
		accountOriginDto.setBalance(accountOrigin.getBalance().getBalance() - amount);
		accountOriginDto.setCurrency(accountOrigin.getCurrency().getId());
		accountOriginDto.setName(accountOrigin.getName());
		
		AccountDto accountDestDto = new AccountDto();

		accountDestDto.setId(accountDest.getId());
		accountDestDto.setBalance(accountDest.getBalance().getBalance() + amount);
		accountDestDto.setCurrency(accountDest.getCurrency().getId());
		accountDestDto.setName(accountDest.getName());
		
		Mockito.when(accountMapper.convertToDto(accountOrigin)).thenReturn(accountOriginDto);
		Mockito.when(accountMapper.convertToDto(accountDest)).thenReturn(accountDestDto);


		// TEST
		TransferDto response = this.accountApiService.doMoneyTransfer(transferParamDto);

		// VERIFIES

		assertNotNull(response);
		assertEquals(accountOriginDto.getBalance(), response.getAccountDtoOrigin().getBalance());
		assertEquals(accountDestDto.getBalance(), response.getAccountDtoDestination().getBalance());
		assertEquals(amount, response.getAmount());
	}
	
	@Test
	public void testdoMoneyTransferOriginNull() {

		// MOCKS

		Long idOrigin = 1L;
		Long idDest = 2L;
		Double amount = 50.0;
		
		TransferParamDto transferParamDto = new TransferParamDto();
		
		transferParamDto.setOriginAccountId(idOrigin);
		transferParamDto.setDestinationAccountId(idDest);
		transferParamDto.setAmount(amount);
		
		Account accountDest = new Account();
		Money balanceOrigin = new Money();
		Money balanceDest = new Money();
		
		balanceOrigin.setBalance(100.0);
		balanceDest.setBalance(500.0);
		
		Currency currency = new Currency();
		
		currency.setId("EUR");
		
		accountDest.setId(idDest);
		accountDest.setName("account_dest");
		accountDest.setCurrency(currency);
		accountDest.setBalance(balanceDest);
		accountDest.setTreasury(Boolean.FALSE);

		Mockito.when(accountRepository.findById(idDest)).thenReturn(Optional.of(accountDest));
		
		
		AccountDto accountDestDto = new AccountDto();

		accountDestDto.setId(accountDest.getId());
		accountDestDto.setBalance(accountDest.getBalance().getBalance() + amount);
		accountDestDto.setCurrency(accountDest.getCurrency().getId());
		accountDestDto.setName(accountDest.getName());


		// TEST
		TransferDto response = this.accountApiService.doMoneyTransfer(transferParamDto);

		// VERIFIES

		assertNull(response);
		
	}
	
	@Test
	public void testdoMoneyTransferDestinationNull() {

		// MOCKS

		Long idOrigin = 1L;
		Long idDest = 2L;
		Double amount = 50.0;
		
		TransferParamDto transferParamDto = new TransferParamDto();
		
		transferParamDto.setOriginAccountId(idOrigin);
		transferParamDto.setDestinationAccountId(idDest);
		transferParamDto.setAmount(amount);
		
		Account accountOrigin = new Account();
		Money balanceOrigin = new Money();
		Money balanceDest = new Money();
		
		balanceOrigin.setBalance(100.0);
		balanceDest.setBalance(500.0);
		
		Currency currency = new Currency();
		
		currency.setId("EUR");

		accountOrigin.setId(idOrigin);
		accountOrigin.setName("account_origin");
		accountOrigin.setCurrency(currency);
		accountOrigin.setBalance(balanceOrigin);
		accountOrigin.setTreasury(Boolean.TRUE);

		Mockito.when(accountRepository.findById(idOrigin)).thenReturn(Optional.of(accountOrigin));

		// TEST
		TransferDto response = this.accountApiService.doMoneyTransfer(transferParamDto);

		// VERIFIES

		assertNull(response);
	}
	
	@Test
	public void testdoMoneyTransferBalanceLessThanZeroWithTreasuryAccount() {

		// MOCKS

		Long idOrigin = 1L;
		Long idDest = 2L;
		Double amount = 250.0;
		
		TransferParamDto transferParamDto = new TransferParamDto();
		
		transferParamDto.setOriginAccountId(idOrigin);
		transferParamDto.setDestinationAccountId(idDest);
		transferParamDto.setAmount(amount);
		
		Account accountOrigin = new Account();
		Account accountDest = new Account();
		Money balanceOrigin = new Money();
		Money balanceDest = new Money();
		
		balanceOrigin.setBalance(100.0);
		balanceDest.setBalance(500.0);
		
		Currency currency = new Currency();
		
		currency.setId("EUR");

		accountOrigin.setId(idOrigin);
		accountOrigin.setName("account_origin");
		accountOrigin.setCurrency(currency);
		accountOrigin.setBalance(balanceOrigin);
		accountOrigin.setTreasury(Boolean.TRUE);
		
		accountDest.setId(idDest);
		accountDest.setName("account_dest");
		accountDest.setCurrency(currency);
		accountDest.setBalance(balanceDest);
		accountDest.setTreasury(Boolean.FALSE);

		Mockito.when(accountRepository.findById(idOrigin)).thenReturn(Optional.of(accountOrigin));
		Mockito.when(accountRepository.findById(idDest)).thenReturn(Optional.of(accountDest));

		AccountDto accountOriginDto = new AccountDto();

		accountOriginDto.setId(accountOrigin.getId());
		accountOriginDto.setBalance(accountOrigin.getBalance().getBalance() - amount);
		accountOriginDto.setCurrency(accountOrigin.getCurrency().getId());
		accountOriginDto.setName(accountOrigin.getName());
		
		AccountDto accountDestDto = new AccountDto();

		accountDestDto.setId(accountDest.getId());
		accountDestDto.setBalance(accountDest.getBalance().getBalance() + amount);
		accountDestDto.setCurrency(accountDest.getCurrency().getId());
		accountDestDto.setName(accountDest.getName());
		
		Mockito.when(accountMapper.convertToDto(accountOrigin)).thenReturn(accountOriginDto);
		Mockito.when(accountMapper.convertToDto(accountDest)).thenReturn(accountDestDto);


		// TEST
		TransferDto response = this.accountApiService.doMoneyTransfer(transferParamDto);

		// VERIFIES

		assertNotNull(response);
		assertEquals(accountOriginDto.getBalance(), response.getAccountDtoOrigin().getBalance());
		assertEquals(accountDestDto.getBalance(), response.getAccountDtoDestination().getBalance());
		assertEquals(amount, response.getAmount());
	}
	
	@Test
	public void testdoMoneyTransferBalanceLessThanZeroWithNoTreasuryAccount() {

		// MOCKS

		Long idOrigin = 1L;
		Long idDest = 2L;
		Double amount = 250.0;
		
		TransferParamDto transferParamDto = new TransferParamDto();
		
		transferParamDto.setOriginAccountId(idOrigin);
		transferParamDto.setDestinationAccountId(idDest);
		transferParamDto.setAmount(amount);
		
		Account accountOrigin = new Account();
		Account accountDest = new Account();
		Money balanceOrigin = new Money();
		Money balanceDest = new Money();
		
		balanceOrigin.setBalance(100.0);
		balanceDest.setBalance(500.0);
		
		Currency currency = new Currency();
		
		currency.setId("EUR");

		accountOrigin.setId(idOrigin);
		accountOrigin.setName("account_origin");
		accountOrigin.setCurrency(currency);
		accountOrigin.setBalance(balanceOrigin);
		accountOrigin.setTreasury(Boolean.FALSE);
		
		accountDest.setId(idDest);
		accountDest.setName("account_dest");
		accountDest.setCurrency(currency);
		accountDest.setBalance(balanceDest);
		accountDest.setTreasury(Boolean.TRUE);

		Mockito.when(accountRepository.findById(idOrigin)).thenReturn(Optional.of(accountOrigin));
		Mockito.when(accountRepository.findById(idDest)).thenReturn(Optional.of(accountDest));


		// TEST
		TransferDto response = this.accountApiService.doMoneyTransfer(transferParamDto);

		// VERIFIES
		assertNull(response);
		
	}
	
	@Test
	public void testdoMoneyTransferZeroBalance() {

		// MOCKS

		Long idOrigin = 1L;
		Long idDest = 2L;
		Double amount = 100.0;
		
		TransferParamDto transferParamDto = new TransferParamDto();
		
		transferParamDto.setOriginAccountId(idOrigin);
		transferParamDto.setDestinationAccountId(idDest);
		transferParamDto.setAmount(amount);
		
		Account accountOrigin = new Account();
		Account accountDest = new Account();
		Money balanceOrigin = new Money();
		Money balanceDest = new Money();
		
		balanceOrigin.setBalance(100.0);
		balanceDest.setBalance(500.0);
		
		Currency currency = new Currency();
		
		currency.setId("EUR");

		accountOrigin.setId(idOrigin);
		accountOrigin.setName("account_origin");
		accountOrigin.setCurrency(currency);
		accountOrigin.setBalance(balanceOrigin);
		accountOrigin.setTreasury(Boolean.TRUE);
		
		accountDest.setId(idDest);
		accountDest.setName("account_dest");
		accountDest.setCurrency(currency);
		accountDest.setBalance(balanceDest);
		accountDest.setTreasury(Boolean.FALSE);

		Mockito.when(accountRepository.findById(idOrigin)).thenReturn(Optional.of(accountOrigin));
		Mockito.when(accountRepository.findById(idDest)).thenReturn(Optional.of(accountDest));

		AccountDto accountOriginDto = new AccountDto();

		accountOriginDto.setId(accountOrigin.getId());
		accountOriginDto.setBalance(accountOrigin.getBalance().getBalance() - amount);
		accountOriginDto.setCurrency(accountOrigin.getCurrency().getId());
		accountOriginDto.setName(accountOrigin.getName());
		
		AccountDto accountDestDto = new AccountDto();

		accountDestDto.setId(accountDest.getId());
		accountDestDto.setBalance(accountDest.getBalance().getBalance() + amount);
		accountDestDto.setCurrency(accountDest.getCurrency().getId());
		accountDestDto.setName(accountDest.getName());
		
		Mockito.when(accountMapper.convertToDto(accountOrigin)).thenReturn(accountOriginDto);
		Mockito.when(accountMapper.convertToDto(accountDest)).thenReturn(accountDestDto);


		// TEST
		TransferDto response = this.accountApiService.doMoneyTransfer(transferParamDto);

		// VERIFIES

		assertNotNull(response);
		assertEquals(accountOriginDto.getBalance(), response.getAccountDtoOrigin().getBalance());
		assertEquals(accountDestDto.getBalance(), response.getAccountDtoDestination().getBalance());
		assertEquals(amount, response.getAmount());
	}

}
