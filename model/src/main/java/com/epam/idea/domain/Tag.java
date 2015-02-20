package com.epam.idea.domain;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import java.util.List;

@Entity
@Table(name = "TAGS")
public class Tag implements Persistent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TAG_ID")
    private int id;

    @Column(name = "NAME")
    private String name;
    
    private List<Idea> ideasWithTag;

    public Tag() {
        //empty
    }

    public Tag(String name) {
        this.name = name;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Idea> getIdeasWithTag() {
        return ideasWithTag;
    }

    public void setIdeasWithTag(List<Idea> ideasWithTag) {
        this.ideasWithTag = ideasWithTag;
    }
}
