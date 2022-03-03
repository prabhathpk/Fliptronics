package com.ppk.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.ppk.Repository.RoleRepository;
import com.ppk.Repository.UserRepository;
import com.ppk.global.GlobalData;
import com.ppk.model.Role;
import com.ppk.model.User;
@Controller
public class LoginController
{
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@GetMapping("/login")
	public String login()
	{  GlobalData.cart.clear();
		return "login";
	}
	@GetMapping("/register")
	public String registerGet()
	{
		return "register";
	}
	//after login request so that he stays logged in after registering
	@PostMapping("/register")
	public String registerPost(@ModelAttribute("user") User user,HttpServletRequest request ) throws ServletException
	{System.out.println("postmapping");
	

		String password =user.getPassword();
		//encrypt password
		user.setPassword(bCryptPasswordEncoder.encode(password) );
		System.out.println(bCryptPasswordEncoder.encode("12345678"));
		List<Role> roles =new ArrayList<>();
		System.out.println(bCryptPasswordEncoder.encode(password));
		roles.add(roleRepository.findById(2).get());
	

		user.setRoles(roles);

		userRepository.save(user);
		request.login(user.getEmail(), password);
		

		return "redirect:/";
		
		
	}
	

}
