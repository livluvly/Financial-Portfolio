package data_access;

import entity.Transaction;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryTransactionHistoryDataAccessObject {
    private final Map<String, List<Transaction>> database = new HashMap<>();

    public InMemoryTransactionHistoryDataAccessObject() {
        // test data
        database.put("alice", List.of(
                new Transaction("TSLA", "Tesla, Inc.", 3, "2024-11-15",
                        750.0, "SELL"),
                new Transaction("AMD", "Advanced Micro Devices, Inc.",6, "2024-11-20",
                        1500.0, "BUY"),
                new Transaction("NVDA","NVIDIA Corporation",9, "2024-11-23",
                        5000.0, "BUY")
        ));
    }

    /**
     *
     */
//    @Override
    public List<Transaction> getTransactionHistory(String userId) {
        return database.getOrDefault(userId, Collections.emptyList());
    }
}

