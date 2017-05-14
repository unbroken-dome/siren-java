package org.unbrokendome.siren.spring

import org.springframework.web.util.UriComponentsBuilder


val currentRequestUri: UriComponentsBuilder get() = RequestUtils.createUriBuilderFromCurrentRequest()
