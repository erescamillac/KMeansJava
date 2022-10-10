package org.eec.main;

import java.util.ArrayList;
import java.util.List;

import org.eec.utils.*;
import org.eec.mappers.*;
import org.eec.math.Point2D;

import javax.swing.SwingUtilities;
// import org.eec.gui.ScatterPlotGUI;
import org.eec.gui.ScatterPlotKMeansGUI;
import org.eec.configuration.GraphicsConfiguration;

import org.eec.math.KMeansAlgorithm;

public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DatasetToPoint2DMapper datasetToPoint2DMapper;
		String filePath = "src\\iris_dataset.csv";
		System.out.println( "Nombre del Archivo (CSV): " + filePath );
		GraphicsConfiguration graphConfig;
		KMeansAlgorithm kMeansAlgorithm;
		// ScatterPlotGUI scatterPlotGUI;
		// ScatterPlotKMeansGUI scatterPlotKMeansGUI;
		int k = 3;
		
		graphConfig = new GraphicsConfiguration();
		graphConfig.setMaxRealValueX( 8.0 );
		graphConfig.setTamanioRealPasoX( 1.0 );
		graphConfig.setMarginPx( 30 );
		graphConfig.setAlturaDeMarcador( 12 );
		
		graphConfig.setMaxRealValueY( 3.0 );
		graphConfig.setTamanioRealPasoY( 0.5 );
		
		List<Double> listaDoubles = new ArrayList<Double>();
		
		
		// scatterPlotGUI = new ScatterPlotGUI();
		
		CSVReader csvReader = new CSVReader();
		List<String[]> listaDeLineas;
		datasetToPoint2DMapper = new DatasetToPoint2DMapper();
		
		listaDeLineas = csvReader.readCSVFile( filePath );
		
		for(String[] row : listaDeLineas){
			for(String attValue : row) {
				System.out.printf("%-17s", attValue);
			}
			System.out.println();
		} //-- fin FOR row:listaDeLineas
		
		// TODO: Implement : Mapping (dataset) -->> List<Point2D> ::
		System.out.println("\n##--Mapeo Dataset to List<Point2D>--##");
		
		List<Point2D> listaPuntos = datasetToPoint2DMapper.datasetToPoint2D( listaDeLineas );
		
		System.out.println("##--Lista de PUNTOS 2D:: --##");
		for(Point2D point: listaPuntos) {
			System.out.println( point );
		}
		
		System.out.println( "--Testing de K-Means Algorithm--" );
		kMeansAlgorithm = new KMeansAlgorithm(k, listaPuntos);
		
		kMeansAlgorithm.asignacionAClusters();
		
		kMeansAlgorithm.printListaAsignacionCluster();
		
		List< List<Point2D> > clusters = kMeansAlgorithm.getClusters();
		
		System.out.println("**Clusters SUMMARY ::");
		int counter = -1;
		for(List<Point2D> listaPuntosTmp : clusters) {
			counter++;
			System.out.println( "[" + counter + "] -- SIZE :: { " + listaPuntosTmp.size() + " }" );
		} //--fin : FOR
		
		// TODO : show ScatterPlotGUI ...
		SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				// ScatterPlotGUI.createAndShowGUI();
				// scatterPlotKMeansGUI
				ScatterPlotKMeansGUI.createAndShowGUI(clusters, graphConfig);
			}
			
		});
		
	} //--fin: main()

}
