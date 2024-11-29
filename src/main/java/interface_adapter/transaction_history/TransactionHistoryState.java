package interface_adapter.transaction_history;

import entity.Transaction;

import java.util.List;

public class TransactionHistoryState {
    private List<Transaction> transactions;

    public TransactionHistoryState(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Transaction> getTransactionsHistory() {
        return transactions;
    }

    public void setTransactionsHistory(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
