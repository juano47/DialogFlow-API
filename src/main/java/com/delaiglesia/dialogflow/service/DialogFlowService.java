package com.delaiglesia.dialogflow.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class DialogFlowService {

	@Value("${serviciosexternos.url.smc}")
	private String ENDPOINT_SMC;

	@Autowired
	RestTemplate restTemplate;

	public void createFolder(Map<String, Object> params) {

		String body = "" +
				"{" +
				"  \"name\":\""+ params.get("folderName") + "\"," +
				"  \"parentId\":\"" + params.get("parentId") + "\"," +
				"  \"serverID\": \"JACKRABBIT\"" +
				"}";
		String url = ENDPOINT_SMC + "/api/folder";
		restTemplate.postForObject(url, body, Object.class);
	}
}
