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
public class Caption {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true)
    private long id;

    private String text;

    @OneToOne(cascade = {CascadeType.ALL})
    @Valid
    private Article article;

    public Caption() {
    }

    public Caption(String text) {
        this.text = text;
    }
}
