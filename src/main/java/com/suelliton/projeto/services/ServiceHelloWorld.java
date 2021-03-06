/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.suelliton.projeto.services;

import com.suelliton.projeto.model.Mensagem;
import java.util.concurrent.atomic.AtomicLong;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author suelliton
 */
@Path("/hello")
public class ServiceHelloWorld {

    private final AtomicLong counter = new AtomicLong();

  
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMensagem(
            @QueryParam("nome") @DefaultValue("World!") String nome) {
        Mensagem m = new Mensagem(counter.incrementAndGet(), "Hello, " + nome);
        return Response.status(Response.Status.CREATED).entity(m).build();
    }

}
