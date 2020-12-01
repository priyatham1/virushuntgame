package com.ps.vh.http;

import androidx.annotation.Keep;

@Keep
public class AddressComponent {
    String long_name;
    String[] types;

    public String getLong_name() {
        return long_name;
    }

    public void setLong_name(String long_name) {
        this.long_name = long_name;
    }

    public String[] getTypes() {
        return types;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }
}
