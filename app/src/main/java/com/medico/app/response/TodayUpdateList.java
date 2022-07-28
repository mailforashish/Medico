package com.medico.app.response;

public class TodayUpdateList {
    private String no_find;
    private String today_date;
    private String today_content;
    private int today_off_images;

    public TodayUpdateList(String no_find, String today_date, String today_content, int today_off_images) {
        this.no_find = no_find;
        this.today_date = today_date;
        this.today_content = today_content;
        this.today_off_images = today_off_images;
    }


    public String getNo_find() {
        return no_find;
    }

    public String getToday_date() {
        return today_date;
    }

    public String getToday_content() {
        return today_content;
    }

    public int getToday_off_images() {
        return today_off_images;
    }
}
