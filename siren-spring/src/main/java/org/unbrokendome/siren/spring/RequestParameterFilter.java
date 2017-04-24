package org.unbrokendome.siren.spring;

import org.springframework.web.util.UriComponentsBuilder;


public interface RequestParameterFilter {

    boolean isFulfilled(UriComponentsBuilder uriComponentsBuilder);

    void fulfill(UriComponentsBuilder uriComponentsBuilder);
}
