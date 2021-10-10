package rest.Beans;


public class Member {
	
	private int flatNumber;
	private  String userName;
	private String firstName ;
	private String lastName ;
	private String	userPassword;
	private String emailId ;
	private String userRole ;
	private int memberCount;
	private String membershipJoin ;
	private String membershipEnd ;
	
	
	public Member() {
		super();
	}
	
	
	public Member(int flatNumber, String userName, String firstName, String lastName, String userPassword,
			String emailId, String userRole, int memberCount, String membershipJoin, String membershipEnd) {
		super();
		this.flatNumber = flatNumber;
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userPassword = userPassword;
		this.emailId = emailId;
		this.userRole = userRole;
		this.memberCount = memberCount;
		this.membershipJoin = membershipJoin;
		this.membershipEnd = membershipEnd;
	}
	
	public Member(int flatNumber, String userName, String firstName, String lastName, String userPassword,
			String emailId, int memberCount, String membershipJoin, String membershipEnd) {
		super();
		this.flatNumber = flatNumber;
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userPassword = userPassword;
		this.emailId = emailId;
		this.memberCount = memberCount;
		this.membershipJoin = membershipJoin;
		this.membershipEnd = membershipEnd;
	}
	
	
	

	public int getFlatNumber() {
		return flatNumber;
	}
	public void setFlatNumber(int flatNumber) {
		this.flatNumber = flatNumber;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
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
	public String getUserPassword() {
		return userPassword;
	}
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	public String getUserRole() {
		return userRole;
	}
	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}
	public int getMemberCount() {
		return memberCount;
	}
	public void setMemberCount(int memberCount) {
		this.memberCount = memberCount;
	}
	public String getMembershipJoin() {
		return membershipJoin;
	}
	public void setMembershipJoin(String membershipJoin) {
		this.membershipJoin = membershipJoin;
	}
	public String getMembershipEnd() {
		return membershipEnd;
	}
	public void setMembershipEnd(String membershipEnd) {
		this.membershipEnd = membershipEnd;
	} 
	
	

}
