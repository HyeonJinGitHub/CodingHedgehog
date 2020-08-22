package org.tensorflow.demo;

import java.io.Serializable;

public class PillInteractionVO implements Serializable {
    String drug_name1;
    String drug_name2;
    String effect;

    public String getDrug_name1() {
        return drug_name1;
    }
    public void setDrug_name1(String drug_name1) {
        this.drug_name1 = drug_name1;
    }
    public String getDrug_name2() {
        return drug_name2;
    }
    public void setDrug_name2(String drug_name2) {
        this.drug_name2 = drug_name2;
    }
    public String getEffect() {
        return effect;
    }
    public void setEffect(String effect) { this.effect = effect; }
}
