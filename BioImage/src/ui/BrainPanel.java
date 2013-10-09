package ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Line2D;
import java.util.HashMap;

import javax.swing.JPanel;

public class BrainPanel extends JPanel {
	private HashMap<Integer, Shape> con_shapes = new HashMap<Integer, Shape>();
	private HashMap<Integer, Shape> med_shapes = new HashMap<Integer, Shape>();
	private HashMap<Integer, Shape> cfs_shapes = new HashMap<Integer, Shape>();
	private Shape currentShape = null;
	
	private int consistencyCount;
	
	private static final int CONSISTENCY_THRESHOLD = 10;

	private int previousCon = 50;
	private int previousMed = 50;
	private int previousCfs = 98;
	private int previousX = 0;

	BrainPanel() {
		setPreferredSize(new Dimension(350, 100));
	}
	
	public void restart(){
		previousCon = 50;
		previousMed = 50;
		previousCfs = 98;
		previousX = 0;
		consistencyCount = 0;
		con_shapes.clear();
		med_shapes.clear();
		cfs_shapes.clear();
	}

	public void paintConcentration(int x, int y) {
		int currentX = x;
		int currentY = y;
		currentShape = new Line2D.Double(previousX * 1.0D, previousCon * 1.0D,
				currentX * 1.0D, currentY * 1.0D);
		if (!con_shapes.containsKey(currentX)) {
			con_shapes.put(currentX, currentShape);
			previousCon = currentY;
		}
	}

	public void paintMeditation(int x, int y) {
		int currentX = x;
		int currentY = y;
		currentShape = new Line2D.Double(previousX * 1.0D, previousMed * 1.0D,
				currentX * 1.0D, currentY * 1.0D);
		if (!med_shapes.containsKey(currentX)) {
			med_shapes.put(currentX, currentShape);
			previousMed = currentY;
		}
	}

	public void paintConfusion(int x, int y) {
		int currentX = x;
		int currentY = 0;
		if (y == 0)
			currentY = this.getHeight() - 2;
		else
			currentY = 2;
		if (currentY == previousCfs)
			consistencyCount ++;
		else if (consistencyCount < CONSISTENCY_THRESHOLD){
			currentY = previousCfs;
			consistencyCount ++;
		}
		else
			consistencyCount = 0;
		currentShape = new Line2D.Double(previousX * 1.0D, previousCfs * 1.0D,
				currentX * 1.0D, currentY * 1.0D);
		if (!cfs_shapes.containsKey(currentX)) {
			cfs_shapes.put(currentX, currentShape);
			previousCfs = currentY;
		}
	}

	public void paintLines(int x, int concentration, int meditation,
			int confusion) {
		paintConcentration(x, concentration);
		paintMeditation(x, meditation);
		paintConfusion(x, confusion);
		previousX = x;
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		int width = getWidth();
		int height = getHeight();

		Graphics2D g2d = (Graphics2D) g;

		float dash1[] = { 5.0f };
		g2d.setStroke(new BasicStroke(1.0f, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND, 10.0f, dash1, 0.0f));
		g2d.setPaint(Color.GRAY);
		for (int i = 0; i <= 4; i++) {
			g2d.draw(new Line2D.Double(0.0D, 0.25D * i * height,
					1.0D * (width - 1), 0.25D * i * height));
		}
		g2d.draw(new Line2D.Double(0.0D, 1.0D * (height - 1),
				1.0D * (width - 1), 1.0D * (height - 1)));
		for (int i = 0; i <= 10; i++) {
			g2d.draw(new Line2D.Double(0.1D * i * width, 0.0D,
					0.1D * i * width, 1.0D * (height - 1)));
		}
		g2d.draw(new Line2D.Double(1.0D * (width - 1), 0.0D,
				1.0D * (width - 1), 1.0D * (height - 1)));

		g2d.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND, 10.0f, null, 0.0f));

		g2d.setPaint(Color.BLUE);
		for (Shape shape : con_shapes.values()) {
			g2d.draw(shape);
		}
		g2d.setPaint(Color.GREEN);
		for (Shape shape : med_shapes.values()) {
			g2d.draw(shape);
		}

		g2d.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_ROUND, 10.0f, null, 0.0f));

		g2d.setPaint(Color.RED);
		for (Shape shape : cfs_shapes.values()) {
			g2d.draw(shape);
		}

	}

	public void setPreviousX(int x) {
		this.previousX = x;
	}
}
