package ru.job4j.model;

import lombok.*;
import lombok.EqualsAndHashCode.Include;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Accident {
    @Include
    private int id;
    private String name;
    private String text;
    private String address;
    private Set<Rule> rules;
}
