package rest.dao;

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
import javax.sql.DataSource;

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
		ArrayList<String> user_id = new ArrayList<String>();
		ArrayList<String> username = new ArrayList<String>();
		ArrayList<String> first_name = new ArrayList<String>();
		ArrayList<String> last_name = new ArrayList<String>();
		ArrayList<String> password = new ArrayList<String>();
		ArrayList<String> email = new ArrayList<String>();
		ArrayList<String> role = new ArrayList<String>();
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		try {

			PreparedStatement pstmt = null;
			String query = null;
			query = "SELECT `user_id`,`username`,`firstname`,`lastname`, `password`, `email`, `role` FROM `Users` WHERE `role`='member'";
			pstmt = connection.prepareStatement(query);
			ResultSet result = pstmt.executeQuery(query);
			System.out.println(result);
			while (result.next()) {
				user_id.add(result.getString("user_id"));
				username.add(result.getString("username"));
				first_name.add(result.getString("firstname"));
				last_name.add(result.getString("lastname"));
				password.add(result.getString("password"));
				email.add(result.getString("email"));
			}
			connection.close();
			String fnameJson = gson.toJson(first_name);
			String lnameJson = gson.toJson(last_name);
			String idJson = gson.toJson(user_id);
			String usernameJson = gson.toJson(username);
			String pwdJson = gson.toJson(password);
			String emailJson = gson.toJson(email);
//			String roleJson = gson.toJson(role);

			if (result != null)
				res = Json.createObjectBuilder().add("status", true).add("message", "success")
						.add("firstname", fnameJson).add("lastname", lnameJson).add("userid", idJson)
						.add("password", pwdJson).add("email", emailJson).add("username", usernameJson);
//						.add("role", roleJson);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		JsonObject jsonObject = res.build();
		StringWriter writer = new StringWriter();
		Json.createWriter(writer).write(jsonObject);
		jsonString = writer.toString();
		return jsonString;	
	}
	
	public String delete(String id) {
		
		Connection connection = myConnection.getConnection();
		String jsonString=null;
		
		JsonObjectBuilder res = Json.createObjectBuilder();
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		try {
			
			PreparedStatement pstmt = null;
			String query = null;
			query = "DELETE FROM `Users` where `user_id`=" + id;
			pstmt = connection.prepareStatement(query);
			// pstmt.setInt(1,Integer.parseInt(id));
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
					+ "`firstName`=?, `lastName`=?, `userPassword`=?,`emailId`=?,`userRole`=?,`memberCount`=?,`membershipJoin`=?,`membershipEnd`=?  where `flatNumber`=?";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, member.getUserName());
			pstmt.setString(2, member.getFirstName());
			pstmt.setString(3, member.getLastName());
			pstmt.setString(4, member.getUserPassword());
			pstmt.setString(5, member.getEmailId());
			pstmt.setString(6, member.getUserRole());
			pstmt.setInt(7, member.getMemberCount());
			pstmt.setString(8, member.getMembershipJoin());
			pstmt.setString(9, member.getMembershipEnd());
			pstmt.setInt(10, member.getFlatNumber());
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
