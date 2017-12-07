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

import java.nio.file.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CtoUtils {

	public static final Logger logger = LoggerFactory.getLogger(CtoUtils.class);


	public  static String capitalize(String token){
		return(token.substring(0, 1).toUpperCase()+token.substring(1));
	}

	public  static String deCapitalize(String token){
		return(token.substring(0, 1).toLowerCase()+token.substring(1));
	}


	public  static String JSonToCtoType(String longType){
		String type = longType.substring(longType.lastIndexOf('.')+1);
		String ctoType;
		switch (type) {
		case "BigIntegerNode":
			ctoType = "Long";		
			break;
		case "LongNode":
			ctoType = "Long";		
			break;
		case "DoubleNode":
			ctoType = "Double";		
			break;
		case "IntNode":
			ctoType = "Integer";
			break;
		case "DecimalNode":
			ctoType = "String"; // No Decimal type in CTO, This may not be desired
			break;
		case "BinaryNode":
			ctoType = "String";	 // No Decimal type in CTO, needs to be processed in the script
			break;
		case "BooleanNode":
			ctoType = "Boolean";
			break;
		case "NullNode":
			ctoType = "String"; // This may not be desired
			break;	
		case "TextNode":
			ctoType = "String";
			break;
		default:
			ctoType = type;
			break;
		}
		return ctoType;
	}
	public static String getFileExtension(Path path){
		String fileNameWithExt = path.getName(path.getNameCount()-1).toString();
		int index = fileNameWithExt.lastIndexOf('.');
		if(index != -1)
			return fileNameWithExt.substring(index + 1);
		else 
			return null;
	}
	public static String getFileName(Path path){
		String fileNameWithExt = path.getName(path.getNameCount()-1).toString();
		int index = fileNameWithExt.lastIndexOf('.');
		if(index != -1)
			return fileNameWithExt.substring(0, index);
		else 
			return fileNameWithExt;
	}
	public static String getFullFileName(Path path){
		String fileNameWithExt = path.getName(path.getNameCount()-1).toString();
		return fileNameWithExt;
	}
	public static String getBaseNameSpace(String ns){
		return ns.substring("http://".length(), ns.lastIndexOf('/')).replace("/json-schema", "").replace('/', '.');
	}
	public static String getNameSpace(String ns){		
		String nameSpace = null;
		if(ns == null){
			logger.debug("ns is null");
			nameSpace = "";
		}else if(ns.startsWith("http://"))
			nameSpace= ns.substring("http://".length()).replace('/', '.');

		else if(ns.startsWith("urn:")){
			nameSpace= ns.substring("urn:".length()).replace(':', '.');

		}else
			nameSpace = "";
		logger.debug("received namespace: {}, returning namespace: {}", ns, nameSpace );
		return nameSpace;
	}

}
