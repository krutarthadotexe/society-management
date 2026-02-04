package com.sm.society_management.controllers;

import com.sm.society_management.dto.BillDto;
import com.sm.society_management.models.Bill;
import com.sm.society_management.models.User;
import com.sm.society_management.repositories.BillRepository;
import com.sm.society_management.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")

public class BillController {
    @Autowired
    BillRepository billRepository;

    @Autowired
    private UserRepo userRepo;

    //admin creates a bill
    @PostMapping("/admin/create-bill")
    public String createBill(@RequestBody BillDto dto)
    {
        Bill bill = new Bill();

        bill.setUserId(dto.getUserId());
        bill.setBillType(dto.getBillType());
        bill.setAmount(dto.getAmount());
        bill.setDueDate(dto.getDueDate());
        bill.setStatus("UNPAID");

        billRepository.save(bill);
        return "Bill created successfully";
    }

    // ✅ Resident views own bills (JWT-based)
    @GetMapping("/resident/bills")
    public List<Bill> getBills(Authentication auth) {

        String email = auth.getName();     // extracted from JWT
        User user = userRepo.findByEmail(email);

        return billRepository.findByUserId(user.getId());
    }

    // ✅ (Optional) Admin manually marks paid (backup feature)
    @PutMapping("/admin/pay-bill/{billId}")
    public String markPaid(@PathVariable int billId) {

        Bill bill = billRepository.findById(billId).orElse(null);

        if(bill == null){
            return "Bill not found";
        }

        bill.setStatus("PAID");
        billRepository.save(bill);
        return "Bill marked as paid";
    }
}
