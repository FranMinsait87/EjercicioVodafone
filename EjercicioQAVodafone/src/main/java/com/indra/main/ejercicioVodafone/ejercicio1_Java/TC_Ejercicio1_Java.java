package com.indra.main.ejercicioVodafone.ejercicio1_Java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.swing.JOptionPane;

import org.json.JSONObject;

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
		String mensaje=null;
		
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
			mensaje= "El código de la respuesta es: "+statusCode+"\nEl precio actual de " + bitCoin + " es " + dolar + " dolares";
			// Se muestra un mensaje informativo al usuario del precio de la criptomoneda en dolares

			JOptionPane.showMessageDialog(null, mensaje);		
			crearReporte(mensaje);
			
			break;

		case 404:
			mensaje= "El código de la respuesta es: "+statusCode+"\nNo existe ninguna criptomoneda con el id " + bitCoin;
			JOptionPane.showMessageDialog(null, mensaje,"MENSAJE DE ERROR", JOptionPane.WARNING_MESSAGE);
			crearReporte(mensaje);
			break;

		default:
			mensaje= "El código de la respuesta es: "+statusCode+"\nSe ha obtenido una respuesta inesperada ";
			JOptionPane.showMessageDialog(null, mensaje , "MENSAJE DE ERROR", JOptionPane.WARNING_MESSAGE);
			crearReporte(mensaje);

		}
	}
	
	//Creación de un archivo con el resultado del reporte.
	public static void crearReporte(String reporte) throws IOException 
	{
        String ruta = "ReporteEjercicio1_Java.txt";
        File file = new File(ruta);
        
        if (!file.exists()) {
            file.createNewFile();
        }
        
        FileWriter fw = new FileWriter(file);
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write(reporte);
        bw.close();
        
	}

}
