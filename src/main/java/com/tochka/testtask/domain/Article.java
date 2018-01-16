package com.tochka.testtask.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.Valid;

@Entity
@Data @Accessors(chain = true)
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true)
    private long id;

    @Valid
    @Column(unique=true)
    private String url;

    @OneToOne(cascade = {CascadeType.ALL})
    @Valid
    private Caption caption;

    private String text;

    public Article() {
    }

    public Article(String url, String text) {
        this.url = url;
        this.text = text;
    }
}
