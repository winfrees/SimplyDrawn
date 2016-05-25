/**SDCanvas.java
 * 
 * 05/03/2015
 * 
 * SDCanvas provides the canvas on which the painting occurs, the
 * methods for manipulating a Graphics object and the methods for
 * loading, saving and exporting files.  It works in conjunction with
 * the class {@link edu.iupui.sw.simplydrawn.canvas.SDLayer},and implements
 * a number of listeners to interface with gui components and 
 * facilitate drawing.  Please see the respective interface files.
 * 
 * Drawing is achieved by stringing points together and forming a path
 * with color and size maintained in multiple objects of
 * {@link edu.iupui.sw.simplydrawn.canvas.SDLayer} that are organized
 * into an ArrayList.  This ArrayList is saved in the native 
 * filetype.  The path in the graphics object is currently created with
 * a Path.toCurve with a non-overlapping 4 point window. Current strings
 * of points are maintained in a separate ArrayList.  
 * 
 * File opening and saving is derived from the "ASCII MovieMaker" project.
 * This includes the methods here and the interface, FileOperationListener.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *    http://www.apache.org/licenses/LICENSE-2.0
 *    
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *    
 * 
 * 
 * @author Seth Winfree
 * 
 * @version 0.1
 * 
 */
package edu.iupui.sw.simplydrawn.canvas;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.ListIterator;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

import edu.iupui.sw.simplydrawn.SimplyDrawn;
import edu.iupui.sw.simplydrawn.listener.EditOperationListener;
import edu.iupui.sw.simplydrawn.listener.FileOperationListener;
import edu.iupui.sw.simplydrawn.listener.StrokeAddListener;
import edu.iupui.sw.simplydrawn.listener.ToolListener;

@SuppressWarnings("serial")
public class SDCanvas extends JPanel implements java.awt.event.MouseMotionListener, MouseListener, ToolListener, FileOperationListener, EditOperationListener {

	private boolean paint = false;
	private ArrayList<Point2D.Double> points = new ArrayList<Point2D.Double>();
	private ArrayList<SDLayer> strokes = new ArrayList<SDLayer>();
	
	private ArrayList<StrokeAddListener> strokeAddListeners = new ArrayList<StrokeAddListener>();

	private Color c = Color.BLACK;
	
	private float scale = 1;
	private Point2D.Double start = new Point2D.Double(0, 0);
	private Point2D.Double stop = new Point2D.Double(0, 0);

	private int[] interpolateStep = { 3, 3, 3, 3 };
	/**
	 * Default constructor
	 *
	 */
	public SDCanvas() {
		super();
		addMouseMotionListener(this);
		addMouseListener(this);
		setSize(SimplyDrawn.getPreferredSize());
		setBackground(Color.WHITE);
		setVisible(true);
	}
	/**
	 * method to link to gui components
	 * @return preferredSize
	 */
	public void addStrokeAddListener(StrokeAddListener listener) {
		strokeAddListeners.add(listener);
    }

	//method for copying an ArrayList
	private ArrayList<Point2D.Double> copyPointsList(
			ArrayList<Point2D.Double> al) {
		ArrayList<Point2D.Double> destination = new ArrayList<Point2D.Double>();
		ListIterator<Point2D.Double> source = al.listIterator();
		while (source.hasNext()) {
			destination.add(source.next());
		}
		return destination;
	}

	//method use for smoothing via point interpolation by 
	//the path method curveTo that uses a Bezier fit
	private ArrayList<Path2D.Float> interpolatePoints(
			ArrayList<Point2D.Double> al, int[] steps) {

		int p0Pos = 0;
		int p1Pos = 1;
		int p2Pos = 2;
		int p3Pos = 3;

		ArrayList<Path2D.Float> paths = new ArrayList<Path2D.Float>();

		while (p3Pos < al.size()) {
			Path2D.Float smooth = new Path2D.Float();
			smooth.moveTo(al.get(p0Pos).x, al.get(p0Pos).y);
			Point2D.Double p1 = al.get(p1Pos);
			Point2D.Double p2 = al.get(p2Pos);
			Point2D.Double p3 = al.get(p3Pos);
			smooth.curveTo(p1.getX(), p1.getY(), p2.getX(), p2.getY(),
					p3.getX(), p3.getY());
			paths.add(smooth);
			p0Pos += steps[0];
			p1Pos += steps[1];
			p2Pos += steps[2];
			p3Pos += steps[3];
		}
		return paths;
	}
	
	//methods from the MouseMotionListener and MouseListener
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	/**
	 * method that signals the points to add
	 * 
	 */
	@Override
	public void mouseDragged(MouseEvent e) {
		if (paint) {
			points.add(new Point2D.Double(e.getX(), e.getY()));
			notifyStrokeAddListener();
			repaint();
		}
	}

    @Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	/**
	 * signals the start of a stroke
	 * 
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		start = new Point2D.Double(e.getX(), e.getY());
		stop = new Point2D.Double(e.getX(), e.getY());

	}
	/**
	 * signals the end of a stroke
	 * 
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		strokes.add(new SDLayer(copyPointsList(points), c, scale));
		start = new Point2D.Double(0, 0);
		stop = new Point2D.Double(0, 0);
		points.clear();
		repaint();
		notifyStrokeAddListener();
	}
	
	//ToolListener implementation for communication
	//with the toolbar
	/**
	 * signals a new color selection
	 * 
	 */
	@Override
	public void newColor(Color c) {
		this.c = c;
	}
	/**
	 * signals a new scale, tool size
	 * 
	 */
	@Override
	public void newScale(float scale) {
		this.scale = scale;
	}

