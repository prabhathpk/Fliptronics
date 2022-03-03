
package com.ppk.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ppk.dto.ProductDTO;
import com.ppk.model.Category;
import com.ppk.model.Product;
import com.ppk.service.CategoryService;
import com.ppk.service.ProductService;

@Controller
public class AdminController
{
	@Autowired CategoryService categoryService;
	@Autowired ProductService productService;
	public static String uploadDir =System.getProperty("user.dir")+"/src/main/resources/static/productImages";//current directory
	
	@GetMapping("/admin")
	public String adminHome()
	{ 
		System.out.println("admin");
		return "adminHome";
	}
	@GetMapping("/admin/categories")
	public String getCat(Model model)
	{
		model.addAttribute("categories",categoryService.getAllCategory());
		return "categories";
	}
	@GetMapping("/admin/categories/add")
	public String getCatAdd(Model model)
	{
	model.addAttribute("category",new Category());
		return "categoriesAdd";
	}
	@PostMapping("/admin/categories/add")
	public String  postCatAdd(@ModelAttribute("category")Category category)
	{
		
		categoryService.addCategory(category);
		return "redirect:/admin/categories";
	}
	
	@GetMapping("/admin/categories/delete/{id}")
	public String deleteCat(@PathVariable int id)
	{
		categoryService.removeCategoryById(id);
		return "redirect:/admin/categories";
	}

	@GetMapping("/admin/categories/update/{id}")
	public String updateCat(@PathVariable int id,Model model)
	{
		Optional <Category> category= categoryService.getCategoryById(id);
		if(category.isPresent())
		{ 
			model.addAttribute("category", category);
			return "categoriesAdd";
		}
		else
		{
			
			return "404";
		}
		
	}

// products
	@GetMapping("/admin/products")
	public String getProducts(Model model)
	{ final List<Product> products = productService.getAllProduct();
		model.addAttribute("products",productService.getAllProduct());
		for(Product product : products) {
            System.out.println(product.toString());
        }
	
			  
		
		return "products";
	}
	
	@GetMapping("/admin/products/add")
	public String productAddGet(Model model)
	{
	model.addAttribute("productDTO",new ProductDTO());
	model.addAttribute("categories",categoryService.getAllCategory());
	
		return "productsAdd";
	}// request param 3 requests file to save image string givesw image name
	@PostMapping("/admin/products/add")
	public String productAddPost (@ModelAttribute("productDTO")ProductDTO productDTO ,@RequestParam("productImage")MultipartFile file,@RequestParam("imgName")String imgName) throws IOException
	{
		// convert productDtO to product get from dto   set it as product
		Product product = new Product();
		product.setId(productDTO.getId());
	    product.setName(productDTO.getName());
	    product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
		product.setPrice(productDTO.getPrice());
		product.setWeight(productDTO.getWeight());
		product.setDescription(productDTO.getDescription());
		String imageUUID;//unique id of image
		if (!file.isEmpty())
		{System.out.println("not Empty");
		System.out.println(uploadDir);
			imageUUID=file.getOriginalFilename();
			Path fileNameAndPath =Paths.get(uploadDir,imageUUID);//create the path of file
			Files.write(fileNameAndPath ,file.getBytes());//getBytes ggive path to oroiginal file filenameandpath is location target
		}
		else
		{
			imageUUID =imgName;
			System.out.println("else condition");
		}
		System.out.println(uploadDir);
		product.setImageName(imageUUID);
		productService.addProduct(product);
		return "redirect:/admin/products";
	}
	@GetMapping("/admin/product/delete/{id}")
	public String deleteCat(@PathVariable long id)
	{
		productService.removeProductById(id);
		return "redirect:/admin/products";
	}
	@GetMapping("/admin/product/update/{id}")
	public String updateProductGet(@PathVariable long id,Model model)
	{
		Product product = productService.getProductById(id).get();
		ProductDTO productDTO = new ProductDTO();
		productDTO.setId(product.getId());
	    productDTO.setName(product.getName());
	    productDTO.setCategoryId((product.getCategory().getId()));
		productDTO.setPrice(product.getPrice());
		productDTO.setWeight(product.getWeight());
		productDTO.setDescription(product.getDescription());
		model.addAttribute("categories",categoryService.getAllCategory());
		model.addAttribute("productDTO",productDTO);
		return "productsAdd";
		
	}
	

}
