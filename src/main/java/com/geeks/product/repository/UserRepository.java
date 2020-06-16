package com.geeks.product.repository;

import com.geeks.product.beans.UserBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserBean, Integer> {

    public UserBean findByEmail(String email);
}
