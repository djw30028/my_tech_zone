package com.hellokoding.springboot.angularjs2;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AngularJs2Controller {
	 @RequestMapping("/angular2")
	 public String hello(Model model, @RequestParam(value="name", required=false, defaultValue="World") String name) {
	     model.addAttribute("name", name);
	     return "angular2";
	 }
	 
	 @RequestMapping("/angular2maven")
	 public String angular2maven(Model model, @RequestParam(value="name", required=false, defaultValue="World") String name) {
	     model.addAttribute("name", name);
	     return "angular2_maven/angular2maven";
	 }
	 
}
