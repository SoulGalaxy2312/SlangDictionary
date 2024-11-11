package controllers;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainController extends JFrame {
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

    public void start() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(10, 1));
        setSize(500, 500);

        JButton[] btns = new JButton[10];
        for (int i = 0; i < numBtns; i++) {
            btns[i] = new JButton(btnTexts[i]);
            btns[i].setActionCommand(String.valueOf(i + 1));
            add(btns[i]);
        }        

        setVisible(true);
    }

    private class ActionController implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            switch (command) {
                case "1":
                    
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
        }
    }
}
