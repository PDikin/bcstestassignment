package bcs.model;

import lombok.Data;

@Data
public class Allocation {
    private String sector;
    private Double assetValue;
    private Double proportion;
}
