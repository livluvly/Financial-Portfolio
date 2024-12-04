package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Comparator;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import entity.Transaction;
import interface_adapter.TransactionHistoryViewModel;
import interface_adapter.transaction.TransactionController;

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

    /**
     * Constructor for the TransactionHistoryView.
     *
     * @param viewModel The ViewModel managing the state of this view.
     */
    public TransactionHistoryView(TransactionHistoryViewModel viewModel) {
        this.viewModel = viewModel;
        this.setLayout(new BorderLayout());
        String[] columnNames = {"Date", "Symbol" ,"Quantity", "Total Cost", "Type"};
        tableModel = new DefaultTableModel(columnNames, 0) {

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

        viewModel.addPropertyChangeListener(evt -> {
                populateTable(); // Update the table
        });
    }

    private void populateTable() {
        tableModel.setRowCount(0);
        List<Transaction> transactions = viewModel.getTransactionHistory();
        for (Transaction transaction : transactions) {
            tableModel.addRow(new Object[]{
                    transaction.getDate(),
                    transaction.getSymbol(),
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
//        sorter.setComparator(0, Comparator.comparing(date -> (Comparable<Object>) date));
        // Quantity column
//        sorter.setComparator(1, Comparator.comparingDouble(quantity -> (Integer) quantity));
        // Total Cost column
//        sorter.setComparator(2, Comparator.comparingDouble(totalCost -> (Double) totalCost));
        // Type column
//        sorter.setComparator(3, Comparator.comparing(type -> (String) type));
    }

    private JPanel createSortingPanel() {
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel sortLabel = new JLabel("Sort By: ");
        String[] options = {
                "Date (Most Recent)",
                "Symbol",
                "Quantity (Largest)",
                "Quantity (Smallest)",
                "Total Cost (Largest)",
                "Total Cost (Smallest)",
        };
        JComboBox<String> sortOptions = new JComboBox<>(options);

        sortOptions.addActionListener(e -> {
            String selectedOption = (String) sortOptions.getSelectedItem();
            switch (selectedOption) {
                case "Date (Newest)" -> transactionHistoryTable.getRowSorter().toggleSortOrder(0);
                case "Date (Oldest)" -> transactionHistoryTable.getRowSorter().toggleSortOrder(0);
                case "Symbol (A-Z)" -> transactionHistoryTable.getRowSorter().toggleSortOrder(1);
                case "Symbol (Z-A)" -> transactionHistoryTable.getRowSorter().toggleSortOrder(1);
                case "Quantity (Largest)" -> transactionHistoryTable.getRowSorter().toggleSortOrder(2);
                case "Quantity (Smallest)" -> transactionHistoryTable.getRowSorter().toggleSortOrder(3);
                case "Total Cost (Largest)" -> transactionHistoryTable.getRowSorter().toggleSortOrder(3);
                case "Total Cost (Smallest)" -> transactionHistoryTable.getRowSorter().toggleSortOrder(3);
            }
        });

        jPanel.add(sortLabel);
        jPanel.add(sortOptions);
        return jPanel;
    }
}
