package org.eec.math;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KMeansAlgorithm {

	private int k;
	private Point2D[] centroides;
	private List< List<Point2D> > clusters;
	private List<Point2D> puntosIniciales;
	private List<RegistroCluster> listaAsignacionCluster;
	
	public KMeansAlgorithm(int k, List<Point2D> puntosIniciales) {
		super();
		this.k = k;
		this.puntosIniciales = puntosIniciales;
		this.centroides = new Point2D[ k ];
		this.clusters = new ArrayList<>();
		this.listaAsignacionCluster = new ArrayList<>();
		initListaAsignacionCluster();
		
		System.out.println("minX: " + getMinX());
		System.out.println("maxX: " + getMaxX());
		
		System.out.println("minY: " + getMinY());
		System.out.println("maxY: " + getMaxY());
		
		initCentroides();
		testDistanciaEuclidiana();
	} //--fin : CONSTRUCTOR
	
	private void initCentroides() {
		Point2D centroideTmp;
		double xCentroide, yCentroide;
		int idxCentroide;
		
		for(int i = 0; i < this.k; i++) {
			
			xCentroide = generateRandomDouble(getMinX(), getMaxX());
			yCentroide = generateRandomDouble(getMinY(), getMaxY());
			
			centroideTmp = new Point2D(xCentroide, yCentroide);
			this.centroides[i] = centroideTmp;
		}
		
		System.out.println("Fin de Inicializacion de CENTROIDES Aleatorios::");
		idxCentroide = -1;
		
		for(Point2D centroide : this.centroides) {
			idxCentroide++;
			System.out.println("[" + idxCentroide + "] - " + centroide);
		}
		
	} //--fin: initCentroides
	
	
	/*
	def distanciaEuclidiana(puntoA, puntoB):
    	return math.sqrt(pow((puntoB.x - puntoA.x),2) + pow((puntoB.y - puntoA.y),2))
	 * */
	private double distanciaEuclidiana(Point2D pointA, Point2D pointB) {
		double distancia = 0.0;
		distancia = Math.sqrt((Math.pow(pointB.getX() - pointA.getX(), 2)) + (Math.pow(pointB.getY() - pointA.getY(), 2)));
		return distancia;
	} //--fin: distanciaEuclidiana
	
	private void testDistanciaEuclidiana() {
		Point2D pointA, pointB;
		double distanciaAB;
		pointA = new Point2D(3.12, 7.54);
		pointB = new Point2D(6.45, 5.51);
		
		System.out.println("A = " + pointA);
		System.out.println("B = " + pointB);
		
		distanciaAB = distanciaEuclidiana(pointA, pointB);
		
		System.out.println( "distancia(A, B) = " + distanciaAB );
	} //--fin: testDistanciaEuclidiana()
	
	public void asignacionAClusters() {
		System.out.println("**--Asignacion de puntos a CLUSTERS...");
		List<RegistroCluster> registrosClusterIesimo;
		int maxEpochs = 50;
		double epsilon = 0.02;
		int epoch = 0;
		int pointIdx;
		double[] distanciasACentroides;
		double distanciaTmp, distanciaMinima;
		int j;
		int clusterTmp;
		double sumaX, sumaY, tamanioCluster, xMean, yMean;
		
		/*
		#TODO: implemetar ciclo PRINCIPAL de Epochs (WHILE) [validar condiciones de PARO]
		#posible uso de epsilon (umbral de Diff, entre Coords. actualizadas de Centroides)...
		 * */
		
		while(epoch < maxEpochs) {
			System.out.println("######++Epoch: " + epoch + "++############");
			
			pointIdx = -1;
			distanciasACentroides = new double[ this.k ];
			
			// #--INI: K-Means Algorithm 'MAIN'-body
			for(Point2D punto : this.puntosIniciales) {
				pointIdx += 1;
				System.out.println("-----------------------------------");
				System.out.println("Analizando el Punto P" + pointIdx + "...");
				// por cada punto medir su distancia a cada Centroide:
				distanciasACentroides = new double[ this.k ];
				j = -1;
				for(Point2D centroide : this.centroides) {
					j++;
					distanciaTmp = distanciaEuclidiana(punto, centroide);
					distanciasACentroides[j] = distanciaTmp;
				} //-- fin : for - this.centroides
				
				System.out.println("P" + pointIdx + " :: distancias a Centroides :: ");
				System.out.println( doubleArrayToString(distanciasACentroides) );
				
				// # Una vez determinadas las Distancias a los Centroides
				// determinar MIN distancia
				// idxMinDistancia ::== cluster (clusterTmp)
				distanciaMinima = minDoubleValue( distanciasACentroides );
				System.out.println( "distanciaMinima: " + distanciaMinima );
				
				clusterTmp = indexOfDoubleValue(distanciasACentroides, distanciaMinima);
				System.out.println("Cluster :: [" + clusterTmp + "]");
				
				this.listaAsignacionCluster.get( pointIdx ).setCluster( clusterTmp );
			} //--fin: FOREACH punto in listaDePuntos
			
			System.out.println("##--Recalculo de Centroides--");
			for(int c = 0; c < this.k; c++) {
				System.out.println( "Cluster [" + c + "] ::" );
				
				registrosClusterIesimo = filtrarClusterIesimo( c );
				System.out.println("Tamanio Cluster : " + registrosClusterIesimo.size() );
				
				sumaX = sumX(registrosClusterIesimo);
				sumaY = sumY(registrosClusterIesimo);
				
				tamanioCluster = registrosClusterIesimo.size();
				if(tamanioCluster == 0.0) {
					tamanioCluster = 1.0;
				} //fin: IF (evitar división entre 0)
				
				xMean = sumaX / tamanioCluster;
				yMean = sumaY / tamanioCluster;
				
				this.centroides[ c ] = new Point2D(xMean, yMean);
				
			} //--fin : FOR : Recalculo de Centroides
			
			// IMPRIMIR : Nuevos centroides ::
			System.out.println("+++NUEVOS Centroides :: ");
			int counter = -1;
			for(Point2D centroide : this.centroides) {
				counter++;
				System.out.println("[" + counter + "] -- " + centroide);
			} //--fin : FOR : this.centroides
			
			System.out.println("************++Epoch: " + epoch + "++*************");
			epoch += 1;
		}//--fin: WHILE principal
		
	} //--fin: asignacionAClusters
	
	private double sumX(List<RegistroCluster> listaRegistros) {
		double suma = 0.0;
		for(RegistroCluster registro : listaRegistros) {
			suma += registro.getRealPoint().getX();
		}
		return suma;
	} //--fin: sumX
	
	private double sumY(List<RegistroCluster> listaRegistros) {
		double suma = 0.0;
		for(RegistroCluster registro : listaRegistros) {
			suma += registro.getRealPoint().getY();
		}
		return suma;
	} //--fin: sumY
	
	private String doubleArrayToString(double[] arrayDouble) {
		StringBuilder strBuilder = new StringBuilder();
		int arrayLength = arrayDouble.length;
		int tmpIdx = -1;
		strBuilder.append( "[" );
		
		for(double value : arrayDouble) {
			tmpIdx++;
			strBuilder.append( value );
			if(tmpIdx < arrayLength - 1) {
				strBuilder.append(", ");
			}
		} //-- fin : FOR arratDouble
		
		strBuilder.append("]");
		return strBuilder.toString();
	} //--fin: doubleArrayToString
	
	private int indexOfDoubleValue(double[] arrayDouble, double key) {
		int idx = -1;
		int internalIdx = -1;
		
		for(double value : arrayDouble) {
			internalIdx++;
			if(value == key) {
				idx = internalIdx;
				break;
			}
		} //--fin : FOR : RECORRIDO arrayDouble
		
		return idx;
	} //--fin : indexOfDoubleValue
	
	private double minDoubleValue(double[] arrayDouble) {
		double minValue = Double.MAX_VALUE;
		
		for(double value : arrayDouble) {
			minValue = Math.min(minValue, value);
		}
		
		return minValue;
	} //--fin: minDoubleValue
	
	private double generateRandomDouble(double minValue, double maxValue) {
		Random r = new Random();
		double randomValue = minValue + (maxValue - minValue) * r.nextDouble();
		return randomValue;
	} //--fin : generateRamdonDouble
	
	private double getMinX() {
		double minX = Double.MAX_VALUE;
		
		for(Point2D realPoint : this.puntosIniciales) {
			minX = Math.min(minX, realPoint.getX());
		} //--fin : RECORRIDO : puntosIniciales
		
		return minX;
	} //--
	
	private double getMaxX() {
		double maxX = Double.MIN_VALUE;
		
		for(Point2D realPoint : this.puntosIniciales) {
			maxX = Math.max(maxX, realPoint.getX());
		} //--fin: RECORRIDO : puntosIniciales
		
		return maxX;
	} //--fin : getMaxX
	
	private double getMinY() {
		double minY = Double.MAX_VALUE;
		for(Point2D realPoint : this.puntosIniciales) {
			minY = Math.min(minY, realPoint.getY());
		}
		return minY;
	} //--fin: getMinY()
	
	private double getMaxY() {
		double maxY = Double.MIN_VALUE;
		for(Point2D realPoint : this.puntosIniciales){
			maxY = Math.max(maxY, realPoint.getY());
		}
		return maxY;
	} //--fin: getMaxY
	
	private List<RegistroCluster> filtrarClusterIesimo(int numCluster){
		List<RegistroCluster> listaRegistros = new ArrayList<>();
		
		for(RegistroCluster registro : this.listaAsignacionCluster) {
			if(registro.getCluster() == numCluster){
				listaRegistros.add( registro );
			}
		}
		
		return listaRegistros;
	} //-- fin: filtrarClusterIesimo
	
	public void printListaAsignacionCluster() {
		System.out.println("****--- Lista Asigancion CLUSTERS ----****");
		for(RegistroCluster registro : this.listaAsignacionCluster){
			System.out.println( registro );
		}
		System.out.println("######++++ Lista Asignacion CLUSTERS +++++######");
	} //--fin: prtinListaAsignacionCluster
	
	public List< List<Point2D> > getClusters(){
		List< List<Point2D> > listaClusters = new ArrayList<>();
		List<RegistroCluster> registrosClusterIesimo;
		List<Point2D> listaPuntosTmp;
		
		for(int i = 0; i < this.k; i++) {
			registrosClusterIesimo = filtrarClusterIesimo( i );
			listaPuntosTmp = transformRegClustListToPoint2DList( registrosClusterIesimo );
			listaClusters.add( listaPuntosTmp );
		} // for : i (Cluster-iesimo)
		
		return listaClusters;
	} //-- fin : getClusters()
	
	private List<Point2D> transformRegClustListToPoint2DList(List<RegistroCluster> listaRegistros){
		List<Point2D> listaPuntos = new ArrayList<>();
		for(RegistroCluster registro : listaRegistros) {
			listaPuntos.add( new Point2D(registro.getRealPoint().getX(), registro.getRealPoint().getY()) );
		}
		return listaPuntos;
	} //--fin: transformRegClustListToPoint2DList
	
	private void initListaAsignacionCluster() {
		RegistroCluster registroCluster;
		int iteracion = -1;
		
		for(Point2D realPoint : this.puntosIniciales) {
			registroCluster = new RegistroCluster(realPoint, -1);
			this.listaAsignacionCluster.add( registroCluster );
		} //-- fin : FOR : RECORRIDO puntosIniciales
		
		System.out.println("##--Fin de Inicialización de ListaAsigancionCluster ::");
		System.out.println("puntosIniciales LENGTH : " + this.puntosIniciales.size() );
		System.out.println("ListaAsignacionCluster LENGTH : " + this.listaAsignacionCluster.size() );
		
		System.out.println("\n**--listaAsignacionCluster--**");
		for(RegistroCluster regCluster : this.listaAsignacionCluster) {
			iteracion++;
			System.out.println(regCluster);
			if(iteracion == 4) {
				break;
			}
		} //-- fin RECORRIDO listaAsignacionCluster
		
	} //-- fin: initListaAsignacionCluster
	
} //--fin : CLASE : KMeansAlgotihm
