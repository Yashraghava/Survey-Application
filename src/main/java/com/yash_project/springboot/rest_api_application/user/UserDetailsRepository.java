package com.yash_project.springboot.rest_api_application.user;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailsRepository extends JpaRepository<UserDetails, Long>{

	public List<UserDetails> findByRole(String role);

}
