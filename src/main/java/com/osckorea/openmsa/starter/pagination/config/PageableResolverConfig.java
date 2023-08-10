package com.osckorea.openmsa.starter.pagination.config;

import java.util.List;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Customized Pageable Resolver를 설정합니다.
 */
@Configuration
public class PageableResolverConfig implements WebMvcConfigurer{
    // Customized Pageable Resolver DI
    private final PageableVerificationArgumentResolver pageableVerificationArgumentResolver;

    public PageableResolverConfig(
        PageableVerificationArgumentResolver pageableVerificationArgumentResolver
    ) {
        this.pageableVerificationArgumentResolver = pageableVerificationArgumentResolver;
    }
    //

    /**
     * Customized Pageable Resolver Bean을 추가합니다.
     * 
     * @param List<HandlerMethodArgumentResolver> resolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(pageableVerificationArgumentResolver);
	}
}
