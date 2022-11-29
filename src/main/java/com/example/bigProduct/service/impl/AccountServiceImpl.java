package com.example.bigProduct.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.bigProduct.domain.Account;
import com.example.bigProduct.repository.AccountRepository;
import com.example.bigProduct.service.AccountService;

@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public Account login(String username, String password) {
		Optional<Account> optExist = findById(username);


		if (optExist.isPresent() && bCryptPasswordEncoder.matches(password, optExist.get().getPassword())) {
			optExist.get().setPassword("");
			return optExist.get();
		}
		return null;
	}

	public <S extends Account> S save(S entity) {
		Optional<Account> optExist = findById(entity.getUsername());
		if (optExist.isPresent()) {
			if (StringUtils.isEmpty(entity.getPassword())) {
				entity.setPassword(optExist.get().getPassword());
			} else {
				entity.setPassword(bCryptPasswordEncoder.encode(entity.getPassword()));
			}

		} else {

			entity.setPassword(bCryptPasswordEncoder.encode(entity.getPassword()));
		}
		return accountRepository.save(entity);
	}

	public List<Account> findAll() {
		return accountRepository.findAll();
	}

	public void deleteById(String id) {
		accountRepository.deleteById(id);
	}

	public Optional<Account> findById(String id) {
		return accountRepository.findById(id);
	}

}
