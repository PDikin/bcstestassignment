package bcs.model;

import lombok.Data;

@Data
public class Quote {
    private String symbol;
    private String companyName;
    private String primaryExchange;
    private String sector;
    private String calculationPrice;
    private int open;
    private long openTime;
    private double close;
    private long closeTime;
    private double high;
    private double low;
    private double latestPrice;
    private String latestSource;
    private String latestTime;
    private long latestUpdate;
    private int latestVolume;
    private double iexRealtimePrice;
    private int iexRealtimeSize;
    private long iexLastUpdated;
    private double delayedPrice;
    private long delayedPriceTime;
    private double extendedPrice;
    private double extendedChange;
    private double extendedChangePercent;
    private long extendedPriceTime;
    private double previousClose;
    private double change;
    private double changePercent;
    private double iexMarketPercent;
    private int iexVolume;
    private int avgTotalVolume;
    private double iexBidPrice;
    private int iexBidSize;
    private double iexAskPrice;
    private int iexAskSize;
    private long marketCap;
    private double peRatio;
    private double week52High;
    private double week52Low;
    private double ytdChange;
}
