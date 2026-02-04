package com.sm.society_management.controllers;

import com.sm.society_management.ai.AiCategorizerService;
import com.sm.society_management.dto.ComplaintDto;
import com.sm.society_management.models.Complaint;
import com.sm.society_management.models.User;
import com.sm.society_management.repositories.ComplaintRepository;
import com.sm.society_management.repositories.UserRepo;
import org.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class ComplaintController {

    @Autowired
    private AiCategorizerService aiService;

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private UserRepo userRepo;

    // ===============================
    // RESIDENT FILES COMPLAINT
    // ===============================
    @PostMapping("/resident/complaint")
    public String raiseComplaint(@RequestBody ComplaintDto dto,
                                 Authentication auth) throws Exception {

        // ✅ Get logged-in user from JWT
        String email = auth.getName();
        User user = userRepo.findByEmail(email);

        Complaint c = new Complaint();

        c.setUserId(user.getId());   // ✅ correct source of userId
        c.setTitle(dto.getTitle());
        c.setDescription(dto.getDescription());
        c.setStatus("PENDING");
        c.setDate(LocalDate.now());

        // 🔥 AI categorization
        JSONObject aiResult = aiService.categorize(dto.getTitle(), dto.getDescription());
        c.setCategory(aiResult.getString("category"));
        c.setPriority(aiResult.getString("priority"));

        complaintRepository.save(c);

        return "Complaint submitted with AI categorization";
    }

    // ===============================
    // RESIDENT VIEWS OWN COMPLAINTS
    // ===============================
    @GetMapping("/resident/complaints")
    public List<Complaint> getComplaints(Authentication auth) {

        String email = auth.getName();
        User user = userRepo.findByEmail(email);

        return complaintRepository.findByUserId(user.getId());
    }

    // ===============================
    // ADMIN VIEWS ALL COMPLAINTS
    // ===============================
    @GetMapping("/admin/complaints")
    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    // ===============================
    // ADMIN RESOLVES COMPLAINT
    // ===============================
    @PutMapping("/admin/resolve-complaint/{id}")
    public String resolveComplaint(@PathVariable int id) {

        Complaint c = complaintRepository.findById(id).orElse(null);
        if(c == null) return "Complaint not found";

        c.setStatus("RESOLVED");
        complaintRepository.save(c);

        return "Complaint resolved";
    }
}
