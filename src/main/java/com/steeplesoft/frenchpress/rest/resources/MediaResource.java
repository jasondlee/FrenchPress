/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.frenchpress.rest.resources;

import com.steeplesoft.frenchpress.model.MediaItem;
import com.steeplesoft.frenchpress.service.MediaService;
import java.net.URI;
import java.text.SimpleDateFormat;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.persistence.NoResultException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author jdlee
 */
@RequestScoped
@Path("/uploads")
@Produces(MediaType.APPLICATION_JSON)
public class MediaResource {
    @Inject
    private MediaService service;

    @Context
    private UriInfo uriInfo;

    @GET
    public Response getItems() {
        JsonArrayBuilder factory = Json.createArrayBuilder();
        for (MediaItem item : service.getItems()) {
            factory = factory.add(item.toJson()
                .add("_ref", uriInfo.getAbsolutePathBuilder().path("id").path(""+item.getId()).build().toString())
                .build());
        }
        return Response.ok(factory.build()).build();
    }

    @GET
    @Path("/id/{id}/")
    public Response getItem(@PathParam("id") Long id) {
        final MediaItem item = service.getItem(id);
        final JsonObject json = item.toJson()
                .add("_ref", uriInfo.getAbsolutePathBuilder().build().toString())
                .build();
        return Response.ok(json).build();
    }

    @GET
    @Path("{year}/{month}/{name}")
    public Response getItem(
            @PathParam("year") int year,
            @PathParam("month") int month,
            @PathParam("name") String name) {
        try {
            final MediaItem item = service.getItem(year, month, name);
            return Response.ok(item.getContents(), item.getMimeType()).build();
        } catch (NoResultException nre) {
            throw new WebApplicationException(Status.NOT_FOUND);
        }
    }
}
