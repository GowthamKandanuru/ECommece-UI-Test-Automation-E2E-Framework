package com.api.filters;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.Message;

public class LoggingFilter implements Filter {

    private static final Logger LOGGER = LogManager.getLogger(LoggingFilter.class);
    @Override
    public Response filter(FilterableRequestSpecification filterableRequestSpecification, FilterableResponseSpecification filterableResponseSpecification, FilterContext filterContext) {
         logRequest(filterableRequestSpecification);
         Response response = filterContext.next(filterableRequestSpecification,filterableResponseSpecification);
        return response;
    }

    public void logRequest(FilterableRequestSpecification filterableRequestSpecification)
    {
        LOGGER.info(filterableRequestSpecification.getBaseUri());
        LOGGER.info(filterableRequestSpecification.getHeaders());
        LOGGER.info(filterableRequestSpecification.getBody().toString());
    }

}
