package com.ppk.configuration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.nimbusds.openid.connect.sdk.AuthenticationResponseParser;
import com.nimbusds.openid.connect.sdk.AuthenticationSuccessResponse;
import com.ppk.Repository.RoleRepository;
import com.ppk.Repository.UserRepository;
import com.ppk.model.Role;
import com.ppk.model.User;
@Component
public class GoogleOAuthHandler implements AuthenticationSuccessHandler
{
@Autowired
RoleRepository roleRepository;
@Autowired
UserRepository userRepository;
private RedirectStrategy redirectStrategy =new DefaultRedirectStrategy() ;
	
	public void sendRedirect(HttpServletRequest request, HttpServletResponse response, String url) throws IOException {
		// TODO Auto-generated method stub
		
	}
;
@Override
public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {
	OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
	String email =token.getPrincipal().getAttributes().get("email").toString();
	// user found else create one
	if(userRepository.findUserByEmail(email).isPresent()  )
	{
		
	}
	else
	{
		User user = new User();
		user.setFirstName(token.getPrincipal().getAttributes().get("given_Name").toString());
		user.setLastName(token.getPrincipal().getAttributes().get("family_name").toString());
		user.setEmail(email);
		List<com.ppk.model.Role> roles= new ArrayList<>();
		Role r1=new Role();
		r1=(Role)(roleRepository.findById(2).get());
		roles.add(r1);
		user.setRoles(roles);
		userRepository.save(user);
	}redirectStrategy.sendRedirect(request, response, "/");
	
}

}
