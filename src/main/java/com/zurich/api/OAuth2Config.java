//package com.zurich.api;
//
//import java.util.HashMap;
//import java.util.Map;
//import java.util.function.Function;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Profile;
//import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
//import org.springframework.security.oauth2.client.OAuth2AuthorizationContext;
//import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
//import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
//import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientProvider;
//import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientProviderBuilder;
//import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientService;
//import org.springframework.security.oauth2.client.registration.ClientRegistration;
//import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
//import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
//import org.springframework.security.oauth2.core.AuthorizationGrantType;
//import reactor.core.publisher.Mono;
//
//@Configuration
////@Profile("dev")
//public class OAuth2Config {
//	
////	@Value("{useKeycloak}")
////	private Boolean useKeycloak;
//
//    @Bean
//    ReactiveClientRegistrationRepository getRegistration(
//        @Value("${spring.security.oauth2.client.provider.keycloak.token-uri}")
//        final String tokenUri,
//        @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
//        final String clientId,
//        @Value("${spring.security.oauth2.client.registration.keycloak.client-secret}")
//        final String clientSecret,
//        @Value("${spring.security.oauth2.client.registration.keycloak.scope}")
//        final String scope
//    ) {
//        final ClientRegistration registration = ClientRegistration
//                .withRegistrationId("keycloak")
//                .tokenUri(tokenUri)
//                .clientId(clientId)
//                .clientSecret(clientSecret)
//                .authorizationGrantType(AuthorizationGrantType.PASSWORD)
//                .scope(scope)
//                .build();
//        return new InMemoryReactiveClientRegistrationRepository(registration);
//    }
//
//    @Bean
//    public ReactiveOAuth2AuthorizedClientManager authorizedClientManager(
//        final ReactiveClientRegistrationRepository clientRegistrationRepository,
//        final ReactiveOAuth2AuthorizedClientService authorizedClientService,
//        @Value("${spring.security.oauth2.client.registration.keycloak.username}") final String username,
//        @Value("${spring.security.oauth2.client.registration.keycloak.password}") final String password) {
//
//        final ReactiveOAuth2AuthorizedClientProvider authorizedClientProvider =
//                ReactiveOAuth2AuthorizedClientProviderBuilder.builder()
//                	.password()
//                	.refreshToken()
//                	.build();
//
//        final AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager authorizedClientManager =
//                new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(
//                        clientRegistrationRepository, authorizedClientService);
//
//        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);
//        authorizedClientManager.setContextAttributesMapper(contextAttributesMapper(username, password));
//
//        return authorizedClientManager;
//    }
//
//    private Function<OAuth2AuthorizeRequest, Mono<Map<String, Object>>> contextAttributesMapper(final String username, final String password) {
//        return authorizeRequest -> {
//            final Map<String, Object> contextAttributes = new HashMap<>();
//            contextAttributes.put(OAuth2AuthorizationContext.USERNAME_ATTRIBUTE_NAME, username);
//            contextAttributes.put(OAuth2AuthorizationContext.PASSWORD_ATTRIBUTE_NAME, password);
//            return Mono.just(contextAttributes);
//        };
//    }
//
//}