package com.ecagataydogan.kredinbizdeservice.client;

import com.ecagataydogan.kredinbizdeservice.client.dto.request.AkbankApplicationRequest;
import com.ecagataydogan.kredinbizdeservice.dto.response.ApplicationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "akbank-service", url = "http://localhost:8081")
public interface AkbankServiceClient {

    @PostMapping("api/v1/akbank/applications")
    ApplicationResponse createApplication(@RequestBody AkbankApplicationRequest request);

}
