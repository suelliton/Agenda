/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.suelliton.projeto.services;

import com.suelliton.projeto.implementations.ContatoImpl;
import com.suelliton.projeto.implementations.CredencialImpl;
import com.suelliton.projeto.interfaces.IContatoDao;
import com.suelliton.projeto.interfaces.ICredencialDao;
import com.suelliton.projeto.model.Contato;
import com.suelliton.projeto.model.Credencial;
import com.suelliton.projeto.model.OutputMessage;
import com.suelliton.projeto.model.Usuario;
import com.suelliton.projeto.security.Secured;
import java.util.ArrayList;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import org.hibernate.criterion.Order;

/**
 *
 * @author suelliton
 */
@Path("/contato")
public class ServiceContato {
    
    
        @Secured
        @POST
        @Produces(MediaType.APPLICATION_JSON)
        @Consumes(MediaType.APPLICATION_JSON)
        public Response create(Contato c,@Context SecurityContext securityContext){
            try{
                    Usuario usuarioLogado =  getUsuarioLogado(securityContext); 
                    if(usuarioLogado != null){
                         c.setUser(usuarioLogado);
                         IContatoDao contatoDAO = new ContatoImpl();    	
                         contatoDAO.save(c); 
                    }else{
                     return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(new OutputMessage(404,"Usuario náo autenticado!")).build();
                    
                    }
         
            }catch(Exception e){
                return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(new OutputMessage(500,e.getMessage()))
                    .build();
            
            }
            return Response.status(Response.Status.CREATED).entity(c).build();
        }
        @Secured
        @DELETE
        @Path("/{id}")
        @Produces(MediaType.APPLICATION_JSON)
        public Response delete(@PathParam("id")  int id,@Context SecurityContext securityContext){
            Usuario usuarioLogado =  getUsuarioLogado(securityContext); 
            IContatoDao contatoDAO = new ContatoImpl();            
            Contato contatoRemover = contatoDAO.findById(id);
            if (contatoRemover == null || contatoRemover.getUser().getId() != usuarioLogado.getId() ){//SE NÃO ACHAR A PESSOA
                return Response.status(Response.Status.NO_CONTENT).build();
            }
            try{//TENTA  DELETAR
                contatoDAO.delete(contatoRemover);
            }catch(Exception e){ //SE NAO DELETAR RETORNA ERRO                        
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new OutputMessage(500,e.getMessage())).build();
            
            }
            //se tudo der certo retorna sucesso
            return Response.status(Response.Status.OK).entity(new OutputMessage(200,"Objeto removido com sucesso")).build();
            
            
        }
        @Secured
        @PUT
        @Produces(MediaType.APPLICATION_JSON)
        @Consumes(MediaType.APPLICATION_JSON)
        public Response update(Contato c,@Context SecurityContext securityContext){
           try{
               Usuario usuarioLogado =  getUsuarioLogado(securityContext); 
               IContatoDao contatoDAO = new ContatoImpl();
               c.setUser(usuarioLogado);
               contatoDAO.save(c);         //atualiza  
           }catch(Exception e){           
               return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new OutputMessage(500,e.getMessage())).build();
           }
            
           return Response.status(Response.Status.OK).entity(new OutputMessage(200,"Objeto atualizado com sucesso!")).build();        
                        
        }
        @Secured
        @GET
        @Path("/{id}")
        @Produces(MediaType.APPLICATION_JSON)        
        public Response listById(@PathParam("id") int id,@Context SecurityContext securityContext){
            try{
                Usuario usuarioLogado =  getUsuarioLogado(securityContext); 
                
                IContatoDao contatoDAO= new ContatoImpl();
                Contato c = contatoDAO.findById(id);
                if(c != null && c.getUser().getId() == usuarioLogado.getId() ){                   
                    return Response.status(Response.Status.OK).entity(c).build();  
                }else{
                    return Response.status(Response.Status.NO_CONTENT).build();                 
                }          
                 
            }catch(Exception e){
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new OutputMessage(500,e.getMessage())).build();
            }
            
        }
        @Secured
        @GET
        @Produces(MediaType.APPLICATION_JSON) 
        public Response listAll(@QueryParam("orderby") @DefaultValue("id") String orderBy,
                                @QueryParam("sort") @DefaultValue("asc") String sort,
                                @Context SecurityContext securityContext){
            try{
                Usuario usuarioLogado =  getUsuarioLogado(securityContext); 
                if(usuarioLogado == null){
                    return Response.status(Response.Status.NO_CONTENT).entity(new OutputMessage(404,"Usuario náo autenticado!")).build();
                }
                IContatoDao contatoDAO = new ContatoImpl();
                List<Contato> contatosAll ;
                List<Contato> contatosUser = new ArrayList();
                if(sort.equals("desc")){
                    contatosAll = contatoDAO.findAll(Order.desc(orderBy));                
                }else{
                    contatosAll = contatoDAO.findAll(Order.asc(orderBy));
                }
                for(Contato c : contatosAll){
                    if(c.getUser().getId()==usuarioLogado.getId()){
                        contatosUser.add(c);
                    }
                 }
                
                if(contatosUser.isEmpty()){
                    return Response.status(Response.Status.NO_CONTENT).build();
                }else{
                    return Response.status(Response.Status.OK).entity(contatosUser).build();
                }
                
            }catch(Exception e){
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new OutputMessage(500,e.getMessage())).build();
            }
        }
        @Secured
        public Usuario getUsuarioLogado(SecurityContext securityContext){
        
                String token = securityContext.getUserPrincipal().getName();
                System.out.println("token "+ token);
                ICredencialDao credencialDAO = new CredencialImpl();
                List<Credencial> credenciais = credencialDAO.findAll(Order.desc("id")) ;    
                for(int i=0;i<credenciais.size();i++){
                    if (token.equals(credenciais.get(i).getToken())){
                        return credenciais.get(i).getUser();
                }
                
        }
                return null;
        }
        
}
