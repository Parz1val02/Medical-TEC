package com.example.medicaltec.pojos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Pagination {
    private int total;

    private int count;

    private int per_page;

    private int current_page;

    private int total_pages;
}
