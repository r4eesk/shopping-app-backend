package com.hbox.shopping.hboxshopping.address;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/address")
public class AddressController {

	@Autowired
	private AddressService addressService;

	@PostMapping("/add/{userId}")
	public ResponseEntity<Object> addNewAddress(@PathVariable String userId, @RequestBody AddressPOJO addressPojo) {
		Address address = addressService.addAddress(userId, addressPojo);
		if (address == null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user id");

		return ResponseEntity.ok(address);
	}

	@GetMapping("/get/{userId}")
	public List<Address> getAllAddressByUser(@PathVariable String userId) {
		List<Address> addresses = addressService.getAllAddressByUser(userId);
		if (addresses == null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user id");
		return addresses;

	}

	@GetMapping("/getbyid/{addressId}")
	public Address getAddressById(@PathVariable Long addressId) {
		Address address = addressService.getAddressById(addressId);
		if (address == null)
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid address id");
		return address;

	}

	@DeleteMapping("/delete/{addressId}")
	public void deleteAddressById(@PathVariable Long addressId) {
		addressService.removeAddressById(addressId);

	}

}
