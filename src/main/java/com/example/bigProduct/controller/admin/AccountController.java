package com.example.bigProduct.controller.admin;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.bigProduct.domain.Account;
import com.example.bigProduct.model.AccountDTO;
import com.example.bigProduct.service.AccountService;

@Controller
@RequestMapping("/admin/accounts")
public class AccountController {
	@Autowired
	private AccountService accountService;
	
	@GetMapping("/add")
	public String add(Model model) {
		
		
		model.addAttribute("account", new AccountDTO());
		return "admin/accounts/addOrEdit";
	}
	@GetMapping("/register")
	public String register(Model model) {


		model.addAttribute("account", new AccountDTO());
		return "admin/accounts/register";
	}
	@PostMapping("saveOrUpdate")
		public ModelAndView saveOrUpdate(ModelMap model, @Valid @ModelAttribute("account") AccountDTO dto,BindingResult result ) {
		if(result.hasErrors()) {
			return new ModelAndView("admin/accounts/addOrEdit");
			
		}
		Account entity = new Account();
		BeanUtils.copyProperties(dto, entity);
		
		accountService.save(entity);
		model.addAttribute("message","Account is saved !");
		return new ModelAndView("forward:/admin/accounts", model);

	}
	@RequestMapping("")
	public String list(ModelMap model) {
		List<Account> list =  accountService.findAll();
		model.addAttribute("accounts", list);	
		return "admin/accounts/list";
	}
	@GetMapping("edit/{username}")
	public ModelAndView edit(ModelMap model, @PathVariable("username") String username) {
		Optional<Account> opt = accountService.findById(username);
		AccountDTO dto = new AccountDTO();
		if(opt.isPresent()) {
			Account entity = opt.get();
			BeanUtils.copyProperties(entity, dto);  // copy tu entity sang dto
			dto.setIsEdit(true);	
			dto.setPassword("");
			model.addAttribute("account", dto);
			return new ModelAndView("admin/accounts/addOrEdit", model);
		}
		model.addAttribute("message", "Account is not exists");
		return new ModelAndView("forward:/admin/accounts",model);
	}
//	
//	
	@GetMapping("delete/{username}")
	public ModelAndView delete(@PathVariable("username") String username, ModelMap model) {
		
		accountService.deleteById(username);
		model.addAttribute("message", "account is deleted");
		return new ModelAndView("forward:/admin/accounts",model);
	}
//	


//	@GetMapping("search")
//	public String search(ModelMap model, @RequestParam(name = "name", required = false) String name) {
//		List<Account> list = null;
//		if(StringUtils.hasText(name)) {
//			list = categoryService.findByNameContaining(name);
//		}else {
//			list = categoryService.findAll();
//		}
//		
//		model.addAttribute("accounts", list);
//		
//		return "/admin/accounts/search";
//	}
	
}
