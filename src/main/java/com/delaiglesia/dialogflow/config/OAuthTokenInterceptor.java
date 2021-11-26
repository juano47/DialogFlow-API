package com.delaiglesia.dialogflow.config;

import lombok.SneakyThrows;
import org.apache.xerces.parsers.DOMParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.xml.sax.SAXException;

import java.io.IOException;

//esta clase intercepta las peticiones enviadas usando un RestTemplate agregandole 
//el token de autenticacion
public class OAuthTokenInterceptor implements ClientHttpRequestInterceptor {
	
	private static Logger log = LoggerFactory.getLogger(OAuthTokenInterceptor.class);
	private final RestTemplate simpleRestTemplate = new RestTemplate();

	private String url;
	private String username;
	private String password;


	public OAuthTokenInterceptor(String url, String username, String password) {
		this.url = url;
		this.username = username;
		this.password = password;
	}

	@SneakyThrows
	@Override
	public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
			throws IOException {
		String xsrfCookie = getXsrfCookie(username, password);
		request.getHeaders().setBasicAuth(username, password);
		request.getHeaders().set("X-XSRF-TOKEN", "34d7404b-9477-4949-a96b-65e9fe3678d3");
		request.getHeaders().set("Cookie", "JSESSIONID=69D27732BDF0154054EC8E5CD7262E58; XSRF-TOKEN=34d7404b-9477-4949-a96b-65e9fe3678d3");
		request.getHeaders().set("Content-Type", "application/json");
		ClientHttpResponse response = execution.execute(request, body);
	    return response;
	}

	private String getXsrfCookie(String username, String password) throws IOException, SAXException {

		HttpHeaders headers = new HttpHeaders();
		headers.setBasicAuth(username, password);

		HttpEntity<String> request = new HttpEntity<>(null, headers);

		String response = simpleRestTemplate.getForObject(url, String.class, request);
		Document doc = Jsoup.parse(response);
		Elements metas = doc.getElementsByTag("meta");
		Element meta = metas.get(0);
		return meta.attr("content");
	}
}