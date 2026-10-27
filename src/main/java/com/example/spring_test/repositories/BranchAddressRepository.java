package com.example.spring_test.repositories;

import com.example.spring_test.models.BranchAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BranchAddressRepository extends JpaRepository<BranchAddress, Long> {
}
