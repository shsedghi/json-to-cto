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
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CtoContainer {
	public static final Logger logger = LoggerFactory.getLogger(CtoContainer.class);
	private HashMap<String , Artifact> map;
	private Set<String> imports;
	private String nameSpace;
	
	public CtoContainer(String nameSpace){
		imports = new HashSet<>();
		map = new LinkedHashMap<>();
		this.nameSpace = nameSpace;
	}
	
	public void addImpoert(String importStr){
		imports.add(importStr);
	}
	public void add(Artifact artifact){
		String artifactClassName = artifact.getClassName();
		logger.trace("Artifact: {} added to the containr", artifactClassName);
		Artifact stored = map.get(artifactClassName);
		if(stored == null || !stored.equals(artifact) )
			map.put(artifactClassName, artifact);
	}
	public int getSize(){
		return map.size();
	}
	
	public String toString(){
		StringBuilder sb = new StringBuilder("\n\n");
		sb.append("namespace "+ nameSpace);
		sb.append("\n\n");
		for(String importStr: imports){
			sb.append("import "+ importStr);
			sb.append('\n');
		}
		sb.append('\n');
		for(String artifactClassName: map.keySet()){
			sb.append(map.get(artifactClassName).toString());
			sb.append('\n');
		}
		return sb.toString();
	}

}
