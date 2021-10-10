package rest.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import rest.Beans.UserPaymentRecords;
import rest.dao.MemberPaymentRecordsDao;


@CrossOrigin(originPatterns = "*")
@Controller
public class MemberPaymentRecordsController {
	
	@Autowired
	MemberPaymentRecordsDao recordsDao;
	
	@GetMapping("/testsession")
	@ResponseBody
	public String Abc(HttpServletRequest request) {
		String role = (String) request.getSession().getAttribute("role");
		return role;
	}
	
	@RequestMapping(value = "/getAllRecords",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String getAllRecords() {
           // System.out.println(flatNumber+" "+type);
		String result=recordsDao.getAllRecords();
		return result;
	}
	

	@RequestMapping(value = "/getRecords",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String loginUser(HttpServletRequest request,@RequestParam String flatNumber,@RequestParam String type) {
           // System.out.println(flatNumber+" "+type);
		String result=recordsDao.getRecords(flatNumber,type);
		return result;
	}

	@RequestMapping(value = "/addUserRecord",produces = MediaType.APPLICATION_JSON_VALUE )
	@ResponseBody
	public String addUserRecord(@RequestParam String flatNumber, @RequestParam String amount, @RequestParam String dateOfPay,@RequestParam String modeOfPayment,@RequestParam String paymentReference) {
//		String uid = formData.getFirst("uid");
//		String amount = formData.getFirst("amount");
//		String date = formData.getFirst("date");
		
		UserPaymentRecords records=new UserPaymentRecords(Integer.parseInt(flatNumber),Float.parseFloat(amount),dateOfPay,modeOfPayment,paymentReference);
		String result=recordsDao.addRecord(records);
		
		return result;
	}

	@RequestMapping(value = "/updateUserRecord", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String updateUserRecord(@RequestParam String recordId,@RequestParam String flatNumber, @RequestParam String amount, @RequestParam String dateOfPay,@RequestParam String modeOfPayment,@RequestParam String paymentReference) {
//		String rid = formData.getFirst("rid");
////		String uid = formData.getFirst("uid");
//		String amount = formData.getFirst("amount");
//		String date = formData.getFirst("date");
		
		
		UserPaymentRecords records=new UserPaymentRecords(Integer.parseInt(recordId),Integer.parseInt(flatNumber),Float.parseFloat(amount),dateOfPay,modeOfPayment,paymentReference);
		String result=recordsDao.updateRecord(records);
		
		return result;
	}

	@RequestMapping(value = "/deleteUserRecord",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String deleteUserRecord(@RequestParam String recordId) {
		
		String result=recordsDao.deleteRecord(recordId);
		return result;
	}

	
	
	
}
