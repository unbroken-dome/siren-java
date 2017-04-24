package org.unbrokendome.siren.model;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;


public abstract class Entity extends Element {

    public Entity(List<String> classes, @Nullable String title, Map<String, Object> customProperties) {
        super(classes, title, customProperties);
    }
}
