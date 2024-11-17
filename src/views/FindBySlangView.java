package views;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

import java.util.Map;
import java.util.List;

public class FindBySlangView extends JFrame implements ViewInterface {
    private Records records;

    private FindBySlangActionListener myActionListener;

    private JScrollPane scrollPane = new JScrollPane();

    private JFrame parentFrame;

    public FindBySlangView(Records records, JFrame parentFrame) {
        this.parentFrame = parentFrame;

        this.records = records;

        setTitle("Find Slang's Definitions");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setMinimumSize(new Dimension(500, 300));
        setToTheRight();

        // headers
        JPanel header = new JPanel();
        header.setLayout(new FlowLayout());

        JLabel label = new JLabel("Input slang");
        label.setHorizontalAlignment(SwingConstants.RIGHT);

        JTextField textField = new JTextField(20);
        this.myActionListener = new FindBySlangActionListener(records, textField, scrollPane);

        JButton findButton = new JButton("find");
        findButton.addActionListener(myActionListener);
        
        header.add(label);
        header.add(textField);
        header.add(findButton);

        add(header, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    public void setToTheRight() {
        Point parentLocation = parentFrame.getLocation();
        int parentWidth = parentFrame.getWidth();
        int parentHeight = parentFrame.getHeight();

        int x = parentLocation.x + parentWidth;
        int y = parentLocation.y + (parentHeight / 2) - (getHeight() / 2);

        setLocation(x, y);
    }

    public void turnOn() {
        setVisible(true);
    }    
    private class FindBySlangActionListener implements ActionListener {
        private JTextField textField = null;
        private Records records;
        private JScrollPane scrollPane;

        public FindBySlangActionListener(Records records, JTextField textField, JScrollPane scrollPane) {
            this.records = records;
            this.textField = textField;
            this.scrollPane = scrollPane;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Map<Slang, Defs> data = records.getRecords();
            String input = textField.getText();
            Slang key = new Slang(input);

            if (data.containsKey(key)) {
                List<String> defs = data.get(key).getDefs();
                String[] defsStr = defs.toArray(new String[defs.size()]);

                this.scrollPane.setViewportView(new JList<>(defsStr));
                return;
            } 

            ViewInterface notificationView = new NotificationView("Your slang has no definitions", FindBySlangView.this);
            notificationView.turnOn();
        }
    }
}
