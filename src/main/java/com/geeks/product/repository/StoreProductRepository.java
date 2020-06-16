package com.geeks.product.repository;

import com.geeks.product.beans.Store;
import com.geeks.product.beans.StoreProducts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StoreProductRepository extends JpaRepository<StoreProducts, Integer> {
    public List<Store> findByStore(Store store);
}
