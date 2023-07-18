package com.example.medicaltec.pojos;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Root {
    private List<Data> data;

    private Meta meta;
}
