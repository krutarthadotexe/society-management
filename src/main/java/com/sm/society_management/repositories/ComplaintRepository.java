package com.sm.society_management.repositories;

import com.sm.society_management.models.Complaint;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplaintRepository extends JpaRepository<Complaint,Integer> {
    List<Complaint>findByUserId(int userId);

    long countByStatus(String status);

}
