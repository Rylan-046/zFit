package edu.cmis.zfit.model;

public record Range (
        Float min,
        Float max
){
    public boolean within(float value) {
        return within(value, true);
    }

    public boolean within(float value, boolean inclusive) {
        return inclusive ?
                (max == null ? value >= min : value >= min && value <= max)  :
                (max == null ? value > min : value > min && value < max);
    }
}