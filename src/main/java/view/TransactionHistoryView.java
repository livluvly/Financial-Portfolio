package view;

import entity.Transaction;
import interface_adapter.TransactionHistoryViewModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TransactionHistoryView shows a page of the past transactions made. Users can sort transactions based on the following
 * criteria:
 * - **Date**: Displays the most recent transactions at the top when sorted.
 * - **Name**: Arranges transactions alphabetically (A-Z) or in reverse alphabetical order (Z-A).
 * - **Quantity**: Sorts transactions by the quantities of items, showing the largest or smallest values at the top.
 * - **Total Value**: Sorts transactions by the total monetary value, showing the largest or smallest values at the top.
 * - **Type**: Sorts transactions by the type of transaction, either "BUY" or "SELL" at the top.
 */
public class TransactionHistoryView extends JPanel {
    private final TransactionHistoryViewModel viewModel;
    private final JTable table;
    private final DefaultTableModel tableModel;

    /**
     * Constructor for the TransactionHistoryView.
     *
     * @param viewModel The ViewModel managing the state of this view.
     */
    public TransactionHistoryView(TransactionHistoryViewModel viewModel) {
        this.viewModel = viewModel;
        this.setLayout(new BorderLayout());
        String[] columnNames = {"Date", "Name", "Quantity", "Total Value", "Type"};
        tableModel = new DefaultTableModel(columnNames, 0) {
        };

        table = new JTable(tableModel);
        table.setFillsViewportHeight(true);

        populateTable();

        JScrollPane scrollPane = new JScrollPane();
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
                    transaction.getName(),
                    transaction.getQuantity(),
                    transaction.getTotalValue(),
                    transaction.getType()
            });
        }
    }

    private void setupSorting() {
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);

        sorter.setComparator(1, Comparator.comparing(Date::parse)); // Date column
        sorter.setComparator(2, Comparator.comparingInt(o -> (Integer) o)); // Quantity column
        sorter.setComparator(3, Comparator.comparingDouble(o -> (Double) o)); // Total Value column
        sorter.setComparator(4, Comparator.comparing(o -> (String) o)); // Type column
    }

    private JPanel createSortingPanel() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel sortLabel = new JLabel("Sort By: ");
        String[] options = {
                "Date (Most Recent)",
                "Name (A-Z)",
                "Name (Z-A)",
                "Quantity (Largest)",
                "Quantity (Smallest)",
                "Total Value (Largest)",
                "Total Value (Smallest)",
                "Type (BUY First)",
                "Type (SELL First)"
        };
        JComboBox<String> sortOptions = new JComboBox(options);

        sortOptions.addActionListener(e -> {
            String selectedOption = (String) sortOptions.getSelectedItem();
            List<Transaction> sortedTransactions = switch (selectedOption) {
                case "Date (Most Recent)" -> (List<Transaction>) viewModel.getTransactionHistory()
                        .stream()
                        .sorted(Comparator.comparing(Transaction::getDate).reversed())
                        .collect(Collectors.toList());
                case "Name (A-Z)" -> viewModel.getTransactionHistory()
                        .stream()
                        .sorted(Comparator.comparing(Transaction::getName))
                        .collect(Collectors.toList());
                case "Name (Z-A)" -> viewModel.getTransactionHistory()
                        .stream()
                        .sorted(Comparator.comparing(Transaction::getName).reversed())
                        .collect(Collectors.toList());
                case "Quantity (Largest)" -> viewModel.getTransactionHistory()
                        .stream()
                        .sorted(Comparator.comparingInt(Transaction::getQuantity).reversed())
                        .collect(Collectors.toList());
                case "Quantity (Smallest)" -> viewModel.getTransactionHistory()
                        .stream()
                        .sorted(Comparator.comparingInt(Transaction::getQuantity))
                        .collect(Collectors.toList());
                case "Total Value (Largest)" -> viewModel.getTransactionHistory()
                        .stream()
                        .sorted(Comparator.comparing(Transaction::getTotalValue).reversed())
                        .collect(Collectors.toList());
                case "Total Value (Smallest)" -> viewModel.getTransactionHistory()
                        .stream()
                        .sorted(Comparator.comparing(Transaction::getTotalValue))
                        .collect(Collectors.toList());
                case "Type (BUY First)" -> viewModel.getTransactionHistory()
                        .stream()
                        .sorted(Comparator.comparing(Transaction::getType))
                        .collect(Collectors.toList());
                case "Type (SELL First)" -> viewModel.getTransactionHistory()
                        .stream()
                        .sorted(Comparator.comparing(Transaction::getType).reversed())
                        .collect(Collectors.toList());
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
                    transaction.getName(),
                    transaction.getQuantity(),
                    transaction.getTotalValue(),
                    transaction.getType()
            });
        }
    }
}