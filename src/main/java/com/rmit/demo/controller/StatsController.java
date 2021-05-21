package com.rmit.demo.controller;

import com.rmit.demo.service.StatsService;
import com.rmit.demo.utils.DateUtils;
import com.rmit.demo.utils.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping(path = "/stats")
public class StatsController {

    @Autowired
    private StatsService statsService;

    // RETRIEVE REVENUE of A Customer in a period
    @RequestMapping(path = "/customers/{id}/revenue/filter", method = RequestMethod.GET, params = {"startDate", "endDate"})
    public ResponseEntity<Object> getRevenueByCustomerAndPeriod(@PathVariable int id,
                                                                @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date startDate,
                                                                @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate) {
        String startDateStr = DateUtils.dateToString(startDate);
        String endDateStr = DateUtils.dateToString(endDate);
        double totalRevenue = statsService.getRevenueByCustomerAndPeriod(id, startDate, endDate);
        return ResponseHandler.generateResponse(HttpStatus.OK, true,
                String.format("/sale-invoices/by-customer/%d/revenue/filter?startDate=%s&endDate=%s", id, startDateStr, endDateStr),
                String.format("Total Revenue of Customer %d is %.2f between %s and %s", id, totalRevenue, startDateStr, endDateStr), totalRevenue);
    }

    // RETRIEVE REVENUE of A Staff in a period
    @RequestMapping(path = "/staffs/{id}/revenue/filter", method = RequestMethod.GET, params = {"startDate", "endDate"})
    public ResponseEntity<Object> getRevenueByStaffAndPeriod(@PathVariable int id,
                                                             @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date startDate,
                                                             @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") Date endDate) {
        String startDateStr = DateUtils.dateToString(startDate);
        String endDateStr = DateUtils.dateToString(endDate);
        double totalRevenue = statsService.getRevenueByStaffAndPeriod(id, startDate, endDate);
        return ResponseHandler.generateResponse(HttpStatus.OK, true,
                String.format("/sale-invoices/by-staff/%d/revenue/filter?startDate=%s&endDate=%s", id, startDateStr, endDateStr),
                String.format("Total Revenue of Staff %d is %.2f between %s and %s", id, totalRevenue, startDateStr, endDateStr), totalRevenue);
    }
}
