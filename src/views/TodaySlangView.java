package views;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import models.Defs;
import models.Records;
import models.Slang;
import services.FileService;

public class TodaySlangView extends PopUpView {
    private Calendar calendar = Calendar.getInstance();
    private int dd = calendar.get(Calendar.DATE);
    private int mm = calendar.get(Calendar.MONTH);
    private int yy = calendar.get(Calendar.YEAR);

    public TodaySlangView(JFrame parentFrame, int width, int height, FileService fileService, Records records) {
        super(parentFrame, width, height);
        
        StringBuilder today = new StringBuilder();
        if (dd < 10) {
            today.append(0);
        }
        today.append(dd);
        if (mm < 10) {
            today.append(0);
        }
        today.append(mm);
        today.append(yy);
        String todayFile = today.toString();

        File file = new File(todayFile);

        if (!file.exists()) {
            Set<Slang> keysSet = records.getRecords().keySet();
            ArrayList<Slang> keysArr = new ArrayList<>(keysSet);

            Random generator = new Random();
            Slang todaySlang = keysArr.get(generator.nextInt(keysArr.size()));
            Defs todayDefs = records.getRecords().get(todaySlang);

            Map<Slang, Defs> todayMap = new HashMap<>();
            todayMap.put(todaySlang, todayDefs);
            Records todayRecords = new Records();
            todayRecords.setRecords(todayMap);

            fileService.saveToFile(todayFile, todayRecords);
        }

        try {
            Records todayRecords = fileService.readData(todayFile);
            Map<Slang, Defs> data = todayRecords.getRecords();
            Set<Slang> todaySlang = data.keySet();

            if (todaySlang.size() == 1) {
                Slang slang = todaySlang.stream().findFirst().get();
                List<String> defs = data.get(slang).getDefs();

                JPanel main = new JPanel();
                main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));
                main.setAlignmentX(Component.CENTER_ALIGNMENT);
                main.setAlignmentY(Component.CENTER_ALIGNMENT);

                JLabel slangLabel = new JLabel(slang.getSlang());
                slangLabel.setFont(new Font("Arial", Font.BOLD, 24));
                slangLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

                main.add(Box.createVerticalGlue());
                main.add(slangLabel);                

                for (String def : defs) {
                    JPanel defPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
                    JLabel defLabel = new JLabel(def);
                    defPanel.add(defLabel);
                    main.add(defPanel);
                }
                add(main);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                TodaySlangView.this,
                "Internal error, today slang word not works",
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }

        this.turnOn();
    }
}
