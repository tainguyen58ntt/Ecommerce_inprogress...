package com.example.bigProduct.domain;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name= "Categories")
public class Category implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long categoryId;
	@Column(name = "category_name", length = 100, columnDefinition = "nvarchar(100) not null")  // if use definition then no need not null = false
	private String name;
	
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)  // khi delete cate thi product delete theo
	private Set<Product> products;
}	
