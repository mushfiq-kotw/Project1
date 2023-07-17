package com.example.demo;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("Login")
public class LoginController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserDetailRepository userDetailRepository;
	@Autowired
	private AddressRepository addressRepository;
	
	@GetMapping("Registration")
	public String Registration(@RequestBody RegisterRequest registerRequest) {
		User user = new User();
		user.setUsername(registerRequest.getUsername());
		user.setPassword(registerRequest.getPassword());
		userRepository.save(user);
		Address address = new Address();
		address.setStreet(registerRequest.getStreet());
		address.setStreet(registerRequest.getStreet());
		address.setCity(registerRequest.getCity());
		address.setPincode(registerRequest.getPincode());
		addressRepository.save(address);
		UserDetail userDetail = new UserDetail();
		userDetail.setUser(user);
		userDetail.setAddress(address);
		userDetail.setFirstName(registerRequest.getFirstName());
		userDetail.setLastName(registerRequest.getLastName());
		userDetail.setDob(registerRequest.getDob());
		userDetail.setDesignation(registerRequest.getDesignation());
		userDetail.setEmail(registerRequest.getEmail());
		userDetail.setGender(registerRequest.getGender());
		userDetailRepository.save(userDetail);
		return "Registration successful";
	}

	@GetMapping("Login")
	public String Validation(@RequestBody User user) {
		Optional<User> optuser = userRepository.findByUsername(user.getUsername());
		if (optuser.isPresent()) {
			if (optuser.get().getPassword().equals(user.getPassword()))
				return "Validated";
			else
				return "Password Incorrect!";
		} else
			return "User Not Found";
	}

	@GetMapping("ChangePassword")
	public String changePassword(@RequestBody NewPassword npwd) {
		Optional<User> optuser = userRepository.findByUsername(npwd.getUsername());
		if ((optuser.isPresent()) && (optuser.get().getPassword().equals(npwd.getOldpwd()))) {
			optuser.get().setPassword(npwd.getNewpwd());
			userRepository.save(optuser.get());
			return "Password changed successfully";
		} else
			return "User does not exist or old password is incorrect!";
	}

	@GetMapping("Modify")
	public String updateUser(@RequestBody UserDetailMod musde) {
		Optional<User> optuser = userRepository.findByUsername(musde.getUsername());
		if (optuser.isPresent()) {
			UserDetail usde = userDetailRepository.findById(optuser.get().getId()).get();
			usde.setFirstName(musde.getFirstName());
			usde.setLastName(musde.getLastName());
			usde.setDob(musde.getDob());
			usde.setDesignation(musde.getDesignation());
			usde.setEmail(musde.getEmail());
			usde.setGender(musde.getGender());
			userDetailRepository.save(usde);
			Address adrs = addressRepository.findById(userRepository.findByUsername(musde.getUsername()).get().getId())
					.get();
			adrs.setCity(musde.getCity());
			adrs.setPincode(musde.getPincode());
			adrs.setStreet(musde.getStreet());
			addressRepository.save(adrs);
			return "Update successful";
		} else
			return "User not recognized.";

	}
}
