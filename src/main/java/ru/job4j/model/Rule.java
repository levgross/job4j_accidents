package ru.job4j.model;

import lombok.*;
import lombok.EqualsAndHashCode.Include;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Rule {
    @Include
    private int id;
    private String name;
}
