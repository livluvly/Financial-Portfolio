package interfaceAdapter.transactionHistory;

import entity.Transaction;
import useCase.transactionHistory.TransactionHistoryInputBoundary;

public class TransactionHistoryController {

    private final TransactionHistoryInputBoundary transactionHistoryInputBoundary;

    /**
     * Constructor for TransactionController.
     * @param transactionHistoryInputBoundary The Input Boundary for the transaction history use case.
     */
    public TransactionHistoryController(TransactionHistoryInputBoundary transactionHistoryInputBoundary) {
        this.transactionHistoryInputBoundary = transactionHistoryInputBoundary;
    }

    /**
     * Fetches the transaction history for a specific user.
     * @param userId The ID of the user whose transaction history is to be fetched.
     */
    public void fetchTransactionHistory(String userId) {
        transactionHistoryInputBoundary.fetchTransactionHistory(userId);
    }
    public void addTransaction(String username, Transaction transaction) {
        transactionHistoryInputBoundary.addTransaction(username, transaction);
    }
}