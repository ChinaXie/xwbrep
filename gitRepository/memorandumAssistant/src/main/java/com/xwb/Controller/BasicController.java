package com.xwb.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xwb.model.Person;
import com.xwb.service.PersonService;

@RequestMapping("/basic")
@Controller
public class BasicController {
	
	@Autowired
	public PersonService personService; 
	
	@RequestMapping("/savePerson")
	public String toSavePerson(String id) {
		Person p = new Person();
		p.setPersonAddress("»ªÉ½");
		p.setPersonName("Áîºü³å");
		p.setPersonSex(0);
		personService.addPerson(p);
		
		
		
		
		return "/firstview";				
	}
}
