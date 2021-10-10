package rest.dao;

import java.io.StringWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

	public String loginUser(String userName, String userPassword) {
		MyConnection myConnection = new MyConnection();
		Connection connection = myConnection.getConnection();
		Login login=null;
		String jsonString=null;
		JsonObjectBuilder res = Json.createObjectBuilder();
                userPassword=encrypt(userPassword);
		if (connection != null) {
			try {
				PreparedStatement pstmt = null;
				String query = null;
				query = "SELECT userRole,flatNumber FROM Users WHERE userName='"+userName+"' and userPassword='"+userPassword+"';";
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
