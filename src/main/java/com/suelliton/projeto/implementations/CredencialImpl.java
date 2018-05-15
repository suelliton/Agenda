/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.suelliton.projeto.implementations;

import com.suelliton.projeto.interfaces.IContatoDao;
import com.suelliton.projeto.interfaces.ICredencialDao;
import com.suelliton.projeto.model.Contato;
import com.suelliton.projeto.model.Credencial;

/**
 *
 * @author Aluno
 */
public class CredencialImpl extends GenericDaoImpl<Credencial, Integer> implements ICredencialDao{
     public CredencialImpl(){
        super(Credencial.class);
    
    }
}
