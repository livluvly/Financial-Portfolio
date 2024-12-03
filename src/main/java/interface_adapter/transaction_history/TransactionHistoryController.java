package interface_adapter.transaction_history;

import entity.Transaction;
import use_case.transaction_history.TransactionHistoryInputBoundary;
import use_case.transaction_history.TransactionHistoryInputData;

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
}
