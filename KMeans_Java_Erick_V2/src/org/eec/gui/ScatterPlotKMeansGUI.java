package org.eec.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.eec.math.MapperPoint2DToAWTPoint;
import org.eec.math.Point2D;

import org.eec.configuration.GraphicsConfiguration;

// import org.eec.math.Point2D;
// import org.eec.math.Point2D;

import java.awt.Point;

public class ScatterPlotKMeansGUI extends JPanel{

	private List<List<Point2D>> clusters;
	private Color axisColor = Color.BLACK;
	private Color pointColor = Color.RED;
	private GraphicsConfiguration graphConfig;
	private int ratioX;
	private int ratioY;
	private Color[] colorsArray;

	// TODO: modificar constructor para que reciba
	// obj de config {realXMaxValue, realYMaxValue, etc.}
	public ScatterPlotKMeansGUI( List< List<Point2D> > clusters, GraphicsConfiguration graphConfig) {
		super();
		this.clusters = clusters;
		this.graphConfig = graphConfig;
		this.ratioX = 0;
		this.ratioY = 0;
		this.colorsArray = new Color[] {Color.RED, Color.GREEN, Color.BLUE, Color.ORANGE, Color.PINK};
	} //--fin: Constructor
	
	public static void createAndShowGUI(List< List<Point2D> > clusters, GraphicsConfiguration graphConfig) {
		ScatterPlotKMeansGUI mainPanel = new ScatterPlotKMeansGUI( clusters, graphConfig );
		mainPanel.setPreferredSize(new Dimension(800, 600));
		
		JFrame frame = new JFrame("K-Means: ScatterPlot by Erick Escamilla C.");
		frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		
		frame.getContentPane().add( mainPanel );
		frame.pack();
		frame.setLocationRelativeTo( null );
		
		frame.setVisible( true );
	} //--fin: createAndShowGUI()
	
	//@OVERRIDE :: .paintComponent()
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent( g );
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
		
		int pixelsWidth = getWidth();
		int pixelsHeight = getHeight();
		
		System.out.println( "pixelsWidth: [" + pixelsWidth + "] px" );
		System.out.println( "pixelsHeight: [" + pixelsHeight + "] px" );
		
		g2.setColor( Color.WHITE );
		g2.fillRect(0, 0, pixelsWidth, pixelsHeight);
		
		// dibujar EJES ::
		drawAxis( g2 );
		
		// TESTING :: pintar java.awt.Point (pixels-points) on Screen...
		// testDrawPointsOnScreen( g2 );
		this.ratioX = calculateRatioRealUnitsToPxOnX();
		this.ratioY = calculateRatioRealUnitsToPxOnY();
		
		// TESTING :: pintar 'Real-Points' on Screen (internal Mapping <Point2D> -->> <java.awt.Point>)
		// testDrawRealPoints( g2 );
		
