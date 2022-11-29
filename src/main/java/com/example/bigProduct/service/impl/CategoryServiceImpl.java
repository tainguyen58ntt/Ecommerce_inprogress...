package com.example.bigProduct.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.bigProduct.domain.Category;
import com.example.bigProduct.repository.CategoryRepositoy;
import com.example.bigProduct.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService{
	@Autowired
	private CategoryRepositoy categoryRepository;

	public List<Category> findAll() {
		return categoryRepository.findAll();
	}

	@Override
	public Page<Category> findAll(Pageable pageable) {
		return categoryRepository.findAll(pageable);
	}

	public Optional<Category> findById(Long id) {
		return categoryRepository.findById(id);
	}

	@Override
	public <S extends Category> S save(S entity) {
		return categoryRepository.save(entity);
	}

	
	@Override
	public List<Category> findByNameContaining(String name) {
		// TODO Auto-generated method stub
		return categoryRepository.findByNameContaining(name);
	}

	public void deleteById(Long id) {
		categoryRepository.deleteById(id);
	}

	public Page<Category> findByNameContaining(String name, Pageable pageable) {
		return categoryRepository.findByNameContaining(name, pageable);
	}
	

	
	

	

	
	
	
}
