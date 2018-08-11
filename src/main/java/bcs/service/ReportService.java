package bcs.service;

import bcs.model.Portfolio;
import bcs.model.Report;


public interface ReportService {
    Report report(Portfolio stocks);
}
