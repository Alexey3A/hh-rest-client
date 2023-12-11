package com.example.hhrestclient.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vacancies {
    List<HHVacancy> items;

    public static List<Job> vacanciesToJob(List<HHVacancy> items) {
        List<Job> jobList = new ArrayList<>();
        items.forEach(hhVacancy -> {
            Job job = new Job();
            job.setName(hhVacancy.getName());
            job.setCompany(hhVacancy.getDepartment().getName());
            job.setHref(hhVacancy.getAlternate_url());
            job.setCity(hhVacancy.getArea().getName());
            jobList.add(job);
        });
        return jobList;
    }

    @Override
    public String toString() {
        return "Vacancies{" +
                "items=" + items +
                '}';
    }
}
