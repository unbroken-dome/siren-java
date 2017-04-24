package org.unbrokendome.siren.spring.ap.controller;

public abstract class AbstractRequestMappingInfo implements RequestMappingInfo {

    @Override
    public String toString() {
        return "path=/" + String.join("/", getPathSegments()) + ", method=" + getRequestMethods();
    }
}
