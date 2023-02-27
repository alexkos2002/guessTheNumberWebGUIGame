package com.example.guessthenumberwebguigame.dto;

public class NumberBoundsDTO {

    private String lowerBound;
    private String upperBound;

    public NumberBoundsDTO(String lowerBound, String upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    public NumberBoundsDTO() {
    }

    public String getLowerBound() {
        return lowerBound;
    }

    public void setLowerBound(String lowerBound) {
        this.lowerBound = lowerBound;
    }

    public String getUpperBound() {
        return upperBound;
    }

    public void setUpperBound(String upperBound) {
        this.upperBound = upperBound;
    }
}
