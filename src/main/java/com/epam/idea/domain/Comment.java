package com.epam.idea.domain;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

public class Comment implements Persistent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "COMMENT_ID")
    private int id;

    @Column(name = "AUTHOR")
    private User author;

    @Column(name = "PUBLISHED_DATE")
    private Date publishedDate;

    private Idea relatedIdea;

    public Comment() {
        //empty
    }

    public Comment(User author, Date publishedDate, Idea relatedIdea) {
        this.author = author;
        this.publishedDate = publishedDate;
        this.relatedIdea = relatedIdea;
    }

    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Idea getRelatedIdea() {
        return relatedIdea;
    }

    public void setRelatedIdea(Idea relatedIdea) {
        this.relatedIdea = relatedIdea;
    }
    
    
}
