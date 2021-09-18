package com.kushagra.springdemo;

public class Organization {
	private String companyName;
	private int yearOfIncorporation;
	private String postalCode;
	private String employeeCount;
	
	public Organization(String companyName, int yearOfIncorporation) {
		
		this.companyName = companyName;
		this.yearOfIncorporation = yearOfIncorporation;
	}
	public void coroprateSlogan() {
		String slogan=" We built the ultimate driving machine!";
		System.out.println(slogan);
	}
	
	public String getEmployeeCount() {
		return employeeCount;
	}

	public String getPostalCode() {
		return postalCode;
	}


	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}



	public void setEmployeeCount(String employeeCount) {
		this.employeeCount = employeeCount;
	}
	
	@Override
	public String toString() {
		return "Organization [companyName=" + companyName + ", yearOfIncorporation=" + yearOfIncorporation
				+ ", postalCount=" + postalCode + ", employeeCount=" + employeeCount + "]";
	}
	

}
