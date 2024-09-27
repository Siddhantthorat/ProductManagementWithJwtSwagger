package com.sid.iauro.jwt.ProductManagementJWT.models;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Product")
public class Product {
	
	@Id
	private int id;
    private String name;
    private int qty;
    private double price;
    private boolean visible;
    
	public Product() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Product(int id, String name, int qty, double price, boolean visible) {
		super();
		this.id = id;
		this.name = name;
		this.qty = qty;
		this.price = price;
		this.visible = visible;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQty() {
		return qty;
	}

	public void setQty(int qty) {
		this.qty = qty;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	

}