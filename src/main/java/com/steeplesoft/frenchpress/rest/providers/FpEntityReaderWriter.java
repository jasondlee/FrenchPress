/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.frenchpress.rest.providers;

import com.google.gson.Gson;
import com.steeplesoft.frenchpress.model.FpEntity;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author jdlee
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class FpEntityReaderWriter implements MessageBodyWriter<FpEntity>,
        MessageBodyReader<FpEntity> {

    private final Gson gson = new Gson();

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return FpEntity.class.isAssignableFrom((Class)genericType) && mediaType.isCompatible(MediaType.APPLICATION_JSON_TYPE);
    }

    @Override
    public long getSize(FpEntity t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return -1;
    }

    @Override
    public void writeTo(FpEntity t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
        entityStream.write(gson.toJson(t).getBytes());
    }

    // Reader Methods
    @Override
    public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        return FpEntity.class.isAssignableFrom((Class)genericType) && mediaType.isCompatible(MediaType.APPLICATION_JSON_TYPE);
    }

    @Override
    public FpEntity readFrom(Class<FpEntity> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, String> httpHeaders, InputStream entityStream) throws IOException, WebApplicationException {
        final String entity = readString(entityStream);
        return (FpEntity)gson.fromJson(entity, type);
    }

    private String readString(InputStream entityStream) {
        StringBuilder sb = new StringBuilder();
        try {

            BufferedReader in = new BufferedReader(new InputStreamReader(entityStream));
            String line = in.readLine();
            while (line != null) {
                sb.append(line);
                line = in.readLine();
            }

        } catch (IOException ex) {
            Logger.getLogger(FpEntityReaderWriter.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sb.toString();
    }
}
