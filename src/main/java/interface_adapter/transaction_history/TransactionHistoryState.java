package interface_adapter.transaction_history;

import java.util.List;

import entity.Transaction;

public class TransactionHistoryState {
    private List<Transaction> transactionHistory;

    /**
     * Constructor for TransactionHistoryState.
     * @param transactionHistory The list of transactions for the current state.
     */
    public TransactionHistoryState(List<Transaction> transactionHistory) {
        this.transactionHistory = transactionHistory;
    }

    /**
     * Retrieves the transaction history.
     * @return A list of transactions.
     */
    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }
}
