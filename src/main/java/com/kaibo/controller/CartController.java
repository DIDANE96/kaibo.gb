package com.kaibo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kaibo.dao.CartDao;
import com.kaibo.dao.ProductDao;
import com.kaibo.model.Cart;
import com.kaibo.model.Product;

@Controller
public class CartController {

	@Autowired
	private CartDao cartDao;
	
	@Autowired
	private ProductDao productDao;
	
	
	@GetMapping("/addToCart")
	public ModelAndView addtoCart(@ModelAttribute Cart cart) {
		ModelAndView mv = new ModelAndView();
		
		Product product = null;
		
		Optional<Product> optional = this.productDao.findById(cart.getProductId());
		
		if(optional.isPresent()) {
			product = optional.get();
		}
		
		if(cart.getQuantity() > product.getQuantity() ) {
			mv.addObject("status", "Insufficient Product Quantity!");
			mv.setViewName("index");
			
			return mv;
			
		}
	
		cartDao.save(cart);
		mv.addObject("status", "Product added to cart!");
		mv.setViewName("index");
		
		return mv;
	
	}
	
	@GetMapping("/deletecart")
	public ModelAndView deleteProductFromCart(@RequestParam("cartId") int  cartId) {
		ModelAndView mv = new ModelAndView();
		
		Cart cart = new Cart();
	
		Optional<Cart> o = cartDao.findById(cartId);
		if(o.isPresent()) {
			cart = o.get();
		}
		
		cartDao.delete(cart);
		
		mv.addObject("status", "Selected Product removed from Cart!");
		mv.setViewName("index");
		
		return mv;
	}
	
}
