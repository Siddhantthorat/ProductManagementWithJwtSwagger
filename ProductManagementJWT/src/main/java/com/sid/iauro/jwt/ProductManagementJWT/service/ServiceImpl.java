package com.sid.iauro.jwt.ProductManagementJWT.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.sid.iauro.jwt.ProductManagementJWT.models.Product;
import com.sid.iauro.jwt.ProductManagementJWT.models.User;
import com.sid.iauro.jwt.ProductManagementJWT.repository.ProductRepository;
import com.sid.iauro.jwt.ProductManagementJWT.repository.UserRepository;


@Service
public class ServiceImpl implements ProductService {
	
	@Autowired
	private ProductRepository repository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;

	@Override
	public Product saveProduct(Product product) {
		
		return repository.save(product);
	}

	@Override
	public List<Product> findAllProducts() {
		List<Product> allProducts=repository.findAll();
		return allProducts;
	}
	


	@Override
	public Product updateProduct(int id,Product updatedProduct) {
		Product product = repository.getById(id);
		if (product==null) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
	    }
	  
	    return  repository.save(updatedProduct);
		
	}

	@Override
	public void deleteProduct(int id) {
		Product product = repository.getById(id);
		if (product==null) {
	        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
	    }          
        repository.deleteById(id);
	}


	@Override
	public String addUser(User userInfo) {
	        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
	        userRepository.save(userInfo);
	        return "User Added to Database ";
	
	}

	@Override
	public Product updateProductVisibility(int id, boolean visible) {
		Product product = repository.getById(id);
		if (product==null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
		product.setVisible(visible);
        return repository.save(product);

	}
	
	@Override
	public List<Product> listVisibleProducts() {
		return repository.findByVisible(true);
	}


	// Admin: List all products
    public List<Product> listProducts() {
        return repository.findAll();
    }


    // Admin/Customer: Get product details
    public Product getProductDetails(int id) throws Exception {
        return repository.findById(id)
                .orElseThrow(() -> new Exception("Product not found"));
    }
    
    

}