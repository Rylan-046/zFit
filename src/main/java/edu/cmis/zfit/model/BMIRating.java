package edu.cmis.zfit.model;

public enum BMIRating {
    UNDERWEIGHT(new Range(0f, 18.4f)),
    HEALTHY_WEIGHT(new Range(18.5f, 24.9f)),
    OVERWEIGHT(new Range(25f, 29f)),
    OBESITY(new Range(30f, null));

    private Range range;

    BMIRating(Range range) {
        this.range = range;
    }
}