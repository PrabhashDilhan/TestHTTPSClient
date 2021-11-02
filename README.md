# TestHTTPSClient
## config.properties <br />
host:localhost. 
port:8253. 
context:/services/test. 
authorization:sdfsdfsd. 
payload:{"appUserId": "bolvrmws1","serviceType": "Case Creation","alertStartTime": "20211010112035","alertEndTime": "20211010112135"}. 
keyspath:/Users/apple/Documents/support/151_/wso2carbon.jks. 
password:wso2carbon. 
<ul>
<li>host:localhost</li>
<li>port:8253</li>
<li>context:/services/test</li>
<li>authorization:sdfsdfsd</li>
<li>payload:{"appUserId": "bolvrmws1","serviceType": "Case Creation","alertStartTime": "20211010112035","alertEndTime": "20211010112135"}</li>
<li>keyspath:/Users/apple/Documents/support/151_/wso2carbon.jks</li>
<li>password:wso2carbon</li>
</ul>

## How to run the client with ssl debug logs enabled <br />
**java -Djavax.net.debug=ssl,handshake -jar TestHTTPSClient-1.0-jar-with-dependencies.jar path-to-config.properties-file**
