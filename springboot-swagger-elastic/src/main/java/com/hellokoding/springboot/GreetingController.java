package com.hellokoding.springboot;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hellokoding.springboot.elastic.ElasticClientAccessor;


@RequestMapping( "/bradways" ) 
@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @Autowired
	private UserService service;
    
    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value="name", defaultValue="World") String name) {
    	
    	//User user = service.findByFirstName(name);
    	User user = null;
		try {
			user = ElasticClientAccessor.getInstance().retrieveUserByFirstName(name);
			System.out.println("-- retreive from elastic: " + user.toString());
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	if (user != null) {
    		return new Greeting(counter.incrementAndGet(),
                    String.format(template, user.toString()));
    	}
    	return new Greeting(counter.incrementAndGet(),
                            String.format(template, "not found for: " + name));
    }
    
    @RequestMapping(value = "/user", method = RequestMethod.POST)
    public User createUser(@RequestBody @Valid final User user) {
    	User createUser = new User();
    	createUser.setFirstName(user.getFirstName());
    	createUser.setLastName(user.getLastName());
    	
    	try {
    		System.out.println("-- save to createUser: " + createUser.toString());
			ElasticClientAccessor.getInstance().saveUser(createUser);
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
   	
        return createUser;
    }
    
    @RequestMapping("/getdogs")
    public List<AgPet> getDogs() {
    	List<AgPet> dogList = new ArrayList<>();
    	dogList.add(new AgPet(1, "Mr. Nicer"));
    	dogList.add(new AgPet(2, "Narco"));
    	dogList.add(new AgPet(3, "Bombasto"));
    	
    	
    	return dogList;
    }
}
