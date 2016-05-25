/**SDLayer.java
 * 
 * 05/03/2015
 * 
 * SDLayer is the class used as a datatype for SimplyDrawn and
 * holds the ArrayList of points and the basic attributes of the line
 * size and color and the getter required by the paint functions 
 * of {@link edu.iupui.sw.simplydrawn.canvas.SDLayer}
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

import java.awt.Color;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;



public class SDLayer implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8141562834104755012L;
	
	private ArrayList<Point2D.Double> points = new ArrayList<Point2D.Double>();
	private float size;
	private Color c;
	/**
	 * Default constructor
	 * @param points, c, size
	 */
	public SDLayer(ArrayList<Point2D.Double> points, Color c, float size){		
		this.c = new Color(c.getRed(),c.getGreen(),c.getBlue());
		this.points = points;
		this.size = size;
	}
	/**
	 * Points Arraylist getter
	 * @returns points
	 */
	public ArrayList<Point2D.Double> getPoints(){
		return points;
	}
	/**
	 * Color getter
	 * @return c
	 */
	public Color getColor(){
		return c;
	}
	/**
	 * scale or size getter
	 * @return size
	 */
	public float getScale(){
		return size;
	}
}
