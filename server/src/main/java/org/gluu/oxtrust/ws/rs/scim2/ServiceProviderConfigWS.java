/*
 * oxTrust is available under the MIT License (2008). See http://opensource.org/licenses/MIT for full text.
 *
 * Copyright (c) 2014, Gluu
 */

package org.gluu.oxtrust.ws.rs.scim2;

import java.net.URI;
import java.util.ArrayList;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

import org.gluu.oxtrust.model.scim2.Constants;
import org.gluu.oxtrust.model.scim2.Meta;
import org.gluu.oxtrust.model.scim2.provider.AuthenticationScheme;
import org.gluu.oxtrust.model.scim2.provider.ServiceProviderConfig;
import org.jboss.seam.annotations.Logger;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.log.Log;

/**
 * @author Rahat Ali Date: 05.08.2015
 */
@Name("serviceProviderConfig")
@Path("/scim/v2/ServiceProviderConfig")
public class ServiceProviderConfigWS extends BaseScimWebService {

	@Logger
	private Log log;

	@GET
	@Produces(Constants.MEDIA_TYPE_SCIM_JSON)
	@HeaderParam("Accept") @DefaultValue(Constants.MEDIA_TYPE_SCIM_JSON)
	public Response listGroups(@HeaderParam("Authorization") String authorization) throws Exception {

		ServiceProviderConfig serviceProviderConfig = new ServiceProviderConfig();

		Meta meta = new Meta();
		meta.setLocation(super.applicationConfiguration.getBaseEndpoint() + "/scim/v2/ServiceProviderConfig");
		meta.setResourceType("ServiceProviderConfig");
		serviceProviderConfig.setMeta(meta);

		ArrayList<AuthenticationScheme> authenticationSchemes = new ArrayList<AuthenticationScheme>();
		if (applicationConfiguration.isScimTestMode()) {
			log.info(" ##### SCIM Test Mode is ACTIVE");
			authenticationSchemes.add(AuthenticationScheme.createOAuth2(true));
		} else {
			authenticationSchemes.add(AuthenticationScheme.createUma(true));
		}
		serviceProviderConfig.setAuthenticationSchemes(authenticationSchemes);

		URI location = new URI(super.applicationConfiguration.getBaseEndpoint() + "/scim/v2/ServiceProviderConfig");

		return Response.ok(serviceProviderConfig).location(location).build();
	}
}
