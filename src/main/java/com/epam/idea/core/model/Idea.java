package com.epam.idea.core.model;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
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
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

@Entity
@Table(name = "IDEA")
public class Idea implements Serializable {

	public static final int MIN_LENGTH_TITLE = 1;
	public static final int MAX_LENGTH_TITLE = 150;
	public static final int MAX_LENGTH_DESCRIPTION = 500;

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
	@CreatedDate
	private ZonedDateTime creationTime;

	@Column(name = "MODIFICATION_TIME", nullable = false)
	@Type(type = "org.jadira.usertype.dateandtime.threeten.PersistentZonedDateTime")
	@LastModifiedDate
	private ZonedDateTime modificationTime;

	@Column(name = "RATING", nullable = false)
	private int rating;

	@ManyToOne
	@JoinColumn(name = "USER_ID")
	private User author;

	@ManyToMany(cascade = CascadeType.PERSIST)
	@JoinTable(name = "IDEA_TAG",
			joinColumns = @JoinColumn(name = "IDEA_ID"),
			inverseJoinColumns = @JoinColumn(name = "TAG_ID"))
	private List<Tag> relatedTags;

	@OneToMany(mappedBy = "subject", cascade = CascadeType.ALL)
	private List<Comment> comments;

	public Idea() {
		this.relatedTags = new ArrayList<>();
		this.comments = new ArrayList<>();
	}

	public long getId() {
		return id;
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

	public ZonedDateTime getCreationTime() {
		return creationTime;
	}

	public ZonedDateTime getModificationTime() {
		return modificationTime;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
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

	public void addTag(Tag tag) {
		this.relatedTags.add(tag);
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public void addComment(Comment comment) {
		this.comments.add(comment);
	}

	public void updateWith(final Idea source) {
		this.title = source.title;
		this.description = source.description;
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

}
