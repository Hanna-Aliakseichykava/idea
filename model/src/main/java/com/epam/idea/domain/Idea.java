package com.epam.idea.domain;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "IDEAS")
public class Idea implements Persistent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "IDEA_ID")
    private int id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "PUBLISHED_DATE")
    private Date publishedDate;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "USER_ID")
    private User author;
    
    private List<Tag> relatedTags;

    @Column(name = "RATING")
    private int rating;

    public Idea() {
        //empty
    }

    public Idea(String title, String description) {
        this.title = title;
        this.description = description;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public List<Tag> getRelatedTags() {
        return relatedTags;
    }

    public void setRelatedTags(List<Tag> relatedTags) {
        this.relatedTags = relatedTags;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        return "Idea{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", publishedDate=" + publishedDate +
                ", author=" + author +
                ", rating=" + rating +
                ", id=" + id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Idea idea = (Idea) o;

        if (rating != idea.rating) return false;
        if (!author.equals(idea.author)) return false;
        if (!description.equals(idea.description)) return false;
        if (!publishedDate.equals(idea.publishedDate)) return false;
        if (!relatedTags.equals(idea.relatedTags)) return false;
        if (!title.equals(idea.title)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + publishedDate.hashCode();
        result = 31 * result + author.hashCode();
        result = 31 * result + relatedTags.hashCode();
        result = 31 * result + rating;
        return result;
    }
}
