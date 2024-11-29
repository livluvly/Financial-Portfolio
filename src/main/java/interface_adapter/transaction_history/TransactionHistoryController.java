package interface_adapter.transaction_history;

import use_case.transaction_history.TransactionHistoryInputBoundary;
import use_case.transaction_history.TransactionHistoryInputData;

public class TransactionHistoryController {
    private final TransactionHistoryInputBoundary transactionHistoryInputBoundary;

    public TransactionHistoryController(TransactionHistoryInputBoundary transactionHistoryInputBoundary) {
        this.transactionHistoryInputBoundary = transactionHistoryInputBoundary;
    }

    public void getTransactionHistory(String userId) {
        TransactionHistoryInputData transactionHistoryInputData = new TransactionHistoryInputData(userId);
        transactionHistoryInputBoundary.fetchTransactionHistory(transactionHistoryInputData);
    }
}
