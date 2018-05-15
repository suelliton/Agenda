/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.suelliton.projeto.implementations;

import com.suelliton.projeto.interfaces.IContatoDao;
import com.suelliton.projeto.interfaces.IGenericDao;
import com.suelliton.projeto.model.Contato;

/**
 *
 * @author suelliton
 */
public class ContatoImpl extends GenericDaoImpl<Contato, Integer> implements IContatoDao{
    
    public ContatoImpl(){
        super(Contato.class);
    
    }
}
