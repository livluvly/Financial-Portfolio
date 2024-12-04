package useCase.transactionHistory;

import java.util.List;

import entity.Transaction;

public class TransactionHistoryOutputData {
    private final List<Transaction> transactionHistory;

    public TransactionHistoryOutputData(List<Transaction> transactionHistory) {
        this.transactionHistory = transactionHistory;
    }

    /**
     * Returns the transaction history.
     * @return a list of transactions.
     */
    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }
}
