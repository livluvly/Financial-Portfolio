package useCase.transactionHistory;

public interface TransactionHistoryOutputBoundary {
    /**
     * Prepares the success view for the TransactionHistory Use Case.
     * @param outputData Data containing the transaction history to be displayed.
     */
    void prepareSuccessView(TransactionHistoryOutputData outputData);

    /**
     * Prepares the failure view for the Transaction History Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
