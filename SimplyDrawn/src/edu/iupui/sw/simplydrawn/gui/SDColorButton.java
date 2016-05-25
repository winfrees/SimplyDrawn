/**SDColorButton.java
 * 
 * 05/03/2015
 *
 * SDColorButton extends a basic JButton and controls the selection
 * of colors with the JColorChooser using a ChangeListener mechanism.
 * Objects of this class are listened to by a 
 * {@link edu.iupui.sw.simplydrawn.SDMenuBar} to pass
 * color changes to {@link edu.iupui.sw.simplydrawn.canvas.SDCanvas}.
 * 
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

package edu.iupui.sw.simplydrawn.gui;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class SDColorButton extends JButton implements ChangeListener {
	
	private Color c = Color.BLACK;
	private JPanel colorIndicator = new JPanel();
	private JFrame colorChooser = new JFrame();
	private JColorChooser jcc = new JColorChooser();
	
	
	/**
	 * Constructor
	 * @param str
	 */
	
	public SDColorButton(String str){
		super();
		setText(str);
	
		colorIndicator.setSize(new Dimension(30, 30));
		colorIndicator.setBackground(c);
		colorIndicator.setVisible(true);
		add(colorIndicator);
		add(new JLabel("Color"));
		
		
		jcc.getSelectionModel().addChangeListener(this);
		jcc.setColor(c);
		
		colorChooser.setTitle("Simply Drawn-Color Chooser");
		colorChooser.setSize(new Dimension(500,300));
		colorChooser.getContentPane().add(jcc);
		colorChooser.setVisible(false);
	}

	/**
	 * getter for ColorChooser
	 * 
	 */
	public void getColorChooser(){
		colorChooser.setVisible(true);
	}
	/**
	 * getter for color
	 * @return c
	 */
	public Color getColor(){
		return c;
	}

	/**
	 * state change and relay
	 * 
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		 this.c = jcc.getColor();
		 colorIndicator.setBackground(this.c);
		 this.fireStateChanged();
	}
}
