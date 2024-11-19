package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import models.Defs;
import models.Records;
import models.Slang;
import services.DisplayRecordService;
import services.FileService;

import java.util.Map;
import java.util.HashMap;

public class FindBySlangView extends PopUpView {
    private Records records;
    
    private FindBySlangActionListener myActionListener;

    private JScrollPane scrollPane = new JScrollPane();

    private FileService fileService;

    public FindBySlangView(Records records, JFrame parentFrame, int width, int height, FileService fileService) {
        super(parentFrame, width, height);
        this.records = records;
        this.fileService = fileService;

        setTitle("Find Slang's Definitions");
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(500, 300));

        // headers
        JPanel header = new JPanel();
        header.setLayout(new FlowLayout());

        JLabel label = new JLabel("Input slang");
        label.setHorizontalAlignment(SwingConstants.RIGHT);

        JTextField textField = new JTextField(20);
        this.myActionListener = new FindBySlangActionListener(records, textField, scrollPane, fileService);

        JButton findButton = new JButton("find");
        findButton.addActionListener(myActionListener);
        
        header.add(label);
        header.add(textField);
        header.add(findButton);

        add(header, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        super.turnOn();
    }

    private class FindBySlangActionListener implements ActionListener {
        private JTextField textField = null;
        private Records records;
        private JScrollPane scrollPane;
        private DisplayRecordService displayRecordService = new DisplayRecordService();
        private FileService fileService;

        public FindBySlangActionListener(Records records, JTextField textField, JScrollPane scrollPane, FileService fileService) {
            this.records = records;
            this.textField = textField;
            this.scrollPane = scrollPane;
            this.fileService = fileService;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Map<Slang, Defs> data = records.getRecords();
            String input = textField.getText();
            Slang key = new Slang(input);
            Map<Slang, Defs> result = new HashMap<>();

            if (data.containsKey(key)) {
                Defs defs = data.get(key);
                result.put(key, defs);
            } 

            // Service section
            this.fileService.saveToHistory(input);
            this.displayRecordService.displayRecords(scrollPane, result);

            if (result.isEmpty()) {
                PopUpView notificationView = new NotificationView("Your slang has no definitions", FindBySlangView.this, 300, 300);
                notificationView.turnOn();
            }
            
        }
    }
}
