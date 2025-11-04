package com.shosholanda.fc;

import java.util.Scanner;
import java.util.List;
import java.util.Random;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.Color;                    // For colors
import java.awt.Shape;                    // For Shape interface
import java.awt.geom.Ellipse2D;           // For ellipse shapes
import java.awt.geom.Rectangle2D;         // For rectangle shapes (optional)
import java.awt.geom.Path2D;              // For custom shapes (optional)
import java.awt.BasicStroke;              // For line thickness (optional)

import javax.swing.*;

/**
 * Práctica 5. Curvas elípticas.
 */
public class App {

    private final static int MAX = 1000;

    private static CurvaEliptica curvaRandom(){
	Random r = new Random();
	int a = r.nextInt(MAX);
	int b = r.nextInt(MAX);
	int p = 1;
	while (!Funciones.esPrimo(p))
	    p = r.nextInt(1000);
	return new CurvaEliptica(a, b, p);
    }
	
    
    public static void main(String[] args) {
	System.out.println("Vamos a ver unas curvas elípticas O.O");
	System.out.print("Generar curva random ? (y/n): ");

	Scanner teclado = new Scanner(System.in);
	String ans = teclado.nextLine();
	CurvaEliptica e;
	if (ans.equalsIgnoreCase("y"))
	    e = curvaRandom();
	else {
	    
	    System.out.print("Ingresa un int: ");
	    int a = teclado.nextInt();
	    System.out.print("Ingresa otro int: ");
	    int b = teclado.nextInt();
	    System.out.print("Ingresa un NÚMERO PRIMO: ");
	    int primo = teclado.nextInt();
	    e = new CurvaEliptica(a, b, primo);
	}

	XYSeries series = new XYSeries("Puntos generados", false);
	
	Punto G = getPrimerPunto(e); // 7u7
	List<Punto> puntos = e.genera(G);
	System.out.println("Lista De puntos generados por " + G.toString() + "\n"
			   + puntos);

	for (Punto p: puntos){
	    if (p != null)
		series.add(p.getX(), p.getY());
	}
        
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);

        JFreeChart chart = ChartFactory.
	    createXYLineChart("Curva Elíptica " + e.toString(), "X", "Y", dataset);

	XYPlot plot = chart.getXYPlot();
	XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();

	Shape largerDot = new Ellipse2D.Double(-4, -4, 8, 8); // 8x8
	// renderer.setSeriesLinesVisible(0, false); // <----------
	renderer.setSeriesPaint(0, Color.BLACK);
	renderer.setSeriesShape(0, largerDot);
	renderer.setSeriesShapesVisible(0, true);

	plot.setRenderer(renderer);

        ChartPanel panel = new ChartPanel(chart);
        JFrame frame = new JFrame();
        frame.setContentPane(panel);
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private static Punto getPrimerPunto(CurvaEliptica e){
	/* Este debería ser un método de CE lol*/
	int p = e.getPrimo();
	int a = e.getA();
	int b = e.getB();
	for (int x = 0; x < p; x++)
	    for (int y = 0; y < p; y++)
		if (y*y%p == (x*x*x + a*x + b)%p)
		    return new Punto(x, y);
	return null;
    }
}
