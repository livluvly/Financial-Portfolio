//package view;
//
//import interface_adapter.change_password.LoggedInState;
//import interface_adapter.change_password.LoggedInViewModel;
//import interface_adapter.login.LoginController;
//import interface_adapter.logout.LogoutController;
//
//import javax.swing.*;
//import java.awt.*;
//import java.beans.PropertyChangeListener;
//
///**
// * The Portfolio View (HomeScreen) for when the user is logged into the program.
// */
//
//public class PortfolioView extends JPanel implements PropertyChangeListener {
//
//    private final String viewName = "Portfolio View";
//    private final LoggedInViewModel loggedInViewModel;
//    private LogoutController logoutController;
//
//    private final JLabel username;
//
//    private final JButton logOut;
//
//    public PortfolioView(LoggedInViewModel loggedInViewModel) {
//        this.loggedInViewModel = loggedInViewModel;
//        this.loggedInViewModel.addPropertyChangeListener(this);
//
//        // Panel with horizontal alignment
//        JPanel horizontalPanel = new JPanel();
//        horizontalPanel.setLayout(new BoxLayout(horizontalPanel, BoxLayout.X_AXIS));
//
//        // Labels
//        final JLabel title = new JLabel("My Portfolio");
//        final JLabel assetsName = new JLabel("Assets/Name");
//        final JLabel amount = new JLabel("Amount");
//        final JLabel price = new JLabel("Price");
//        final JLabel dailyGain = new JLabel("Daily Gain");
//        final JLabel percentValue = new JLabel("% Value");
//
//        int gap = 10;
//        horizontalPanel.add(title);
//        horizontalPanel.add(Box.createRigidArea(new Dimension(gap, 0)));
//        horizontalPanel.add(assetsName);
//        horizontalPanel.add(Box.createRigidArea(new Dimension(gap, 0)));
//        horizontalPanel.add(amount);
//        horizontalPanel.add(Box.createRigidArea(new Dimension(gap, 0)));
//        horizontalPanel.add(price);
//        horizontalPanel.add(Box.createRigidArea(new Dimension(gap, 0)));
//        horizontalPanel.add(dailyGain);
//        horizontalPanel.add(Box.createRigidArea(new Dimension(gap, 0)));
//        horizontalPanel.add(percentValue);
//
//        final JLabel usernameInfo = new JLabel("Currently logged in: ");
//        username = new JLabel();
//
//        final JPanel buttons = new JPanel();
//        logOut = new JButton("Log Out");
//        buttons.add(logOut);
//
//        logOut.addActionListener(
//                // This creates an anonymous subclass of ActionListener and instantiates it.
//                evt -> {
//                    if (evt.getSource().equals(logOut)) {
//                        // 1. get the state out of the loggedInViewModel. It contains the username.
//                        final LoggedInState currentState = loggedInViewModel.getState();
//                        // 2. Execute the logout Controller.
//                        logoutController.execute(currentState.getUsername());
//                    }
//                }
//        );
//
//        this.add(title);
//        this.add(usernameInfo);
//        this.add(username);
//
//    }
//
//    public String getViewName() {
//        return viewName;
//    }
//
//    public void setLogoutController(LogoutController logoutController) {
//        this.logoutController = logoutController;
//    }
//
//}
