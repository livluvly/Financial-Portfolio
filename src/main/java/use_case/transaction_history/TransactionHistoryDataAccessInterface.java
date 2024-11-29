package use_case.transaction_history;

import entity.Transaction;

import java.util.List;

public interface TransactionHistoryDataAccessInterface {
    List<Transaction> getTransactionHistory(String userId);
}
