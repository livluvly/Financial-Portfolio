package interface_adapter.transaction_history;

import interface_adapter.TransactionHistoryViewModel;
import use_case.transaction_history.TransactionHistoryOutputBoundary;
import use_case.transaction_history.TransactionHistoryOutputData;
import view.TransactionHistoryView;

public class TransactionHistoryPresenter implements TransactionHistoryOutputBoundary {
    private final TransactionHistoryViewModel viewModel;

    public TransactionHistoryPresenter(TransactionHistoryViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void presentTransactionHistory(TransactionHistoryOutputData transactionHistoryOutputData) {
        viewModel.updateTransactionHistory(transactionHistoryOutputData.getTransactions());
    }
}

