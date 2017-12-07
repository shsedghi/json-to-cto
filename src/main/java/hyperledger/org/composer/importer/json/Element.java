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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Element {
	public static final Logger logger = LoggerFactory.getLogger(Element.class);
	public void setOptional(Boolean optional) {
		this.optional = optional;
	}

	private Boolean pointer = false;
	private String name;
	private String type = null;
	private Boolean optional = false;
	private Boolean array = false;
	private String regX;
	private String range;
	private Object defaultValue;
	
	public void setRange(String range) {
		this.range = range;
	}

	public void setDefaultValue(Object defaultValue) {
		if(defaultValue instanceof String)
			this.defaultValue = "\""+ defaultValue+ "\"";
		else
			this.defaultValue = defaultValue;
	}


	public void setArray(Boolean array) {
		this.array = array;
	}

	public Boolean getArray() {
		return array;
	}

	public void setPointer(Boolean pointer) {
		this.pointer = pointer;
	}
	
	public Boolean getPointer() {
		return pointer;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public void setRegX(String regX) {
		this.regX = regX;
	}

	public String getType() {
		return type;
	}

	public void setOptional(boolean qualifier) {
		this.optional = qualifier;
	}
	public Boolean getOptional() {
		return optional;
	}

	public String toString(){
		//TODO add default min and max
		StringBuilder sb = new StringBuilder("   ");
		if(!pointer)
			sb.append("o");
		else 
			sb.append(" -->");
		sb.append(" ");
		if(type != null)
			sb.append(type);
		if(array)
			sb.append("[]");
		sb.append(" ");
		sb.append(name);
		sb.append(' ');
		if(regX !=null){
			sb.append("regex=/");
			sb.append(regX);
			sb.append("/ ");
		}
		if(defaultValue != null){
			sb.append("default=");
			sb.append(defaultValue);
			sb.append(' ');
		}
		if(range != null){
			sb.append("range=");
			sb.append(range);
			sb.append(' ');
		}
		
		
		if(optional)
			sb.append("optional");
		return sb.toString();
	}
}
