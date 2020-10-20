package com.targsoftware.FTAS.ui;

import com.targsoftware.FTAS.domain.Transaction;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.OptionalDouble;
import java.util.Scanner;

import static com.targsoftware.FTAS.constants.Constants.*;
import static com.targsoftware.FTAS.core.CoreMethods.*;

public class ConsoleUI {

    public void startDialog() {
        System.out.println(HELLO_MESSAGE);
        mainDialog();
    }

    /**
     * mainDialog() - just console UI, dialog with user and takes all needed parameters: - file path,
     * merchant, from/to dates.
     */
    public void mainDialog() {
        System.out.println(SPECIFY_CSV_FILE);
        Scanner scanner = new Scanner(System.in);
        File csvFile = fileFromConsole(scanner);

        if (csvFile.isFile()) {

            List<Transaction> transactions = null;
            try {
                transactions = listOfTransactionsFromFile(csvFile);
            } catch (FileNotFoundException e) {
                mainDialog();
            }
            System.out.println(FILE_PARSED);

            System.out.println(SPECIFY_INPUT_FROMDATE);
            LocalDateTime fromDateTime = dateTimeFromConsole(scanner);
            System.out.println(SPECIFY_INPUT_TODATE);
            LocalDateTime toDateTime = dateTimeFromConsole(scanner);

            System.out.println(SPECIFY_MERCHANT);
            String merchant = scanner.nextLine();

            List<Transaction> filteredByParams = filterTransactionsMerchantDateTime(transactions, fromDateTime, toDateTime, merchant);

            OptionalDouble sum = averageAmount(filteredByParams);
            double averageResult = 0;
            if (sum.isPresent()) {
                averageResult = sum.getAsDouble();
            }

            System.out.format(NUMBER_OF_TRANSACTIONS
                            + AVERAGE_OF_TRANSACTIONS,
                    filteredByParams.size(),
                    averageResult);
            System.out.println("Input 1 to specify another file.");
            System.out.println("Input 0 to exit.");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    mainDialog();
                case "0":
                    System.exit(0);
            }
        } else {
            System.out.println(FILE_NOT_FOUND);
            startDialog();
        }


    }

    /**
     * Read file from console, return new File.
     */
    private File fileFromConsole(Scanner scanner) {
        String filePath = scanner.nextLine();
        File csvFile = new File(filePath);
        return csvFile;
    }

    /**
     * Read DateTime from console as sting, catching parsing exceptions.
     */
    private LocalDateTime dateTimeFromConsole(Scanner scanner) {
        String fromDate = scanner.nextLine();
        LocalDateTime dateTime = null;
        try {
            dateTime = LocalDateTime.parse(fromDate, DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
        } catch (Exception e) {
            System.out.println(WRONG_FORMAT + " " + e.getMessage());
            dateTimeFromConsole(scanner);
            return dateTime;
        }
        return dateTime;
    }
}
