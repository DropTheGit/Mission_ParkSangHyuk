package com.ll;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
class Quotation {
    private String content;
    private String author;
    private int id;
}