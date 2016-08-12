package edu.hm.cs.benchsql.model.data;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

public class ImportAssignment {
    private final SimpleStringProperty propGrpPropAttributeCode;
    private final SimpleObjectProperty<ImportData> importData;

    public ImportAssignment(final String propGrpPropAttributeCode, final ImportData importData) {
        this.propGrpPropAttributeCode = new SimpleStringProperty(propGrpPropAttributeCode);
        this.importData = new SimpleObjectProperty<>(importData);
    }

    public ObjectProperty<ImportData> getImportDataObject() {
        return this.importData;
    }

    public ImportData getImportData() {
        return this.importData.get();
    }

    public String getPropGrpPropAttributeCode() {
        return this.propGrpPropAttributeCode.get();
    }

    public void setImportData(final ImportData importData) {
        this.importData.set(importData);
    }

}
