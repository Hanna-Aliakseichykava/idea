package com.epam.idea.domain;


import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "IDEAS")
public class Idea implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID")
    private long id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

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

    @ManyToMany
    @JoinTable(name = "IDEA_TAGS",
            joinColumns = @JoinColumn(name = "IDEA_ID"),
            inverseJoinColumns = @JoinColumn(name = "TAG_ID"))
    private Set<Tag> relatedTags;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
    private Set<Comment> comments;

    public Idea() {
        //empty
    }

    private Idea(final Builder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.description = builder.description;
        this.creationTime = builder.creationTime;
        this.modificationTime = builder.modificationTime;
        this.rating = builder.rating;
        this.author = builder.author;
        this.relatedTags = builder.relatedTags;
        this.comments = builder.comments;
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static Builder getBuilderFrom(final Idea idea) {
        return new Builder()
                .withId(idea.id)
                .withTitle(idea.title)
                .withDescription(idea.description)
                .withCreationTime(idea.creationTime)
                .withModificationTime(idea.modificationTime)
                .withRating(idea.rating)
                .withAuthor(idea.author)
                .withTags(idea.relatedTags)
                .withComments(idea.comments);
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
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

    public Set<Tag> getRelatedTags() {
        return relatedTags;
    }

    public Set<Comment> getComments() {
        return comments;
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
        private String title;
        private String description;
        private ZonedDateTime creationTime;
        private ZonedDateTime modificationTime;
        private int rating;
        private User author;
        private Set<Tag> relatedTags = new HashSet<>();
        private Set<Comment> comments = new HashSet<>();

        private Builder() {
            //empty
        }

        private Builder withId(final long id) {
            this.id = id;
            return this;
        }

        public Builder withTitle(final String title) {
            this.title = title;
            return this;
        }

        public Builder withDescription(final String description) {
            this.description = description;
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

        public Builder withRating(final int rating) {
            this.rating = rating;
            return this;
        }

        public Builder withAuthor(final User author) {
            this.author = author;
            return this;
        }

        public Builder withTags(final Set<Tag> relatedTags) {
            this.relatedTags = relatedTags;
            return this;
        }

        public Builder addTag(final Tag tag) {
            this.relatedTags.add(tag);
            return this;
        }

        public Builder withComments(final Set<Comment> comments) {
            this.comments = comments;
            return this;
        }

        public Builder addComment(final Comment comment) {
            this.comments.add(comment);
            return this;
        }

        public Idea build() {
            return new Idea(this);
        }
    }

    @Override
    public String toString() {
        return "Idea{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", rating=" + rating +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Idea idea = (Idea) o;

        if (rating != idea.rating) return false;
        if (!author.equals(idea.author)) return false;
        if (comments != null ? !comments.equals(idea.comments) : idea.comments != null) return false;
        if (creationTime != null ? !creationTime.equals(idea.creationTime) : idea.creationTime != null) return false;
        if (!description.equals(idea.description)) return false;
        if (modificationTime != null ? !modificationTime.equals(idea.modificationTime) : idea.modificationTime != null)
            return false;
        if (relatedTags != null ? !relatedTags.equals(idea.relatedTags) : idea.relatedTags != null) return false;
        if (!title.equals(idea.title)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + (creationTime != null ? creationTime.hashCode() : 0);
        result = 31 * result + (modificationTime != null ? modificationTime.hashCode() : 0);
        result = 31 * result + rating;
        result = 31 * result + author.hashCode();
        result = 31 * result + (relatedTags != null ? relatedTags.hashCode() : 0);
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        return result;
    }
}
