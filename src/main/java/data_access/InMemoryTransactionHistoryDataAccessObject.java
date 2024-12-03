package data_access;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import entity.Transaction;
import useCase.transactionHistory.TransactionHistoryDataAccessInterface;

public class InMemoryTransactionHistoryDataAccessObject implements TransactionHistoryDataAccessInterface {
    private static final String STOCK_TSLA = "TSLA";
    private static final String STOCK_AMD = "AMD";
    private static final String STOCK_NVDA = "NVDA";
    private static final String TRANSACTION_SELL = "SELL";
    private static final String TRANSACTION_BUY = "BUY";

    private static final int QUANTITY_TSLA = 3;
    private static final int QUANTITY_AMD = 6;
    private static final int QUANTITY_NVDA = 9;

    private static final double COST_TSLA = 750.0;
    private static final double COST_AMD = 1500.0;
    private static final double COST_NVDA = 5000.0;

    private static final int YEAR_2024 = 2024;
    private static final int MONTH_NOVEMBER = 11;
    private static final int DAY_15 = 15;
    private static final int DAY_20 = 20;
    private static final int DAY_23 = 23;

    private final Map<String, List<Transaction>> database = new HashMap<>();

    public InMemoryTransactionHistoryDataAccessObject() {
        // test data
        database.put("alice", List.of(
                new Transaction(STOCK_TSLA, QUANTITY_TSLA, createDate(YEAR_2024, MONTH_NOVEMBER, DAY_15),
                        COST_TSLA, TRANSACTION_SELL),
                new Transaction(STOCK_AMD, QUANTITY_AMD, createDate(YEAR_2024, MONTH_NOVEMBER, DAY_20),
                        COST_AMD, TRANSACTION_BUY),
                new Transaction(STOCK_NVDA, QUANTITY_NVDA, createDate(YEAR_2024, MONTH_NOVEMBER, DAY_23),
                        COST_NVDA, TRANSACTION_BUY)
        ));
    }

    @Override
    public List<Transaction> getTransactionHistory(String userId) {
        return database.getOrDefault(userId, Collections.emptyList());
    }

    @Override
    public void addTransaction(String userId, Transaction transaction) {
        database.computeIfAbsent(userId, key -> new ArrayList<>()).add(transaction);
    }

    /**
     * Creates a Date object for the specified year, month, and day.
     * @param year The year.
     * @param month The month.
     * @param day The day of the month.
     * @return A Date object representing the specified date.
     */
    private Date createDate(int year, int month, int day) {
        LocalDate localDate = LocalDate.of(year, month, day);
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}

