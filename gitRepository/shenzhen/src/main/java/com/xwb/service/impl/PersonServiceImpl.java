package com.xwb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Service;

import com.xwb.mappers.PersonMapper;
import com.xwb.model.Person;
import com.xwb.service.PersonService;

@Service
public class PersonServiceImpl implements PersonService {
	
	@Autowired
	private PersonMapper personMapper;

	public void addPerson(Person person) { 
		personMapper.insert(person);
	}

}
