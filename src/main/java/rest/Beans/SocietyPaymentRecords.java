package rest.Beans;

public class SocietyPaymentRecords {

	private String month;
    private String expenseType;
    private float amount;
    private String dateOfPay;
    private String modeOfPayment;
    private String paymentReference;
    private String expenseDescription;
    private String expenseId;
    private String year;
    
   

   public SocietyPaymentRecords(String expenseType, float amount, String dateOfPay, String modeOfPayment,
			String paymentReference, String expenseDescription) {
		super();
		this.expenseType = expenseType;
		this.amount = amount;
		this.dateOfPay = dateOfPay;
		this.modeOfPayment = modeOfPayment;
		this.paymentReference = paymentReference;
		this.expenseDescription = expenseDescription;
	}

public String getYear() {
       return year;
   }

   public void setYear(String year) {
       this.year = year;
   }

   public String getExpenseId() {
       return expenseId;
   }

   public void setExpenseId(String expenseId) {
       this.expenseId = expenseId;
   }

   public String getMonth() {
       return month;
   }

   public void setMonth(String month) {
       this.month = month;
   }

   public String getExpenseType() {
       return expenseType;
   }

   public void setExpenseType(String expenseType) {
       this.expenseType = expenseType;
   }

   public float getAmount() {
       return amount;
   }

   public void setAmount(float amount) {
       this.amount = amount;
   }

   public String getDateOfPay() {
       return dateOfPay;
   }

   public void setDate(String dateOfPay) {
       this.dateOfPay = dateOfPay;
   }

   public String getModeOfPayment() {
       return modeOfPayment;
   }

   public void setModeOfPayment(String modeOfPayment) {
       this.modeOfPayment = modeOfPayment;
   }

   public String getPaymentReference() {
       return paymentReference;
   }

   public void setPaymentReference(String paymentReference) {
       this.paymentReference = paymentReference;
   }

   public String getExpenseDescription() {
       return expenseDescription;
   }

   public void setExpenseDescription(String expenseDescription) {
       this.expenseDescription = expenseDescription;
   }

   
   
     public SocietyPaymentRecords(){
         
     }
}
