package views;

import javax.swing.*;

import controllers.ViewController;

import java.awt.*;
import java.awt.event.*;

public class HomePage extends JFrame {
    private ViewController viewController;

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

    public HomePage(ViewController controller) {
        this.viewController = controller;
        
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(10, 1));
        setSize(500, 500);

        ActionListener actionListener = new ActionController();

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
            ViewInterface view = null;

            switch (command) {
                case "1":
                    view = new FindSlangDefsView();
                    break;
                case "2":
                    
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
            viewController.showView(view);
        }
    }

    public static void main(String[] args) {
        ViewController controller = new ViewController();
        new HomePage(controller).setVisible(true);
    }
}