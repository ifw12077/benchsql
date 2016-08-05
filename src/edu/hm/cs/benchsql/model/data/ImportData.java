package edu.hm.cs.benchsql.model.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ImportData {

    private final SimpleStringProperty importData;

    public ImportData(final String importData) {
        this.importData = new SimpleStringProperty(importData);
    }

    public StringProperty getImportData() {
        return this.importData;
    }

    public String getImportDataValue() {
        return this.importData.get();
    }

    public void setImportDataValue(final String typ) {
        this.importData.set(typ);
    }

    @Override
    public String toString() {
        return this.importData.get();
    }
}