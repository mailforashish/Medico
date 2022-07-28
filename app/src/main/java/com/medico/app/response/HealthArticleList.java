package com.medico.app.response;

public class HealthArticleList {
    String article;
    String articleDate;

    public HealthArticleList(String article, String articleDate) {
        this.article = article;
        this.articleDate = articleDate;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getArticleDate() {
        return articleDate;
    }

    public void setArticleDate(String articleDate) {
        this.articleDate = articleDate;
    }
}
