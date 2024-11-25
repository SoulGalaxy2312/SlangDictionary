package services;

import models.Defs;
import models.Records;
import models.Slang;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.io.*;

public class FileService {
    private LineProcessor lineProcessor = new LineProcessor();

    public Records readData(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        Records result = new Records();

        String line = reader.readLine();
        boolean isFirstLine = true;
        while (line != null) {
            this.lineProcessor.process(line);
            Slang slang = this.lineProcessor.getSlang();
            Defs definitions = this.lineProcessor.getDefinitions();

            if (isFirstLine) {
                isFirstLine = false;
                line = reader.readLine();
                continue; 
            }

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

    public void saveCurrentDictionary(String fileName, Records records) {
        Map<Slang, Defs> data = records.getRecords();
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            writer.write("Slag`Meaning");
            writer.newLine();

            Set<Slang> keyList = data.keySet();

            for (Slang slang : keyList) {
                Defs defs = data.get(slang);
                List<String> defsList = defs.getDefs();

                StringBuilder line = new StringBuilder();
                line.append(slang.getSlang());
                line.append("`");
                line.append(String.join("|", defsList));

                writer.write(line.toString());
                writer.newLine();
            }   

            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }       
    }

    public boolean deleteFile(String fileName) {
        File file = new File(fileName);
        return file.delete();
    }
}