package com.tochka.testtask.domain;

import com.tochka.testtask.domain.converter.ResourceTypeConverter;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Data @Accessors(chain = true)
public class NewsResource {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, unique = true)
    private long id;

    @Column(unique = true)
    private String url;

    @OneToOne(cascade = {CascadeType.ALL})
    private ParsingRule parsingRule;

    @Basic
    @Convert(converter = ResourceTypeConverter.class)
    private ResourceType resourceType;

    public NewsResource() {
    }

    public NewsResource(String url) {
        this.url = url;
    }
}
