package com.ll;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@AllArgsConstructor
@Getter
@Setter
class Quotation implements Serializable {
    private String content;
    private String author;
    private int id;
}