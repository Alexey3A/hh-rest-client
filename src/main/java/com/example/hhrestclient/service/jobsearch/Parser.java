package com.example.hhrestclient.service.jobsearch;

import java.util.List;
import java.util.Set;

public interface Parser<T> {
    public Set<T> parse();
}
