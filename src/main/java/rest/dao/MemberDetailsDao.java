package rest.dao;

import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import rest.Beans.Member;
import rest.connection.MyConnection;

@Component("memberDao")
public class MemberDetailsDao {
	
	@Autowired
	MyConnection myConnection;
	
	public String getAll() {
		Connection connection = myConnection.getConnection();
		String jsonString=null;
	
		JsonObjectBuilder res = Json.createObjectBuilder();
		
		
		
		ArrayList<Integer> flatNumber = new ArrayList<Integer>();
		ArrayList<String> userName = new ArrayList<String>();
		ArrayList<String> firstName = new ArrayList<String>();
		ArrayList<String> lastName = new ArrayList<String>();
		ArrayList<String> userPassword = new ArrayList<String>();
		ArrayList<String> emailId = new ArrayList<String>();
		//ArrayList<String> userRole = new ArrayList<String>();
		ArrayList<String> membershipJoin = new ArrayList<String>();
		ArrayList<String> membershipEnd = new ArrayList<String>();
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		try {

			PreparedStatement pstmt = null;
			String query = null;
			query = "SELECT `flatNumber`,`userName`,`firstName`,`lastName`, `userPassword`, `emailId`, `userRole`,`membershipJoin`,`membershipEnd` FROM `Users` WHERE `userRole`='member'";
			pstmt = connection.prepareStatement(query);
			ResultSet result = pstmt.executeQuery(query);
			System.out.println(result);
			while (result.next()) {
				flatNumber.add(result.getInt("flatNumber"));
				userName.add(result.getString("userName"));
				firstName.add(result.getString("firstName"));
				lastName.add(result.getString("lastName"));
				userPassword.add(result.getString("userPassword"));
				emailId.add(result.getString("emailId"));
				//userRole.add(result.getString("userRole"));
				membershipJoin.add(result.getString("membershipJoin"));
				membershipEnd.add(result.getString("membershipEnd"));
			}
			connection.close();
			String flatNumberJson=gson.toJson(flatNumber);
			String userNameJson=gson.toJson(userName);
			String firstNameJson = gson.toJson(firstName);
			String lastNameJson = gson.toJson(lastName);
			String emailIdJson=gson.toJson(emailId);
			String userPasswordJson = gson.toJson(userPassword);
			String membershipJoinJson = gson.toJson(membershipJoin);
			String membershipEndJson = gson.toJson(membershipEnd);

			if (result != null)
				res = Json.createObjectBuilder().add("status", true).add("message", "success")
				        .add("flatNumber", flatNumberJson).add("userName", userNameJson)
						.add("firstname", firstNameJson).add("lastname", lastNameJson).add("emailId", emailIdJson)
						.add("userPassword", userPasswordJson).add("membershipJoin", membershipJoinJson).add("membershipEnd", membershipEndJson);


		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		JsonObject jsonObject = res.build();
		StringWriter writer = new StringWriter();
		Json.createWriter(writer).write(jsonObject);
		jsonString = writer.toString();
		return jsonString;	
	}
	
	public String delete(String flatNumber) {
		
		Connection connection = myConnection.getConnection();
		String jsonString=null;
		
		JsonObjectBuilder res = Json.createObjectBuilder();
		
		
		try {
			
			PreparedStatement pstmt = null;
			String query = null;
			query="UPDATE `Users` SET `userName`=NULL,`firstName`=NULL,`lastName`=NULL, `userPassword`=NULL,`emailId`=NULL,`userRole`=NULL,`memberCount`=NULL,`membershipJoin`=NULL,`membershipEnd`=NULL  where `flatNumber`=? ";
			pstmt = connection.prepareStatement(query);
			 pstmt.setInt(1,Integer.parseInt(flatNumber));
			int result = pstmt.executeUpdate();
			System.out.println(result);
			if (result > 0) {
				connection.close();
				res = Json.createObjectBuilder().add("status", true).add("message", "success");
			} else {
				connection.close();
				res = Json.createObjectBuilder().add("status", false).add("message", "failure");

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		JsonObject jsonObject = res.build();
		StringWriter writer = new StringWriter();
		Json.createWriter(writer).write(jsonObject);
		jsonString = writer.toString();
		return jsonString;

	}
	
	public String update(Member member) {
		
		Connection connection = myConnection.getConnection();
		String jsonString=null;
		
		JsonObjectBuilder res = Json.createObjectBuilder();
		try {
			
			PreparedStatement pstmt = null;
			String query = null;
			query = "UPDATE `Users` SET `userName`=?, "
					+ "`firstName`=?, `lastName`=?, `userPassword`=?,`emailId`=?,`memberCount`=?,`membershipJoin`=?,`membershipEnd`=?  where `flatNumber`=?";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, member.getUserName());
			pstmt.setString(2, member.getFirstName());
			pstmt.setString(3, member.getLastName());
			pstmt.setString(4, member.getUserPassword());
			pstmt.setString(5, member.getEmailId());
			//pstmt.setString(6, member.getUserRole());
			pstmt.setInt(6, member.getMemberCount());
			pstmt.setString(7, member.getMembershipJoin());
			pstmt.setString(8, member.getMembershipEnd());
			pstmt.setInt(9, member.getFlatNumber());
			int result = pstmt.executeUpdate();
			System.out.println(result);
			if (result > 0) {
				res = Json.createObjectBuilder().add("status", true).add("message", "success");
				connection.close();
			} else {
				connection.close();
				res = Json.createObjectBuilder().add("status", false).add("message", "error");

			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		JsonObject jsonObject = res.build();

		StringWriter writer = new StringWriter();
		Json.createWriter(writer).write(jsonObject);
		jsonString = writer.toString();
		return jsonString;
	}

}
