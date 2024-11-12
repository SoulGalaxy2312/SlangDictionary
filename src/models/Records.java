package models;

import java.util.HashMap;
import java.util.Map;

public class Records {
    Map<Slang, Defs> records;

    public Records() {
        this.records = new HashMap<>();
    }

    public void print() {
        for (Map.Entry<Slang, Defs> entry : this.records.entrySet()) {
               System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            }
    }
    
    public Defs putIfAbsent(Slang slang, Defs defs) {
        return this.records.putIfAbsent(slang, defs);
    }
}
