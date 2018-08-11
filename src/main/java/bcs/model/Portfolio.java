package bcs.model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Portfolio {
    private ArrayList<Stock> stocks;
    private Double value;
}
