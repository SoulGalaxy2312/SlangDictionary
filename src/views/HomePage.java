package views;

import javax.swing.*;

import models.Records;
import services.FileService;

import java.awt.*;
import java.awt.event.*;

public class HomePage extends JFrame {
    private final int WIDTH = 500;

    private final int HEIGHT = 300;
    
    private Records records;

    private FileService fileService = new FileService();

    private int numBtns = 10;

    private String fileData = "data1.txt";

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
            this.records = fileService.readData("newSlang.txt");
        } catch (Exception e) {
            try {
                this.records = fileService.readData(fileData);
            } catch (Exception e2) {
                System.out.println(e.getMessage());
                System.out.println(e2.getMessage());
                return;
            }
        }

        // try {
        //     this.records = fileService.readData("data2.txt");
        // } catch (Exception e) {
        //     System.out.println(e.getMessage());
        // }

        // JFrame configuration
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(11, 1, 0, 5));
        setMinimumSize(new Dimension(500, 500));
        setWindowLocationToSecondQuarter();

        // Add window listener
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                fileService.saveToFile("newSlang.txt", records);
                return;
            }
        });

        // ActionListener
        ActionListener actionListener = new ActionController(this.records);

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
        private Records records;

        public ActionController(Records records) {
            this.records = records;    
        }

        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            PopUpView popUpView = null;

            switch (command) {
                case "1":
                    popUpView = new FindBySlangView(records, HomePage.this, WIDTH, HEIGHT, fileService);
                    break;
                case "2":
                    popUpView = new FindByDefView(records, HomePage.this, WIDTH, HEIGHT);
                    break;
                case "3":
                    popUpView = new ShowHistoryView(HomePage.this, WIDTH, HEIGHT, fileService);
                    break;
                case "4":
                    popUpView = new AddNewSlangView(HomePage.this, WIDTH, HEIGHT, records);
                    break;
                case "5":
                    popUpView = new EditSlangView(records, HomePage.this, WIDTH, HEIGHT);
                    break;
                case "6":
                    popUpView = new DeleteSlangView(HomePage.this, WIDTH, HEIGHT, records);
                    break;
                case "7":
                    popUpView = new ResetView(HomePage.this, WIDTH, HEIGHT, records, fileService, fileData);
                    break;
                case "8":
                    popUpView = new TodaySlangView(HomePage.this, WIDTH, HEIGHT, fileService, records);
                    break;
                case "9":
                    popUpView = new GuessDefinitionView(HomePage.this, WIDTH, HEIGHT, records);
                    break;
                case "10":
                    popUpView = new GuessSlangView(HomePage.this, WIDTH, HEIGHT, records);
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