package rest.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import rest.dao.LoginDao;

@RestController
public class LoginController {
	
	@Autowired
	LoginDao loginDao;
	
	@RequestMapping(value = "/login",produces = MediaType.APPLICATION_JSON_VALUE)
//	@PostMapping(value = "/login")
	@ResponseBody
	public String loginUser(HttpServletRequest request, @RequestParam String username, @RequestParam String password) {

		
		
		String result=loginDao.loginUser(username, password);
		return result;
		
	}

}
