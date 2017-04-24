package org.unbrokendome.siren.ap

import spock.lang.Specification

import java.util.stream.Collectors


class ResourceUtilsTest extends Specification {

    def "collect lines from resources"() {
        when:
            def lines = ResourceUtils.collectLinesFromResources('org/unbrokendome/siren/ap/ResourceUtilsTest.txt')
                .collect(Collectors.toList())
        then:
            lines == [ 'line1', 'line2' ]
    }
}
