package org.unbrokendome.siren.model;


public abstract class EntityBuilder<T extends Entity, B extends EntityBuilder<T, B>>
        extends ElementBuilder<T, B> {
}
