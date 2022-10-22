package edu.cmis.zfit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.Instant;

public record DateRange(Instant begin, Instant end) {
    // TODO
    @JsonIgnore
    public DateRange getDuration() {
        return null;
    }
}