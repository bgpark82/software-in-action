package com.bgpark.springjpaappv1.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue(value = "B")
@Getter
@Setter
public class Book extends Item {

    private String author;

    private String isbn;
}
