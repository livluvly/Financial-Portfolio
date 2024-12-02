package interface_adapter;

import java.util.List;

import entity.Transaction;
import interface_adapter.transaction_history.TransactionHistoryState;

public class TransactionHistoryViewModel extends ViewModel<TransactionHistoryState> {
    private List<Transaction> transactionHistory;

    public TransactionHistoryViewModel() {
        super("Transaction History");
        this.transactionHistory = transactionHistory;
        this.setState(new TransactionHistoryState(List.of()));
    }

    /**
     * Updates the transaction history in the state and notifies observers.
     * @param transactions The new list of transaction to update.
     */
    public void updateTransactionHistory(List<Transaction> transactions) {
        TransactionHistoryState newState = new TransactionHistoryState(transactions);
        this.setState(newState);
        this.firePropertyChanged();
    }

    /**
     * Retrieves the current transaction history from the state.
     * @return a list of transactions.
     */
    public List<Transaction> getTransactionHistory() {
        return this.getState().getTransactionHistory();
    }
}
