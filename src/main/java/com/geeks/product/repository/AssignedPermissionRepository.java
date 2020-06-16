package com.geeks.product.repository;

import com.geeks.product.beans.AssignedPermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssignedPermissionRepository extends JpaRepository<AssignedPermission, Integer> {
}
