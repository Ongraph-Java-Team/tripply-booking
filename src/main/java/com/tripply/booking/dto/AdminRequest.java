package com.tripply.booking.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class AdminRequest {

    @Column(name = "admin_email")
    private String adminEmail;

    @Column(name = "admin_first_name")
    private String adminFirstName;
    

	@Column(name = "admin_last_name")
    private String adminLastName;

    @Column(name = "admin_phone_number")
    private String adminPhoneNumber;

    public String getAdminEmail() {
		return adminEmail;
	}

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}

	public String getAdminFirstName() {
		return adminFirstName;
	}

	public void setAdminFirstName(String adminFirstName) {
		this.adminFirstName = adminFirstName;
	}

	public String getAdminLastName() {
		return adminLastName;
	}

	public void setAdminLastName(String adminLastName) {
		this.adminLastName = adminLastName;
	}

	public String getAdminPhoneNumber() {
		return adminPhoneNumber;
	}

	public void setAdminPhoneNumber(String adminPhoneNumber) {
		this.adminPhoneNumber = adminPhoneNumber;
	}


}
