package view;

import javax.swing.*;

public class TransactionPopup {
    public static Double promptForQuantity(String symbol, double price, String type) {
        String message = String.format("Current price for %s: $%.2f\nEnter the quantity:", symbol, price);
        String input = JOptionPane.showInputDialog(
                null,
//                "Enter the quantity to purchase/sell for " + symbol + ":",
                message,
                type.equals("BUY") ? "Purchase Asset" : "Sell Asset",
                JOptionPane.QUESTION_MESSAGE
        );

        if (input == null) { // User canceled the dialog
            return null;
        }

        try {
            double quantity = Double.parseDouble(input);
            if (quantity <= 0) {
                JOptionPane.showMessageDialog(
                        null,
                        "Quantity must be greater than 0.",
                        "Invalid Input",
                        JOptionPane.ERROR_MESSAGE
                );
                return null;
            }
            return quantity;
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Please enter a valid number.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE
            );
            return null;
        }
    }
}
