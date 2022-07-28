package com.medico.app.response;

public class VisitedList {
    String Doctor_Name;
    String specialist;

    public VisitedList(String doctor_Name, String specialist) {
        Doctor_Name = doctor_Name;
        this.specialist = specialist;
    }

    public String getDoctor_Name() {
        return Doctor_Name;
    }

    public String getSpecialist() {
        return specialist;
    }

}
