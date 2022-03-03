package com.ppk.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.ppk.global.GlobalData;
import com.ppk.model.Product;
import com.ppk.service.ProductService;

@Controller
public class CartController {
@Autowired
ProductService productService;
@GetMapping("/addToCart/{id}")
public String addToCart(@PathVariable Long  id)
{
	
	
GlobalData.cart.add(productService.getProductById(id).get());
return "redirect:/shop";

}
@GetMapping("/cart")
public String cartGet(Model model)
{
model.addAttribute("cartCount", GlobalData.cart.size());
model.addAttribute("total",GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
model.addAttribute("cart", GlobalData.cart);
System.out.println("price");
GlobalData.cart.stream().mapToDouble(Product::getPrice).toString();
return "cart";

}
@PostMapping("/cart/{id}")
public String cartItemRemove(@PathVariable int index)
{
GlobalData.cart.remove(index)	;

return "redirect:/cart";
}
@GetMapping("/checkout")
public String checkout(Model model)
{
	model.addAttribute("total",GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
return "checkout";
}
}
