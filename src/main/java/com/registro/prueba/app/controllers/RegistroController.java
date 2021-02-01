package com.registro.prueba.app.controllers;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.registro.prueba.app.models.dao.IRegistroDao;
import com.registro.prueba.app.models.entity.Registro;

@Controller
public class RegistroController {

	@Autowired
	private IRegistroDao registroDao;
	private List<Registro> registroList;

	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public String listar(Model model) {
		model.addAttribute("titulo", "Prueba Práctica - Backend Developer");
		model.addAttribute("registros", registroDao.findAll());
		return "listar";
	}

	@RequestMapping(value = "/form")
	public String crear(Map<String, Object> model) {
		Registro registro = new Registro();
		model.put("registro", registro);
		model.put("titulo", "Registro de Datos de Usuario");
		return "form";
	}

	@RequestMapping(value = "/form/{id}", method = RequestMethod.GET)
	public String edit(@PathVariable(value = "id") Long id, Map<String, Object> model) {
		Registro registro = null;
		if (id > 0) {
			registro = registroDao.findOne(id);
		} else {
			return "redirect:listar";
		}
		model.put("registro", registro);
		model.put("titulo", "Editar Registro");
		return "form";
	}

	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String guardar(Registro registro) {
		registro.setProcesado(false);
		registroDao.save(registro);
		return "redirect:listar";
	}

	@RequestMapping(value = "/procesar")
	public String procesar(@RequestParam(value = "procesoSeleccionado", required = false) List<Boolean> procesoSeleccionado) {
		List<Registro> registros = registroDao.findAll();
		for(int i=0;i<registros.size();i++) {
			for(int j=0; j<procesoSeleccionado.size();j++) {
				if(i==j) {
					Registro registro = null;
					registro = registroDao.findOne(registros.get(i).getId());
					registro.setProcesado(procesoSeleccionado.get(j).booleanValue());
					registroDao.save(registro);
				}
			}
		}
		
		return "redirect:listar";
	}

	public List<Registro> getRegistroList() {
		return registroList;
	}

	public void setRegistroList(List<Registro> registroList) {
		this.registroList = registroList;
	}

}
