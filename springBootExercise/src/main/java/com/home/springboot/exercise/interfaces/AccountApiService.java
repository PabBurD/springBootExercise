package com.home.springboot.exercise.interfaces;

import com.home.springboot.exercise.dto.AccountDto;
import com.home.springboot.exercise.dto.TransferDto;
import com.home.springboot.exercise.dto.TransferParamDto;

public interface AccountApiService {

	AccountDto findAccount(Long id);

	AccountDto createAccount(AccountDto accountDto);

	AccountDto updateAccount(Long id, AccountDto accountDto);

	TransferDto doMoneyTransfer(TransferParamDto transferDtoParam);

}
