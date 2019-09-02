package com.company.service;

import com.company.Exception.ResourceNotFoundException;
import com.company.model.Company;
import com.company.repository.CompanyRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsumerServiceImp implements ConsumerService {

    @Autowired
    private CompanyRepository companyRepository;

    @Override
    @RabbitListener(queues = "${rabbitmq.queue}")
    public Object consumerMessage(long msg) {
        System.out.println("=============== Message ==================");
        System.out.println(msg);
        System.out.println("==========================================");
        Company company=null;
        try {
            company = companyRepository.findById(msg).orElseThrow(() -> new ResourceNotFoundException("Company", "id", msg));
        }catch (Exception e){ }

        if(company==null){
            return null;
        }
        else{
            ObjectMapper obj = new ObjectMapper();
            try {
                String pro = obj.writeValueAsString(company);
                return pro;
            }catch(JsonProcessingException e){
                return null;
            }
        }
    }
}
