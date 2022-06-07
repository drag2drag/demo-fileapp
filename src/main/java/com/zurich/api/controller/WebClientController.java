package com.zurich.api.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zurich.api.service.WebClientService;

import reactor.core.publisher.Mono;


@RestController
public class WebClientController {
	
    private final Logger logger= LoggerFactory.getLogger(WebClientController.class);
    

    @Autowired
	private WebClientService webClientService;


    @GetMapping("/storage")
    public Mono<String> getStorage() {

    	logger.info("Retrieving storage");
    	return webClientService.getStorage("/scenario-3/files/storage");
    }

    @GetMapping("/download/{fileName:.+}")
    public Mono<String> getFile(@PathVariable String fileName) {

    	logger.info("Download " + fileName);
    	return webClientService.getFile("/scenario-3/files/" + fileName);
    }

    @PostMapping("/upload/{fileName:.+}")
	public Mono<HttpStatus> setFile(@PathVariable String fileName) {

    	logger.info("Upload " + fileName);
	    return webClientService.setFile("/scenario-2/files/" + fileName, fileName);
	}


}
