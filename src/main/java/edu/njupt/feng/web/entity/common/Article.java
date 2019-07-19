package edu.njupt.feng.web.entity.common;

import java.io.Serializable;

/**
 * 文章信息实体类
 */
public class Article implements Serializable {
    private String title;
    private String author;
    private String publishTime;
    private String journal;
    private String summary;
    private String word;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return "Article{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publishTime='" + publishTime + '\'' +
                ", journal='" + journal + '\'' +
                ", summary='" + summary + '\'' +
                ", word='" + word + '\'' +
                '}';
    }
}
