package com.sid.iauro.jwt.ProductManagementJWT.service;


import java.util.List;

import com.sid.iauro.jwt.ProductManagementJWT.models.Product;
import com.sid.iauro.jwt.ProductManagementJWT.models.User;

public interface ProductService {

	Product saveProduct(Product product);

	List<Product> findAllProducts();

	Product updateProduct(int id, Product updatedProduct);

	void deleteProduct(int id);

	List<Product> listVisibleProducts();

	Product getProductDetails(int id) throws Exception;

	String addUser(User userInfo);

	Product updateProductVisibility(int id, boolean visible);

	List<Product> listProducts();

}
