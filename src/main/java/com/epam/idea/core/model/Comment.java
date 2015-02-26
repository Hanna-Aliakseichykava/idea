package com.epam.idea.core.model;

import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.ZonedDateTime;

@Entity
@Table(name = "COMMENT")
public class Comment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;

    @Column(name = "BODY", nullable = false)
    private String body;

    @Column(name = "CREATION_TIME", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime")
    private ZonedDateTime creationTime;

    @Column(name = "MODIFICATION_TIME", nullable = false)
    @Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime")
    private ZonedDateTime modificationTime;

    @Column(name = "RATING", nullable = false)
    private int rating;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User author;

    @ManyToOne
    @JoinColumn(name = "IDEA_ID")
    private Idea subject;

    public Comment() {
        //empty
    }

    private Comment(final Builder builder) {
        this.id = builder.id;
        this.body = builder.body;
        this.rating = builder.rating;
        this.creationTime = builder.creationTime;
        this.modificationTime = builder.modificationTime;
        this.author = builder.author;
        this.subject = builder.subject;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static Builder getBuilderFrom(final Comment comment) {
        return new Builder()
                .withId(comment.id)
                .withBody(comment.body)
                .withRating(comment.rating)
                .withCreationTime(comment.creationTime)
                .withModificationTime(comment.modificationTime)
                .withAuthor(comment.author)
                .withSubject(comment.subject);
    }

    public long getId() {
        return id;
    }

    public String getBody() {
        return body;
    }

    public ZonedDateTime getCreationTime() {
        return creationTime;
    }

    public ZonedDateTime getModificationTime() {
        return modificationTime;
    }

    public int getRating() {
        return rating;
    }

    public User getAuthor() {
        return author;
    }

    public Idea getSubject() {
        return subject;
    }

    @PreUpdate
    public void preUpdate() {
        this.modificationTime = ZonedDateTime.now();
    }

    @PrePersist
    public void prePersist() {
        ZonedDateTime now = ZonedDateTime.now();
        this.creationTime = now;
        this.modificationTime = now;
    }

    public static class Builder {
        private long id;
        private String body;
        private int rating;
        private ZonedDateTime creationTime;
        private ZonedDateTime modificationTime;
        private User author;
        private Idea subject;

        private Builder() {
            //empty
        }

        private Builder withId(final long id) {
            this.id = id;
            return this;
        }

        public Builder withBody(final String body) {
            this.body = body;
            return this;
        }

        public Builder withRating(final int rating) {
            this.rating = rating;
            return this;
        }

        private Builder withCreationTime(final ZonedDateTime creationTime) {
            this.creationTime = creationTime;
            return this;
        }

        private Builder withModificationTime(final ZonedDateTime modificationTime) {
            this.modificationTime = modificationTime;
            return this;
        }

        public Builder withAuthor(final User author) {
            this.author = author;
            return this;
        }

        public Builder withSubject(final Idea subject) {
            this.subject = subject;
            return this;
        }

        public Comment build() {
            return new Comment(this);
        }
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", body='" + body + '\'' +
                ", rating=" + rating +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Comment comment = (Comment) o;

        if (rating != comment.rating) return false;
        if (!author.equals(comment.author)) return false;
        if (!body.equals(comment.body)) return false;
        if (creationTime != null ? !creationTime.equals(comment.creationTime) : comment.creationTime != null)
            return false;
        if (modificationTime != null ? !modificationTime.equals(comment.modificationTime) : comment.modificationTime != null)
            return false;
        if (!subject.equals(comment.subject)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = body.hashCode();
        result = 31 * result + (creationTime != null ? creationTime.hashCode() : 0);
        result = 31 * result + (modificationTime != null ? modificationTime.hashCode() : 0);
        result = 31 * result + rating;
        result = 31 * result + author.hashCode();
        result = 31 * result + subject.hashCode();
        return result;
    }
}
