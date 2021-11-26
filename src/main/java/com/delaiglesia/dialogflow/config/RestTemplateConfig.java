package com.delaiglesia.dialogflow.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Configuration
public class RestTemplateConfig {
		
	@Value("${serviciosexternos.url.smc}")
	private String ENDPOINT_SMC;

    @Bean
    public RestTemplate restTemplate() {
    	RestTemplate restTemplate = new RestTemplate();
    	OAuthTokenInterceptor oAuthTokenInterceptor =
    			new OAuthTokenInterceptor(ENDPOINT_SMC, "mr", "ec");
    	//agregamos un interceptor que se encarga de agregar
    	//la autenticacion contra el servicio que se llama
    	List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		interceptors.add(oAuthTokenInterceptor);
    	restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }
}
