package rest.dao;

import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import rest.Beans.Login;
import rest.connection.MyConnection;

@Component("loginDao")
public class LoginDao {
	
	@Autowired
	MyConnection myConnection;

	public String loginUser(String userName, String userPassword) {
//		MyConnection myConnection = new MyConnection();
		Connection connection = myConnection.getConnection();
		Login login=null;
		String jsonString=null;
		JsonObjectBuilder res = Json.createObjectBuilder();

		if (connection != null) {
			try {
				PreparedStatement pstmt = null;
				String query = null;
				query = "SELECT userRole,flatNumber FROM `users` WHERE `userName`='" + userName
						+ "' AND `userPassword`='" + userPassword + "';";
				pstmt = connection.prepareStatement(query);
//				pstmt.setString(1, username);
//				pstmt.setString(2, password);
				ResultSet result = pstmt.executeQuery(query);
				if (result.next()) {
					 login = new Login(userName, userPassword, result.getString(1), result.getString(2));
					connection.close();
				  
				} else {
					connection.close();
					
				}

			}

			catch (SQLException e) {
				e.printStackTrace();

			}
			
			
//			Login login=loginDao.setLoginUser(username, password);
			if (login!=null) {
				//request.getSession().setAttribute("role",login.getUserRole() );
				res.add("role", login.getUserRole())
				.add("flatNumber", login.getFlatNumber());
//				return "success";
			} else {
				res.add("role", "");
//				return "failed";
			}
			
			JsonObject jsonObject = res.build();
			
			StringWriter writer = new StringWriter();
			Json.createWriter(writer).write(jsonObject);
			jsonString = writer.toString();
			

		}
		return jsonString;

	}
}
