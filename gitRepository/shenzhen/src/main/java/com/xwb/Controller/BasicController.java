package com.xwb.Controller;

import javax.annotation.Resource;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xwb.model.Person;
import com.xwb.service.PersonService;
import com.xwb.utils.JedisUtil;

import redis.clients.jedis.Jedis;

@RequestMapping("/basic")
@Controller
public class BasicController {
	
	@Autowired
	public PersonService personService;
	
	@Autowired
	private JedisUtil redisUtil;
	
	@Resource(name="wyglMongoTemplate")
	private MongoTemplate mongoTemplate;
	
	@RequestMapping("/savePerson")
	public String toSavePerson(String id) {
		Person p = new Person();
		p.setPersonAddress("»ªÉ½");
		p.setPersonName("Áîºü³å");
		p.setPersonSex(0);
//		personService.addPerson(p);
		/*Jedis jedis = redisUtil.getJedis();
		if(jedis != null) {
			jedis.set("zname", "zhangshang");
			System.out.println(jedis.get("zname"));
		}*/
		
		mongoTemplate.insert(p, "myperson");
		
		return "/firstview";				
	}
}
