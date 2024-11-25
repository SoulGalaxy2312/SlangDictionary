package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.Border;

import models.Defs;
import models.Records;
import models.Slang;

public class DeleteSlangView extends PopUpView {
    public DeleteSlangView(JFrame parentFrame, int width, int height, Records records) {
        super(parentFrame, width, height);

        setTitle("Delete Slang");
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(500, 300));

        // Header
        JPanel header = new JPanel(new FlowLayout());
        JLabel label = new JLabel("Search slang");
        JTextField searchField = new JTextField(20);
        JButton searchButton = new JButton("search");
        header.add(label);
        header.add(searchField);
        header.add(searchButton);

        // Body
        JPanel body = new JPanel(new BorderLayout());
        JLabel label2 = new JLabel("One definition per line");
        JTextArea slangArea = new JTextArea();
        JScrollPane slangScrollPane = new JScrollPane(slangArea);
        body.add(label2, BorderLayout.NORTH);
        body.add(slangScrollPane, BorderLayout.CENTER);

        // Footer
        JPanel footer = new JPanel(new FlowLayout());
        JButton deleteButton = new JButton("delete");
        JButton cancelButton = new JButton("cancel");
        footer.add(deleteButton);
        footer.add(cancelButton);

        searchButton.addActionListener(new SearchActionListener(searchField, slangArea, records));
        deleteButton.addActionListener(new DeleteActionListener(searchField, records));
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DeleteSlangView.this.turnOff();
            }
        });

        add(header, BorderLayout.NORTH);
        add(body, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);
        this.turnOn();
    }

    private class SearchActionListener implements ActionListener {
        private JTextField inputChannel;
        private JTextArea outputChannel;
        private Records records;

        public SearchActionListener(JTextField input, JTextArea output, Records records) {
            this.inputChannel = input;
            this.outputChannel = output;
            this.records = records;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String searchStr = inputChannel.getText();
            if (searchStr.isBlank()) {
                this.outputChannel.setText("");
                JOptionPane.showMessageDialog(
                    DeleteSlangView.this, 
                    "Search field cannot be empty!!!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            Slang searchSlang = new Slang(searchStr);   
            Map<Slang, Defs> data = records.getRecords();
            if (data.containsKey(searchSlang)) {
                List<String> defs = data.get(searchSlang).getDefs();
                outputChannel.setText(String.join("\n", defs));
            } else {
                this.outputChannel.setText("");
                JOptionPane.showMessageDialog(
                    DeleteSlangView.this, 
                    "Slang not found!!!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE
                );   
            }
        }
    }

    private class DeleteActionListener implements ActionListener {
        private Records records;
        private JTextField inputChannel;

        public DeleteActionListener(JTextField input, Records records) {
            this.inputChannel = input;
            this.records = records;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String input = this.inputChannel.getText().trim();
            Slang deleteSlang = new Slang(input);
            Map<Slang, Defs> data = records.getRecords();

            if (data.containsKey(deleteSlang)) {
                data.remove(deleteSlang);
                JOptionPane.showMessageDialog(
                    DeleteSlangView.this, 
                    "Remove the slang successfully!!!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                    DeleteSlangView.this, 
                    "Slang not found!!!\nTry again", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}
 