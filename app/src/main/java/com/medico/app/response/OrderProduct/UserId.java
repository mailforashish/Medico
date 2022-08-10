package com.medico.app.response.OrderProduct;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserId {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("first_name")
    @Expose
    private Object firstName;
    @SerializedName("email")
    @Expose
    private Object email;
    @SerializedName("last_name")
    @Expose
    private Object lastName;
    @SerializedName("mobile")
    @Expose
    private Long mobile;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Object getFirstName() {
        return firstName;
    }

    public void setFirstName(Object firstName) {
        this.firstName = firstName;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Object getLastName() {
        return lastName;
    }

    public void setLastName(Object lastName) {
        this.lastName = lastName;
    }

    public Long getMobile() {
        return mobile;
    }

    public void setMobile(Long mobile) {
        this.mobile = mobile;
    }

}
