package interfaceAdapter.transactionHistory;

import java.util.Collections;

import interfaceAdapter.TransactionHistoryViewModel;
import useCase.transactionHistory.TransactionHistoryOutputBoundary;
import useCase.transactionHistory.TransactionHistoryOutputData;

/**
 * Presenter for handling history output data and updating the ViewModel.
 */
public class TransactionHistoryPresenter implements TransactionHistoryOutputBoundary {
    private final TransactionHistoryViewModel viewModel;

    /**
     * Constsructor for TransactionHistoryPresenter.
     * @param viewModel The ViewModel instance to be updated by the Presenter.
     */
    public TransactionHistoryPresenter(TransactionHistoryViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(TransactionHistoryOutputData outputData) {
        if (outputData != null && outputData.getTransactionHistory() != null) {
            viewModel.updateTransactionHistory(outputData.getTransactionHistory());
        }
        else {
            prepareFailView("Transaction history is null.");
        }
    }

    /**
     * Prepares the fail view by clearing the transaction history and setting and error message in the ViewModel.
     * @param errorMessage The error message to be displayed.
     */
    @Override
    public void prepareFailView(String errorMessage) {
        viewModel.updateTransactionHistory(Collections.emptyList());
    }
}
