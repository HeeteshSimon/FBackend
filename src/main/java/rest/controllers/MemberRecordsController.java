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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import rest.Beans.Records;
import rest.dao.MemberRecordsDao;

@CrossOrigin(originPatterns = "*")
@Controller
public class MemberRecordsController {
	
	@Autowired
	MemberRecordsDao recordsDao;
	
	@GetMapping("/testsession")
	@ResponseBody
	public String Abc(HttpServletRequest request) {
		String role = (String) request.getSession().getAttribute("role");
		return role;
	}

	@RequestMapping(value = "/getUserRecords", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getById(@RequestParam(defaultValue = "all") String uid,
			@RequestParam(defaultValue = "monthly") String type) {
		
		String result=recordsDao.getById(uid, type);
		return result;
		
		
		
	}

	@RequestMapping(value = "/addUserRecord")
	@ResponseBody
	public String addUserRecord(@RequestParam String flatNumber, @RequestParam String amount, @RequestParam String dateOfPay,@RequestParam String modeOfPayment,@RequestParam String paymentReference) {
//		String uid = formData.getFirst("uid");
//		String amount = formData.getFirst("amount");
//		String date = formData.getFirst("date");
		
		Records records=new Records(Integer.parseInt(flatNumber),Float.parseFloat(amount),dateOfPay,modeOfPayment,paymentReference);
		String result=recordsDao.addRecord(records);
		
		return result;
	}

	@RequestMapping(value = "/updateUserRecord")
	@ResponseBody
	public String updateUserRecord(@RequestParam String recordId, @RequestParam String amount, @RequestParam String dateOfPay,@RequestParam String modeOfPayment,@RequestParam String paymentReference) {
//		String rid = formData.getFirst("rid");
////		String uid = formData.getFirst("uid");
//		String amount = formData.getFirst("amount");
//		String date = formData.getFirst("date");
		
		
		Records records=new Records(Integer.parseInt(recordId) ,Float.parseFloat(amount),dateOfPay,modeOfPayment,paymentReference);

		
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

	@RequestMapping(value = "/deleteUserRecord")
	@ResponseBody
	public String deleteUserRecord(@RequestParam String rid) {
		DataSource ds;
		Connection con;
		try {
			Context ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:comp/env/jdbc/jit");
			con = ds.getConnection();
			PreparedStatement pstmt = null;
			String query = null;
			query = "DELETE FROM `records` WHERE `record_id`=?";
			pstmt = con.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(rid));
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

	@RequestMapping(value = "/getSocietyRecords", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getSocietyRecords(@RequestParam(defaultValue = "monthly") String type) {
		DataSource ds;
		Connection con;
		JsonObjectBuilder res = Json.createObjectBuilder();

		ArrayList<String> date = new ArrayList<String>();
		ArrayList<String> expense_type = new ArrayList<String>();
		ArrayList<String> amount = new ArrayList<String>();

		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		try {
			Context ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:comp/env/jdbc/jit");
			con = ds.getConnection();
			PreparedStatement pstmt = null;
			String query = null;
			if (type.equalsIgnoreCase("yearly"))
				query = "select Case when ROW_NUMBER() over(Partition by year(date_of_pay) order by year(date_of_pay))=1 then year(date_of_pay) else '' end as 'year',expense_type,sum(amount)\r\n"
						+ "from Society group by expense_type,year(date_of_pay) order by year(date_of_pay) desc;"; 
			else
				query = "select Case when ROW_NUMBER() over(Partition by Concat(month(date_of_pay),\"/\",year(date_of_pay)) order by Concat(month(date_of_pay),\"/\",year(date_of_pay)))=1 then Concat(month(date_of_pay),\"/\",year(date_of_pay)) else '' end as 'month' ,expense_type,sum(amount)\r\n"
						+ "from Society group by expense_type,Concat(month(date_of_pay),\"/\",year(date_of_pay)) order by Concat(year(date_of_pay),\"/\",month(date_of_pay)) desc;";
			pstmt = con.prepareStatement(query);
			ResultSet result = pstmt.executeQuery(query);
			while (result.next()) {
				date.add(result.getString(1));
				expense_type.add(result.getString(2));
				amount.add(result.getString(3));
			}
			con.close();
			String dateJson = gson.toJson(date);
			String expenseJson = gson.toJson(expense_type);
			String amountJson = gson.toJson(amount);
			if (result != null)
				res = Json.createObjectBuilder().add("status", true).add("message", "success")
				.add("date", dateJson)
				.add("expense", expenseJson)
				.add("amount", amountJson);

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
	
	@RequestMapping(value = "/addSocietyRecord")
	@ResponseBody
	public String addSocietyRecord(@RequestParam String expense_type, @RequestParam String amount, @RequestParam String date) {
		DataSource ds;
		Connection con;
		try {
			Context ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:comp/env/jdbc/jit");
			con = ds.getConnection();
			PreparedStatement pstmt = null;
			String query = null;
			query = "INSERT INTO `society` (`expense_type`, `amount`, `date_of_pay`) VALUES (?, ?, ?)";
			pstmt = con.prepareStatement(query);

			pstmt.setString(1, expense_type);
			pstmt.setString(2, amount);
			pstmt.setString(3, date);
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
