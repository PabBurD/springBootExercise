package com.home.springboot.exercise.mapper;

import javax.annotation.PostConstruct;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;

import com.home.springboot.exercise.dto.AccountDto;
import com.home.springboot.exercise.entity.Account;

@Component
public class AccountMapper extends ModelMapper {

	public AccountMapper() {
		super();
	}

	@PostConstruct
	public void init() {
		PropertyMap<Account, AccountDto> entityToDto = new PropertyMap<Account, AccountDto>() {

			@Override
			protected void configure() {
				map(source.getId(), destination.getId());
				map(source.getCurrency().getId(), destination.getCurrency());
				map(source.getBalance().getBalance(), destination.getBalance());
				map(source.getTreasury(), destination.getTreasury());

			}

		};

		addMappings(entityToDto);

		PropertyMap<AccountDto, Account> dtoToEntity = new PropertyMap<AccountDto, Account>() {

			@Override
			protected void configure() {
				map(source.getId(), destination.getId());
				map(source.getCurrency(), destination.getCurrency().getId());
				map(source.getBalance(), destination.getBalance().getBalance());
				map(source.getTreasury(), destination.getTreasury());
			}

		};

		addMappings(dtoToEntity);
	}

	public AccountDto convertToDto(Account account) {
		return map(account, AccountDto.class);
	}

	public Account convertToEntity(AccountDto accountDto) {
		return map(accountDto, Account.class);
	}

}
