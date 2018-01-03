package com.hulu.ap.apiframework.api;

import io.swagger.annotations.*;

/**
 * An empty class for generating top-level Swagger Restful service properties
 */
@SwaggerDefinition(
        info = @Info(
                description = "API Specification",
                version = "N/A",
                title = "API",
                contact = @Contact(
                        name = "hulu"
                        //email = "@hulu.com"
                )
        ),
        schemes = {SwaggerDefinition.Scheme.HTTP}
)
//TODO Not in use currently
public class SwaggerRSConfig {
}
