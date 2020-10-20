package com.targsoftware.FTAS.core;

import com.targsoftware.FTAS.domain.Transaction;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

import static com.targsoftware.FTAS.constants.Constants.DATE_TIME_PATTERN;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CoreMethodsTest {

    @Test
    void successfulFilteringAndAmount() {
        Transaction transaction = new Transaction();
        transaction.setId("WLMFRDGD");
        transaction.setAmount(8.88);
        transaction.setMerchant("Kwik-E-Mart");
        transaction.setDate(LocalDateTime.parse("20/08/2018 12:46:35", DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)));
        transaction.setType("PAYMENT");

        Transaction transaction1 = new Transaction();
        transaction1.setId("WLMFRRTR");
        transaction1.setAmount(12.88);
        transaction1.setMerchant("Kwik-E-Mart");
        transaction1.setDate(LocalDateTime.parse("20/08/2018 12:50:35", DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)));
        transaction1.setType("PAYMENT");

        Transaction transaction2 = new Transaction();
        transaction2.setId("WLMFRRTR");
        transaction2.setAmount(12.88);
        transaction2.setMerchant("Wallmart");
        transaction2.setDate(LocalDateTime.parse("20/08/2018 12:51:35", DateTimeFormatter.ofPattern(DATE_TIME_PATTERN)));
        transaction2.setType("PAYMENT");

        List<Transaction> tested = new ArrayList<>();
        tested.add(transaction);
        tested.add(transaction1);
        tested.add(transaction2);

        LocalDateTime from = LocalDateTime.parse("20/08/2018 12:00:00", DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
        LocalDateTime to = LocalDateTime.parse("20/08/2018 14:00:00", DateTimeFormatter.ofPattern(DATE_TIME_PATTERN));
        String merchant = "Kwik-E-Mart";
        List<Transaction> actual = CoreMethods.filterTransactionsMerchantDateTime(tested, from, to, merchant);

        List<Transaction> expected = new ArrayList<>();
        expected.add(transaction);
        expected.add(transaction1);


        assertEquals(actual, expected);
        assertEquals(transaction, expected.get(0));

        OptionalDouble result = CoreMethods.averageAmount(tested);
        assertEquals(tested.stream().mapToDouble(Transaction::getAmount).average().getAsDouble(), result.getAsDouble());


    }
}