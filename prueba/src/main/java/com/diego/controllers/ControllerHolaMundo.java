package com.arquitecturajava.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import modelos.Persona;

@Controller
public class ControllerHolaMundo {

	@RequestMapping("/")
	@ResponseBody
	public String home(){
		return "Hola Mundo.";
	}
	
	@RequestMapping("/saludo")
	@ResponseBody
	public String saludoTodos(){
		return "Hola a todos.";
	}
	
	@RequestMapping(value = "/persona/id", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Persona> datosPersona(@RequestParam(value="id", required=true) String identificacion){
		Persona persona = null;
		List<Persona> listPersona = cargarListaPersonas();
		for (Persona unaPersona : listPersona) {
			if(unaPersona.getIdentificacion().equals(identificacion)){
				persona = unaPersona;
				break;
			}
		}
		return new ResponseEntity<>(persona, getHttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/primerpersona", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Persona> primerPersona(){
		Persona persona = null;
		List<Persona> listPersona = cargarListaPersonas();
		if(listPersona != null && listPersona.size()>1){
			persona = listPersona.get(0);
		}

	   return new ResponseEntity<>(persona, getHttpHeaders(), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/listaPersonas", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Persona>> listaPersonas(){
		return new ResponseEntity<>(cargarListaPersonas(), getHttpHeaders(), HttpStatus.OK);
	}
	
	private List<Persona> cargarListaPersonas(){

		List<Persona> listPersona = new ArrayList<>();
		listPersona.add(new Persona("1", "Carlos", "Mario", null, 37, new Date()));
		listPersona.add(new Persona("2", "Andres", "Perez", "Quinto", 43, new Date()));
		listPersona.add(new Persona("3", "Matias", "Gomez", "Men", 23, new Date()));
		listPersona.add(new Persona("4", "Azuna", "Zuzumiya", "Kuzanami", 15, new Date()));
		return listPersona;
	}
	
	//Encabezado CORS necesario consumir servicio desde angular en los navegadores
	private HttpHeaders getHttpHeaders(){
		   HttpHeaders responseHeaders = new HttpHeaders();
		   responseHeaders.add("Access-Control-Allow-Origin", "*");
		   return responseHeaders;
	}
}
