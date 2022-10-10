package org.eec.configuration;

public class GraphicsConfiguration {

	private double maxRealValueX;
	private double maxRealValueY;
	private double tamanioRealPasoX;
	private double tamanioRealPasoY;
	private int marginPx;
	private int alturaDeMarcador;
	private int paddingLabelOnY;
	private int paddingLabelOnX;
	
	private int pointWidth;

	public GraphicsConfiguration() {
		super();
		this.maxRealValueX = 3.0;
		this.maxRealValueY = 2.5;
		this.tamanioRealPasoX = 0.2;
		this.tamanioRealPasoY = 0.1;
		this.marginPx = 30;
		this.alturaDeMarcador = 10;
		this.paddingLabelOnX = (int)((1.0/3.0)*this.alturaDeMarcador);
		this.paddingLabelOnY = (int)((1.0/3.0)*this.alturaDeMarcador);
		this.pointWidth = 4;
	} //--fin: Constructor
	
	

	public int getPointWidth() {
		return pointWidth;
	}



	public void setPointWidth(int pointWidth) {
		this.pointWidth = pointWidth;
	}



	public double getMaxRealValueX() {
		return maxRealValueX;
	}

	public void setMaxRealValueX(double maxRealValueX) {
		this.maxRealValueX = maxRealValueX;
	}

	public double getMaxRealValueY() {
		return maxRealValueY;
	}

	public void setMaxRealValueY(double maxRealValueY) {
		this.maxRealValueY = maxRealValueY;
	}

	public double getTamanioRealPasoX() {
		return tamanioRealPasoX;
	}

	public void setTamanioRealPasoX(double tamanioRealPasoX) {
		this.tamanioRealPasoX = tamanioRealPasoX;
	}

	public double getTamanioRealPasoY() {
		return tamanioRealPasoY;
	}

	public void setTamanioRealPasoY(double tamanioRealPasoY) {
		this.tamanioRealPasoY = tamanioRealPasoY;
	}

	public int getMarginPx() {
		return marginPx;
	}

	public void setMarginPx(int marginPx) {
		this.marginPx = marginPx;
	}

	public int getAlturaDeMarcador() {
		return alturaDeMarcador;
	}

	public void setAlturaDeMarcador(int alturaDeMarcador) {
		this.alturaDeMarcador = alturaDeMarcador;
		this.paddingLabelOnX = (int)((1.0/3.0)*this.alturaDeMarcador);
		this.paddingLabelOnY = (int)((1.0/3.0)*this.alturaDeMarcador);
	}

	public int getPaddingLabelOnY() {
		return paddingLabelOnY;
	}

	public void setPaddingLabelOnY(int paddingLabelOnY) {
		this.paddingLabelOnY = paddingLabelOnY;
	}

	public int getPaddingLabelOnX() {
		return paddingLabelOnX;
	}

	public void setPaddingLabelOnX(int paddingLabelOnX) {
		this.paddingLabelOnX = paddingLabelOnX;
	}
	
	
	
} //-- fin : CLASE : GraphicsConfiguration
