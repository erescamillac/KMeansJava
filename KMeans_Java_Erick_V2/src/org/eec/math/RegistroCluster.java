package org.eec.math;

public class RegistroCluster {
	
	private Point2D realPoint;
	private int cluster;
	
	public RegistroCluster(Point2D realPoint, int cluster) {
		super();
		this.realPoint = new Point2D( realPoint.getX(), realPoint.getY() );
		this.cluster = cluster;
	}

	public Point2D getRealPoint() {
		return realPoint;
	}

	public void setRealPoint(Point2D realPoint) {
		this.realPoint = realPoint;
	}

	public int getCluster() {
		return cluster;
	}

	public void setCluster(int cluster) {
		this.cluster = cluster;
	}

	@Override
	public String toString() {
		return "RegistroCluster [realPoint=" + realPoint + ", cluster=" + cluster + "]";
	}
	
} //-- fin : CLASE : RegistroCluster
