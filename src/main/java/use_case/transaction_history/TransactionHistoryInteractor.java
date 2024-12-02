package use_case.transaction_history;

import java.util.List;

import entity.Transaction;

/**
 * The Transaction History Interactor.
 * Handles the logic for fetching transaction history and delegates responses to the output boundary.
 */

public class TransactionHistoryInteractor implements TransactionHistoryInputBoundary {
    private final use_case.transaction_history.TransactionHistoryDataAccessInterface transactionHistoryDataAccessInterface;
    private final TransactionHistoryOutputBoundary transactionHistoryOutputBoundary;

    public TransactionHistoryInteractor(
            TransactionHistoryOutputBoundary transactionHistoryOutputBoundary,
            use_case.transaction_history.TransactionHistoryDataAccessInterface transactionHistoryDataAccessInterface) {
        this.transactionHistoryOutputBoundary = transactionHistoryOutputBoundary;
        this.transactionHistoryDataAccessInterface = transactionHistoryDataAccessInterface;

    }

    /**
     * Fetches the transaction history and passes it to the output boundary.
     */
    @Override
    public void fetchTransactionHistory(String userId) {
        try {
            List<Transaction> transactions = transactionHistoryDataAccessInterface.getTransactionHistory(userId);
            TransactionHistoryOutputData outputData = new TransactionHistoryOutputData(transactions);
            transactionHistoryOutputBoundary.prepareSuccessView(outputData);
        } catch (Exception e) {
            transactionHistoryOutputBoundary.prepareFailView(
                    "Failed to retrieve transaction history: " + e.getMessage());
        }
    }
}