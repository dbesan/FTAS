package com.targsoftware.FTAS.domain;

import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvDate;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Transaction {
    @CsvBindByName(column = "ID")
    private String id;
    @CsvBindByName(column = "Date")
    @CsvDate("dd/MM/yyyy HH:mm:ss")
    private LocalDateTime date;
    @CsvBindByName(column = "Amount")
    private double amount;
    @CsvBindByName(column = "Merchant")
    private String merchant;
    @CsvBindByName(column = "Type")
    private String type;
    @CsvBindByName(column = "Related Transaction", required = false)
    private String relatedTransaction;
}
