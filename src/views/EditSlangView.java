package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Flow;

import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import models.Defs;
import models.Records;
import models.Slang;

public class EditSlangView extends PopUpView {
    public EditSlangView(Records records, JFrame parentFrame, int width, int height) {
        super(parentFrame, width, height);

        setTitle("Edit Slang");
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(500, 300));

        // Header
        JPanel header = new JPanel(new FlowLayout());
        JLabel searchLabel = new JLabel("Search Slang");
        JTextField searchField = new JTextField(20);
        JButton searchBtn = new JButton("Search");

        header.add(searchLabel);
        header.add(searchField);
        header.add(searchBtn);

        // Body
        JPanel body = new JPanel(new BorderLayout());
        JLabel defsLabel = new JLabel("Definitions (one per line)");
        JTextArea defsArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(defsArea);
        body.add(defsLabel, BorderLayout.NORTH);
        body.add(scrollPane, BorderLayout.CENTER);

        // Footer
        JPanel footer = new JPanel(new FlowLayout());
        JButton saveBtn = new JButton("Save");
        JButton cancelBtn = new JButton("Cancel");

        footer.add(saveBtn);
        footer.add(cancelBtn);

        searchBtn.addActionListener(new SearchActionListener(records, searchField, defsArea));
        saveBtn.addActionListener(new SaveActionListener(records, searchField, defsArea));
        cancelBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                EditSlangView.this.turnOff();
            }
        });

        add(header, BorderLayout.NORTH);
        add(body, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);

        this.turnOn();
    }

    private class SearchActionListener implements ActionListener {
        private Records records;
        private JTextField searchField;
        private JTextArea defsArea;

        public SearchActionListener(Records records, JTextField searchField, JTextArea defsArea) {
            this.records = records;
            this.searchField = searchField;
            this.defsArea = defsArea;
        }

        @Override 
        public void actionPerformed(ActionEvent e) {
            String strSlang = searchField.getText().trim();
            if (strSlang.isEmpty()) {
                JOptionPane.showMessageDialog(
                    EditSlangView.this, 
                    "Please enter a slang to search", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            Map<Slang, Defs> data = records.getRecords();
            Slang slang = new Slang(strSlang);

            if (data.containsKey(slang)) {
                Defs defs = data.get(slang);
                List<String> defsList = defs.getDefs();
                defsArea.setText(String.join("\n", defsList));
            } else {
                JOptionPane.showMessageDialog(
                    EditSlangView.this, 
                    "Slang not found!!!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE
                );
                defsArea.setText("");
            }
        }
    }

    private class SaveActionListener implements ActionListener {
        private Records records;
        private JTextField searchField;
        private JTextArea defsArea;

        public SaveActionListener(Records records, JTextField searchField, JTextArea defsArea) {
            this.records = records;
            this.searchField = searchField;
            this.defsArea = defsArea;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String strSlang = searchField.getText().trim();
            String defs = defsArea.getText().trim();

            if (strSlang.isEmpty() || defs.isEmpty()) {
                JOptionPane.showMessageDialog(
                    EditSlangView.this, 
                    "Slang/ Defs is empty!!!", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            Map<Slang, Defs> data = records.getRecords();
            Slang slang = new Slang(strSlang);

            if (data.containsKey(slang)) {
                String[] defsArr = defs.split("\\n");
                data.get(slang).setDefs(List.of(defsArr));

                JOptionPane.showMessageDialog(
                    EditSlangView.this, 
                    "Slang updated successfully!!!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                    EditSlangView.this, 
                    "Slang not found", 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }
}