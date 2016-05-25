/**SDConfig.java
 * 
 * 05/03/2015
 * 
 * Currently unused.  Would be used to allow extensibility of tools
 * and brushes.
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

package edu.iupui.sw.simplydrawn.config;

import java.awt.Dimension;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.nio.file.Path;

import edu.iupui.sw.simplydrawn.gui.SDMenuBar;
import edu.iupui.sw.simplydrawn.gui.SDToolBar;
import edu.iupui.sw.simplydrawn.listener.AppQuitListener;

public class SDConfig implements AppQuitListener {
	
	private SDToolBar toolBar;
	private SDMenuBar menuBar;
	private static int xSize = 1024;
	private static int ySize = 768;
	
	
	
	private Path SDPath;
	private InputStream is;
	
	
	public SDConfig(){
		
		if(!openConfigFile()){
			//createConfigFile();
			menuBar = new SDMenuBar();
			toolBar = new SDToolBar();
		}else{
			openConfigFile();	
			System.out.println("NOTICE: Config file openned.");
		}		
	}
	
	private boolean openConfigFile(){
		if(Files.exists(Paths.get(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + "config.conf"), LinkOption.NOFOLLOW_LINKS)){
		try {
			is = Files.newInputStream(Paths.get(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + "config.conf"));
			ObjectInputStream ois = new ObjectInputStream(is);
			toolBar = (SDToolBar) ois.readObject();
			menuBar = (SDMenuBar) ois.readObject();
		} catch (NoSuchFileException e) {	
			System.out.println("ERROR: File not found. Config file could not be openned.");
			e.printStackTrace();
			return false;
		} catch (ClassNotFoundException e) {
			System.out.println("ERROR: Class error. Config file could not be openned.");
			e.printStackTrace();
			return false;
		} catch (IOException e) {	
			System.out.println("ERROR: IO error. Config file could not be openned.");
			e.printStackTrace();
			return false;
		}
		}
		return false;
	}
	
	private boolean createConfigFile(){
		try {
			SDPath = Files.createFile(Paths.get(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath() + "config.conf"));
		    FileOutputStream fos = new FileOutputStream(SDPath.toFile());
		    ObjectOutputStream oos = new ObjectOutputStream(fos); 
		    oos.writeObject(toolBar);
		    oos.writeObject(menuBar);
		    oos.close();
			return true;
		} catch (IOException e) {
			System.out.println("ERROR: Config file could not be created.");
			e.printStackTrace();
			return false;
		}
	}
	
	public SDToolBar getSDToolBar(){
		return toolBar;
	}
	
	public SDMenuBar getSDMenuBar(){
		return menuBar;
	}
	
	public Path getSDPath(){
		return SDPath;
	}
	
	public static Dimension getCanvasSize(){
		return new Dimension(xSize, ySize);
	}
	
	
	public void writeConfig(){
		createConfigFile();
	}

	@Override
	public void onQuit() {
		writeConfig();
	}

}
