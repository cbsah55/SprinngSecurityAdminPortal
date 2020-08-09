package com.bookstoreadmin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bookstoreadmin.domains.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByemail(String email);

}
