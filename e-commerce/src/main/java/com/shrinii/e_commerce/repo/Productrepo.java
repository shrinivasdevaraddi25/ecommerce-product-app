package com.shrinii.e_commerce.repo;

import com.shrinii.e_commerce.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Productrepo extends JpaRepository<Product, Integer> {
}
