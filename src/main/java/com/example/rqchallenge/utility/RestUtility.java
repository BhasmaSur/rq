package com.example.rqchallenge.utility;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RestUtility {

    @Autowired
    RestTemplate restTemplate;

}
