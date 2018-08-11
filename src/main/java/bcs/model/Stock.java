package bcs.model;

import lombok.Data;

@Data
public class Stock {
    private String symbol;
    private Integer volume;
    private Double latestPrice;
    private String sector;
    private Double assetValue;
}
