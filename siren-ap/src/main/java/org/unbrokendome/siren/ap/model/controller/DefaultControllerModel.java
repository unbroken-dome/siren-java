package org.unbrokendome.siren.ap.model.controller;

import com.google.common.collect.ImmutableList;

import java.util.Collection;


public class DefaultControllerModel implements ControllerModel {

    private final Collection<ControllerInfo> controllerInfos;


    public DefaultControllerModel(Collection<ControllerInfo> controllerInfos) {
        this.controllerInfos = ImmutableList.copyOf(controllerInfos);
    }


    @Override
    public Collection<ControllerInfo> getControllerInfos() {
        return controllerInfos;
    }
}
