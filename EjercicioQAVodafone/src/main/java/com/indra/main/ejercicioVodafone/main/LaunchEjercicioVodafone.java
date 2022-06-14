package com.indra.main.ejercicioVodafone.main;

import com.indra.cilantrum.framework.main.Main;

public class LaunchEjercicioVodafone {

	public static void main(String[] args) {
		
		System.setProperty("tags", "QAVodafone");
		
		//Se llama a Main para generar el arbol de pruebas e iniciar la ejecucion.
		Main.launchBuild("resources/build.properties");
	}
}
