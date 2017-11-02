package org.unbrokendome.siren.kotlin

import org.unbrokendome.siren.model.*
import java.util.function.Consumer


abstract class ElementBuilderKt<T : Element, B : ElementBuilder<T, B>>
protected constructor (protected val builder: B) {

    var title: String?
            get() = builder.title
            set(value) { builder.title = value }

    var className: String?
            get() = builder.classNames.firstOrNull()
            set(value) { builder.setClassName(requireNotNull(value)) }

    var classNames: List<String>
            get() = builder.classNames
            set(value) { builder.setClassNames(value) }

    fun with(spec: Consumer<B>) =
            builder.with(spec)
}


abstract class EntityBuilderKt<T : Entity, B : EntityBuilder<T, B>>
protected constructor(builder: B)
    : ElementBuilderKt<T, B>(builder)


abstract class ReferenceBuilderKt<T : Reference, B : ReferenceBuilder<T, B>>
protected constructor(builder: B)
    : ElementBuilderKt<T, B>(builder) {

    var href: String?
            get() = builder.href
            set(value) { builder.href = value }

    var type: String?
            get() = builder.type
            set(value) { builder.type = value }
}


class LinkBuilderKt (builder: LinkBuilder)
    : ReferenceBuilderKt<Link, LinkBuilder>(builder)


class ActionBuilderKt (builder: ActionBuilder)
    : ReferenceBuilderKt<Action, ActionBuilder>(builder) {

    var method: String?
            get() = builder.method
            set(value) { builder.method = value }

    fun field(name: String, spec: Consumer<ActionFieldBuilder>) {
        builder.addField(name, spec)
    }

    fun field(name: String, spec: ActionFieldBuilderKt.() -> Unit) =
            field(name, Consumer<ActionFieldBuilder> { ActionFieldBuilderKt(it).spec() })
}


class ActionFieldBuilderKt (builder: ActionFieldBuilder)
    : ElementBuilderKt<ActionField, ActionFieldBuilder>(builder) {

    var type: ActionField.Type
            get() = builder.type
            set(value) { builder.type = value }

    var value: Any?
            get() = builder.value
            set(value) { builder.value = value }
}



abstract class FullEntityBuilderKt<T : FullEntity, B : FullEntityBuilder<T, B>>
protected constructor(builder: B)
    : EntityBuilderKt<T, B>(builder) {

    fun property(name: String, value: Any?) =
            builder.addProperty(name, value)

    fun properties(value: Map<String, *>) =
            builder.addProperties(value)

    fun embeddedEntity(rel: String, spec: Consumer<EmbeddedEntityBuilder>) {
        builder.addEmbeddedEntity(rel, spec)
    }

    fun embeddedEntity(rels: Array<String>, spec: Consumer<EmbeddedEntityBuilder>) {
        builder.addEmbeddedEntity(rels, spec)
    }

    fun embeddedEntity(rel: String, spec: EmbeddedEntityBuilderKt.() -> Unit) =
            embeddedEntity(rel, Consumer<EmbeddedEntityBuilder> { EmbeddedEntityBuilderKt(it).spec() })

    fun embeddedEntity(rels: Array<String>, spec: EmbeddedEntityBuilderKt.() -> Unit) =
            embeddedEntity(rels, Consumer<EmbeddedEntityBuilder> { EmbeddedEntityBuilderKt(it).spec() })

    fun embeddedLink(rel: String, spec: Consumer<LinkBuilder>) {
        builder.addEmbeddedLink(rel, spec)
    }

    fun embeddedLink(rels: Array<String>, spec: Consumer<LinkBuilder>) {
        builder.addEmbeddedLink(rels, spec)
    }

    fun embeddedLink(rel: String, spec: LinkBuilderKt.() -> Unit) =
            embeddedLink(rel, Consumer<LinkBuilder> { LinkBuilderKt(it).spec() })

    fun embeddedLink(rels: Array<String>, spec: LinkBuilderKt.() -> Unit) =
            embeddedLink(rels, Consumer<LinkBuilder> { LinkBuilderKt(it).spec() })

    fun link(rel: String, spec: Consumer<LinkBuilder>) {
        builder.addLink(rel, spec)
    }

    fun link(rels: Array<String>, spec: Consumer<LinkBuilder>) {
        builder.addLink(rels, spec)
    }

    fun link(rel: String, spec: LinkBuilderKt.() -> Unit) =
            link(rel, Consumer<LinkBuilder> { LinkBuilderKt(it).spec() })

    fun link(rels: Array<String>, spec: LinkBuilderKt.() -> Unit) =
            link(rels, Consumer<LinkBuilder> { LinkBuilderKt(it).spec() })

    fun action(name: String, spec: Consumer<ActionBuilder>) {
        builder.addAction(name, spec)
    }

    fun action(name: String, spec: ActionBuilderKt.() -> Unit) =
            action(name, Consumer<ActionBuilder> { ActionBuilderKt(it).spec() })
}


class EmbeddedEntityBuilderKt (builder: EmbeddedEntityBuilder)
    : FullEntityBuilderKt<EmbeddedEntity, EmbeddedEntityBuilder>(builder)


class RootEntityBuilderKt : FullEntityBuilderKt<RootEntity, RootEntityBuilder>(RootEntity.builder()){
    internal fun build() = builder.build()
}


fun rootEntity(spec: RootEntityBuilderKt.() -> Unit) =
        RootEntityBuilderKt().run {
            spec()
            build()
        }
