package views;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import models.Defs;
import models.Records;
import models.Slang;

public class AddNewSlangView extends PopUpView {
    public AddNewSlangView(JFrame parentFrame, int width, int height, Records records) {
        super(parentFrame, width, height);

        setLayout(new GridLayout(7, 1));

        JPanel slangRow = new JPanel();
        slangRow.setLayout(new FlowLayout());
        slangRow.setAlignmentX(LEFT_ALIGNMENT);

        JLabel slangLabel = new JLabel("New slang");
        JTextField slangTextField = new JTextField(20);
        slangRow.add(slangLabel);
        slangRow.add(slangTextField);
        
        List<JPanel> defRows = new ArrayList<>();
        List<JTextField> defInputs = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            JPanel defRow = new JPanel();
            defRow.setLayout(new FlowLayout());
            defRow.setAlignmentX(LEFT_ALIGNMENT);

            JLabel defLabel = new JLabel("Definition " + (i + 1));
            JTextField defTextField = new JTextField(20);
            defRow.add(defLabel);
            defRow.add(defTextField);
            
            defInputs.add(defTextField);

            defRows.add(defRow);
        }

        JPanel btnRows = new JPanel();
        btnRows.setLayout(new FlowLayout());
        JButton addBtn = new JButton("add");
        btnRows.add(addBtn);

        MyActionListener myActionListener = new MyActionListener(slangTextField, defInputs, records);
        addBtn.setActionCommand("addBtn");
        addBtn.addActionListener(myActionListener);

        add(slangRow);
        for (JPanel defRow : defRows) {
            add(defRow);
        }
        add(btnRows);
        super.turnOn();
    }

    private class MyActionListener implements ActionListener {
        private JTextField slangRow;
        private List<JTextField> defInputs;
        private Records records;

        public MyActionListener(JTextField slangRow, List<JTextField> defInputs, Records records) {
            this.slangRow = slangRow;
            this.defInputs = defInputs;
            this.records = records;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (command.equals("addBtn")) {
                if (slangRow.getText().isBlank()) {
                    NotificationView notificationView = new NotificationView("Slang cannot be empty", AddNewSlangView.this, WIDTH, HEIGHT);
                    notificationView.turnOn();
                    return;
                }

                Slang newSlang = new Slang(slangRow.getText());
                List<String> newDefsStrList = new ArrayList<>();
                Defs newDefs = new Defs();

                for (JTextField defInput : this.defInputs) {
                    String def = defInput.getText();
                    if (!def.isBlank()) {
                        newDefsStrList.add(def);
                    }
                }
                newDefs.setDefs(newDefsStrList);

                Map<Slang, Defs> data = records.getRecords();
                Defs currentDefs = data.get(newSlang);

                if (currentDefs == null) {
                    data.put(newSlang, newDefs);
                    NotificationView notificationView = new NotificationView("Add new slang successfully", AddNewSlangView.this, WIDTH, HEIGHT);
                    notificationView.turnOn();
                } else {
                    String message = "The slang already exists.\n"
                                        + "Yes: Override\n"
                                        + "No: Add new definitions to the slang";

                    int response = JOptionPane.showConfirmDialog(
                        AddNewSlangView.this, 
                        message,
                        "Confirmation", 
                        JOptionPane.YES_NO_OPTION, 
                        JOptionPane.QUESTION_MESSAGE
                    );
                    
                    if (response == JOptionPane.YES_OPTION) {
                        data.put(newSlang, newDefs);
                        NotificationView notificationView = new NotificationView("Definition overridden successfully!", AddNewSlangView.this, WIDTH, HEIGHT);
                        notificationView.turnOn();
                    } else {
                        currentDefs.getDefs().addAll(newDefsStrList);
                        NotificationView notificationView = new NotificationView("New definitions added to the exist slang successfully!", AddNewSlangView.this, WIDTH, HEIGHT);
                        notificationView.turnOn();
                    }
                }
            }
        }
    }
}