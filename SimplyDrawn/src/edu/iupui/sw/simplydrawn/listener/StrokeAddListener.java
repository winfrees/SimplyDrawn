/**StrokeAddListener.java
 * 
 * 05/03/2015
 * 
 * Currently used to signal the additon of a stroke
 * to {@link edu.iupui.sw.simplydrawn.gui.SDMenuBar} from
 * {@link edu.iupui.sw.simplydrawn.canvas.SDCanvas} to
 * control the undo accessibility.
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

public interface StrokeAddListener {
	
	public void onAddStroke(int stroke);
	
}
