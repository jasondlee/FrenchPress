/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.frenchpress.rest.providers;

import com.google.gson.Gson;
import com.steeplesoft.frenchpress.model.FpEntity;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

/**
 *
 * @author jdlee
 */
@Provider
@Produces(MediaType.APPLICATION_JSON)
public class FpEntityCollectionReaderWriter implements MessageBodyWriter<List<FpEntity>> {

    private final Gson gson = new Gson();

    @Override
    public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        System.out.println("***** type = " + type.getName());
        System.out.println("***** genericType = " + genericType.toString());
        boolean isWritable;
        if (List.class.isAssignableFrom(type)
                && genericType instanceof ParameterizedType) {
            ParameterizedType parameterizedType = (ParameterizedType) genericType;
            Type[] actualTypeArgs = (parameterizedType.getActualTypeArguments());
            isWritable = (actualTypeArgs.length == 1 && FpEntity.class.isAssignableFrom((Class)actualTypeArgs[0]));
        } else {
            isWritable = false;
        }

        return isWritable;
//        if (genericType instanceof ParameterizedType) {
//            ParameterizedType pt = (ParameterizedType)genericType;
//            for (Type actual : pt.getActualTypeArguments()) {
//                System.out.println("***** actual = " + actual.toString());
//                if (FpEntity.class.equals(actual)) {
//                    return true;
//                }
//            }
//        }
//
//        return false;
    }

    @Override
    public long getSize(List<FpEntity> t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void writeTo(List<FpEntity> t, Class<?> type, Type genericType, Annotation[] annotations, MediaType mediaType, MultivaluedMap<String, Object> httpHeaders, OutputStream entityStream) throws IOException, WebApplicationException {
//        String list = gson.toJson(t);
        StringBuilder sb = new StringBuilder("[");
        String sep = "";
        for (FpEntity entity : (List<FpEntity>)t) {
            sb.append(sep).append(gson.toJson(entity));
            sep=",";
        }

        entityStream.write(sb.append("]").toString().getBytes());
    }

}
