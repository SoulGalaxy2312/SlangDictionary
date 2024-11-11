package services;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.io.*;

public class FileService {
    private LineProcessor lineProcessor = new LineProcessor();

    public Map<String, List<String> > readData(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        Map<String, List<String> > result = new HashMap<>();

        String line = reader.readLine();
        while (line != null) {
            this.lineProcessor.process(line);
            String slang = this.lineProcessor.getSlang();
            List<String> definitions = this.lineProcessor.getDefinitions();

            // Check if 2 attributes are both processed
            if (slang != null && definitions != null) {
                List<String> values = result.putIfAbsent(slang, definitions);
                if (values != null) {
                    for (String definition : definitions) {
                        values.add(definition);
                    }
                }
            }
            line = reader.readLine();
        }
        reader.close();
        return result;
    }
}
