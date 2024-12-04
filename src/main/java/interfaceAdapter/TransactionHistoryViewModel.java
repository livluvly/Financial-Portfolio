package interfaceAdapter;

import java.util.ArrayList;
import java.util.List;

import entity.Transaction;
import interfaceAdapter.transactionHistory.TransactionHistoryController;
import interfaceAdapter.transactionHistory.TransactionHistoryState;

public class TransactionHistoryViewModel extends ViewModel<TransactionHistoryState> {
    private TransactionHistoryController transactionHistoryController;

    public TransactionHistoryViewModel() {
        super("Transaction History");
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

    public void addTransaction(String username, Transaction transaction) {
        transactionHistoryController.addTransaction(username, transaction);
        List<Transaction> newHistory = new ArrayList<>(this.getState().getTransactionHistory());
        newHistory.add(transaction);
        updateTransactionHistory(newHistory);
    }
    /**
     * Retrieves the current transaction history from the state.
     * @return a list of transactions.
     */
    public List<Transaction> getTransactionHistory() {
        return this.getState().getTransactionHistory();
    }

    public void setController(TransactionHistoryController transactionHistoryController) {
        this.transactionHistoryController=transactionHistoryController;
    }
}
