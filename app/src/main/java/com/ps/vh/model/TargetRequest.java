package com.ps.vh.model;

import androidx.annotation.Keep;

@Keep
public class TargetRequest {

    Target target;
    TargetArea country;
    TargetArea state;
    TargetArea city;

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public TargetArea getCountry() {
        return country;
    }

    public void setCountry(TargetArea country) {
        this.country = country;
    }

    public TargetArea getState() {
        return state;
    }

    public void setState(TargetArea state) {
        this.state = state;
    }

    public TargetArea getCity() {
        return city;
    }

    public void setCity(TargetArea city) {
        this.city = city;
    }
}
