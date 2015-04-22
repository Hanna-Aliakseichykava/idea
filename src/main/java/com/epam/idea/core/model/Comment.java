package com.epam.idea.core.model;

import java.io.Serializable;
import java.time.ZonedDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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

	@ManyToOne
	@JoinColumn(name = "IDEA_ID")
	private Idea subject;

	public Comment() {
		//empty
	}

	public long getId() {
		return id;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
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

	public Idea getSubject() {
		return subject;
	}

	public void setSubject(Idea subject) {
		this.subject = subject;
	}

	@Override
	public String toString() {
		return "Comment{" +
				"id=" + id +
				", body='" + body + '\'' +
				", rating=" + rating +
				'}';
	}

}
