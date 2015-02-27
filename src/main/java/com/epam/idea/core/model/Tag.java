package com.epam.idea.core.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "TAG")
public class Tag implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private long id;

	@Column(name = "NAME", nullable = false)
	private String name;

	@ManyToMany
	@JoinTable(name = "IDEA_TAG",
			joinColumns = @JoinColumn(name = "TAG_ID"),
			inverseJoinColumns = @JoinColumn(name = "IDEA_ID"))
	private List<Idea> ideasWithTag;

	public Tag() {
		//empty
	}

	private Tag(final Builder builder) {
		this.id =           builder.id;
		this.name =         builder.name;
		this.ideasWithTag = builder.ideasWithTag;
	}

	public static Builder getBuilder() {
		return new Builder();
	}

	public static Builder getBuilderFrom(final Tag tag) {
		return new Builder()
				.withId(tag.id)
				.withName(tag.name)
				.withIdeas(tag.ideasWithTag);
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<Idea> getIdeasWithTag() {
		return ideasWithTag;
	}

	public static class Builder {
		private long id;
		private String name;
		private List<Idea> ideasWithTag = new ArrayList<>();

		private Builder() {
			//empty
		}

		private Builder withId(final long id) {
			this.id = id;
			return this;
		}

		public Builder withName(final String name) {
			this.name = name;
			return this;
		}

		public Builder withIdeas(final List<Idea> ideasWithTag) {
			this.ideasWithTag = ideasWithTag;
			return this;
		}

		public Builder addIdea(final Idea idea) {
			this.ideasWithTag.add(idea);
			return this;
		}

		public Tag build() {
			return new Tag(this);
		}
	}

	@Override
	public String toString() {
		return "Tag{" +
				"id=" + id +
				", name='" + name +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Tag tag = (Tag) o;

		if (ideasWithTag != null ? !ideasWithTag.equals(tag.ideasWithTag) : tag.ideasWithTag != null) return false;
		if (!name.equals(tag.name)) return false;

		return true;
	}

	@Override
	public int hashCode() {
		int result = name.hashCode();
		result = 31 * result + (ideasWithTag != null ? ideasWithTag.hashCode() : 0);
		return result;
	}
}
