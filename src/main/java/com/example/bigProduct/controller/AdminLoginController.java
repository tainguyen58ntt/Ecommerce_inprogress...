package com.example.bigProduct.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.bigProduct.domain.Account;
import com.example.bigProduct.model.AdminLoginDTO;
import com.example.bigProduct.service.AccountService;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;

@Controller
public class AdminLoginController {
	@Autowired
	private AccountService accountService;
	@Autowired
	private HttpSession session;
	@GetMapping("alogin")   // mean: admin login
	public String login(ModelMap model) {
		model.addAttribute("account", new AdminLoginDTO());
		return "/admin/accounts/login";
	}
	@PostMapping("alogin")
	public ModelAndView login(ModelMap model, @Valid @ModelAttribute("account") AdminLoginDTO dto, BindingResult result) {
		if(result.hasErrors()) {
			return new ModelAndView("/admin/accounts/login", model);
		}
		Account account = accountService.login(dto.getUsername(), dto.getPassword());
		if(account == null) {
			model.addAttribute("message", "invalid username or passsword");
			return new ModelAndView("/admin/accounts/login", model);
		}
		session.setAttribute("username", account.getUsername());
		Object ruri = session.getAttribute("redirect-uri");
		if(ruri != null) {
			session.removeAttribute("redirect-uri");
			return new ModelAndView("redirect:" + ruri);
		}
		
		
		return new ModelAndView("forward:/admin/categories", model);
	}
	
}
