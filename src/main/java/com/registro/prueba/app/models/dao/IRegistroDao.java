package com.registro.prueba.app.models.dao;

import java.util.List;

import com.registro.prueba.app.models.entity.Registro;

public interface IRegistroDao {
	
	public List<Registro> findAll();
	public void save(Registro registro);
	public Registro findOne(Long id);

}
