package com.indra.main.ejercicioVodafone.ejercicio1;

import org.pmw.tinylog.Logger;

import com.indra.cilantrum.framework.api.test.WebTestCase;
import com.indra.cilantrum.webservices.ReqResponse;

import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;

public class TC_GetMuseo_REST extends WebTestCase{

	@Override
	public void preExecute() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	/**
	 * Servicio para filtrar por un museo y guarda su ID en el TC-data
	 */
	public void execute() {
		
		
		ReqResponse respuesta=
				ws
				.createRestRequest()
				.contentType(ContentType.JSON)
				.params("latitud", data.get("LATITUD"),
						"longitud", data.get("LONGITUD"),
						"distancia", data.get("DISTANCIA"),
						"distrito_nombre", data.get("DISTRITO_NOMBRE")
						)
				.get(data.get("URL_BASE")+data.get("METODO"))
				.validations()
				.statusCode(200)
				.isNotEmpty("id")
				.evaluate();
		
		String idMuseo= respuesta.extract().jsonPath().getString("@graph.@id").substring(65, 72);
		
		data.set("APIMADRID","TC002","ID",idMuseo);
		Logger.info(idMuseo);
	
	}

	@Override
	public void postExecute() {
		// TODO Auto-generated method stub
		
	}

}
