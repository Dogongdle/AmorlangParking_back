package com.parkinglot.controller;

import com.parkinglot.domain.Parking;
import com.parkinglot.dto.ParkingDto;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class ArduinoController {

    @RequestMapping(value = "/test", method = RequestMethod.POST, produces = {"application/json"})
    public @ResponseBody Map<String, Object> parkingData(@RequestBody Map<String, Object> info, Parking parking,
                                    ParkingDto parkingDto, HttpServletRequest request) {

        Map<String, Object> retVal = new HashMap<String, Object>();

        //System.out.println("sector: " + info.get("sector"));
        //System.out.println("number: " + info.get("number"));
        //System.out.println("enable: " + info.get("enable"));

        // 현재 날짜 생성
        Date date = new Date();
        //String str = date.toString();
        SimpleDateFormat formatType = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("yyyy-MM-dd 형식의 현재 날짜: " + formatType.format(date));

        String sector = info.get("sector").toString(); // sector 값
        int number = Integer.parseInt(info.get("number").toString()); // 자리번호
        boolean enable = Boolean.parseBoolean(info.get("enable").toString()); // 주차 가능여부

        // 날짜 + 시간
        String measureTime = formatType.format(date);
        measureTime += " ";
        //measureTime += mtime;

        // DB저장
        Map<String, Object> m = new HashMap<String, Object>();
        parking.setSector(sector);
        parkingDto.setParkingSeat(number);
        parkingDto.setEnable(enable);

        System.out.println("sector: " + sector);
        System.out.println("number: " + number);
        System.out.println("enable: " + enable);
        System.out.println("measureTime: " + measureTime);

        m.put("parking", parking);
        m.put("parkingDto",parkingDto);
        m.put("time", (Object) measureTime);

        //----------------//
        System.out.println("parking: " + parking);
        System.out.println("parkingDto: " + parkingDto);

        System.out.println("========================================");

        retVal.put("result", "success!!");
        System.out.println("retVal: " + retVal);

        return retVal;
    }
}
