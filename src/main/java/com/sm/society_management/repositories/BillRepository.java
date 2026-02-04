package com.sm.society_management.repositories;

import com.sm.society_management.models.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BillRepository extends JpaRepository<Bill,Integer> {
    List<Bill> findByUserId(int userId);

    @Query("SELECT COALESCE(SUM(b.amount),0) FROM Bill b WHERE b.status='PAID'")
    double sumPaidAmount();

    @Query("SELECT COALESCE(SUM(b.amount),0) FROM Bill b WHERE b.status='UNPAID'")
    double sumPendingAmount();
}
