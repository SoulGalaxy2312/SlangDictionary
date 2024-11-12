package models;

import java.util.Objects;

public class Slang {
    private String slang;
    
    public Slang(String slang) {
        this.slang = slang;
    }
    
    public String getSlang() {
        return this.slang;
    }

    public void setSlang(String slang) {
        this.slang = slang;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Slang compared = (Slang) o;
        return this.slang.equals(compared.getSlang());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.slang);
    }

    @Override
    public String toString() {
        return this.slang.toString();
    }
}
