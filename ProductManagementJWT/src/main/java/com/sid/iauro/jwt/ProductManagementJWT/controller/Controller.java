package com.sid.iauro.jwt.ProductManagementJWT.controller;



import java.util.Base64;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.sid.iauro.jwt.ProductManagementJWT.Dto.AuthRequest;
import com.sid.iauro.jwt.ProductManagementJWT.models.Product;
import com.sid.iauro.jwt.ProductManagementJWT.models.User;
import com.sid.iauro.jwt.ProductManagementJWT.repository.UserRepository;
import com.sid.iauro.jwt.ProductManagementJWT.service.JwtService;
import com.sid.iauro.jwt.ProductManagementJWT.service.ProductService;


@RestController
public class Controller {
	

	@Autowired
    private UserRepository repository;
	

	@Autowired
    private PasswordEncoder passwordEncoder;

	@Autowired 
	private ProductService service;
	
	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@PostMapping("/add")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<Product> addProduct(@RequestBody Product product){
		Product savedProduct=service.saveProduct(product);
		return new ResponseEntity<Product>(savedProduct, HttpStatus.CREATED);
		
	}
	
	@PutMapping("/edit/{id}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Product> editProduct(@PathVariable int id, @RequestBody Product updatedProduct) {
	 Product upProduct =service.updateProduct(id,updatedProduct);
        return new ResponseEntity<>(upProduct, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteProduct(@PathVariable int id) {
        service.deleteProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
	

    @PostMapping("/register")
    public String addNewUser(@RequestBody User userInfo){
        return service.addUser(userInfo);
    }
    
//    @GetMapping("/login")
//	public ResponseEntity<?> login(@RequestHeader("Authorization")String auth) {
//
//       String pair=new String(Base64.getDecoder().decode(auth.substring(6)));
//       String password=pair.split(":")[1];
//       String username=pair.split(":")[0];
//
//       
//        Optional<User> optionalUser= repository.findByUsername(username);
//        if(!optionalUser.isEmpty())
//        {
//        	User user=optionalUser.get();        	
//			if(passwordEncoder.matches(password,user.getPassword()))
//        	{ 	
//				return new ResponseEntity<User>(user,HttpStatus.OK);
//        	}
//        	else {
//        		return new ResponseEntity<String>("User not found", HttpStatus.OK);
//        	}
//        }
//        return new ResponseEntity<String>("User not found", HttpStatus.OK);
//    }
    
    @PatchMapping("/visibility/{id}")
    public ResponseEntity<Product> updateProductVisibility(@PathVariable int id, @RequestParam boolean visible) {
 
            Product updatedProduct = service.updateProductVisibility(id, visible);
            return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
   
    }
    @GetMapping("/list")
    public ResponseEntity<List<Product>> listVisibleProducts(Authentication authentication) {
    	try {
            // Admin can see all products, while customers only see visible products
    		User user = new User();
            List<Product> products;
            if (isAdmin(authentication)) {
                products = service.listProducts();  // Admin sees all products
            } else {
                products = service.listVisibleProducts();  // Customer sees only visible products
            }
            return new ResponseEntity<>(products, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

 // Admin/Customer: Get product details
    @GetMapping("/details/{id}")
    public ResponseEntity<Object> getProductDetails(@PathVariable int id, Authentication authentication) {
        try {
            Product product = service.getProductDetails(id);
            // Customers can't access non-visible products
            if (!isAdmin(authentication) && !product.isVisible()) {
                return new ResponseEntity<>("Customer Visibility is blocked by admin",HttpStatus.FORBIDDEN);
            }
            return new ResponseEntity<>(product, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Utility method to check if the user is an admin
    private boolean isAdmin(Authentication authentication) {
        return authentication.getAuthorities().stream()
                .anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"));
    }
    
    @PostMapping("/login")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid user request,Failed to login !");
        }


    }
    
}