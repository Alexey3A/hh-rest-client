package com.example.hhrestclient.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class HHVacancy {
    String id;
    String name;
    String alternate_url;
    String created_at;
    Company department;
    Area area;

    @Override
    public String toString() {
        return "HHVacancy{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", alternate_url='" + alternate_url + '\'' +
                ", created_at='" + created_at + '\'' +
                ", department=" + department +
                ", area=" + area +
                '}';
    }
}