		// Draw REAL-K-Means-Point2D in JPanel...
		drawRealKMeansPoints( g2 );
		
	} //--fin: .paintComponent(g : Graphics)
	
	private void drawRealKMeansPoints(Graphics2D g2) {
		Point screenCenterPoint, screenPointTmp;
		int idxTmp = -1;
		
		screenCenterPoint = new Point( getWidth() / 2, getHeight() / 2 );
		
		for(List<Point2D> listaPuntosReales : this.clusters) {
			idxTmp++;
			// Select color ::
			g2.setColor( this.colorsArray[idxTmp] );
			for(Point2D realPoint : listaPuntosReales) {
				// private void drawPointOnScreen(Point point, int widthPoint, Color color, Graphics2D g2)
				// public static Point mapearPoint2DToAWTPoint(Point2D realPoint, Point screenCenter, int ratioX, int ratioY) {
				screenPointTmp = MapperPoint2DToAWTPoint.mapearPoint2DToAWTPoint(realPoint, screenCenterPoint, this.ratioX, this.ratioY);
				// drawPointOnScreen();
				drawPointOnScreen(screenPointTmp, 6, this.colorsArray[idxTmp], g2);
			} //--Recorrido de Todos los Puntos en el Cluster-Iesimo
		} //-- Recorrido CLUSTERS
	} //--fin: drawRealKMeansPoints(g2 : Graphics2D)
	
	private void drawAxis(Graphics2D g2) {
		System.out.println( ".drawAxis()---" );
		int pixelsWidth = getWidth();
		int pixelsHeight = getHeight();
		int x1, y1, x2, y2;
		int xCentroPantalla, yCentroPantalla, screenWidthPx, screenHeightPx;
		
		screenWidthPx = getWidth();
		screenHeightPx = getHeight();
		xCentroPantalla = (int) (screenWidthPx / 2);
		yCentroPantalla = (int) (screenHeightPx / 2);
		
		x1 = 0;
		y1 = (int)(pixelsHeight / 2);
		x2 = pixelsWidth;
		y2 = y1;
		
		System.out.println("x1: " + x1);
		System.out.println("y1: " + y1);
		System.out.println("x2: " + x2);
		System.out.println("y2: " + y2);
		
		
		// RENDER : x-axis
		g2.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON );
		
		g2.setColor( axisColor );
		g2.drawLine(x1, y1, x2, y2);
		
		// RENDER : y-axis
		g2.setColor( axisColor );
		x1 = xCentroPantalla;
		y1 = 0;
		x2 = xCentroPantalla;
		y2 = screenHeightPx;
		g2.drawLine(x1, y1, x2, y2);
		
		// RENDER : x-axis Markers (including Labels)...
		renderXMarkers( g2 );
		
		// RENDER : y-axis Markers (including Labels)...
		renderYMarkers( g2 );
		
	} //--fin: drawAxis(g2 : Graphics2D)
	
	private void renderYMarkers(Graphics2D g2) {
		FontMetrics metrics;
		String yLabel = "";
		int numSegmentosY = 1;
		int xMCP, yMCP, xMLP, yMLP, xMRP, yMRP;
		int xCentroPantalla, yCentroPantalla;
		int screenHeightPx;
		int longitudSegmY;
		int coordXLabel, coordYLabel, anchoEtiq, alturaEtiq;
		
		screenHeightPx = getHeight();
		
		xCentroPantalla = getWidth() / 2;
		yCentroPantalla = getHeight() / 2;
		
		numSegmentosY = (int)(this.graphConfig.getMaxRealValueY() / this.graphConfig.getTamanioRealPasoY());
		longitudSegmY = ( (screenHeightPx - 2*this.graphConfig.getMarginPx()) / 2 ) / numSegmentosY;
		
		metrics = g2.getFontMetrics();
		
		for(int i = 1; i <= numSegmentosY; i++) {
			// PASO 1.- Colocar Marker POSITIVO
			xMCP = xCentroPantalla;
			yMCP = yCentroPantalla - (i * longitudSegmY);
			
			yMLP = yMCP;
			yMRP = yMCP;
			
			xMLP = xMCP - ( this.graphConfig.getAlturaDeMarcador() / 2);
			xMRP = xMCP + ( this.graphConfig.getAlturaDeMarcador() / 2);
			
			g2.setColor( axisColor );
			g2.drawLine(xMLP, yMLP, xMRP, yMRP);
			// -- 1.1 yLabels (POSITIVOS) ::
			yLabel = Double.toString( i * this.graphConfig.getTamanioRealPasoY() );
			
			anchoEtiq = metrics.stringWidth( yLabel );
			alturaEtiq = metrics.getHeight();
			
			coordXLabel = xMLP - this.graphConfig.getPaddingLabelOnX() - anchoEtiq;
			coordYLabel = yMCP + ( alturaEtiq / 2 );
			
			g2.setColor( axisColor );
			g2.drawString( yLabel, coordXLabel, coordYLabel );
			
			// PASO 2.- Colocar Marker NEGATIVO
			xMCP = xCentroPantalla;
			yMCP = yCentroPantalla + (i * longitudSegmY);
			
			yMLP = yMCP;
			yMRP = yMCP;
			
			xMLP = xMCP - ( this.graphConfig.getAlturaDeMarcador() / 2);
			xMRP = xMCP + ( this.graphConfig.getAlturaDeMarcador() / 2);
			
			g2.setColor( axisColor );
			g2.drawLine(xMLP, yMLP, xMRP, yMRP);
			// --2.1 yLabels (NEGATIVOS) ::
			yLabel = Double.toString( (-1) * i * this.graphConfig.getTamanioRealPasoY() );
			
			anchoEtiq = metrics.stringWidth( yLabel );
			alturaEtiq = metrics.getHeight();
			
			coordXLabel = xMLP - this.graphConfig.getPaddingLabelOnX() - anchoEtiq;
			coordYLabel = yMCP + ( alturaEtiq / 2 );
			
			g2.setColor( axisColor );
			g2.drawString( yLabel, coordXLabel, coordYLabel );
			
		} //--fin: FOR
		
	} //--fin: renderYMarkers(g2 : Graphics2D)
	
	private void renderXMarkers(Graphics2D g2) {
		FontMetrics metrics;
		String xLabel = "";
		int numSegmentosX = 1;
		int xMCP, yMCP, xMSP, yMSP, xMIP, yMIP;
		int xCentroPantalla, yCentroPantalla;
		int screenWidthPx;
		int longitudSegmX;
		int coordXLabel, coordYLabel, anchoEtiq, alturaEtiq;
		
		screenWidthPx = getWidth();
		
		xCentroPantalla = getWidth() / 2;
		yCentroPantalla = getHeight() / 2;
		
		numSegmentosX = (int)(this.graphConfig.getMaxRealValueX() / this.graphConfig.getTamanioRealPasoX());
		longitudSegmX = ( (screenWidthPx - 2*this.graphConfig.getMarginPx()) / 2 ) / numSegmentosX;
		
		metrics = g2.getFontMetrics();
		
		for(int i = 1; i <= numSegmentosX; i++) {
			//# Paso 1.- Colocar Marker POSITIVO ::
			xMCP = xCentroPantalla + (i)*longitudSegmX;
			yMCP = yCentroPantalla;
			
			xMSP = xMCP;
			xMIP = xMCP;
			
			yMSP = yMCP - ( this.graphConfig.getAlturaDeMarcador() / 2);
			yMIP = yMCP + ( this.graphConfig.getAlturaDeMarcador() / 2);
			
			g2.setColor( axisColor );
			g2.drawLine(xMSP, yMSP, xMIP, yMIP);
			
			// 1.1 LABELS para Markers POSITIVOS
			xLabel = Double.toString( i * this.graphConfig.getTamanioRealPasoX() );
			/*
			 FontMetrics metrics = g2.getFontMetrics();
				int labelWidth = metrics.stringWidth( yLabel );
				
				g2.drawString( yLabel, x0-labelWidth-6, y0+(metrics.getHeight()/2)-3);
			 * */
			anchoEtiq = metrics.stringWidth( xLabel );
			alturaEtiq = metrics.getHeight();
			
			coordXLabel = xMCP - (anchoEtiq / 2);
			coordYLabel = yMIP + this.graphConfig.getPaddingLabelOnY() + alturaEtiq;
			
			g2.drawString( xLabel, coordXLabel, coordYLabel );
			
			//# Paso 2.- Colocar Marker NEGATIVO ::
			xMCP = xCentroPantalla + (-1*i)*longitudSegmX;
			yMCP = yCentroPantalla;
			
			xMSP = xMCP;
			xMIP = xMCP;
			
			yMSP = yMCP - ( this.graphConfig.getAlturaDeMarcador() / 2);
			yMIP = yMCP + ( this.graphConfig.getAlturaDeMarcador() / 2);
			
			g2.setColor( axisColor );
			g2.drawLine(xMSP, yMSP, xMIP, yMIP);
			
			// 2.1 LABELS para Markers NEGATIVOS ::
			xLabel = Double.toString( (-1.0) * i * this.graphConfig.getTamanioRealPasoX() );
			
			anchoEtiq = metrics.stringWidth( xLabel );
			alturaEtiq = metrics.getHeight();
			
			coordXLabel = xMCP - (anchoEtiq / 2);
			coordYLabel = yMIP + this.graphConfig.getPaddingLabelOnY() + alturaEtiq;
			
			g2.drawString( xLabel, coordXLabel, coordYLabel );
			
		} ///--fin: for (Recorrido de 'Markers')
	} //--fin: renderXMarkers
	
	private void drawPointOnScreen(Point point, int widthPoint, Color color, Graphics2D g2) {
		int xULP, yULP, xCenter, yCenter;
		int ovalW, ovalH;
		xCenter = point.x;
		yCenter = point.y;
		// Point pointTmp = new Point(3, 5);
		ovalW = widthPoint;
		ovalH = widthPoint;
		
		xULP = xCenter - widthPoint / 2;
		yULP = yCenter - widthPoint / 2;
		
		g2.setColor( color );
		g2.fillOval(xULP, yULP, ovalW, ovalH);
	} //--fin: drawPointOnScreen
	
	// (800, 600) :: 800px Ancho * 600px Alto ::
	private void testDrawPointsOnScreen(Graphics2D g2) {
		Point pointsArray[] = new Point[3];
		
		pointsArray[0] = new Point(550, 250);
		pointsArray[1] = new Point(125, 455);
		pointsArray[2] = new Point(760, 559);
		
		for(Point pointTmp : pointsArray) {
			drawPointOnScreen(pointTmp, 6, Color.BLUE, g2);
		} //--fin : FOR : Recorrido pointsArray
	} //--fin: testDrawPointsOnScreen
	
	private int calculateRatioRealUnitsToPxOnX() {
		int ratioOnXRealUnitToPx = 0;
		int pixelsOnSemiXAxis = 0;
		
		pixelsOnSemiXAxis = (getWidth() - 2*this.graphConfig.getMarginPx()) / 2;
		ratioOnXRealUnitToPx = (int)( (1.0 * pixelsOnSemiXAxis) / (this.graphConfig.getMaxRealValueX()) );
		
		return ratioOnXRealUnitToPx;
	} //--fin: calculateRatioRealUnitsToPxOnX
	
	private int calculateRatioRealUnitsToPxOnY() {
		int ratioOnYRealUnitToPx = 0;
		int pixelsOnSemiYAxis = 0;
		
		pixelsOnSemiYAxis = (getHeight() - 2*this.graphConfig.getMarginPx()) / 2;
		ratioOnYRealUnitToPx = (int) ( (1.0 * pixelsOnSemiYAxis) / (this.graphConfig.getMaxRealValueY()) );
		
		return ratioOnYRealUnitToPx;
	} //--fin: calculateRatioRealUnitsToPxOnY()
	
	private void testDrawRealPoints(Graphics2D g2) {
		List<Point2D> listRealPoints = new ArrayList<>();
		Point screenCenter, pointOnScreen;
		int xScreenCenter, yScreenCenter;
		
		listRealPoints.add( new Point2D(4.9, 1.5) );
		listRealPoints.add( new Point2D(5.6, 2.2) );
		listRealPoints.add( new Point2D(1.6, 0.2) );
		
		xScreenCenter = getWidth() / 2;
		yScreenCenter = getHeight() / 2;
		
		screenCenter = new Point(xScreenCenter, yScreenCenter);
		
		for(Point2D realPoint : listRealPoints) {
			pointOnScreen = MapperPoint2DToAWTPoint.mapearPoint2DToAWTPoint(realPoint, 
					screenCenter, 
					this.ratioX, 
					this.ratioY);
			
			drawPointOnScreen(pointOnScreen, 6, Color.RED, g2);
		} //--fin: FOR : Recorrido de lista de RealPoints
		
	} //--fin: testDrawRealPoints
	
} //--fin: CLASE : ScatterPlotKMeansGUI
