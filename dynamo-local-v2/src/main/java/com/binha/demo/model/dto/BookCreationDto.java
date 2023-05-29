package com.binha.demo.model.dto;

import lombok.Data;

@Data
public class BookCreationDto {
    private String name;
    private String isbn;
    private String authorId;
}
