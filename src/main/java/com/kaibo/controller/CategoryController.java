package com.kaibo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kaibo.dao.CategoryDao;
import com.kaibo.dao.ProductDao;
import com.kaibo.model.Category;
import com.kaibo.model.Product;

@Controller
public class CategoryController {
	
	@Autowired
	private ProductDao productDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	@PostMapping("/addcategory")
	public ModelAndView addcategory(@ModelAttribute Category category) {
		ModelAndView  mv = new ModelAndView();
		
		categoryDao.save(category);
		
		mv.addObject("status", "Category Added Successfully!");
		mv.setViewName("index");
		
		return mv;
	}
	
	@GetMapping("/deletecategory")
	public ModelAndView deleteCategory(@RequestParam("categoryId") int categoryId) {
        ModelAndView  mv = new ModelAndView();
		
        Category category = null;
		Optional<Category> o = categoryDao.findById(categoryId);
		
		if(o.isPresent()) {
			category = o.get();
		}
		
		categoryDao.delete(category);
        
		List<Product> products = productDao.findByCategoryId(categoryId);
		
		for(Product product : products) {
			productDao.delete(product);
		}
		
		mv.addObject("status", "Category Deleted Successfully!");
		mv.setViewName("index");
		
		return mv;
	}
	
	@GetMapping("/category")
	public ModelAndView category(@RequestParam("categoryId") int  categoryId) {
		ModelAndView mv = new ModelAndView();
		List<Product> products = new ArrayList<>();
		if(categoryId == 0) {
			products = productDao.findAll();
		}
		
		else {
			products = productDao.findByCategoryId(categoryId);
		}
		
		mv.addObject("products", products);
		mv.addObject("sentFromOtherSource", "yes");
		mv.setViewName("index");
		
		return mv;
	}

}
