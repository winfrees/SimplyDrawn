/**SDToolBar.java
 * 
 * 05/03/2015
 *
 * SDToolBar extends a basic JToolBar and organizes and relays
 * events to the appropriate listeners for tool selection, line size
 * and color selections. It interacts with {@link edu.iupui.sw.simplydrawn.canvas.SDCanvas}.
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
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JToolBar;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import edu.iupui.sw.simplydrawn.listener.ToolListener;

public class SDToolBar extends JToolBar implements Serializable, ChangeListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5217863907803064142L;

	private ArrayList<ToolListener> ToolListeners = new ArrayList<ToolListener>();
	
	private JComboBox<String> sizeComboBox = new JComboBox<String>();
	private JComboBox<String> texturedComboBox = new JComboBox<String>();
	private String[] sizeValues = {"1 pixel","2 pixels","3 pixels","4 pixels","5 pixels","6 pixels","7 pixels","8 pixels","9 pixels","10 pixels"};
	private String[] textureValues = {"smooth","medium","rough"};
	private SDToggleButton pencil = new SDToggleButton("Pencil");
	private SDToggleButton eraser = new SDToggleButton("Eraser");
	private SDColorButton color = new SDColorButton("Color");
	
	/**
	 * Default Constructor
	 */
	public SDToolBar(){
		super();
		sizeComboBox.setModel(new DefaultComboBoxModel<String>(sizeValues));
		sizeComboBox.addActionListener(new ActionListener(){
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent e) {
				notifyToolListenersScale(((JComboBox<String>)e.getSource()).getSelectedIndex());
			}	
		});
		
		texturedComboBox.setModel(new DefaultComboBoxModel<String>(textureValues));
		texturedComboBox.addActionListener(new ActionListener(){
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent e) {
				notifyToolListenersTexture(((JComboBox<String>)e.getSource()).getSelectedIndex());
			}	
		});

		pencil.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
					notifyToolListenersPencil(color.getColor());
					eraser.setSelected(false);
					if(!pencil.isSelected()){
						notifyToolListenersOff();
					}
			}	
		});
		
		eraser.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
					notifyToolListenersEraser();
					pencil.setSelected(false);
					if(!eraser.isSelected()){
						notifyToolListenersOff();
					}
			}
		});
		
		color.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				color.getColorChooser();
				notifyToolListenersColor(color.getColor());
			}	
		});	
		
		color.addChangeListener(this);
		
		setRollover(true);
		setName("ToolBar");		
		setMargin(new Insets(0,0,0,0));
		
		setLayout(new FlowLayout(FlowLayout.LEFT, 0,0));
		
		pencil.setEnabled(true);
		
		add(pencil);
		add(eraser);	
		add(sizeComboBox);
		add(color);	
		//add(texturedComboBox);
		addSeparator(new Dimension(3,40));
 
	}
	/**
	 * organizes ToolListeners
	 */
	public void addToolListener(ToolListener listener) {
		ToolListeners.add(listener);
    }

    private void notifyToolListenersScale(float scale) {
        for (ToolListener listener : ToolListeners) {	
        	listener.newScale(scale);
        }
    }
    
    private void notifyToolListenersTexture(int texture) {
        for (ToolListener listener : ToolListeners) {	
        	listener.newTexture(texture);
        }
    }
    
    private void notifyToolListenersColor(Color c) {
    	if(!eraser.isSelected()){
    		for (ToolListener listener : ToolListeners) {	
    			listener.newColor(color.getColor());
    		}
    	}
    }
    
    private void notifyToolListenersPencil(Color c) {
        for (ToolListener listener : ToolListeners) {	
        	listener.selectPencil(c, pencil.isEnabled());
        }
    }
    
    private void notifyToolListenersEraser() {
        for (ToolListener listener : ToolListeners) {	
        	listener.selectEraser(eraser.isEnabled());
        }
    }
    
    private void notifyToolListenersOff() {
        for (ToolListener listener : ToolListeners) {	
        	listener.noTools();
        }
    }
	/**
	 * facilitates color selection change relaying
	 * from {@link edu.iupui.sw.simplydrawn.gui.SDToolBar}
	 * to {@link edu.iupui.sw.simplydrawn.canvas.SDCanvas}.
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		notifyToolListenersColor(color.getColor());
	}
}
