package bcs.controller;

import bcs.model.*;
import org.decimal4j.util.DoubleRounder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;

@RestController
public class MainController {

    @RequestMapping(value = "/start", method = RequestMethod.POST)
    public Report latestPriceAndSector(@RequestBody Portfolio stocks){

        /**
         * Для подсчета стоимости портфеля
         * */
        Double sum = 0.0;

        /**
         * Названия секторов
         * */
        HashSet<String> sectorNames = new HashSet();
        /**
         * Для всех акций из портфеля делаем запрос iextradingData и проставляем им цену,
         * сектор и полную стоимость (количество * цену)
         * */
        for (Stock s: stocks.getStocks()
             ) {
            Quote quote = iextradingData(s.getSymbol());
            s.setLatestPrice(quote.getLatestPrice());
            s.setSector(quote.getSector());
            s.setAssetValue(s.getVolume()*s.getLatestPrice());
            sectorNames.add(s.getSector());
            sum+=s.getAssetValue();
        }
        stocks.setValue(sum);

        /**
         * Создание ответа
         * */
        Report report = new Report();
        ArrayList<Allocation> allocationList = new ArrayList();
        report.setValue(DoubleRounder.round(sum, 2));
        for (String sector: sectorNames
             ) {
            Double assetValue = 0.0;
            Allocation allocation = new Allocation();
            allocation.setSector(sector);
            for (Stock s: stocks.getStocks()){
                if(sector.equals(s.getSector())){
                    assetValue+=s.getAssetValue();
                }
            }
            allocation.setAssetValue(DoubleRounder.round(assetValue,2));
            allocation.setProportion(DoubleRounder.round(assetValue/sum,2));
            allocationList.add(allocation);
        }
        report.setAllocations(allocationList);

        for (Stock s: stocks.getStocks()
             ) {
            System.out.println(s.getLatestPrice()+" -- "+s.getSector()+" -- "+s.getAssetValue());
        }
        System.out.println(stocks.getValue());
        for (String s: sectorNames
             ) {
            System.out.println(s);
        }

        return report;
    }

    @RequestMapping(value = "/report", method = RequestMethod.POST)
    public void report(@RequestBody Portfolio stocks){
        for (Stock s: stocks.getStocks()
                ) {
            System.out.println(s.getLatestPrice()+" -- "+s.getAssetValue());
        }

    }



    @GetMapping("/quote/{symbol}")
    public Quote iextradingData(@PathVariable String symbol) {
        /**
         * Берем данные об акции по ее названию
         * */
        RestTemplate restTemplate = new RestTemplate();
        Quote quote = restTemplate.getForObject("https://api.iextrading.com/1.0/stock/"+symbol+"/quote", Quote.class);
        return quote;
    }
}
