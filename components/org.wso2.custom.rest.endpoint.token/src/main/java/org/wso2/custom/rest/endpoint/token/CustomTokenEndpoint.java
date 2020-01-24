package org.wso2.custom.rest.endpoint.token;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.custom.rest.endpoint.token.util.Constants;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class CustomTokenEndpoint {

    private static final Log log = LogFactory.getLog(CustomTokenEndpoint.class);

    @GET
    @Path("{id}")
    @Produces({MediaType.APPLICATION_JSON, Constants.APPLICATION_JSON})
    public Response getUser(@PathParam(Constants.USERNAME) String username,
                            @HeaderParam(Constants.AUTHORIZATION) String authorizationHeader,
                            @HeaderParam(Constants.ACCEPT_HEADER) String outputFormat,
                            @QueryParam(Constants.ATTRIBUTES) String attribute,
                            @QueryParam(Constants.EXCLUDE_ATTRIBUTES) String  excludedAttributes) {

        log.info("/user/{username} path hit");
        log.info("Username: " + username);
        Response.ResponseBuilder respBuilder = Response
                .status(200);
        return respBuilder.entity("xxx").build();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON, Constants.APPLICATION_JSON})
    public Response getUser(@HeaderParam(Constants.AUTHORIZATION) String authorizationHeader,
                            @HeaderParam(Constants.ACCEPT_HEADER) String format,
                            @QueryParam (Constants.ATTRIBUTES) String attribute,
                            @QueryParam (Constants.EXCLUDE_ATTRIBUTES) String excludedAttributes,
                            @QueryParam (Constants.FILTER) String filter,
                            @QueryParam (Constants.START_INDEX) Integer startIndex,
                            @QueryParam (Constants.COUNT) Integer count,
                            @QueryParam (Constants.SORT_BY) String sortBy,
                            @QueryParam (Constants.SORT_ORDER) String sortOrder,
                            @QueryParam (Constants.DOMAIN) String domainName) {

        log.info("/user path hit");
        Response.ResponseBuilder respBuilder = Response
                .status(200);
        return respBuilder.entity("xxx").build();
    }
}