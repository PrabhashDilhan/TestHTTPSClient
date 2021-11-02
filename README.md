# TestHTTPSClient
## config.properties <br />
 
<ul>
<li>host:localhost</li>
<li>port:8253</li>
<li>context:/services/test</li>
<li>authorization:sdfsdfsd</li>
<li>payload:{"appUserId": "dd","service": "Case","name": "juan","age": "22"}</li>
<li>keyspath:/Users/wso2carbon.jks</li>
<li>password:wso2carbon</li>
</ul>

## How to run the client with ssl debug logs enabled <br />
**java -Djavax.net.debug=ssl,handshake -jar TestHTTPSClient-1.0-jar-with-dependencies.jar path-to-config.properties-file**
