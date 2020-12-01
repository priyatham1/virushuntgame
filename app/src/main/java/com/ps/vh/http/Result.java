package com.ps.vh.http;

import androidx.annotation.Keep;

@Keep
public class Result {
    AddressComponent[] address_components;
    Geometry geometry;

    public AddressComponent[] getAddress_components() {
        return address_components;
    }

    public void setAddress_components(AddressComponent[] address_components) {
        this.address_components = address_components;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }
}
