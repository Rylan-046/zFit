package edu.cmis.zfit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.Instant;

public class DateRange {
   private Instant begin;
   private Instant end;

   public DateRange() {
   }

    public DateRange(Instant begin, Instant end) {
        this.begin = begin;
        this.end = end;
    }

    public Instant getBegin() {
        return begin;
    }

    public void setBegin(Instant begin) {
        this.begin = begin;
    }

    public Instant getEnd() {
        return end;
    }

    public void setEnd(Instant end) {
        this.end = end;
    }

    // TODO
    @JsonIgnore
    public DateRange getDuration() {
        return null;
    }

    @Override
    public String toString() {
        return "DateRange{" +
                "begin=" + begin +
                ", end=" + end +
                '}';
    }
}