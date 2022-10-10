package org.eec.mappers;

import java.util.ArrayList;
import java.util.List;

import org.eec.math.Point2D;

public class DatasetToPoint2DMapper {

	public List<Point2D> datasetToPoint2D(List<String[]> listaDeFilas){
		List<Point2D> listaPuntos = new ArrayList<Point2D>();
		Point2D point;
		int rowCounter = -1;
		
		for(String[] row : listaDeFilas) {
			rowCounter++;
			if(rowCounter >= 1) {
				point = new Point2D( Double.parseDouble(row[2]), Double.parseDouble(row[3]) );
				listaPuntos.add( point );
			}
		}
		
		return listaPuntos;
	} //--fin: datasetToPoint2D
	
} //--fin : CLASE : DatasetToPoint2DMapper
