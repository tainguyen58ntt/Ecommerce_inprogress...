package com.example.bigProduct.service;

import java.util.List;
import java.util.Optional;

import com.example.bigProduct.domain.Account;

public interface AccountService {
	<S extends Account> S save(S entity);

	List<Account> findAll();

	Optional<Account> findById(String id);

	void deleteById(String id);

	Account login(String username, String password);
}
