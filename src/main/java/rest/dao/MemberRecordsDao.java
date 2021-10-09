//http://localhost:9090/sqlartifact/addUserRecord?flatNumber=102&amount=5000&dateOfPay=2021-05-01&modeOfPayment=Online&paymentReference=49494

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
import org.springframework.web.bind.annotation.RequestParam;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import rest.Beans.Records;
import rest.connection.MyConnection;

@Component("recordsDao")
public class MemberRecordsDao {
	
	@Autowired
	MyConnection myConnection;
	
	public String getById(@RequestParam(defaultValue = "all") String uid,
			@RequestParam(defaultValue = "monthly") String type) {
		
		Connection connection = myConnection.getConnection();
		String jsonString=null;
		
		JsonObjectBuilder res = Json.createObjectBuilder();

		ArrayList<String> id = new ArrayList<String>();
		ArrayList<String> fname = new ArrayList<String>();
		ArrayList<String> lname = new ArrayList<String>();
		ArrayList<String> amount = new ArrayList<String>();
		ArrayList<String> date = new ArrayList<String>();

		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		try {
			
			PreparedStatement pstmt = null;
			String query = null;
			if (uid.equals("all"))
				query = "SELECT r.record_id, u.firstname, u.lastname, r.amount, r.date_of_pay FROM Records r INNER JOIN Users u ON u.user_id=r.user_id ORDER BY r.date_of_pay DESC;";
			else if (type.equalsIgnoreCase("yearly"))
				query = "select u.user_id,u.firstname,u.lastname,sum(r.amount),year(date_of_pay) "
						+ "from Records r inner join Users u on u.user_id=r.user_id " + "where u.user_id=" + uid
						+ " group by r.user_id,year(r.date_of_pay) " + "order by r.date_of_pay desc;";
			else
				query = "SELECT r.record_id, u.firstname, u.lastname, r.amount, r.date_of_pay FROM Records r "
						+ "INNER JOIN Users u ON u.user_id=r.user_id WHERE u.user_id=" + uid
						+ " ORDER BY r.date_of_pay DESC;";
			pstmt = connection.prepareStatement(query);
//			pstmt.setInt(1, Integer.parseInt(uid));
			ResultSet result = pstmt.executeQuery(query);
//			System.out.println(result);
			while (result.next()) {
				id.add(result.getString(1));
				fname.add(result.getString("firstname"));
				lname.add(result.getString("lastname"));
				amount.add(result.getString(4));
				date.add(result.getString(5));
			}
			connection.close();
			String idJson = gson.toJson(id);
			String fnameJson = gson.toJson(fname);
			String lnameJson = gson.toJson(lname);
			String amountJson = gson.toJson(amount);
			String dateJson = gson.toJson(date);
			if (result != null)
				res = Json.createObjectBuilder().add("status", true).add("message", "success").add("fname", fnameJson)
						.add("lname", lnameJson).add("id", idJson).add("amount", amountJson).add("date", dateJson);

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		JsonObject jsonObject = res.build();
		StringWriter writer = new StringWriter();
		Json.createWriter(writer).write(jsonObject);
		jsonString = writer.toString();
		return jsonString;
	}
	
	public String addRecord(Records records) {
		Connection connection = myConnection.getConnection();
	
		try {
			
			PreparedStatement pstmt = null;
			String query = null;
			query = "INSERT INTO `records` (`flatNumber`, `amount`, `dateOfPay`,`modeOfPayment`,`paymentReference`) VALUES (?,?,?,?,?);";
			pstmt = connection.prepareStatement(query);

			pstmt.setInt(1, records.getFlatNumber());
			pstmt.setFloat(2, records.getAmount());
			pstmt.setString(3, records.getDateOfPay());
			pstmt.setString(4, records.getModeOfPayment());
			pstmt.setString(5, records.getPaymentReference());
//			System.out.println(pstmt);
			int result = pstmt.executeUpdate();
			connection.close();
			if (result > 0) {
				return "success";
			} else {
				return "failed";
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return "error";
	}
	
	public String updateRecord() {
//		String rid = formData.getFirst("rid");
////		String uid = formData.getFirst("uid");
//		String amount = formData.getFirst("amount");
//		String date = formData.getFirst("date");
		DataSource ds;
		Connection con;
		try {
			Context ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:comp/env/jdbc/jit");
			con = ds.getConnection();
			PreparedStatement pstmt = null;
			String query = null;
			query = "UPDATE `records` SET `amount`=?, `date_of_pay`=? WHERE `record_id`=?";
			pstmt = con.prepareStatement(query);

			pstmt.setString(1, amount);
			pstmt.setString(2, date);
			pstmt.setString(3, rid);
			System.out.println(pstmt);
			int result = pstmt.executeUpdate();
			con.close();
			if (result > 0) {
				return "success";
			} else {
				return "failed";
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return "error";
	}


}
