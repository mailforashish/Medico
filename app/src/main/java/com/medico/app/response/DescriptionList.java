package com.medico.app.response;

public class DescriptionList {

    private int img_tab;
    String md_title;
    String md_sub_title;

    public DescriptionList(int img_tab, String md_title, String md_sub_title) {
        this.img_tab = img_tab;
        this.md_title = md_title;
        this.md_sub_title = md_sub_title;
    }

    public int getImg_tab() {
        return img_tab;
    }

    public void setImg_tab(int img_tab) {
        this.img_tab = img_tab;
    }

    public String getMd_title() {
        return md_title;
    }

    public void setMd_title(String md_title) {
        this.md_title = md_title;
    }

    public String getMd_sub_title() {
        return md_sub_title;
    }

    public void setMd_sub_title(String md_sub_title) {
        this.md_sub_title = md_sub_title;
    }
}
