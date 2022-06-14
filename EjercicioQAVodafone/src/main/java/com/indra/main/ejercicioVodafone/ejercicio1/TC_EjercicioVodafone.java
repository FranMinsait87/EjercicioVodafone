package com.indra.main.ejercicioVodafone.ejercicio1;

import javax.swing.JOptionPane;

import com.indra.cilantrum.framework.api.test.WebTestCase;
import com.indra.cilantrum.messages.MessageLibrary;
import com.indra.cilantrum.webservices.ReqResponse;

import io.restassured.http.ContentType;

public class TC_EjercicioVodafone extends WebTestCase {

	@Override
	public void preExecute() {
		System.out.println("Iniciando ejecución");
		MessageLibrary.log("Pedimos al usuario que introduzca en el buscador el tipo de criptomoneda");
	

	}

	@Override
	/**
	 * Servicio para buscar un tipo de Criptomoneda, que se pedira por consola, y
	 * mostrar su valor en dólares
	 */

	public void execute() {
		
		/**
		 * Pedimos al usuario que introduzca la criptomoneda que desee buscar
		 */
		  String bitCoin = JOptionPane.showInputDialog(null,
	         "Hola!, buenvenid@ al buscador de Criptomonedas de Vodafone \n \nPor favor, introduzca el nombre de la criptomoneda").toLowerCase();

		MessageLibrary.log("El usuario ha introducido <b>'" + bitCoin + "'</b> como criterio de búsqueda");
		MessageLibrary.log("Se ejecuta la llamada al servicio con el criterio de búsqueda <b>'" + bitCoin+ "'</b> como parámetro");
		
		/**
		 * Ejecutamos la consulta con los datos obtenidos del usuario
		 */
		ReqResponse respuesta =
				ws.createRestRequest()
				.contentType(ContentType.JSON)
				.get(data.get("URL_BASE") + data.get("METODO") + bitCoin);

		/**
		 * Extraemos el código de la respuesta y ejecutamos el caso que corresponda.
		 */
		
		int statusCode = respuesta.extract().statusCode();
		switch (statusCode) {
		case 200:

			MessageLibrary.log("El código del servicio es: <b>" + statusCode + "</b>. Respuesta satisfactoria.");
			String current_price = respuesta.extract().jsonPath().getString("market_data.current_price.usd");
			System.out.println("El precio actual en dólares de la criptomoneda <b>'" + bitCoin + "'</b> es: <b>"
					+ current_price + "$</b>");
			MessageLibrary.log("El precio actual en dólares de la criptomoneda <b>'" + bitCoin + "'</b> es: <b>"
					+ current_price + "$</b>");
			break;

		case 404:
			System.err.println("\t \t \n El activo no existe. \n");
			lib.reportError("El código del servicio es: <b>" + statusCode + "</b>. El activo <b>'" + bitCoin
					+ "'</b> no existe.", false, "Error de datos", "Los datos introducidos no son correctos.");
			break;

		default:
			System.err.println("\t \t \nSe ha obtenido una respuesta inesperada.\n");
			lib.reportError("El código del servicio es: <b>" + statusCode + "</b>. Se ha obtenido una respuesta inesperada.", false, "Error de datos ", "Los datos introducidos no son correctos.");

		}

	}

	@Override
	public void postExecute() {
		System.out.println("Ejecución terminada.");

	}

}
