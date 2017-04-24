package org.unbrokendome.siren.kotlin

import org.junit.Assert.assertEquals
import org.junit.Test
import org.unbrokendome.siren.model.ActionField
import org.unbrokendome.siren.model.RootEntity
import org.unbrokendome.siren.model.RootEntityBuilder
import java.util.function.Consumer


class SirenBuilderTest {

    @Test
    fun emptyRootEntity() {
        val entity = rootEntity {  }

        assertEquals(RootEntity.builder().build(), entity)
    }


    @Test
    fun rootEntityWithTitle() {
        val entity = rootEntity {
            title = "entity title"
        }

        assertEquals(
                RootEntity.builder()
                        .setTitle("entity title")
                        .build(),
                entity)
    }


    @Test
    fun rootEntityWithClassName() {
        val entity = rootEntity {
            className = "foo"
        }

        assertEquals(
                RootEntity.builder()
                        .setClassName("foo")
                        .build(),
                entity)
    }


    @Test
    fun rootEntityWithMultipleClassNames() {
        val entity = rootEntity {
            classNames = listOf("foo", "bar")
        }

        assertEquals(
                RootEntity.builder()
                        .setClassNames("foo", "bar")
                        .build(),
                entity)
    }


    @Test
    fun rootEntityWithExternalConsumer() {
        val consumer = Consumer<RootEntityBuilder> { it.title = "entity title" }

        val entity = rootEntity {
            with(consumer)
        }

        assertEquals(
                RootEntity.builder()
                        .with(consumer)
                        .build(),
                entity)
    }


    @Test
    fun rootEntityWithLink() {
        val entity = rootEntity {
            link("self") {
                href = "http://api.example.com"
            }
        }

        assertEquals(
                RootEntity.builder()
                        .addLink("self") { link ->
                            link.href = "http://api.example.com"
                        }
                        .build(),
                entity)
    }


    @Test
    fun rootEntityWithAction() {
        val entity = rootEntity {
            action("edit") {
                href = "http://api.example.com/edit"
                method = "POST"
            }
        }

        assertEquals(
                RootEntity.builder()
                        .addAction("edit") { action ->
                            action.href = "http://api.example.com/edit"
                            action.method = "POST"
                        }
                        .build(),
                entity)
    }


    @Test
    fun rootEntityWithActionAndActionField() {
        val entity = rootEntity {
            action("login") {
                href = "http://api.example.com/login"
                method = "POST"
                field("email") {
                    type = ActionField.Type.EMAIL
                    value = "someone@example.com"
                }
                field("password") {
                    type = ActionField.Type.PASSWORD
                }
            }
        }

        val expected = RootEntity.builder()
                .addAction("login") { action ->
                    action.href = "http://api.example.com/login"
                    action.method = "POST"
                    action.addField("email") { field ->
                        field.type = ActionField.Type.EMAIL
                        field.value = "someone@example.com"
                    }
                    action.addField("password") { field ->
                        field.type = ActionField.Type.PASSWORD
                    }
                }
                .build()

        assertEquals(expected, entity)
    }


    @Test
    fun rootEntityWithEmbeddedEntity() {
        val entity = rootEntity {
            embeddedEntity("item") {
                title = "item 1"
            }
        }

        assertEquals(RootEntity.builder()
                .addEmbeddedEntity("item") { entity ->
                    entity.title = "item 1"
                }
                .build(),
                entity)
    }


    @Test
    fun rootEntityWithEmbeddedLink() {
        val entity = rootEntity {
            embeddedLink("item") {
                href = "http://api.example.com/item1"
                title = "item 1"
            }
        }

        assertEquals(RootEntity.builder()
                .addEmbeddedLink("item") { link ->
                    link.href = "http://api.example.com/item1"
                    link.title = "item 1"
                }
                .build(),
                entity)
    }
}
