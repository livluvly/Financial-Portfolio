package use_case.transaction_history;

import entity.Transaction;

import java.util.List;

public class TransactionHistoryOutputData {
    private final List<Transaction> transactions;

    public TransactionHistoryOutputData(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
}
