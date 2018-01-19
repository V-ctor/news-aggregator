package com.tochka.testtask.domain;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Articles")
@Immutable
@Data @Accessors(chain = true)
public class Title {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true)
    private long id;

    private String title;
    private String author;
    private String date;
    public Title() {
    }

    public Title(String title) {
        this.title = title;
    }
}
