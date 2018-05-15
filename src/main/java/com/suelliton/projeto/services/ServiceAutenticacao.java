/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.suelliton.projeto.services;

import com.suelliton.projeto.implementations.CredencialImpl;
import com.suelliton.projeto.implementations.UsuarioImpl;
import com.suelliton.projeto.interfaces.ICredencialDao;
import com.suelliton.projeto.interfaces.IUsuarioDao;
import com.suelliton.projeto.model.Credencial;
import com.suelliton.projeto.model.Login;
import com.suelliton.projeto.model.OutputMessage;
import com.suelliton.projeto.model.Usuario;
import com.ufrn.projeto.security.TokenUtil;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.hibernate.criterion.Order;

/**
 *
 * @author Aluno
 */
@Path("/autenticacao")
public class ServiceAutenticacao {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response authenticaUser(Login login) {
        try {
            Credencial c = validaCredenciais(login.getUsername(),login.getPassword());
            String token = TokenUtil.criaToken(login.getUsername());
            c.setToken(token);
            ICredencialDao credencialDAO = new CredencialImpl();
            credencialDAO.save(c);
            return Response
                    .status(Response.Status.OK)
                    .entity(new OutputMessage(200, token))
                    .build();
        } catch (Exception e) {
            return Response
                    .status(Response.Status.UNAUTHORIZED)
                    .entity(new OutputMessage(Response.Status.UNAUTHORIZED.getStatusCode(), "Não autorizado: "
                            + e.getMessage()))
                    .build();
        }
    }

    private Credencial validaCredenciais(String username, String password) throws Exception {
        
        ICredencialDao credencialDAO = new CredencialImpl();
        List<Credencial> credenciais = credencialDAO.findAll(Order.desc("id")) ;    
        boolean logado = false;
        Credencial c = new Credencial();
        for(int i=0;i<credenciais.size();i++){
            if (username.equals(credenciais.get(i).getUsername()) && password.equals(credenciais.get(i).getPassword())){
               logado = true;
               c = credenciais.get(i);
            }
        
        }
        if(!logado){
            throw new Exception("Verifique o nome de usuário e senha.");
        }
        return c;
        
    }

}
