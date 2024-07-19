package com.tawasalna.shared.mail;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TemplateVariable {

    private String name;
    private Object value;
}
