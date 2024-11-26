package views;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Files;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import models.Records;
import services.FileService;

public class ResetView extends PopUpView {
    public ResetView(JFrame parentFrame, int width, int height, Records records, FileService fileService) {
        super(parentFrame, width, height);

        JPanel main = new JPanel();
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
        main.setAlignmentX(Component.CENTER_ALIGNMENT);
        main.setAlignmentY(Component.CENTER_ALIGNMENT);

        JLabel label = new JLabel("Are you sure to reset the data?");
        label.setFont(new Font("Arial", Font.BOLD, 24));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton yesButton = new JButton("yes");
        JButton noButton = new JButton("no");
        buttons.add(yesButton);
        buttons.add(noButton);

        yesButton.addActionListener(new YesActionListener(records, fileService));
        noButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ResetView.this.turnOff();
            }
        });

        main.add(Box.createVerticalGlue());
        main.add(label);
        main.add(buttons);
        add(main);
        this.turnOn();
    }

    private class YesActionListener implements ActionListener{
        private Records records;
        private FileService fileService;

        public YesActionListener(Records records, FileService fileService) {
            this.records = records;
            this.fileService = fileService;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            try {
                Records newRecords = fileService.readData("slang.txt"); 
                this.records.setRecords(newRecords.getRecords());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(
                    ResetView.this,
                    "Error reset the data!!!\nTry again",
                    "Error", 
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            if (new File("newSlang.txt").exists() == false || fileService.deleteFile("newSlang.txt")) {
                JOptionPane.showMessageDialog(
                    ResetView.this,
                    "Reset the data successfully!!!",
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE
                );
                ResetView.this.turnOff();
            } else {
                JOptionPane.showMessageDialog(
                    ResetView.this,
                    "Error reset the data!!!\nTry again",
                    "Error", 
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}
