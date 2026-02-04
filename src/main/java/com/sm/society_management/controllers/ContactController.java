package com.sm.society_management.controllers;

import com.sm.society_management.dto.ContactDto;
import com.sm.society_management.models.Contact;
import com.sm.society_management.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@RestController
@CrossOrigin(origins = "*")
public class ContactController {
    @Autowired
    ContactRepository contactRepository;

    @PostMapping("/admin/contact")
    public String addContact(@RequestBody ContactDto dto)
    {
        Contact c = new Contact();
        c.setName(dto.getName());
        c.setPhone(dto.getPhone());
        c.setEmail(dto.getEmail());
        c.setCategory(dto.getCategory());

        contactRepository.save(c);
        return "Contact Added";
    }

    @GetMapping("/admin/contacts")
    public List<Contact> getAllContacts()
    {
        return  contactRepository.findAll();
    }

    @GetMapping("/resident/contacts")
    public List<Contact> getResidentContacts(){
        return contactRepository.findAll();
    }
    @DeleteMapping("/admin/contact/{id}")
    public String deleteContact(@PathVariable int id){
        if(!contactRepository.existsById(id)){
            return "Contact not found";
        }
        contactRepository.deleteById(id);
        return "Contact deleted";
    }

    @PutMapping("/admin/contact/{id}")
    public String updateContact(@PathVariable int id, @RequestBody ContactDto dto)
    {
        Contact c = contactRepository.findById(id).orElse(null);
        if(c==null)
        {
            return "Contact not found";
        }
        c.setName(dto.getName());
        c.setPhone(dto.getPhone());
        c.setEmail(dto.getEmail());
        c.setCategory(dto.getCategory());

        contactRepository.save(c);
        return "Contact updated";
    }
}
