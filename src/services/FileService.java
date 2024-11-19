package services;

import models.Defs;
import models.Records;
import models.Slang;

import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class FileService {
    private LineProcessor lineProcessor = new LineProcessor();

    public Records readData(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        Records result = new Records();

        String line = reader.readLine();
        while (line != null) {
            this.lineProcessor.process(line);
            Slang slang = this.lineProcessor.getSlang();
            Defs definitions = this.lineProcessor.getDefinitions();

            // Check if 2 attributes are both processed
            if (slang != null && definitions != null) {
                List<String> defs = definitions.getDefs();
                Defs values = result.putIfAbsent(slang, definitions);
                if (values != null) {
                    
                    for (String definition : defs) {
                        values.add(definition);
                    }
                }
            }
            line = reader.readLine();
        }
        reader.close();
        return result;
    }

    public void saveToHistory(String search) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("history.txt", true));
            writer.append(search);
            writer.newLine();
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public List<String> getFromHistory() {
        List<String> result = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("history.txt"));
            
            String line = reader.readLine();
            while (line != null) {
                result.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return result;
    }
}