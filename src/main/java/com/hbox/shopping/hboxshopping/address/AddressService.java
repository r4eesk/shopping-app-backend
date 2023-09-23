package com.hbox.shopping.hboxshopping.address;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbox.shopping.hboxshopping.user.User;
import com.hbox.shopping.hboxshopping.user.UserRepository;

@Service
public class AddressService {

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private UserRepository userRepository;

	public Address addAddress(String userId, AddressPOJO addressPojo) {
		User user = userRepository.findById(userId).orElse(null);
		if (user == null)
			return null;

		Address address = new Address();
		address.setId(addressPojo.id());
		address.setUser(user);
		address.setFullName(addressPojo.fullName());
		address.setAddressLine1(addressPojo.addressLine1());
		address.setAddressLine2(addressPojo.addressLine2());
		address.setCity(addressPojo.city());
		address.setPincode(addressPojo.pincode());
		address.setType(addressPojo.type());

		addressRepository.save(address);
		return address;
	}

	public List<Address> getAllAddressByUser(String userId) {
		User user = userRepository.findById(userId).orElse(null);
		if (user == null)
			return null;
		return addressRepository.findByUser(user);
	}

	public Address getAddressById(Long id) {
		return addressRepository.findById(id).orElse(null);
	}

	public void removeAddressById(Long id) {
		addressRepository.deleteById(id);
	}

}

record AddressPOJO(long id, String fullName, String addressLine1, String addressLine2, String city, String pincode,
		AddressType type) {
}
