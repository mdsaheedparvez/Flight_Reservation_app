package com.flight_reservation_app_5.controller;

import org.slf4j.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.flight_reservation_app_5.entity.User;
import com.flight_reservation_app_5.repository.UserRepository;

@Controller
public class UserController {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	UserRepository userRepo;
     
	@RequestMapping("/showReg")
	public String showReg() {
		LOGGER.info("Inside showReg()");
		return "login/showReg";
	}
	
	@RequestMapping(value="/saveReg",method=RequestMethod.POST)
	public String saveReg(@ModelAttribute User user,ModelMap modelMap) {
		LOGGER.info("Inside saveReg()");
		userRepo.save(user);
		modelMap.addAttribute("msg", "Record saved");
		return "login/showReg";
	}
	
	@RequestMapping("/showLogin")
	public String showLogin() {
		LOGGER.info("Inside showLogin()");
		return "login/login";
	}
	
	@RequestMapping("/verifyLogin")
	public String verifyLogin(@RequestParam("email") String email,@RequestParam("password") String password,ModelMap modelMap) {
		LOGGER.info("Inside verifyLogin()");
		try {
		User user = userRepo.findByEmail(email);
		if(user.getEmail().equals(email) && user.getPassword().equals(password)) {
			return "searchFlight";
		}else {
			modelMap.addAttribute("msg", "invalid user name / password");
			return "login/login";
		}
		}catch(Exception e) {
			modelMap.addAttribute("msg", "invalid user name / password");
			return "login/login";
		}
	}
	
	
	
}
