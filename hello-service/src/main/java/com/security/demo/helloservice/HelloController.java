package com.security.demo.helloservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
	@Value("${accepted-roles}")
	 String acceptedRoles;
    
    @Autowired
    private  UaaProxy uaaproxy;

    @GetMapping("/user/hello/{token}")
    public String getHello(@PathVariable String token) {
        
        String status = uaaproxy.verifyTokenComingFromService(token);
        String[] data=status.split(",");
        String role=data[1];
        String isAuthorised=data[0];
        if(isAuthorised.equals("true")) {
        	String[] rolesFromConfig = acceptedRoles.split(",");
			for(String temp: rolesFromConfig) {
				if(temp.equals(role)) {
					return "Do Your Work";
				}
			}      	
        }      
        else {
        	 return "Unauthorized";
        } 
        return "Unauthorized";
    }
}