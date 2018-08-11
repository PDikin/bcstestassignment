package bcs.service.impl;

import bcs.model.*;
import bcs.service.ReportService;
import org.decimal4j.util.DoubleRounder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashSet;

@Service
public class ReportServiceImpl implements ReportService {

    public Report report(Portfolio stocks){
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
        return report;
    }


    public Quote iextradingData(String symbol) {
        /**
         * Берем данные об акции по ее названию
         * */
        RestTemplate restTemplate = new RestTemplate();
        Quote quote = restTemplate.getForObject("https://api.iextrading.com/1.0/stock/"+symbol+"/quote", Quote.class);
        return quote;
    }
}
