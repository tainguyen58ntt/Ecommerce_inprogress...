package com.example.bigProduct.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
	private int productId;
	private String name; // name of product
	private int quantity;
	private double unitPrice;
	
}
