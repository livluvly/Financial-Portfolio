package data_access;

import entity.Transaction;
import use_case.transaction_history.TransactionHistoryDataAccessInterface;

import java.io.*;
import java.util.*;

public class FileTransactionHistoryDataAccessObject implements TransactionHistoryDataAccessInterface {
    private static final String HEADER = "username,date,symbol,quantity,totalValue" ;
    private final File csvFile;
    private final List<String> header = Arrays.asList(HEADER.split(","));

    public FileTransactionHistoryDataAccessObject(String csvPath) throws IOException {
        this.csvFile = new File(csvPath);
        if (!csvFile.exists()|| csvFile.length() == 0) {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile))) {
                bw.write(HEADER);
                bw.newLine();
            }
        }
    }
    /**
     * @param userId The ID of the user whose transaction history is being retrieved.
     * @return
     */
    @Override
    public List<Transaction> getTransactionHistory(String userId) {
        List<Transaction> transactions = new ArrayList<>();

        try (BufferedReader reader=new BufferedReader(new FileReader(csvFile))){
            String row;
            while ((row=reader.readLine())!=null) {
                String[] columns = row.split(",");
                if (columns[0].equals(userId)){
                    transactions.add(new Transaction(
                            columns[1], // sym
                            Double.parseDouble(columns[2]), // quantity
                            new Date(columns[3]), // date
                            Double.parseDouble(columns[4]), // totalValue
                            columns[5] // type
                    ));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error while reading history file",e);
        }
        return transactions;
    }

    /**
     * @param userId      The user ID for whom the transaction is being added.
     * @param transaction The transaction to be added.
     */
    @Override
    public void addTransaction(String userId, Transaction transaction) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
            String row = String.format("%s,%s,%.2f,%s,%.2f,%s",
                    userId,
                    transaction.getSymbol(),
                    transaction.getQuantity(),
                    transaction.getDate().toString(),
                    transaction.getTotalCost(),
                    transaction.getType());
            writer.write(row);
            writer.newLine();
        } catch (IOException e) {
            throw new RuntimeException("Error saving history file.", e);
        }
    }

    public void saveHistory(String username, List<Transaction> transactions) {
        List<String> rows = new ArrayList<>();

        // Append the updated or new assets to rows
        for (Transaction transaction : transactions) {
            rows.add(String.format("%s,%s,%.2f,%s,%.2f,%s",
                    username,
                    transaction.getSymbol(),
                    transaction.getQuantity(),
                    transaction.getDate().toString(),
                    transaction.getTotalCost(),
                    transaction.getType()));
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile))) {
          for (String row : rows) {
                writer.write(row);
                writer.newLine();
          }
        } catch (IOException e) {
            throw new RuntimeException("Error saving history file.", e);
        }
    }
}
