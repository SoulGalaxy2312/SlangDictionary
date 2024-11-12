package services;

import models.Defs;
import models.Slang;

public class LineProcessor {
    private Slang slang;
    private Defs definitions;
    
    public void process(String line) {
        this.slang = null;
        this.definitions = null;

        String[] elements = line.split("`");

        // Check for line where missing seperator (`)
        if (elements.length != 2) {
            System.out.println(line);
            return;
        }

        this.slang = new Slang(elements[0]);
        this.definitions = new Defs();

        String[] defs = elements[1].split("\\|");
        for (String def : defs) {
            this.definitions.add(def.trim());
        }
    }
    
    public Slang getSlang() {
        return this.slang;
    }

    public Defs getDefinitions() {
        return this.definitions;
    }
}
