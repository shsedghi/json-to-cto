package hyperledger.org.composer.importer.json.test;
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
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hyperledger.org.composer.importer.json.CtoUtils;
import hyperledger.org.composer.importer.json.JsonToCTO;



@RunWith(Parameterized.class)
public class TestJsonPayload {
	public static final Logger logger = LoggerFactory.getLogger(TestJsonPayload.class);
	@Parameter
	public Path fInput;

	@Parameters
	public static Object[] data() {
		List<Path> list = new ArrayList<>();
		try {
			Path inputDirectoy = Paths.get(ClassLoader.getSystemResource("./Json/Payload").toURI());
			DirectoryStream<Path> directoryStream = Files.newDirectoryStream(inputDirectoy);
			for (Path path : directoryStream) {
				if ("json".equals(CtoUtils.getFileExtension(path)))
					list.add(path);
			}
			return list.toArray();
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	@Test
	public void test() {
		String test = CtoUtils.getFileName(fInput);
		try {
			new JsonToCTO().buildCto(fInput, test);
		} catch (Exception e) {
			e.printStackTrace();
			Assert.fail("Test : " + CtoUtils.getFullFileName(fInput) + " Failed");
		}
	}
}