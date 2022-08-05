package com.medico.app.response;

public class NeedList {
    private Integer image_icon;
    private String modeTitle;

    public NeedList(Integer image_icon, String modeTitle) {
        this.image_icon = image_icon;
        this.modeTitle = modeTitle;
    }

    public Integer getImage_icon() {
        return image_icon;
    }

    public void setImage_icon(Integer image_icon) {
        this.image_icon = image_icon;
    }

    public String getModeTitle() {
        return modeTitle;
    }

    public void setModeTitle(String modeTitle) {
        this.modeTitle = modeTitle;
    }
}
