package interface_adapter;

import java.util.ArrayList;
import java.util.List;

import entity.Transaction;
import interface_adapter.transaction_history.TransactionHistoryController;
import interface_adapter.transaction_history.TransactionHistoryState;

public class TransactionHistoryViewModel extends ViewModel<TransactionHistoryState> {
    private TransactionHistoryController transactionHistoryController;

    public TransactionHistoryViewModel() {
        super("Transaction History");
        this.setState(new TransactionHistoryState(List.of(), null));
    }

    /**
     * Updates the transaction history in the state and notifies observers.
     * @param transactions The new list of transaction to update.
     */
    public void updateTransactionHistory(List<Transaction> transactions) {
        TransactionHistoryState newState = new TransactionHistoryState(transactions, this.getState().getUsername());
        this.setState(newState);
        this.firePropertyChanged();
    }

    public void addTransaction(String username, Transaction transaction) {
        transactionHistoryController.addTransaction(username, transaction);
        List<Transaction> newHistory = new ArrayList<>(this.getState().getTransactionHistory());
        newHistory.add(transaction);
        updateTransactionHistory(newHistory);
    }
    public void setName(String name){
        TransactionHistoryState newState = this.getState();
        newState.setUsername(name);
        this.setState(newState);
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
