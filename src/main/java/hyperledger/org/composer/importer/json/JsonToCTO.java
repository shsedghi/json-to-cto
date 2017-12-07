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
package hyperledger.org.composer.importer.json;

import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class JsonToCTO {
	private static final Charset UTF8 = StandardCharsets.UTF_8;
	private static CtoContainer container ;
	public static final Logger logger = LoggerFactory.getLogger(JsonToCTO.class);

	public void buildCto(Path jsonFile, String ctoIdentifier) throws Exception{
			logger.info("Reading JSON file: {}", jsonFile.toString());
			Reader reader = Files.newBufferedReader(jsonFile, UTF8);
			ObjectMapper mapper = new ObjectMapper();
			container = new CtoContainer(null);
			
			JsonNode rootNode = mapper.readValue(reader, JsonNode.class);
			Artifact	artifact = new Artifact("concept", "root_"+ctoIdentifier);
			container.add(artifact);
			inspect(container, artifact, rootNode, ctoIdentifier);
			Path ctoFile = jsonFile.getParent().resolve(ctoIdentifier+".cto");
			Writer writer = Files.newBufferedWriter(ctoFile, UTF8, StandardOpenOption.CREATE);
			logger.info("Writing  CTO file: {}", ctoFile.toString());
			logger.debug(container.toString());
			writer.write(container.toString());
			writer.close();
	}
	private  void inspect(CtoContainer container, Artifact artifact, JsonNode node, String name)throws Exception{
		if(node == null)
			return;
		Element element;
		String className;
		if (node instanceof ObjectNode){
			// here we add the element
			element = new Element();
			element.setName(name);
			className = CtoUtils.capitalize(name);
			element.setType(className);
			artifact.addElement(element);
			artifact = new Artifact("concept", className);
			container.add(artifact);
			inspectObject(container,artifact, (ObjectNode)node, name);
		}else if(node instanceof ArrayNode){
			logger.trace("Strat Array: {} ", name);
			node = ((ArrayNode)node).get(0);
			if(node == null)
				return;
			element = new Element();
			element.setName(CtoUtils.deCapitalize(name));
			element.setArray(true);
			if(node instanceof ObjectNode){
				className = CtoUtils.capitalize(name);
				element.setType(className);
				//element.setPointer(true); // only is valid if we implement other Artifcats
				artifact.addElement(element);
				artifact = new Artifact("concept", className);
				container.add(artifact);
				inspectObject(container,artifact, (ObjectNode)node, name);
			}else{
				String type = CtoUtils.JSonToCtoType(node.getClass().getName());
				element.setType(type);
				artifact.addElement(element);
			}
			logger.trace("End Array: {} ", name);
		}else{
			String type = CtoUtils.JSonToCtoType(node.getClass().getName());
			element = new Element();
			element.setName(CtoUtils.deCapitalize(name));
			element.setType(type);
			artifact.addElement(element);
		}

	}
	private   void inspectObject(CtoContainer container, Artifact artifact, ObjectNode object, String name) throws Exception{
		logger.trace("Strat Object: {} ", name);

			 
		for(Iterator<String> nodes= object.fieldNames(); nodes.hasNext();){
			String fieldName = nodes.next();
			inspect(container,artifact, object.get(fieldName),fieldName);
		}
		
		logger.trace("End Object: {}",name);
	}
}

