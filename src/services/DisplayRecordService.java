package services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JList;
import javax.swing.JScrollPane;

import models.Defs;
import models.Slang;

public class DisplayRecordService {
    public void displayRecords(JScrollPane scrollPane, Map<Slang, Defs> data) {
        ArrayList<String> result = new ArrayList<>();

        Set<Slang> slangs = data.keySet();        
        for (Slang slang : slangs) {
            List<String> defs = data.get(slang).getDefs();
            String slangText = slang.getSlang();
            String defsText = "\t\t\t\t" + String.join(" -- ", defs);

            result.add(slangText);
            result.add(defsText);
        }

        scrollPane.setViewportView(
            new JList<>(
                result.toArray(
                    new String[result.size()]
                )
            )
        );
    }
}
