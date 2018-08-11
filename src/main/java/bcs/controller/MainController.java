package bcs.controller;

import bcs.model.*;
import bcs.service.ReportService;
import org.decimal4j.util.DoubleRounder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;

@RestController
public class MainController {

    @Autowired
    ReportService reportService;

    @RequestMapping(value = "/start", method = RequestMethod.POST)
    public Report latestPriceAndSector(@RequestBody Portfolio stocks){
        return reportService.report(stocks);
    }


}
