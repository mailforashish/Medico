package com.medico.app.response;

public class AllSpecialityDoctorsList {
    private String doctors_name;
    private String specialist_field;
    private String total_year_exp;
    private long fees;
    private double rating;
    private int review;
    private String availability;

    public AllSpecialityDoctorsList(String doctors_name, String specialist_field, String total_year_exp, long fees, double rating, int review, String availability) {
        this.doctors_name = doctors_name;
        this.specialist_field = specialist_field;
        this.total_year_exp = total_year_exp;
        this.fees = fees;
        this.rating = rating;
        this.review = review;
        this.availability = availability;
    }

    public String getDoctors_name() {
        return doctors_name;
    }

    public String getSpecialist_field() {
        return specialist_field;
    }

    public String getTotal_year_exp() {
        return total_year_exp;
    }

    public long getFees() {
        return fees;
    }

    public double getRating() {
        return rating;
    }

    public int getReview() {
        return review;
    }

    public String getAvailability() {
        return availability;
    }

}
