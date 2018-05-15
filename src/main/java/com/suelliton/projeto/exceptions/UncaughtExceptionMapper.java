/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.suelliton.projeto.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import com.suelliton.projeto.model.OutputMessage;

/**
 *
 * @author Taniro
 */
@Provider
public class UncaughtExceptionMapper implements ExceptionMapper<Throwable>{

    @Override
    public Response toResponse(Throwable exception) {
        return Response
                .status(getStatusType(exception))
                .entity(new OutputMessage(getStatusType(exception).getStatusCode(), "generic:" + exception.getMessage()))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }
    
    private Response.StatusType getStatusType(Throwable ex) {
        if (ex instanceof WebApplicationException) {
            return Response.Status.INTERNAL_SERVER_ERROR;
            //return((WebApplicationException)ex).getResponse().getStatusInfo();
        } else {
            return Response.Status.INTERNAL_SERVER_ERROR;
        }
    }
    
}
