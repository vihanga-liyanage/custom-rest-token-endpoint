package org.wso2.custom.rest.endpoint.token;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.wso2.carbon.identity.application.authentication.framework.model.AuthenticatedUser;
import org.wso2.carbon.identity.oauth2.IdentityOAuth2Exception;
import org.wso2.carbon.identity.oauth2.dao.OAuthTokenPersistenceFactory;
import org.wso2.custom.rest.endpoint.token.util.Constants;

import java.util.Set;
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
    @Path("/user/{username}")
    @Produces({MediaType.APPLICATION_JSON, Constants.APPLICATION_JSON})
    public Response getTokensOfUser(@PathParam(Constants.PATH_PARAM_USERNAME) String username,
                            @HeaderParam(Constants.AUTHORIZATION) String authorizationHeader,
                            @HeaderParam(Constants.ACCEPT_HEADER) String outputFormat,
                            @QueryParam(Constants.ATTRIBUTES) String attribute,
                            @QueryParam(Constants.EXCLUDE_ATTRIBUTES) String  excludedAttributes) {

        log.info("/user/{username} path hit with username: " + username);

        AuthenticatedUser authenticatedUser = new AuthenticatedUser();
        authenticatedUser.setUserStoreDomain(Constants.USER_STORE_DOMAIN);
        authenticatedUser.setTenantDomain(Constants.TENANT_DOMAIN);
        authenticatedUser.setUserName(username);

        Set<String> tokens = null;

        try {
            tokens = OAuthTokenPersistenceFactory.getInstance().getAccessTokenDAO()
                    .getAccessTokensByUser(authenticatedUser);

        } catch (IdentityOAuth2Exception e) {
            String errorMsg = "Error occurred while retrieving access tokens issued for user : " + username;
            log.error(errorMsg, e);
        }

        Response.ResponseBuilder respBuilder = Response.status(200);
        if (tokens != null) {
            return respBuilder.entity(getTokenResponse(tokens)).build();
        } else {
            return respBuilder.entity("No tokens found for the user: " + username).build();
        }
    }

    @GET
    @Path("/client/{clientId}")
    @Produces({MediaType.APPLICATION_JSON, Constants.APPLICATION_JSON})
    public Response getTokensOfClient(@PathParam(Constants.PATH_PARAM_CLIENT_ID) String clientId,
                            @HeaderParam(Constants.AUTHORIZATION) String authorizationHeader,
                            @HeaderParam(Constants.ACCEPT_HEADER) String outputFormat,
                            @QueryParam(Constants.ATTRIBUTES) String attribute,
                            @QueryParam(Constants.EXCLUDE_ATTRIBUTES) String  excludedAttributes) {

        log.info("/client/{clientId} path hit with clientId: " + clientId);

        Set<String> tokens = null;

        try {
            tokens = OAuthTokenPersistenceFactory.getInstance().getAccessTokenDAO()
                    .getActiveTokensByConsumerKey(clientId);

        } catch (IdentityOAuth2Exception e) {
            String errorMsg = "Error occurred while retrieving access tokens issued for the client : " + clientId;
            log.error(errorMsg, e);
        }

        Response.ResponseBuilder respBuilder = Response.status(200);
        if (tokens != null) {
            return respBuilder.entity(getTokenResponse(tokens)).build();
        } else {
            return respBuilder.entity("No tokens found for the client: " + clientId).build();
        }
    }

    private String getTokenResponse(Set<String> tokens) {

        if (tokens != null && !tokens.isEmpty()) {
            StringBuilder out = new StringBuilder();
            out.append("{\"tokens\":[");
            String suffix = "";
            log.info("Tokens found: ");
            for (String token : tokens) {
                log.info("    " + token);
                out.append(suffix).append("\"").append(token).append("\"");
                suffix = ",";
            }
            out.append("]}");
            return out.toString();
        }
        return "";
    }
}