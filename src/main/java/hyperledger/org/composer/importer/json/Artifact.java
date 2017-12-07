
package hyperledger.org.composer.importer.json;
/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Artifact {
	private String artifactName;
	
	private String className;
	private String extender;
	private  List<Element> elements;
	public static final Logger logger = LoggerFactory.getLogger(Artifact.class);
	
	public Artifact(String artifactName, String className){
		this.artifactName = artifactName;
		this.className = className;
		elements = new ArrayList<>();
	}
	public void addElement(Element e){	
		logger.trace("Element: {} added to Artifact: {} ", e.getName(), className );
		elements.add(e);
	}
	public String getArtifactName() {
		return artifactName;
	}
	
	public void setExtender(String extender) {
		this.extender = extender;
	}
	public  String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append('\n');
		sb.append(artifactName);
		sb.append(' ');
		sb.append(className);
		sb.append(' ');
		if(extender != null){
			sb.append("extends ");
			sb.append(extender);
			sb.append(' ');
		}
		sb.append('{');
		sb.append('\n');
		for(Element e:elements){
			sb.append(e.toString());
			sb.append('\n');
		}
		sb.append('}');
		return sb.toString();
	}
	public String getClassName() {
		return className;
	}
	public boolean equals(Artifact a ){
		return this.toString().equals(a.toString());
	}
}
