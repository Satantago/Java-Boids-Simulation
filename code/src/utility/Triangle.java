package utility;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Stroke;

import gui.GraphicalElement;

/**
 * Class of the graphhical element Triangle
 * 
 * @author Mohamed Errazki
 */
public class Triangle implements GraphicalElement {

	private Color drawColor;
	private Color fillColor;
	private int[] xPoints;
	private int[] yPoints;
	private static int nPoints = 3;

	/**
	 * Constructor
	 * 
	 * @param xPoints 	x coordinates of triangle vertices
	 * @param yPoints 	y coordinates of triangle vertices
	 * @param drawColor drawing color
	 * @param fillColor filling color
	 */
	public Triangle(int xPoints[], int yPoints[], Color drawColor, Color fillColor) {
		this.drawColor = drawColor;
		this.fillColor = fillColor;
		this.xPoints = xPoints;
		this.yPoints = yPoints;
	}


	/**
	 * Draws triangle in simulator g2d
	 * {@inheritDoc}
	 */
	public void paint(Graphics2D g2d) {
		Stroke currentStroke = g2d.getStroke();
		g2d.setStroke(new BasicStroke(2.0F));
		Color current = g2d.getColor();

		if (this.fillColor != null) {
			g2d.setColor(this.fillColor);
			g2d.fillPolygon(xPoints, yPoints, nPoints);
		}

		g2d.setColor(Color.WHITE);
		g2d.drawPolygon(xPoints, yPoints, nPoints);
		g2d.setColor(current);
		g2d.setStroke(currentStroke);
	}

	/**
	 * Display triangle.
	 * 
	 * @return textual representation of triangle
	 */
	public String toString() {
		return drawColor + " triangle";
	}
}
