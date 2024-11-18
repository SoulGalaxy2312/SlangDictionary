package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import models.Defs;
import models.Records;
import models.Slang;

public class FindByDefView extends PopUpView {
    private Records records;

    private JScrollPane scrollPane = new JScrollPane();

    private FindByDefActionListener myActionListener; 

    public FindByDefView(Records records, JFrame parentFrame, int width, int height) {
        
        super(parentFrame, width, height);
        this.records = records;

        setTitle("Find Slang's Definitions");
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(500, 300));

        // headers
        JPanel header = new JPanel();
        header.setLayout(new FlowLayout());

        JLabel label = new JLabel("Input slang");
        label.setHorizontalAlignment(SwingConstants.RIGHT);

        JTextField textField = new JTextField(20);
        this.myActionListener = new FindByDefActionListener(records, textField, scrollPane);

        JButton findButton = new JButton("find");
        findButton.addActionListener(myActionListener);
        
        header.add(label);
        header.add(textField);
        header.add(findButton);

        add(header, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        super.turnOn();
    }

    private class FindByDefActionListener implements ActionListener {
        private Records records;

        private JTextField textField;

        private JScrollPane scrollPane;

        public FindByDefActionListener(Records records, JTextField textField, JScrollPane scrollPane) {
            this.records = records;
            this.textField = textField;
            this.scrollPane = scrollPane;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Map<Slang, Defs> data = records.getRecords();
            String input = textField.getText();
            ArrayList<String> result = new ArrayList<>();

            for (Map.Entry<Slang, Defs> entry : data.entrySet()) {
                Defs defs = entry.getValue();
                Slang slang = entry.getKey();
                List<String> defsList = defs.getDefs();

                boolean isFound = false;
                for (String def : defsList) {
                    String[] words = def.split(" " );
                    for (String word : words) {
                        if (word.equalsIgnoreCase(input)) {
                            result.add(slang.getSlang());
                            isFound = true;
                            break;
                        }
                    }

                    if (isFound) break;
                }
            }

            if (result.size() > 0) {
                this.scrollPane.setViewportView(
                    new JList<>(
                        result.toArray(
                            new String[result.size()]
                        )
                    )
                );
            } else {
                PopUpView notificationView = new NotificationView("Your slang has no definitions", FindByDefView.this, 300, 300);
                notificationView.turnOn();
            }            
        }
    }
}
