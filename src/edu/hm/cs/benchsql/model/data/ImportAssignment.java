package edu.hm.cs.benchsql.model.data;

import java.util.ArrayList;

public class ImportAssignment {
    private final String profileTypeCode;
    private final ArrayList<String> assignmentFields;

    public ImportAssignment(final String profileTypeCode, final ArrayList<String> assignmentFields) {
        this.profileTypeCode = profileTypeCode;
        this.assignmentFields = assignmentFields;
    }

    public ArrayList<String> getAssignmentFields() {
        return this.assignmentFields;
    }

    public String getProfileTypeCode() {
        return this.profileTypeCode;
    }

}
