/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.suelliton.projeto.implementations;

import com.suelliton.projeto.model.Usuario;
import com.suelliton.projeto.interfaces.IUsuarioDao;


public class UsuarioImpl extends GenericDaoImpl<Usuario,Integer> implements IUsuarioDao {
 
    public UsuarioImpl() { 
         super(Usuario.class); 
    } 
    
    
}
