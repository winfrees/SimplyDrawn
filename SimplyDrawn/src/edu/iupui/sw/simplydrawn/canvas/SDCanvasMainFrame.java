/**SDCanvasMainFrame.java
 * 
 * 05/03/2015
 * 
 * SDCanvasMainFrame provides the window in which all the gui
 * components are found or originate.  Simple Jframe based class.
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

import javax.swing.JFrame;

import edu.iupui.sw.simplydrawn.SimplyDrawn;


@SuppressWarnings("serial")
public class SDCanvasMainFrame extends JFrame {

	public SDCanvasMainFrame(){
		super();
		setTitle("Simply Drawn");
		setSize(SimplyDrawn.getPreferredSize());
		setResizable(false);
	}

}
