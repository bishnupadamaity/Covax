package com.covax.Controller;

import com.covax.Services.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MyController {
	@Autowired
	DataService dataService;
	@RequestMapping("/")
	public String home(Model model) {

		model.addAttribute("allStates",dataService.getAllStates());
		model.addAttribute("allDistricts",dataService.getAllDistricts());
		return "index";
	}

}
