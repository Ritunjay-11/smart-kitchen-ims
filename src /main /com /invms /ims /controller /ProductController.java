package com.invms.ims.controller;

import java.util.List; 
import java.util.Optional; 
import java.util.Random; 

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Controller; 
import org.springframework.ui.Model; 
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.ModelAttribute; 
import org.springframework.web.bind.annotation.PostMapping; 
import org.springframework.web.bind.annotation.RequestParam; 
import org.springframework.web.servlet.mvc.support.RedirectAttributes; 

import com.invms.ims.model.Product; 
import com.invms.ims.service.ProductService; 

@Controller
public class ProductController { 

	@Autowired
	private ProductService productService; 

	@GetMapping("/") 
	public String getIndexPage(Model model) { 
		List<Product> productList = productService.getAllProducts(); 
		model.addAttribute("products", productList); 
		model.addAttribute("product", new Product()); 
		return "index"; 
	} 

	@PostMapping("/newProduct") 
	public String newProduct(@ModelAttribute("product") Product product, RedirectAttributes redirectAttributes) { 
		// Creating dynamic product ID 
		String productId = "PD" + (1000 + new Random().nextInt(9000)); 
		product.setId(productId); 
		productService.addProduct(product); 
		redirectAttributes.addFlashAttribute("insertSuccess", true); 
		return "redirect:/"; 
	} 

	@PostMapping("/searchProduct") 
	public String searchProduct(@RequestParam(name = "id") String id, RedirectAttributes redirectAttributes, 
			Model model) { 
		Optional<Product> foundProduct = productService.getProductById(id); 
		model.addAttribute("product", new Product()); 
		if (foundProduct.isPresent()) { 
			redirectAttributes.addFlashAttribute("foundProduct", foundProduct); 
		} else { 
			redirectAttributes.addFlashAttribute("notFound", true); 
		} 

		return "redirect:/"; 
	} 

	@PostMapping("/deleteProduct") 
	public String deleteProduct(@RequestParam(name = "id") String id, RedirectAttributes redirectAttributes) { 
		boolean deleteSuccess = productService.deleteProductById(id); 
		if (deleteSuccess) { 
			redirectAttributes.addFlashAttribute("deleteSuccess", true); 
		} else { 
			redirectAttributes.addFlashAttribute("notFound", true); 
		} 

		return "redirect:/"; 
	} 

	@GetMapping("/productAnalysis") 
	public String productAnalysis(Model model) { 
		List<Product> products = productService.getAllProducts(); 
		model.addAttribute("products", products); 
		return "productAnalysis"; // Thymeleaf template name 
	} 
	
} 
