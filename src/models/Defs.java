package models;

import java.util.ArrayList;
import java.util.List;

public class Defs {
    private List<String> defs;

    public Defs() {
        this.defs = new ArrayList<>();
    }

    public List<String> getDefs() {
        return this.defs;
    }

    public void setDefs(List<String> defs) {
        if (defs == null) {
            this.defs = new ArrayList<>();
        } else {
            this.defs = new ArrayList<>(defs);
        }
    }

    public void add(String def) {
        this.defs.add(def);
    }

    @Override
    public String toString() {
        return defs.toString();
    }
}
