package com.example.hhrestclient.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vacancies {
    List<HHVacancy> items;

    @Override
    public String toString() {
        return "Vacancies{" +
                "items=" + items +
                '}';
    }
}
