package models;

import java.util.HashMap;
import java.util.Map;

public class Records {
    private Map<Slang, Defs> records = new HashMap<>();

    public Map<Slang, Defs> getRecords() {
        return records;
    }

    public void setRecords(Map<Slang, Defs> records) {
        this.records = records;    
    }

    public void print() {
        for (Map.Entry<Slang, Defs> entry : records.entrySet()) {
               System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
            }
    }
    
    public Defs putIfAbsent(Slang slang, Defs defs) {
        return records.putIfAbsent(slang, defs);
    }
}
