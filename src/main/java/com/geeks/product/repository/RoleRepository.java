package com.geeks.product.repository;

import com.geeks.product.beans.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public abstract class RoleRepository implements JpaRepository<Role, Integer> {
}
