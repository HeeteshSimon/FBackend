package rest.Beans;

public class Records {
	private int recordId;
	private int flatNumber;
	private float amount;
	private String dateOfPay;
	private String modeOfPayment;
	private String paymentReference;

	public Records(int recordId, float amount, String dateOfPay, String modeOfPayment, String paymentReference) {
		super();
		this.recordId = recordId;
		this.amount = amount;
		this.dateOfPay = dateOfPay;
		this.modeOfPayment = modeOfPayment;
		this.paymentReference = paymentReference;
	}

	public Records(int flatNumber, float amount, String dateOfPay, String modeOfPayment, String paymentReference) {
		super();
		this.flatNumber = flatNumber;
		this.amount = amount;
		this.dateOfPay = dateOfPay;
		this.modeOfPayment = modeOfPayment;
		this.paymentReference = paymentReference;
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
