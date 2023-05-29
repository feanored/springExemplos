package com.binha.demo.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class BookLendDto {
    private List<String> bookIds;
    private String memberId;
}
