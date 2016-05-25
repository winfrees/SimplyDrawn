/**Simply Drawn.java
 * 
 * A simple drawing program for sketching doodles that can save,
 * load and export files.
 * 
 * 05/03/2015
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
 * Basic design: The class SimplyDrawn organizes and initiates the 
 * base classes and gui for the application SimplyDrawn via the private method 
 * InitGui().  Objects created include the toolbar 
 * {@link edu.iupui.sw.simplydrawn.gui.serialized.SDToolBar}, menus 
 * {@link edu.iupui.sw.simplydrawn.gui.serialized.SDMenuBar}, main window 
 * {@link edu.iupui.sw.simplydrawn.canvas.SDCanvasMainFrame} and the canvas 
 * space for drawing {@link edu.iupui.sw.simplydrawn.canvas.SDCanvas}. Drawing
 * and file activities are managed by {@link edu.iupui.sw.simplydrawn.canvas.SDCanvas} 
 * to simplify the management of painted graphics and the datatypes used in saved files.  
 * 
 * Basic use: The interface provides two basic tools, a pencil and eraser.
 * Each tool is selected from the toolbar. Nothing can be drawn or erased without
 * a button selected. The size of the pencil tool or erase tool is adjusted with
 * the pop down menu.  Color can be selected for the pencil tool using the last 
 * button.  The current color is given as a rectangle in the button.  Selecting 
 * the color button opens a color palette for selecting a desired color in a 
 * number of color spaces.  An unlimited number of undos is provided for every
 * image-included files opened from the native file type.  This applies to both
 * pencil and eraser tools.
 * 
 * Files may be saved with the unregistered file type of .sdf.  Via an export 
 * mechanism .png files can be created from the open document.  .sdf files can
 * be opened directly.  
 * 
 * Future features: 
 * 	1) Implement better extensibility across function
 * 	2) Open additional file type for editting.
 * 	3) Expand tools to include selection mechanisms
 * 	4) Expand options for brushes through an extensible class loading mechanism
 * 	5) Add filter mechanisms for basic kernel based processing
 * 
 * 
 * 
 * @author Seth Winfree
 * 
 * @version 0.1
 * 
 */

package edu.iupui.sw.simplydrawn;

import java.awt.BorderLayout;
import java.awt.Dimension;

import edu.iupui.sw.simplydrawn.canvas.SDCanvas;
import edu.iupui.sw.simplydrawn.canvas.SDCanvasMainFrame;
import edu.iupui.sw.simplydrawn.config.SDConfig;
import edu.iupui.sw.simplydrawn.gui.SDMenuBar;
import edu.iupui.sw.simplydrawn.gui.SDToolBar;


public class SimplyDrawn {

	private SDCanvasMainFrame sdCanvasMain;
	private SDToolBar sdToolBar;
	private SDMenuBar sdMenuBar;
	private SDCanvas canvas = new SDCanvas();
	private SDConfig sdConfig;
	private static final Dimension preferredSize = new Dimension(1024, 768);
	private static final String appName = "Simply Drawn";
	private static final String appVersion = "0.1";
	private static final String appDate = "05/02/15";
	private static final String appNote = "Small application for drawing.";
	/**
	 * static method to return application publication date
	 */
	public static String getAppDate() {
		return appDate;
	}
	/**
	 * static method to return application name
	 * @return appName
	 */
	public static String getAppName() {
		return appName;
	}
	/**
	 * static method to return application note
	 * @return appNote
	 */
	public static String getAppNote() {
		return appNote;
	}
	/**
	 * static method to return application version
	 * @return appVersion
	 */
	public static String getAppVersion() {
		return appVersion;
	}
	/**
	 * static method to return application size
	 * @return preferredSize
	 */
	public static Dimension getPreferredSize() {
		return preferredSize;
	}
	/**
	 * main function
	 */
	public static void main(String[] args) {

		@SuppressWarnings("unused")
		SimplyDrawn app = new SimplyDrawn();

	}
	/**
	 * Default constructor
	 */

	public SimplyDrawn() {
		 InitGui();
	}

	private void InitGui(){
		sdConfig = new SDConfig();
		sdToolBar = sdConfig.getSDToolBar();
		sdToolBar.addToolListener(canvas);
		sdMenuBar = sdConfig.getSDMenuBar();
		sdMenuBar.addFileOperationListener(canvas);
		sdMenuBar.addEditOperationListener(canvas);
		canvas.addStrokeAddListener(sdMenuBar);
		sdCanvasMain = new SDCanvasMainFrame();
		sdCanvasMain.add(sdToolBar, BorderLayout.NORTH);
		sdCanvasMain.setJMenuBar(sdMenuBar);
		sdCanvasMain.add(canvas, BorderLayout.CENTER);
		sdCanvasMain.setVisible(true);
	}

}
