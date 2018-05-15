/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.suelliton.projeto.services;

import com.suelliton.projeto.implementations.CredencialImpl;
import com.suelliton.projeto.implementations.UsuarioImpl;
import com.suelliton.projeto.interfaces.ICredencialDao;
import com.suelliton.projeto.model.OutputMessage;
import com.suelliton.projeto.model.Usuario;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.hibernate.criterion.Order;
import com.suelliton.projeto.interfaces.IUsuarioDao;
import com.suelliton.projeto.model.Credencial;

/**
 *
 * @author suelliton
 */
@Path("/usuario")
public class ServiceUsuario {
    
        @POST
        @Produces(MediaType.APPLICATION_JSON)
        @Consumes(MediaType.APPLICATION_JSON)
        public Response create(Credencial c){
            try{
            ICredencialDao credencialDAO = new CredencialImpl();    	
            credencialDAO.save(c);
            System.out.print(c.toString());
            }catch(Exception e){
                return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new OutputMessage(500,e.getMessage()))
                    .build();
            
            }
            return Response.status(Response.Status.CREATED).entity(c).build();
        }
        
        @DELETE
        @Path("/{id}")
        @Produces(MediaType.APPLICATION_JSON)
        public Response delete(@PathParam("id")  int id){
        
            IUsuarioDao usuarioDAO = new UsuarioImpl();
            Usuario usuarioRemover = usuarioDAO.findById(id);
            if (usuarioRemover == null){//SE NÃO ACHAR A PESSOA
                return Response.status(Response.Status.NO_CONTENT).build();
            }
            try{//TENTA  DELETAR
                usuarioDAO.delete(usuarioRemover);
            }catch(Exception e){ //SE NAO DELETAR RETORNA ERRO                        
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new OutputMessage(500,e.getMessage())).build();
            
            }
            //se tudo der certo retorna sucesso
            return Response.status(Response.Status.OK).entity(new OutputMessage(200,"Objeto removido com sucesso")).build();
            
            
        }
        
        @PUT
        @Produces(MediaType.APPLICATION_JSON)
        @Consumes(MediaType.APPLICATION_JSON)
        public Response update(Usuario u){
           try{
               IUsuarioDao usuarioDAO = new UsuarioImpl();
               usuarioDAO.save(u);         //atualiza  
           }catch(Exception e){           
               return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new OutputMessage(500,e.getMessage())).build();
           }
            
           return Response.status(Response.Status.OK).entity(new OutputMessage(200,"Objeto atualizado com sucesso!")).build();        
                        
        }
        
        @GET
        @Path("/{id}")
        @Produces(MediaType.APPLICATION_JSON)        
        public Response listById(@PathParam("id") int id){
            try{
                IUsuarioDao usuarioDAO= new UsuarioImpl();
                Usuario p = usuarioDAO.findById(id);
                if(p == null){
                    return Response.status(Response.Status.NO_CONTENT).build();                
                }else{                                
                    return Response.status(Response.Status.OK).entity(p).build();                
                }            
            }catch(Exception e){
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new OutputMessage(500,e.getMessage())).build();
            }
            
        }
        @GET
        @Produces(MediaType.APPLICATION_JSON) 
        public Response listAll(@QueryParam("orderby") @DefaultValue("id") String orderBy,
                                @QueryParam("sort") @DefaultValue("asc") String sort){
            try{
                IUsuarioDao usuarioDAO = new UsuarioImpl();
                List<Usuario> usuarios ;
                if(sort.equals("desc")){
                    usuarios = usuarioDAO.findAll(Order.desc(orderBy));                
                }else{
                    usuarios = usuarioDAO.findAll(Order.asc(orderBy));
                }
                
                if(usuarios == null){
                    return Response.status(Response.Status.NO_CONTENT).build();
                }else{
                    return Response.status(Response.Status.OK).entity(usuarios).build();
                }
                
            }catch(Exception e){
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new OutputMessage(500,e.getMessage())).build();
            }
        }

    
    
}
