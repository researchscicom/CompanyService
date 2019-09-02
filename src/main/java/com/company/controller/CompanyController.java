package com.company.controller;

import com.company.Exception.ResourceNotFoundException;
import com.company.model.Company;
import com.company.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CompanyController {
    @Autowired
    CompanyRepository companyRepository;

    @GetMapping("/company")
    public List<Company> getAllCompanies()
    {
        return companyRepository.findAll();
    }

    @GetMapping("/company/{id}")
    public  Company getCompany(@PathVariable(name = "id") Long id)
    {
        return companyRepository.findById(id) .orElseThrow(() -> new ResourceNotFoundException("Company","id",id));
    }

    @PostMapping("/company")
    public Company saveCompany(@RequestBody Company company)
    {
        return companyRepository.save(company);
    }

    @PutMapping("/company/{id}")
    public Company editCompany(@PathVariable(name = "id") Long id,@RequestBody Company company)
    {
        Company company1 = companyRepository.findById(id) .orElseThrow(() -> new ResourceNotFoundException("Company","id",id));
        company1.setAddress(company.getAddress());
        company1.setEmail(company.getEmail());
        company1.setName(company.getName());
        company1.setPassword(company.getPassword());
        company1.setPhone(company.getPhone());

        Company updatedCompany = companyRepository.save(company1);
        return updatedCompany;
    }

    @DeleteMapping("/company/{id}")
    public ResponseEntity<?> deleteCompany(@PathVariable(name = "id") Long id)
    {
        Company company = companyRepository.findById(id) .orElseThrow(() -> new ResourceNotFoundException("Company","id",id));
        companyRepository.delete(company);
        return ResponseEntity.ok().build();
    }

}
