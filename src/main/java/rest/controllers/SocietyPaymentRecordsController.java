package rest.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import rest.Beans.SocietyPaymentRecords;
import rest.dao.SocietyPaymentRecordsDao;
@RestController
@CrossOrigin
public class SocietyPaymentRecordsController {
    SocietyPaymentRecordsDao societyPaymentRecordsDao=new SocietyPaymentRecordsDao() ;
	
	@RequestMapping(value = "/society",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String loginUser(HttpServletRequest request,@RequestParam String type) {
            
		String result=societyPaymentRecordsDao.getRecords(type);
		return result;
	}
	
	@RequestMapping(value = "/addSocietyRecord",produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public String addSocietyRecord(@RequestParam String expenseType, @RequestParam String amount, @RequestParam String dateOfPay,@RequestParam String modeOfPayment,@RequestParam String expenseDescription,@RequestParam String paymentReference) {
		
		SocietyPaymentRecords societyPaymentRecords=new SocietyPaymentRecords(expenseType,Float.parseFloat(amount),dateOfPay,modeOfPayment, expenseDescription, paymentReference);
		String result=societyPaymentRecordsDao.addRecord(societyPaymentRecords);
		return result;
	}
}