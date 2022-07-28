package com.medico.app.response;

public class MyAccountList {
    private String options;
    private Integer options_image;

    public MyAccountList(String options, Integer options_image) {
        this.options = options;
        this.options_image = options_image;
    }

    public String getOptions() {
        return options;
    }

    public Integer getOptions_image() {
        return options_image;
    }
}
