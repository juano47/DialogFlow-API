package com.delaiglesia.dialogflow.Controller;

import com.delaiglesia.dialogflow.service.DialogFlowService;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.dialogflow.v2beta1.model.GoogleCloudDialogflowV2WebhookRequest;
import com.google.api.services.dialogflow.v2beta1.model.GoogleCloudDialogflowV2WebhookResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class DialogFlowController {

	@Autowired
	DialogFlowService dialogFlowService;

	private static JacksonFactory jacksonFactory = JacksonFactory.getDefaultInstance();

	@RequestMapping(method = RequestMethod.POST, value = "/dialogFlowWebHook")
	public ResponseEntity<?> dialogFlowWebHook(@RequestBody String requestStr, HttpServletRequest servletRequest) throws IOException {

		try {

			GoogleCloudDialogflowV2WebhookResponse response = new GoogleCloudDialogflowV2WebhookResponse();
			GoogleCloudDialogflowV2WebhookRequest request = jacksonFactory.createJsonParser(requestStr).parse(GoogleCloudDialogflowV2WebhookRequest.class);

			Map<String,Object> params = request.getQueryResult().getParameters();
			String displayName = request.getQueryResult().getIntent().getDisplayName();
			if (params.size() > 0) {
				String result = "";
				switch (displayName){
					case "CreateFolder":
						result = dialogFlowService.createFolder(params);
						break;
				}
			}
			else {
				response.setFulfillmentText("Sorry you didn't send enough to process");
			}


			response.setFulfillmentText("todo oK!");
			return new ResponseEntity<GoogleCloudDialogflowV2WebhookResponse>(response, HttpStatus.OK);
		}
		catch (Exception ex) {
			return new ResponseEntity<Object>(ex.getMessage(),HttpStatus.BAD_REQUEST);
		}
	}
}
