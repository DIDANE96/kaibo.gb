package com.kaibo.utility;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.kaibo.dao.ProductDao;
import com.kaibo.model.Cart;
import com.kaibo.model.Product;

public class Helper {
	
	@Autowired
	private ProductDao productDao;
	
	public static String get10Words(String productDescription)
    {
    	String[] str=productDescription.split(" ");
    	if(str.length>10)
    	{
    		String res="";
    		for(int i=0;i<10;i++)
    		{
    			res=res+str[i]+" ";
    		}
    		
    		return res+"...";
    	}
    	
    	else
    	{
    		return productDescription+"...";
    	}
    }
    
    public static double getProductSellingPrice(double productPrice,double productDiscountPercent)
    {
    	
    	Double tenPercentValue=productDiscountPercent*0.01;
    	Double tenPercentOfPrice=tenPercentValue*productPrice;
    	Double finalPrice=productPrice-tenPercentOfPrice;
    	
    	return finalPrice;
    }
    
    public static String getAlphaNumericOrderId(int n)
    {
  
        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "0123456789"
                                    + "abcdefghijklmnopqrstuvxyz";
  
        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);
  
        for (int i = 0; i < n; i++) {
  
            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                = (int)(AlphaNumericString.length()
                        * Math.random());
  
            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                          .charAt(index));
        }
  
        return sb.toString();
    }
    
    public double getProductsTotalSellingPrice(List<Cart> carts) {
    	double totalPrice = 0;
    	for(Cart c : carts) {
    		Optional<Product> optinal = productDao.findById(c.getProductId());
    		Product p = null;
    		p = optinal.get();
    	    double productPrice = getProductSellingPrice(p.getPrice(),p.getDiscount());
    	    totalPrice = totalPrice + (productPrice*c.getQuantity());
    	    
    	}
    	
    	return totalPrice;
    	
    }
    
    
}
