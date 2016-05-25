/**SDMenuBar.java
 * 
 * 05/03/2015
 *
 * SDMenubar extends a basic JMenuBar and organizes and relays
 * events to the appropriate listeners for basic file operation,
 * undo functionality and the about window.  It interacts bidirectionally
 * with {@link edu.iupui.sw.simplydrawn.canvas.SDCanvas}.
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

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import edu.iupui.sw.simplydrawn.SimplyDrawn;
import edu.iupui.sw.simplydrawn.listener.AppQuitListener;
import edu.iupui.sw.simplydrawn.listener.EditOperationListener;
import edu.iupui.sw.simplydrawn.listener.FileOperationListener;
import edu.iupui.sw.simplydrawn.listener.StrokeAddListener;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.ArrayList;

public class SDMenuBar extends JMenuBar implements Serializable, StrokeAddListener {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7792666253949171702L;
	
	private ArrayList<AppQuitListener> AppQuitListeners = new ArrayList<AppQuitListener>();
	private ArrayList<FileOperationListener> fileOperationListeners = new ArrayList<FileOperationListener>();
	private ArrayList<EditOperationListener> editOperationListeners = new ArrayList<EditOperationListener>();
	JMenuItem undo;
	
	/**
	 * Default Constructor
	 * @return c
	 */
	public SDMenuBar(){
		super();
		
		JMenu file = new JMenu("File");
		JMenu edit = new JMenu("Edit");
		//JMenu filters = new JMenu("Filters");
		JMenu help = new JMenu("Help");
		

		JMenuItem save = new JMenuItem("Save");
		save.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {	
				notifyFileOperationListener(e);
			}
        });
		JMenuItem export = new JMenuItem("Export");
		export.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {	
				notifyFileOperationListener(e);
			}
        });
		JMenuItem load = new JMenuItem("Open");
		load.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				notifyFileOperationListener(e);
				
			}
 
        });
		JMenuItem quit = new JMenuItem("Quit");
		quit.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				notifyAppQuitListener();
				System.exit(0);
			}
    
        });
		
		undo = new JMenuItem("Undo");
		undo.addActionListener(new java.awt.event.ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {	
				notifyEditOperationListener();
			}
        });
		
		undo.setEnabled(false);
		
		JMenuItem about = new JMenuItem("About");
		about.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
            	JFrame aboutFrame = new JFrame();
            	JPanel aboutPane = new JPanel();
            	aboutFrame.setTitle("About:" + SimplyDrawn.getAppName());
            	aboutFrame.setSize(500,100);
            	aboutFrame.setResizable(false);
            	aboutFrame.setContentPane(aboutPane);
            	aboutPane.setLayout(new FlowLayout());
            	aboutPane.add(new JLabel(SimplyDrawn.getAppName()));
            	aboutPane.add(new JLabel("ver. " + SimplyDrawn.getAppVersion()));
            	aboutPane.add(new JLabel("date: " + SimplyDrawn.getAppDate()));
            	aboutPane.add(new JLabel(SimplyDrawn.getAppNote()));
            	aboutFrame.setVisible(true);
            }
        });
		
		file.add(load);
		file.add(save);
		file.addSeparator();
		file.add(export);
		file.addSeparator();
		file.add(quit);	
		edit.add(undo);
		help.add(about);
		add(file);
		add(edit);
		//add(filters);
		add(help);		
	}
	
	/**
	 * AppQuit listener control
	 * 
	 */
	public void addAppQuitListener(AppQuitListener listener) {
		AppQuitListeners.add(listener);
    }

    private void notifyAppQuitListener() {
        for (AppQuitListener listener : AppQuitListeners) {	
        	listener.onQuit();
        }
    }
	/**
	 * EditOperation listener control
	 * 
	 */
    public void addEditOperationListener(EditOperationListener listener) {
    	editOperationListeners.add(listener);
    }

    private void notifyEditOperationListener() {
        for (EditOperationListener listener : editOperationListeners) {	
        	listener.onUndo();
        }
    }
	/**
	 * FileOperation listener control
	 * 
	 */
	public void addFileOperationListener(FileOperationListener listener) {
		fileOperationListeners.add(listener);
	   }

	private void notifyFileOperationListener(ActionEvent ae) {
	     for (FileOperationListener listener : fileOperationListeners) {
	    	 JMenuItem temp = (JMenuItem)(ae.getSource());
	    	 if(temp.getText().equals("Open")){
				try {
						int returnVal = listener.onFileOpen();
						
						if(returnVal == 1){
							//resetWindow();
						}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(this.getParent(),
					"File could not be opened...",
					SimplyDrawn.getAppName(),
					JOptionPane.WARNING_MESSAGE);
					}
					
	    	 } else if (temp.getText().equals("Save")){
				try {
					listener.onFileSave();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(this.getParent(),
					"File could not be saved...",
					SimplyDrawn.getAppName(),
					JOptionPane.WARNING_MESSAGE);
				}
			 } else if (temp.getText().equals("Export")){
				try {
					listener.onFileExport();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(this.getParent(),
					"File could not be exported...",
					SimplyDrawn.getAppName(),
					JOptionPane.WARNING_MESSAGE);
				}
			}
	    }
	}

	/**
	 * implementation of StrokeAddListener to
	 * control undo accesibility
	 * 
	 */
	@Override
	public void onAddStroke(int stroke) {
		if(stroke > 0){
			undo.setEnabled(true);
		}else{
			undo.setEnabled(false);
		}
	}
	
}
