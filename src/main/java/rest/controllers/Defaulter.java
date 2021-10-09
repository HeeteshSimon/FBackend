package rest.controllers;

import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@CrossOrigin(originPatterns = "*")
@RestController
@RequestMapping("/def")
public class Defaulter {
	@GetMapping(value = "/all", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getList() {
		DataSource ds;
		Connection con;
		JsonObjectBuilder res = Json.createObjectBuilder();
		ArrayList<String> user_id = new ArrayList<String>();
		ArrayList<String> first_name = new ArrayList<String>();
		ArrayList<String> last_name = new ArrayList<String>();
		ArrayList<String> password = new ArrayList<String>();
		ArrayList<String> email = new ArrayList<String>();
		ArrayList<String> role = new ArrayList<String>();
		GsonBuilder gsonBuilder = new GsonBuilder();
		Gson gson = gsonBuilder.create();
		try {
			Context ic = new InitialContext();
			ds = (DataSource) ic.lookup("java:comp/env/jdbc/jit");
			con = ds.getConnection();
			ArrayList<Integer> User_Ids = new ArrayList();

			PreparedStatement pstmt1 = null;
			PreparedStatement pstmt2 = null;
			PreparedStatement pstmt3 = null;
			String query1 = null;
			String query2 = null;
			String query3 = null;
			ArrayList<ArrayList<String>> map = new ArrayList();
			ArrayList<ArrayList<ArrayList<String>>> AllDefaultors = new ArrayList();
			Float Amount;
			Date date = new Date();
			LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			int month = localDate.getMonthValue();
			int year = localDate.getYear();
			query1 = "SELECT user_id FROM Users";
			pstmt1 = con.prepareStatement(query1);
			ResultSet result = pstmt1.executeQuery(query1);
			System.out.println(result);
			while (result.next()) {
// con.close();
				User_Ids.add(result.getInt(1));
// return "{status: true, message: success, batchId: "+batchMapped+"}";
			}

			for (int i = 1; i < User_Ids.size(); i++) {
				ArrayList<Integer> Months = new ArrayList();
				ArrayList<Integer> DiffMonths = new ArrayList();
				ArrayList<String> mapValues = new ArrayList();
//HashMap<String, ArrayList<String>> map = new HashMap();

				query2 = "SELECT MONTH(date_of_pay) from Records where year(date_of_pay)=" + year + " and user_id="
						+ User_Ids.get(i);
				pstmt2 = con.prepareStatement(query2);
// pstmt2.setInt(1, User_Ids.get(i));
				ResultSet result2 = pstmt2.executeQuery(query2);
				while (result2.next()) {
					Months.add(result2.getInt(1));
				}

				for (int j = 1; j < month; j++) {
					if (!Months.contains(j)) {
						DiffMonths.add(j);
					}
				}
				Amount = (float) (DiffMonths.size() * 0.2 * 6000);
				query3 = "SELECT firstname,lastname FROM Users WHERE user_id=" + User_Ids.get(i);
				pstmt3 = con.prepareStatement(query3);
// pstmt3.setInt(1, User_Ids.get(i));
				ResultSet result3 = pstmt3.executeQuery(query3);
				mapValues.add(String.valueOf(User_Ids.get(i)));
				while (result3.next()) {
					mapValues.add(result3.getString(1));
					mapValues.add(result3.getString(2));
				}
				mapValues.add(String.valueOf(Amount));
				mapValues.add(String.valueOf(DiffMonths.size()));

				if (DiffMonths.size() > 0) {
					map.add(mapValues);
//AllDefaultors.add(map);
				}

			}
			con.close();
			String defJson = gson.toJson(map);

			if (result != null)
				res = Json.createObjectBuilder().add("status", true).add("message", "success").add("Defaultors",
						defJson);
// else {
// con.close();
// res = Json.createObjectBuilder()
// .add("status", false)
// .add("message", "error");
//// return "{status: true, message: batch_id_not_mapped, batchId: not mapped}";
// }
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

}
