package bcs.model;

import lombok.Data;

import java.util.ArrayList;

@Data
public class Report {
    private Double value;
    private ArrayList<Allocation> allocations;

}
