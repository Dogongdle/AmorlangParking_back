package com.parkinglot.controller;

import com.parkinglot.domain.Response;
import com.parkinglot.dto.ApartDto;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApartController {


    @PostMapping("/verify")
    public Response verifyCode(@RequestBody ApartDto apart){

        Response response=new Response();

        if(apart.getApart().equals("광교아이파크")){
            if (apart.getCode().equals("sayharahihello")){
                response.setResponse("유효");
                response.setMessage("코드가 정확합니다.");
            }
            else{
                response.setResponse("무효");
                response.setMessage("코드가 틀렸습니다.");
            }
        }

        return response;
    }
}
