package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Comparator;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import entity.Transaction;
import interface_adapter.TransactionHistoryViewModel;
import interface_adapter.transaction_history.TransactionHistoryController;

/**
 * TransactionHistoryView shows a page of the past transactions made. Users can sort transactions based on the following
 * criteria:
 * - **Date**: Displays the most recent transactions at the top when sorted.
 * - **Quantity**: Sorts transactions by the quantities of items, showing the largest or smallest values at the top.
 * - **Total Cost**: Sorts transactions by the total monetary cost, showing the largest or smallest values at the top.
 * - **Type**: Sorts transactions by the type of transaction, either "BUY" or "SELL" at the top.
 */
public class TransactionHistoryView extends JPanel {
    private final TransactionHistoryViewModel viewModel;
    private final JTable transactionHistoryTable;
    private final DefaultTableModel tableModel;
    private TransactionHistoryController transactionHistoryController;

    /**
     * Constructor for the TransactionHistoryView.
     *
     * @param viewModel The ViewModel managing the state of this view.
     */
    public TransactionHistoryView(TransactionHistoryViewModel viewModel) {
        this.viewModel = viewModel;
        this.setLayout(new BorderLayout());
        String[] columnNames = {"Date", "Quantity", "Total Cost", "Type"};
        tableModel = new DefaultTableModel(columnNames, 0) {

            // Prevents editing of table cells
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        transactionHistoryTable = new JTable(tableModel);
        transactionHistoryTable.setFillsViewportHeight(true);

        populateTable();

        JScrollPane scrollPane = new JScrollPane(transactionHistoryTable);
        this.add(scrollPane, BorderLayout.CENTER);

        setupSorting();

        JPanel sortingPanel = createSortingPanel();
        this.add(sortingPanel, BorderLayout.NORTH);
    }

    private void populateTable() {
        tableModel.setRowCount(0);
        List<Transaction> transactions = viewModel.getTransactionHistory();
        for (Transaction transaction : transactions) {
            tableModel.addRow(new Object[]{
                    transaction.getDate(),
                    transaction.getQuantity(),
                    transaction.getTotalCost(),
                    transaction.getType(),
            });
        }
    }

    private void setupSorting() {
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(transactionHistoryTable.getModel());
        transactionHistoryTable.setRowSorter(sorter);

        // Date column
        sorter.setComparator(0, Comparator.comparing(date -> (Comparable<Object>) date));
        // Quantity column
        sorter.setComparator(1, Comparator.comparingDouble(quantity -> (Integer) quantity));
        // Total Cost column
        sorter.setComparator(2, Comparator.comparingDouble(totalCost -> (Double) totalCost));
        // Type column
        sorter.setComparator(3, Comparator.comparing(type -> (String) type));
    }

    private JPanel createSortingPanel() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel sortLabel = new JLabel("Sort By: ");
        String[] options = {
                "Date (Most Recent)",
                "Quantity (Largest)",
                "Quantity (Smallest)",
                "Total Cost (Largest)",
                "Total Cost (Smallest)",
                "Type (BUY First)",
                "Type (SELL First)",
        };
        JComboBox<String> sortOptions = new JComboBox<>(options);

        sortOptions.addActionListener(e -> {
            String selectedOption = (String) sortOptions.getSelectedItem();
            List<Transaction> sortedTransactions = switch (selectedOption) {
                case "Date (Most Recent)" -> (List<Transaction>) viewModel.getTransactionHistory()
                        .stream()
                        .sorted(Comparator.comparing(Transaction::getDate).reversed())
                        .toList();
                case "Quantity (Largest)" -> viewModel.getTransactionHistory()
                        .stream()
                        .sorted(Comparator.comparingDouble(Transaction::getQuantity).reversed())
                        .toList();
                case "Quantity (Smallest)" -> viewModel.getTransactionHistory()
                        .stream()
                        .sorted(Comparator.comparingDouble(Transaction::getQuantity))
                        .toList();
                case "Total Cost (Largest)" -> viewModel.getTransactionHistory()
                        .stream()
                        .sorted(Comparator.comparing(Transaction::getTotalCost).reversed())
                        .toList();
                case "Total Cost (Smallest)" -> viewModel.getTransactionHistory()
                        .stream()
                        .sorted(Comparator.comparing(Transaction::getTotalCost))
                        .toList();
                case "Type (BUY First)" -> viewModel.getTransactionHistory()
                        .stream()
                        .sorted(Comparator.comparing(Transaction::getType))
                        .toList();
                case "Type (SELL First)" -> viewModel.getTransactionHistory()
                        .stream()
                        .sorted(Comparator.comparing(Transaction::getType).reversed())
                        .toList();
                default -> viewModel.getTransactionHistory();
            };
            updateTableData(sortedTransactions);
        });

        jPanel.add(sortLabel);
        jPanel.add(sortOptions);
        return jPanel;
    }

    private void updateTableData(List<Transaction> transactions) {
        tableModel.setRowCount(0);
        for (Transaction transaction : transactions) {
            tableModel.addRow(new Object[]{
                    transaction.getDate(),
                    transaction.getQuantity(),
                    transaction.getTotalCost(),
                    transaction.getType(),
            });
        }
    }

    /**
     * Sets the controller for handling user interaction with the transaction history.
     * @param controller The controller for the Transaction History use case.
     */
    public void setController(TransactionHistoryController controller) {
        this.transactionHistoryController = controller;
        populateTable();
    }
}
