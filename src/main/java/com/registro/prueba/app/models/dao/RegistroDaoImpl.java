package com.registro.prueba.app.models.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.registro.prueba.app.models.entity.Registro;

@Repository
public class RegistroDaoImpl implements IRegistroDao {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true)
	@Override
	public List<Registro> findAll() {
		return em.createQuery("from Registro").getResultList();
	}

	@Override
	@Transactional
	public void save(Registro registro) {
		if (registro.getId() != null && registro.getId() > 0) {
			em.merge(registro);
		}else {
			em.persist(registro);
		}
	}

	@Override
	public Registro findOne(Long id) {
		return em.find(Registro.class, id);
	}

}
