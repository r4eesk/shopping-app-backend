package com.hbox.shopping.hboxshopping.address;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hbox.shopping.hboxshopping.user.User;

public interface AddressRepository extends JpaRepository<Address, Long> {
	List<Address> findByUser(User user);
}
