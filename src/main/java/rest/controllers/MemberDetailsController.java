//http://localhost:9090/sqlartifact/mem/add?username=sanjay&firstname=sanjay&lastname=prabhu&password=chinna&email=psanjay@gmail.com&role=member

package rest.controllers;

import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import rest.Beans.Login;
import rest.Beans.Member;
import rest.connection.MyConnection;
import rest.dao.LoginDao;
import rest.dao.MemberDetailsDao;

@CrossOrigin(origins = "*")
@RestController
public class MemberDetailsController {
	
	@Autowired
	MemberDetailsDao memberDao;
	
	
	
	
	@RequestMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
	public String addMember(@RequestParam String uname, @RequestParam String fname,@RequestParam String lname, @RequestParam String password, @RequestParam String email) {
//		String uname = formData.getFirst("username");
//		String fname = formData.getFirst("firstname");
//		String lname = formData.getFirst("lastname");
//		String pwd = formData.getFirst("password");
//		String email = formData.getFirst("email");

		DataSource ds;
		Connection con;
		JsonObjectBuilder res = Json.createObjectBuilder();
		try {
			Context ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:comp/env/jdbc/jit");
			con = ds.getConnection();
			PreparedStatement pstmt = null;
			String query = null;
			query = "INSERT INTO Users(`username`, `firstname`, `lastname`, `password`,`email`,`role`) VALUES(?, ?, ?, ?,?, 'member')";
			pstmt = con.prepareStatement(query);
//	         pstmt.setInt(1, Integer.parseInt(userId));
			pstmt.setString(1, uname);
			pstmt.setString(2, fname);
			pstmt.setString(3, lname);
			pstmt.setString(4, password);
			pstmt.setString(5, email);
			int result = pstmt.executeUpdate();
			System.out.println(result);
			if (result > 0) {
				con.close();
				res = Json.createObjectBuilder().add("status", true).add("message", "success");
//	        	 return "{status: true, message: success, batchId: "+batchMapped+"}";
			} else {
				con.close();
				res = Json.createObjectBuilder().add("status", false).add("message", "error");
//	        	 return "{status: true, message: batch_id_not_mapped, batchId: not mapped}";
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		JsonObject jsonObject = res.build();
		String jsonString;
		StringWriter writer = new StringWriter();
		Json.createWriter(writer).write(jsonObject);
		jsonString = writer.toString();
		return jsonString;

	}

	@RequestMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getAll() {
		
		String result=memberDao.getAll();
		return result;
		
	}

	@RequestMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String delete(@PathVariable("id") String id) {
		
		String result=memberDao.delete(id);
		return result;
		
	}

	@RequestMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String update(@PathVariable("id") String flatNumber, @RequestParam("username") String userName,
			@RequestParam("firstname") String firstName, @RequestParam("lastname") String lastName,
			@RequestParam("password") String userPassword, @RequestParam("email") String email,
			@RequestParam("role") String userRole,@RequestParam("memberCount") String memberCount,@RequestParam("membershipJoin") String membershipJoin,@RequestParam("membershipEnd") String membershipEnd) {
		
		
		Member member=new Member(Integer.parseInt(flatNumber),userName,firstName,lastName,userPassword,email,userRole,Integer.parseInt( memberCount),membershipJoin,membershipEnd);
		String result=memberDao.update(member);
		return result;
	}

	

}