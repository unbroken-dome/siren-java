package org.unbrokendome.siren.model;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;


public final class RootEntity extends FullEntity {

    public RootEntity(@Nullable Object properties,
                      List<Element> entities,
                      List<Link> links,
                      List<Action> actions,
                      List<String> classes,
                      @Nullable String title,
                      Map<String, Object> customProperties) {
        super(properties, entities, links, actions, classes, title, customProperties);
    }


    @Nonnull
    public static RootEntityBuilder builder() {
        return new RootEntityBuilder();
    }

    @Override
    public String toString() {
        return "RootEntity{" +
                "properties=" + getProperties() +
                ", entities=" + getEntities() +
                ", links=" + getLinks() +
                ", actions=" + getActions() +
                ", classes=" + getClasses() +
                ", title='" + getTitle() + '\'' +
                ", customProperties=" + getCustomProperties() +
                '}';
    }
}
