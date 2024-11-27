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

public class GuessDefinitionView extends PopUpView {
    public GuessDefinitionView(JFrame parentFrame, int width, int height, Records records) {
        super(parentFrame, width, height);
        setLayout(new GridLayout(2, 1));
        Slang slang = new Slang("");
        List<String> defs = new ArrayList<>();
        this.getRandom(slang, defs, records);
        String answer = defs.get(0);
        GuessDefinitionActionListener actionListener = new GuessDefinitionActionListener(answer);

        JPanel header = new JPanel(new GridBagLayout());
        JLabel question = new JLabel(slang.getSlang());
        question.setFont(new Font("Arial", Font.BOLD, 24));
        header.add(question);
        

        JPanel body = new JPanel(new GridLayout(2, 2, 10, 10));
        for (int i = 0; i < 4; i++) {
            String text = defs.get(new Random().nextInt(defs.size()));
            
            JButton btn = new JButton(text);
            btn.addActionListener(actionListener);
            btn.setActionCommand(text);
            
            defs.remove(text);
            body.add(btn);
        }

        System.out.println(answer);
        add(header);
        add(body);
        this.turnOn();
    }

    private void getRandom(Slang slang, List<String> defs, Records records) {
        Random generator = new Random();    
        List<Slang> slangs = new ArrayList<>(records.getRecords().keySet());

        List<Integer> idxs = new ArrayList<>();

        while (idxs.size() != 4) {
            int trial = generator.nextInt(slangs.size());
            if (idxs.contains(trial)) {
                continue;
            }

            idxs.add(trial);
        }

        slang.setSlang(slangs.get(idxs.get(0)).getSlang());
        defs.clear();

        for (Integer idx : idxs) {
            Slang key = slangs.get(idx);
            Defs def = records.getRecords().get(key);
            List<String> defsStr = def.getDefs();

            defs.add(defsStr.get(generator.nextInt(defsStr.size())));
        }
    }

    private class GuessDefinitionActionListener implements ActionListener {
        private String answer;

        public GuessDefinitionActionListener(String answer) {
            this.answer = answer;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();
            if (command.equals(answer)) {
                JOptionPane.showMessageDialog(
                    GuessDefinitionView.this, 
                    "Congratulation, you are correct!!!", 
                    "Success", 
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(
                    GuessDefinitionView.this, 
                    "Your answer is wrong, try again!!!", 
                    "Failed", 
                    JOptionPane.OK_OPTION);
            }
        }
        
    }
}
