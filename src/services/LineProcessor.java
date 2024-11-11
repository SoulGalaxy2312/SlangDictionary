package services;

import java.util.ArrayList;
import java.util.List;

public class LineProcessor {
    private String slang = null;
    private List<String> definitions = null;
    
    public void process(String line) {
        this.slang = null;
        this.definitions = null;

        String[] elements = line.split("`");

        // Check for line where missing seperator (`)
        if (elements.length != 2) {
            System.out.println(line);
            return;
        }

        this.slang = elements[0];
        this.definitions = new ArrayList<>();

        String[] defs = elements[1].split("\\|");
        for (String def : defs) {
            this.definitions.add(def.trim());
        }
    }
    
    public String getSlang() {
        return this.slang;
    }

    public List<String> getDefinitions() {
        return this.definitions;
    }
}
