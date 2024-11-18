package views;

import javax.swing.*;

import models.Records;
import services.FileService;

import java.awt.*;
import java.awt.event.*;

public class HomePage extends JFrame {
    
    private Records records;

    private FileService fileService = new FileService();

    private int numBtns = 10;

    private String[] btnTexts = {
        "Find By Slang Word",
        "Find By Definition",
        "Show History",
        "Add New Slang",
        "Edit Slang",
        "Delete Slang",
        "Reset Slang List",
        "Random A Slang (On This Day Slang Word)",
        "Guess Slang Definition",
        "Guess Definition Of Slang"
    };

    public HomePage() {
        try {
            records = fileService.readData("slang.txt");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return;
        }
        
        // JFrame configuration
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(11, 1, 0, 5));
        setMinimumSize(new Dimension(500, 500));
        setWindowLocationToSecondQuarter();

        // ActionListener
        ActionListener actionListener = new ActionController();

        // header
        JPanel header = new JPanel();
        header.setLayout(new FlowLayout());
        JLabel label = new JLabel("Slang Dictionary");
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setForeground(Color.RED);
        header.add(label);
        add(header);

        // Body (10 function)
        JButton[] btns = new JButton[10];
        for (int i = 0; i < numBtns; i++) {
            btns[i] = new JButton(btnTexts[i]);
            btns[i].setActionCommand(String.valueOf(i + 1));
            btns[i].addActionListener(actionListener);
            add(btns[i]);
        }
    }

    private class ActionController implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            PopUpView popUpView = null;

            switch (command) {
                case "1":
                    popUpView = new FindBySlangView(records, HomePage.this, 500, 300);
                    break;
                case "2":
                    popUpView = new FindByDefView(records, HomePage.this, 500, 300);
                    break;
                case "3":
                    
                    break;
                case "4":
                    
                    break;
                case "5":
                    
                    break;
                case "6":
                    
                    break;
                case "7":
                    
                    break;
                case "8":
                    
                    break;
                case "9":
                    
                    break;
                case "10":
                    
                    break;
                default:
                    break;
            }
        }
    }

    // https://stackoverflow.com/questions/3680221/how-can-i-get-screen-resolution-in-java
    private void setWindowLocationToSecondQuarter() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width;
        int screenHeight = screenSize.height;

        int x = screenWidth / 4;
        int y = (screenHeight - getHeight()) / 2;

        setLocation(x, y);
    }

    public static void main(String[] args) {    
        new HomePage().setVisible(true);
    }
}