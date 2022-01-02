package com.example.spin_.model;

public class JoinedEstateTypes {
    private Long eId;
    private String typeName;

    public JoinedEstateTypes() {
    }

    public JoinedEstateTypes(Long eId, String typeName) {
        this.eId = eId;
        this.typeName = typeName;
    }

    public Long geteId() {
        return eId;
    }

    public void seteId(Long eId) {
        this.eId = eId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
