package org.eec.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {
	
	public List<String[]> readCSVFile(String filePath) {
		BufferedReader reader = null;
		String line = "";
		List<String[]> listaDeFilas = new ArrayList<String[]>();
		
		try {
			reader = new BufferedReader(new FileReader(filePath));
			
			while( (line = reader.readLine()) != null ) {
				String[] row = line.split(",");
				listaDeFilas.add( row );
			} //--fin: WHILE (ciclo de Lectura LINEA a LINEA)
			
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return listaDeFilas;
	} //-- fin: readCSVFile( filePath: String )

} //-- fin: CLASE : CSVReader