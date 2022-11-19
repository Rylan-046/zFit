package edu.cmis.zfit.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

public record DateRange(Instant begin, Instant end) {

    public boolean within(Instant value) {
        ZoneOffset zoneOffset = ZoneId.systemDefault().getRules().getOffset(Instant.now());

        LocalDateTime ldtBegin = LocalDateTime.ofInstant(begin, zoneOffset);
        LocalDateTime ldtEnd = LocalDateTime.ofInstant(end, zoneOffset);
        LocalDateTime ldtValue = LocalDateTime.ofInstant(value, zoneOffset);

        return ldtValue.isAfter(ldtBegin) && ldtValue.isBefore(ldtEnd);
    }
}