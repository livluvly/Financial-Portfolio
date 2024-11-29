package use_case.transaction_history;

import entity.Transaction;

import java.util.List;

public class TransactionHistoryInteractor implements TransactionHistoryInputBoundary {
    private final TransactionHistoryDataAccessInterface transactionHistoryDataAccessInterface;
    private final TransactionHistoryOutputBoundary transactionHistoryOutputBoundary;

    public TransactionHistoryInteractor(TransactionHistoryDataAccessInterface transactionHistoryDataAccessInterface,
                                        TransactionHistoryOutputBoundary transactionHistoryOutputBoundary) {
        this.transactionHistoryDataAccessInterface = transactionHistoryDataAccessInterface;
        this.transactionHistoryOutputBoundary = transactionHistoryOutputBoundary;
    }

    public void fetchTransactionHistory(TransactionHistoryInputData transactionHistoryInputData) {
        List<Transaction> transactions = transactionHistoryDataAccessInterface
                .getTransactionHistory(transactionHistoryInputData.getUserId());
        var outputData = new TransactionHistoryOutputData(transactions);
        transactionHistoryOutputBoundary.presentTransactionHistory(outputData);
    }
}
