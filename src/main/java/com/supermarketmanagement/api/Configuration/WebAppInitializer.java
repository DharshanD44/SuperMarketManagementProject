package com.supermarketmanagement.api.Configuration;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    // Root application context (Service, Repository, JPA config)
    @Override
    protected Class<?>[] getRootConfigClasses() {
           	return null;
    }

    // Servlet application context (Controllers, ViewResolvers)
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[] { SpringConfiguration.class };
        
    }

    // DispatcherServlet mapping
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }
}