package org.eec.math;

import java.awt.Point;

public class MapperPoint2DToAWTPoint {

	public static Point mapearPoint2DToAWTPoint(Point2D realPoint, Point screenCenter, int ratioX, int ratioY) {
		Point point;
		int xScreen, yScreen;
		
		xScreen = 0;
		yScreen = 0;
		
		xScreen = (int) ( screenCenter.x + (ratioX * realPoint.getX()) );
		yScreen = (int) ( screenCenter.y + (ratioY * (-1.0) * realPoint.getY()) );
		
		point = new Point(xScreen, yScreen);
		return point;
	} //-- fin: mapearPoint2DToAWTPoint
	
} //--fin: CLASE : MapperPoint2DToAWTPoint
