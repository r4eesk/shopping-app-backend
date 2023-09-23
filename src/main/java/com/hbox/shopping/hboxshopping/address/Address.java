package com.hbox.shopping.hboxshopping.address;

import com.hbox.shopping.hboxshopping.user.User;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;

@Entity
public class Address {

	public Address() {
		super();
	}

	public Address(long id, User user, String fullName, String addressLine1, String addressLine2, String city,
			String pincode, AddressType type) {
		super();
		this.id = id;
		this.user = user;
		this.fullName = fullName;
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.pincode = pincode;
		this.type = type;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_sequence")
	@SequenceGenerator(name = "address_sequence", sequenceName = "address_sequence", allocationSize = 1)
	private long id;

	@ManyToOne
	private User user;

	private String fullName;

	private String addressLine1;

	private String addressLine2;

	private String city;

	private String pincode;

	private AddressType type;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getAddressLine1() {
		return addressLine1;
	}

	public void setAddressLine1(String addressLine1) {
		this.addressLine1 = addressLine1;
	}

	public String getAddressLine2() {
		return addressLine2;
	}

	public void setAddressLine2(String addressLine2) {
		this.addressLine2 = addressLine2;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public AddressType getType() {
		return type;
	}

	public void setType(AddressType type) {
		this.type = type;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
}

enum AddressType {
	HOME, OFFICE, OTHER
}
