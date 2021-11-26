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

	public String createFolder(Map<String, Object> params) {

		String body = "" +
				"{" +
				"  'name': 'TEST/FOLDER/4/'," +
				"  'parentId': '1630682963228'," +
				"  'serverID': 'JACKRABBIT'" +
				"}";
		String url = ENDPOINT_SMC + "/api/folder";
		Object result = restTemplate.postForObject(url, body, Object.class);
		return "";
	}
}
