package com.targsoftware.FTAS.core;

import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.bean.CsvToBeanBuilder;
import com.targsoftware.FTAS.domain.Transaction;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDateTime;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

import static com.targsoftware.FTAS.constants.Constants.PAYMENT;

public class CoreMethods {
    /**
     * OptionalDouble averageAmount(List<Transaction> filteredByParams)
     * return OptionalDouble with average amount
     */
    public static OptionalDouble averageAmount(List<Transaction> filteredByParams) {
        return filteredByParams.stream().mapToDouble(Transaction::getAmount).average();
    }

    /**
     * List<Transaction> filterTransactionsMerchantDateTime(List<Transaction> transactions, LocalDateTime fromDateTime, LocalDateTime toDateTime, String merchant)
     * return list of transaction, filtered by merchant and dateFrom and dateTo, excludes REVERSAL transactions.
     */
    public static List<Transaction> filterTransactionsMerchantDateTime(List<Transaction> transactions,
                                                                       LocalDateTime fromDateTime,
                                                                       LocalDateTime toDateTime,
                                                                       String merchant) {
        return transactions
                .stream()
                .filter(r -> r.getMerchant().equals(merchant))
                .filter(r -> r.getDate().isAfter(fromDateTime))
                .filter(r -> r.getDate().isBefore(toDateTime))
                .filter(r -> r.getType().equals(PAYMENT))
                .collect(Collectors.toList());

    }

    /**
     * List<Transaction> listOfTransactionsFromFile(File csvFile)
     * reading and parsing csv file to java beans, return List of Transactions
     */
    public static List<Transaction> listOfTransactionsFromFile(File csvFile) throws FileNotFoundException {
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(',')
                .withIgnoreQuotations(true)
                .build();

        CSVReader csvReader = new CSVReaderBuilder(new FileReader(csvFile))
                .withCSVParser(parser)
                .build();

        return new CsvToBeanBuilder(csvReader)
                .withType(Transaction.class)
                .build()
                .parse();

    }
}
