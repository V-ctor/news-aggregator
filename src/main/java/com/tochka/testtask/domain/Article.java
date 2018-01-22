package com.tochka.testtask.domain;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.Valid;
import java.util.Date;

@Entity
@Table(name = "Articles")
@Data @Accessors(chain = true)
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true)
    private long id;

    @Valid
    @Column(unique = true, length = 5 * 1024)
    private String url;

    @Column(length = 1024)
    private String title;
    private String author;
    private Date date;

    @Column(length = 5 * 1024 * 1024)
    private String text;

    public Article() {
    }

    public Article(String url, String text) {
        this.url = url;
        this.text = text;
    }

    public Article(String url, String text, Date date, String author) {
        this.url = url;
        this.author = author;
        this.date = date;
        this.text = text;
    }
}
