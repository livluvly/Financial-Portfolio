package use_case.transaction_history;

public class TransactionHistoryInputData {
    private final String userId;

    public TransactionHistoryInputData(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

}
