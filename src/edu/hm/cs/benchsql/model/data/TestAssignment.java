package edu.hm.cs.benchsql.model.data;

import javafx.beans.property.SimpleStringProperty;

public class TestAssignment {
    private final SimpleStringProperty propGrpPropAttributeCode;
    private final SimpleStringProperty value;

    public TestAssignment(final String propGrpPropAttributeCode, final String value) {
        this.propGrpPropAttributeCode = new SimpleStringProperty(propGrpPropAttributeCode);
        this.value = new SimpleStringProperty(value);
    }

    public String getPropGrpPropAttributeCode() {
        return this.propGrpPropAttributeCode.get();
    }

    public String getValue() {
        return this.value.get();
    }

    public SimpleStringProperty propGrpPropAttributeCodeProperty() {
        return this.propGrpPropAttributeCode;
    }

    public void setValue(final String value) {
        this.value.set(value);
    }

    public SimpleStringProperty valueProperty() {
        return this.value;
    }
}