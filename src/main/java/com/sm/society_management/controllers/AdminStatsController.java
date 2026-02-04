package com.sm.society_management.controllers;

import com.sm.society_management.repositories.BillRepository;
import com.sm.society_management.repositories.ComplaintRepository;
import com.sm.society_management.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/admin/stats")
@CrossOrigin(origins = "*")
public class AdminStatsController {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private BillRepository billRepo;
    @Autowired
    private ComplaintRepository complaintRepo;

    @GetMapping("/residents")
    public long totalResidents() {
        return userRepo.count();
    }

    @GetMapping("/bills/paid")
    public double totalPaid() {
        return billRepo.sumPaidAmount();
    }

    // 💸 Total Pending Amount
    @GetMapping("/bills/pending")
    public double totalPending() {
        return billRepo.sumPendingAmount();
    }

    // ⚠️ Complaint Stats
    @GetMapping("/complaints")
    public Map<String, Long> complaintStats() {
        Map<String, Long> map = new HashMap<>();
        map.put("total", complaintRepo.count());
        map.put("pending", complaintRepo.countByStatus("PENDING"));
        map.put("resolved", complaintRepo.countByStatus("RESOLVED"));
        return map;

    }
}

