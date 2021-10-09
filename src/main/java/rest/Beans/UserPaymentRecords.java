package rest.Beans;

public class UserPaymentRecords {
	private int recordId;
	private int flatNumber;
	private float amount;
	private String firstName;
	private String lastName;
	private String dateOfPay;
	private String modeOfPayment;
	private String paymentReference;
	
	
	

	
	public UserPaymentRecords() {
		super();
	}

	public UserPaymentRecords(int flatNumber, float amount, String dateOfPay, String modeOfPayment, String paymentReference) {
		super();
		this.flatNumber = flatNumber;
		this.amount = amount;
		this.dateOfPay = dateOfPay;
		this.modeOfPayment = modeOfPayment;
		this.paymentReference = paymentReference;
	}

	public UserPaymentRecords(int recordId,int flatNumber, float amount, String dateOfPay, String modeOfPayment, String paymentReference) {
		super();
		this.recordId = recordId;
		this.flatNumber = flatNumber;
		this.amount = amount;
		this.dateOfPay = dateOfPay;
		this.modeOfPayment = modeOfPayment;
		this.paymentReference = paymentReference;
	}
	
	public UserPaymentRecords(int recordId,int flatNumber, String modeOfPayment, String paymentReference, float amount, String firstName, String lastName, String date) {
        this.flatNumber = flatNumber;
        this.modeOfPayment = modeOfPayment;
        this.paymentReference = paymentReference;
        this.amount = amount;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfPay = date;
        this.recordId =recordId;
    }

	public int getRecordId() {
		return recordId;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

	public int getFlatNumber() {
		return flatNumber;
	}

	public void setFlatNumber(int flatNumber) {
		this.flatNumber = flatNumber;
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
	

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setDateOfPay(String dateOfPay) {
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

}
