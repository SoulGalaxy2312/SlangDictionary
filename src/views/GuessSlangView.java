package views;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import models.Defs;
import models.Records;
import models.Slang;

public class GuessSlangView extends PopUpView {
    public GuessSlangView(JFrame parentFrame, int width, int height, Records records) {
        super(parentFrame, width, height);
        setLayout(new GridLayout(2, 1));
        List<Slang> slangs = new ArrayList<>();
        Defs defs = new Defs();
        this.getRandom(slangs, defs, records);

        String question = defs.getDefs().get(new Random().nextInt(defs.getDefs().size()));
        String answer = slangs.get(0).getSlang();
        GuessSlangActionListener actionListener = new GuessSlangActionListener(answer);

        JPanel header = new JPanel(new GridBagLayout());
        JLabel label = new JLabel(question);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        header.add(label);
        
        JPanel body = new JPanel(new GridLayout(2, 2, 10, 10));
        for (int i = 0; i < 4; i++) {
            String text = slangs.get(new Random().nextInt(slangs.size())).getSlang();
            
            JButton btn = new JButton(text);
            btn.addActionListener(actionListener);
            btn.setActionCommand(text);
            
            slangs.remove(new Slang(text));
            body.add(btn);
        }

        System.out.println(answer);
        add(header);
        add(body);
        this.turnOn();
    }

    private void getRandom(List<Slang> slangs, Defs defs, Records records) {
        Random generator = new Random();    
        List<Slang> slangsList = new ArrayList<>(records.getRecords().keySet());
        List<Integer> idxs = new ArrayList<>();

        while (idxs.size() != 4) {
            int trial = generator.nextInt(slangsList.size());
            if (idxs.contains(trial)) {
                continue;
            }

            idxs.add(trial);
        }

        slangs.clear();
        for (Integer idx : idxs) {
            slangs.add(slangsList.get(idx));
        }
        defs.setDefs(records.getRecords().get(slangs.get(0)).getDefs());
    }

    private class GuessSlangActionListener implements ActionListener {
        private String answer;

        public GuessSlangActionListener(String answer) {
            this.answer = answer;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (command.equals(answer)) {
                JOptionPane.showMessageDialog(
                    GuessSlangView.this, 
                    "Congratulation, you are correct!!!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(
                    GuessSlangView.this, 
                    "Your answer is wrong, try again!!!", 
                    "Failed", 
                    JOptionPane.OK_OPTION);
            }
        }
        
    }
}
