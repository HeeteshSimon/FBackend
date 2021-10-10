//addUser
//http://localhost:9090/sqlartifact/add?flatNumber=203&userName=aaa&firstName=bbb&lastName=ccc&userPassword=ddd&emailId=eee@gmail.com&userRole=admin&memberCount=99&membershipJoin=2018-01-01&membershipEnd=2029-01-01

//updateUser
//http://localhost:9090/sqlartifact/update/202?userName=aaa&firstName=bbb&lastName=ccc&userPassword=ddd&emailId=eee@gmail.com&memberCount=99&membershipJoin=2018-01-01&membershipEnd=2029-01-01

package rest.controllers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import rest.Beans.Member;
import rest.dao.MemberDetailsDao;

@CrossOrigin(origins = "*")
@RestController
public class MemberDetailsController {

	@Autowired
	MemberDetailsDao memberDao;
	
	public static String encrypt(String pass){
        String password = pass;
        
        String encryptedpassword = null;  
        try   
        {  
        
            MessageDigest m = MessageDigest.getInstance("SHA-256");  
            
            m.update(password.getBytes());  
            
            byte[] bytes = m.digest();  
            
            
            StringBuilder s = new StringBuilder();  
            for(int i=0; i< bytes.length ;i++)  
            {  
                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));  
            }  
            
            
            encryptedpassword = s.toString();  
        }   
        catch (NoSuchAlgorithmException e)   
        {  
            e.printStackTrace();  
        }  
        
    
        System.out.println("Plain-text password: " + password);  
        System.out.println("Encrypted password using SHA: " + encryptedpassword);  
        return encryptedpassword;
    }

	@RequestMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
	public String addMember(@RequestParam String flatNumber, @RequestParam("userName") String userName,
			@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
			@RequestParam("userPassword") String userPassword, @RequestParam("emailId") String email,
			@RequestParam("userRole") String userRole, @RequestParam("memberCount") String memberCount,
			@RequestParam("membershipJoin") String membershipJoin,
			@RequestParam("membershipEnd") String membershipEnd) {
		

		Member member = new Member(Integer.parseInt(flatNumber), userName, firstName, lastName, encrypt(userPassword), email,
				userRole, Integer.parseInt(memberCount), membershipJoin, membershipEnd);
		String result = memberDao.add(member);
		return result;

	}
	
	

	@RequestMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getAll() {

		String result = memberDao.getAll();
		return result;

	}

	@RequestMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String delete(@PathVariable("id") String id) {

		String result = memberDao.delete(id);
		return result;

	}

	@RequestMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String update(@PathVariable("id") String flatNumber, @RequestParam("userName") String userName,
			@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
			@RequestParam("userPassword") String userPassword, @RequestParam("emailId") String email,
			@RequestParam("memberCount") String memberCount, @RequestParam("membershipJoin") String membershipJoin,
			@RequestParam("membershipEnd") String membershipEnd) {

		Member member = new Member(Integer.parseInt(flatNumber), userName, firstName, lastName, userPassword, email,
				Integer.parseInt(memberCount), membershipJoin, membershipEnd);
		String result = memberDao.update(member);
		return result;
	}

}