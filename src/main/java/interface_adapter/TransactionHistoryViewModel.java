package interface_adapter;

import entity.Transaction;
import interface_adapter.transaction_history.TransactionHistoryState;

import java.util.List;

public class TransactionHistoryViewModel extends ViewModel<TransactionHistoryState> {
    private List<Transaction> transactions;

    public TransactionHistoryViewModel() {
        super("Transaction History");
        this.setState(new TransactionHistoryState(List.of()));
    }

    public void updateTransactionHistory(List<Transaction> transactions) {
        TransactionHistoryState newState = new TransactionHistoryState(transactions);
        this.setState(newState);
        this.firePropertyChanged();
    }

    public List<Transaction> getTransactionHistory() {
        return this.getState().getTransactionsHistory();
    }
}
