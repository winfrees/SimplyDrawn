/**SDLog.java
 * 
 * 05/03/2015
 * 
 * Currently unused.
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

package edu.iupui.sw.simplydrawn.log;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

public class SDLog {
	
	private Path logPath;
	
	public SDLog(String path){
		try {
			Files.deleteIfExists(Paths.get(path));
			System.out.println("New log file does not exist.");
		} catch (IOException e1) {
			System.out.println("WARNING: Log file does not exist.");
			e1.printStackTrace();
		}	
		try {
			logPath = Files.createFile(Paths.get(path));
			System.out.println("Log file created.");
		} catch (IOException e) {
			System.out.println("ERROR: Log file could not be created.");
			e.printStackTrace();
		}		
	}
	
	public boolean writeln(String str){
		Date logtime = new Date(System.nanoTime());
		String line = logtime + ": " + str +"\n";
		
		DataOutputStream logOut;
		try {
			logOut = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File(logPath.toString()))));
			logOut.writeChars(line);
			logOut.close();
		} catch (IOException e) {
			System.out.println("ERROR: Log file could not be written to.");
			e.printStackTrace();
		}

		return true;
	}
}
