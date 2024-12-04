package interface_adapter.transactionHistory;

import java.util.List;

import entity.Transaction;

public class TransactionHistoryState {
    private List<Transaction> transactionHistory;
    private String username;
    /**
     * Constructor for TransactionHistoryState.
     *
     * @param transactionHistory The list of transactions for the current state.
     * @param username
     */
    public TransactionHistoryState(List<Transaction> transactionHistory, String username) {
        this.transactionHistory = transactionHistory;
        this.username=username;
    }

    /**
     * Retrieves the transaction history.
     * @return A list of transactions.
     */
    public List<Transaction> getTransactionHistory() {
        return transactionHistory;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
