package com.indra.main.ejercicioVodafone.ejercicio1_Java;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.JOptionPane;

import org.json.JSONArray;
import org.json.JSONObject;

import com.indra.cilantrum.messages.MessageLibrary;

public class TC_Ejercicio1_Java {

	private static HttpsURLConnection connection;

	public TC_Ejercicio1_Java() throws IOException {

		// Se le pide el parámetro de búsqueda al usuario

		String bitCoin = JOptionPane.showInputDialog(null,
				"Bienvenid@ al buscador de Criptomonedas de Vodafone  \nPor favor, introduzca el nombre de la criptomoneda")
				.toLowerCase();

		// Se crea la conexión

		URL url = new URL("https://api.coingecko.com/api/v3/coins/" + bitCoin);
		connection = (HttpsURLConnection) url.openConnection();
		connection.setRequestMethod("GET");

		// Se extrae el código de la respuesta

		int statusCode = connection.getResponseCode();

		switch (statusCode) {

		case 200:

			// Se crea el lector de respuesta del servicio

			BufferedReader reader;
			String line;
			StringBuffer responseContent = new StringBuffer();
			reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			while ((line = reader.readLine()) != null) {
				responseContent.append(line);

			}

			reader.close();

			// Se extrae de la respuesta el parámetro del dolar

			JSONObject json = new JSONObject(responseContent.toString());
			String dolar = json.getJSONObject("market_data").getJSONObject("current_price").get("usd").toString();

			// Se muestra un mensaje informativo al usuario del precio de la criptomoneda en
			// dolares

			JOptionPane.showMessageDialog(null, "El precio actual de " + bitCoin + " es " + dolar + " dolares");
			break;

		case 404:
			JOptionPane.showMessageDialog(null, "No existe ninguna criptomoneda con el id " + bitCoin,
					"MENSAJE DE ERROR", JOptionPane.WARNING_MESSAGE);
			break;

		default:
			JOptionPane.showMessageDialog(null, "Se ha obtenido una respuesta inesperada", "MENSAJE DE ERROR",
					JOptionPane.WARNING_MESSAGE);

		}
	}

}
