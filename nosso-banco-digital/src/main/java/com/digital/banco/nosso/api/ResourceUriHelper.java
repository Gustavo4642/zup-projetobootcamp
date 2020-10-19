package com.digital.banco.nosso.api;

import java.net.URI;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ResourceUriHelper {

	public static void addUriInResponseHeader(Object resource) {
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
				.path("/{resource}")
				.buildAndExpand(resource).toUri();
			
			HttpServletResponse response = ((ServletRequestAttributes) 
					RequestContextHolder.getRequestAttributes()).getResponse();
			
			response.setHeader(HttpHeaders.LOCATION, uri.toString());
	}
}