	//not implemented yet
	@Override
	public void newTexture(int texture) {
		switch(texture){
		case 1:
			interpolateStep[0] = 3;
			interpolateStep[1] = 3;
			interpolateStep[2] = 3;
			interpolateStep[3] = 3;
			break;
		case 2:
			interpolateStep[0] = 2;
			interpolateStep[1] = 2;
			interpolateStep[2] = 2;
			interpolateStep[3] = 2;
			break;
		case 3:
			interpolateStep[0] = 3;
			interpolateStep[1] = 3;
			interpolateStep[2] = 2;
			interpolateStep[3] = 1;
			break;
		default:
			break;
		}
		
	}

	private void notifyStrokeAddListener() {
        for (StrokeAddListener listener : strokeAddListeners) {	
        	listener.onAddStroke(strokes.size());
        }
    }
	/**
	 * signals no tool selected
	 * 
	 */
	@Override
	public void noTools() {
		paint = false;
	}
	/**
	 * handles request for file export
	 * 
	 */
	@Override
	public void onFileExport() throws Exception {
		System.out.println("Export!");
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("Export...");
	    chooser.showSaveDialog(this);    
	    BufferedImage image = new BufferedImage(SimplyDrawn.getPreferredSize().width,SimplyDrawn.getPreferredSize().height,BufferedImage.TYPE_INT_RGB);
	    Graphics2D g2 = (Graphics2D)image.getGraphics();
		g2.setStroke(new BasicStroke(scale, BasicStroke.CAP_ROUND,BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
	    g2.setBackground(this.getBackground());
	    g2.clearRect(0, 0, SimplyDrawn.getPreferredSize().width,SimplyDrawn.getPreferredSize().height);
	    paintExistingStrokes(g2); 
	    File file = chooser.getSelectedFile(); 
	    ImageIO.write(image, "PNG", file);
	    repaint();
	}
	/**
	 * handles request for file open
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int onFileOpen() throws Exception{
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("Simply Drawn files, .sdf", "sdf");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(this);	
		if(returnVal == JFileChooser.APPROVE_OPTION){
			File file = chooser.getSelectedFile();
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			Object temp = ois.readObject();
			strokes.clear();
			strokes = (ArrayList<SDLayer>)temp;
			repaint();
			ois.close();
			notifyStrokeAddListener();
			return 1;
		}else{
			repaint();
			notifyStrokeAddListener();
			return -1;
		}
	}
	/**
	 * handles request for file save
	 * 
	 */
	@Override
	public void onFileSave() throws Exception{
	    JFileChooser chooser = new JFileChooser();
	    chooser.showSaveDialog(this);	
	    File file = chooser.getSelectedFile();
	    FileOutputStream fos = new FileOutputStream(file);
	    ObjectOutputStream oos = new ObjectOutputStream(fos);
	    oos.writeObject(strokes);
	    oos.close();
	    repaint();
	}
	/**
	 * EditOperationListener implementation
	 * 
	 */
	@Override
	public void onUndo() {
		if(strokes.size() > 0){
		strokes.remove(strokes.size()-1);
		repaint();
		notifyStrokeAddListener();
		}
		
	}
	/**
	 * paint function used to generate the main graphic
	 * 
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(c);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		paintExistingStrokes(g2);
		paintRoutine(g2, this.points, this.scale, this.c);
	}
	
	//paints existing SDLayer object data
	private void paintExistingStrokes(Graphics2D g2) {
		ListIterator<SDLayer> itr = strokes.listIterator();

		while (itr.hasNext()) {
			SDLayer s = itr.next();
			paintRoutine(g2, s.getPoints(), s.getScale(), s.getColor());
		}
	}
	//common graphics handling used by both tools and when
	//painting existing strokes
	private void paintRoutine(Graphics2D g2,
			ArrayList<Point2D.Double> pointsToPlot, float scale, Color c) {
		g2.setColor(c);
		stop.x = -1;
		stop.y = -1;
		start.x = -1;
		start.y = -1;
		if (pointsToPlot.size() < 3) {
			ListIterator<Point2D.Double> itr = pointsToPlot.listIterator();
			g2.setStroke(new BasicStroke(scale, BasicStroke.CAP_ROUND,
					BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
			while (itr.hasNext()) {
				Point2D.Double p = itr.next();
				start.x = p.x;
				start.y = p.y;
				if (stop.x >= 0 && stop.y >= 0) {
					g2.draw(new Line2D.Float(start, stop));
				}
				stop.x = start.x;
				stop.y = start.y;
			}
		} else if (pointsToPlot.size() >= 3) {
			ArrayList<Path2D.Float> paths = interpolatePoints(pointsToPlot,interpolateStep);
			ListIterator<Path2D.Float> itr2 = paths.listIterator();
			while (itr2.hasNext()) {
				Path2D.Float path = itr2.next();
				g2.setColor(c);
				g2.setStroke(new BasicStroke(scale, BasicStroke.CAP_ROUND,
						BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
				g2.draw(path);
			}
		} else {
		}
	}
	/**
	 * ToolListener implementation for Eraser
	 * 
	 */
	@Override
	public void selectEraser(boolean active) {
		paint = active;
		this.c = this.getBackground();
	}
	/**
	 * ToolListener implementation for Pencil
	 * 
	 */
	@Override
	public void selectPencil(Color c, boolean active) {
		paint = active;
		this.c = c;
	}


}
