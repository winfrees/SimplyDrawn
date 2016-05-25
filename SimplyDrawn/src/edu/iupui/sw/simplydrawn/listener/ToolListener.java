/**ToolListener.java
 * 
 * 05/03/2015
 * 
 * Currently used to signal tool selection between 
 * to {@link edu.iupui.sw.simplydrawn.gui.SDMenuBar} and
 * {@link edu.iupui.sw.simplydrawn.canvas.SDCanvas}.
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

package edu.iupui.sw.simplydrawn.listener;

import java.awt.Color;


public interface ToolListener {
	
	public void newScale(float scale);
	
	public void newTexture(int texture);
	
	public void selectPencil(Color c, boolean active);
	
	public void newColor(Color c);
	
	public void selectEraser(boolean active);
	
	public void noTools();

}
