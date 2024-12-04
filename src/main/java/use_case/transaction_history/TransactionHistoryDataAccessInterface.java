package use_case.transaction_history;

import java.util.List;

import entity.Transaction;

public interface TransactionHistoryDataAccessInterface {

    /**
     * Retrieves the transaction history for a specific user.
     * @param userId The ID of the user whose transaction history is being retrieved.
     * @return A list of transactions.
     */
    List<Transaction> getTransactionHistory(String userId);

    /**
     * Adds a transaction to this transaction history of a specific user.
     * @param userId The user ID for whom the transaction is being added.
     * @param transaction The transaction to be added.
     */
    void addTransaction(String userId, Transaction transaction);

}
