package com.home.springboot.exercise.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

@Service
public class AccountApiServiceImpl implements AccountApiService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private CurrencyRepository currencyRepository;

	@Autowired
	private MoneyRepository moneyRepository;

	@Autowired
	private AccountMapper accountMapper;

	@Override
	@Transactional
	public AccountDto findAccount(Long id) {

		Account account = this.accountRepository.findById(id).orElse(null);

		AccountDto accountDto = null;

		if (account != null) {
			accountDto = this.accountMapper.convertToDto(account);
		}

		return accountDto;
	}

	@Override
	@Transactional
	public AccountDto createAccount(AccountDto accountDto) {

		Money balance = new Money();

		balance.setBalance(accountDto.getBalance());

		balance = this.moneyRepository.save(balance);

		Currency currency = this.currencyRepository.findById(accountDto.getCurrency()).orElse(null);

		AccountDto response = null;

		if (currency != null) {
			Account accountToSave = this.accountMapper.convertToEntity(accountDto);

			accountToSave.setBalance(balance);

			Account accountSaved = this.accountRepository.save(accountToSave);

			response = this.accountMapper.convertToDto(accountSaved);
		}

		return response;
	}

	@Override
	@Transactional
	public AccountDto updateAccount(Long id, AccountDto accountDto) {

		Account accountToUpdate = this.accountRepository.findById(id).orElse(null);

		Currency currency = this.currencyRepository.findById(accountDto.getCurrency()).orElse(null);

		AccountDto response = null;

		if (accountToUpdate != null && currency != null) {
			Boolean initialTreasury = accountToUpdate.getTreasury();

			Money balanceToUpdate = this.moneyRepository.findById(accountToUpdate.getBalance().getId()).orElse(null);

			if (balanceToUpdate == null) {
				balanceToUpdate = new Money();
			}
			balanceToUpdate.setBalance(accountDto.getBalance());

			balanceToUpdate = this.moneyRepository.save(balanceToUpdate);

			accountToUpdate = this.accountMapper.convertToEntity(accountDto);

			accountToUpdate.setBalance(balanceToUpdate);
			accountToUpdate.setTreasury(initialTreasury);

			this.accountRepository.save(accountToUpdate);

			response = this.accountMapper.convertToDto(accountToUpdate);
		}

		return response;
	}

	@Override
	@Transactional
	public TransferDto doMoneyTransfer(TransferParamDto transferParamDto) {

		Account origin = this.accountRepository.findById(transferParamDto.getOriginAccountId()).orElse(null);
		Account destination = this.accountRepository.findById(transferParamDto.getDestinationAccountId()).orElse(null);
		Double amount = transferParamDto.getAmount();

		TransferDto response = null;

		if (origin != null && destination != null) {
			Double originBalance = origin.getBalance().getBalance() - amount;

			if ((amount > 0.0) && (originBalance >= 0 || (originBalance < 0 && origin.getTreasury()))) {

				Double destBalance = destination.getBalance().getBalance() + amount;

				origin.getBalance().setBalance(originBalance);
				destination.getBalance().setBalance(destBalance);

				response = new TransferDto();

				AccountDto responseOriginDto = this.accountMapper.convertToDto(origin);
				AccountDto responseDestDto = this.accountMapper.convertToDto(destination);

				this.accountRepository.save(origin);
				this.accountRepository.save(destination);

				response.setAccountDtoOrigin(responseOriginDto);
				response.setAccountDtoDestination(responseDestDto);
				response.setAmount(amount);

			}
		}

		return response;
	}

}
