# JSON Payload Importer for Hyperledger Composer   
This tool imports a JSON payload (aka. Document) and creates a CTO file, which is the Fabric Composer modeling language. The CTO file comprised of cto artifacts ("concept" only at this stage) from JSON objects and arrays.

## Type interchange ##


| JSON|CTO|Note |
|:--|:----|:---|
|BigInteger|Long| |
|Long|Long| |
| Double| Double |  |
|Int|Integer| |
|Decimal|String| No Decimal type in CTO, This may not be desired|
|Binary|String| No Binary type in CTO, needs to be processed in the script|
|Boolean|Boolean| |
|Null|String|This may not be desired|
|Text|String| |
|others|Jackson parser Node Type| |

## Installation and Testing ##

__You need Maven with Java 8__

 *  Clone repository in a folder, called xyz later on
 *  cd xyz/json-to-cto
 *  mvn install
 *  After successful test you will see 5 JSON and corresponding CTO files  under xyz/json-to-cto/target/test-classes.
 *  TwitterGetUser.josn is different from others because it starts as a JSON array and not a JSON object
 
 ## Use it as a tool ##
 To test your own data:
 
  * Drop a JSON file at  xyz/json-to-cto/scr/test/resources/Json/Payload
  * cd xyz/json-to-cto
  *  mvn test
 
  If the JSON file is valid and does not break the tool will generate a file with extension .cto at:  xyz/json-to-cto/target/test-classes/Json/Payload 
 ## Debugging ##
 There is a log42j.properties file in the xyz/json-to-cto/scr/test/resources, which the debugging level is set to TRACE by default. It can be changed to DEBUG (less verbose) and INFO (not verbose). Output will be directed to console
 
