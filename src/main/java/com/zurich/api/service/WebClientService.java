package com.zurich.api.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.zurich.api.exception.FileException;

import reactor.core.publisher.Mono;

@Service
public class WebClientService {

	@Autowired
	WebClient webClient;


	public WebClientService() {
	}

	public Mono<String> getStorage(String path) {
		return this.webClient
				.get()
				.uri(path)
				.retrieve()
				.bodyToMono(String.class);
	}

	public Mono<String> getFile(String path) {
		return this.webClient
				.get()
				.uri(path)
				.retrieve()
				.bodyToMono(String.class);
	}

	public Mono<HttpStatus> setFile(String path, String fileName) {
		
		
		try {
			File file = new File(fileName);
			FileInputStream input = new FileInputStream("upload/" + fileName);
			MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "plain/text", IOUtils.toByteArray(input));		

			
			MultipartBodyBuilder builder = new MultipartBodyBuilder();
			builder.part("file", multipartFile.getResource());

			return webClient.post()
				    .uri(path)
				    .contentType(MediaType.MULTIPART_FORM_DATA)
				    .body(BodyInserters.fromMultipartData(builder.build()))
				    .exchangeToMono(response -> {
				        if (response.statusCode().equals(HttpStatus.OK)) {
				            return response.bodyToMono(HttpStatus.class).thenReturn(response.statusCode());
				        } else {
				            throw new FileException("Error uploading file");
				        }
				      });

		} catch (FileNotFoundException e) {
			throw new FileException(e.getMessage());
		} catch (IOException e) {
			throw new FileException(e.getMessage());
		}
		
		
	}

}
