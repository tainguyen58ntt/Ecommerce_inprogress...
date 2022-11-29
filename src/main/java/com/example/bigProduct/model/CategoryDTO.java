package com.example.bigProduct.model;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO implements Serializable{  
	private Long categoryId;
	@NotEmpty
	@Length(min = 5)
	private String name;
	private Boolean isEdit = false;
}
