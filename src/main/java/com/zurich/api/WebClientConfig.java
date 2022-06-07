package com.zurich.api;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.netty.http.client.HttpClient;

@Configuration
@EnableWebFlux
public class WebClientConfig {

    private final Logger logger= LoggerFactory.getLogger(WebClientConfig.class);
    
    
    @Value("${fileservice.endpoint}")
	private String fileserviceEndpoint;
    
//	@Value("{useKeycloak}")
//	private Boolean useKeycloak;


    @Bean
    public WebClient webClient() {
    	
		HttpClient httpClient = HttpClient.create()
				  .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
				  .responseTimeout(Duration.ofMillis(5000))
				  .doOnConnected(conn -> 
				    conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
				      .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));

    	logger.info("Calling service: " + fileserviceEndpoint);
    	
    	return WebClient.builder()
    		.baseUrl(fileserviceEndpoint)
    		.clientConnector(new ReactorClientHttpConnector(httpClient))
   			.build();
    }

//    @Bean(name = "keycloak")
//    public WebClient webClient(final ReactiveOAuth2AuthorizedClientManager authorizedClientManager) {
//
//        final HttpClient httpClient  = HttpClient.create()
//            	.option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 30000)
//            	.doOnConnected(connection -> {
//            		connection.addHandler(new ReadTimeoutHandler(10))
//            			.addHandlerLast(new WriteTimeoutHandler(10));
//            	});
//
//    	
//    	final ServerOAuth2AuthorizedClientExchangeFilterFunction oauth =
//                new ServerOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
//    	oauth.setDefaultClientRegistrationId("zurich");
//    	
//        logger.info("Call service url: " + fileserviceEndpoint);
//        return WebClient.builder()
//        		.baseUrl(fileserviceEndpoint)
//                .filter(oauth)
//                .clientConnector(new ReactorClientHttpConnector(httpClient))
//                .build();
//    }
//    
 
//    @Bean
//    public ReactiveOAuth2AuthorizedClientManager authorizedClientManager(
//            ReactiveClientRegistrationRepository clientRegistrationRepository,
//            ServerOAuth2AuthorizedClientRepository authorizedClientRepository) {
// 
//        ReactiveOAuth2AuthorizedClientProvider authorizedClientProvider =
//                ReactiveOAuth2AuthorizedClientProviderBuilder.builder()
//                        .authorizationCode()
//                        .build();
// 
//        DefaultReactiveOAuth2AuthorizedClientManager authorizedClientManager =
//                new DefaultReactiveOAuth2AuthorizedClientManager(
//                        clientRegistrationRepository, authorizedClientRepository);
// 
//        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
// 
//        return authorizedClientManager;
//    }
    
//    @Bean
//    ReactiveClientRegistrationRepository getRegistration(
//            @Value("${spring.security.oauth2.client.provider.zurich.token-uri}") String tokenUri,
//            @Value("${spring.security.oauth2.client.registration.zurich.client-id}") String clientId,
//            @Value("${spring.security.oauth2.client.registration.zurich.client-secret}") String clientSecret
//    ) {
//        ClientRegistration registration = ClientRegistration
//                .withRegistrationId("zurich")
//                .tokenUri(tokenUri)
//                .clientId(clientId)
//                .clientSecret(clientSecret)
//                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
//                .build();
//        return new InMemoryReactiveClientRegistrationRepository(registration);
//    }
//
//    @Bean("zurich")
//    public WebClient webClient(ReactiveClientRegistrationRepository clientRegistrations) {
//        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth = new ServerOAuth2AuthorizedClientExchangeFilterFunction(
//                clientRegistrations, new UnAuthenticatedServerOAuth2AuthorizedClientRepository());
//        oauth.setDefaultClientRegistrationId("zurich");
//        return WebClient.builder()
//                .filter(oauth)
//                .build();
//    }
    
//    /**
//     * Create a instance of the OAuth2AuthorizedClientManager
//     * @param clientRegistrationRepository
//     * @param authorizedClientService
//     * @return
//     */
//    @Bean
//    public OAuth2AuthorizedClientManager authorizedClientManager(ClientRegistrationRepository clientRegistrationRepository
//    															,OAuth2AuthorizedClientService authorizedClientService) {
//    
//        OAuth2AuthorizedClientProvider authorizedClientProvider = OAuth2AuthorizedClientProviderBuilder.builder()
//                                                                  .clientCredentials()
//                                                                  .build();
//
//        AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientManager = new AuthorizedClientServiceOAuth2AuthorizedClientManager(
//                clientRegistrationRepository, authorizedClientService);
//        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
//
//        return authorizedClientManager;
//    }
//    
//    /**
//     * Create a Webclient wired with the OAuth2AuthorizedClientManager to generate the Authorization token
//     * @param authorizedClientManager
//     * @return
//     */
//    @Bean
//    WebClient webClient(OAuth2AuthorizedClientManager authorizedClientManager) {
//        
//        ServletOAuth2AuthorizedClientExchangeFilterFunction oauth2Client = new ServletOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
//        			
//        oauth2Client.setDefaultClientRegistrationId("zurich");
//        return WebClient.builder()
//                .apply(oauth2Client.oauth2Configuration())
//                .build();
//    }
    
//    /**
//     * Log request for debugging purposes
//     * @return
//     */
//    private static ExchangeFilterFunction logRequest() {
//        return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
//            log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
//            clientRequest.headers().forEach((name, values) -> values.forEach(value -> log.info("{}={}", name, value)));
//            
//            return Mono.just(clientRequest);
//        });
//    }

}
