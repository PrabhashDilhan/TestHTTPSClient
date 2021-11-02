# TestHTTPSClient
## config.properties <br />
host:localhost. 
port:8253. 
context:/services/test. 
authorization:sdfsdfsd. 
payload:{"appUserId": "bolvrmws1","serviceType": "Case Creation","alertStartTime": "20211010112035","alertEndTime": "20211010112135"}. 
keyspath:/Users/apple/Documents/support/151_/wso2carbon.jks. 
password:wso2carbon. 

## How to run the client with ssl debug logs enabled <br />
**java -Djavax.net.debug=ssl,handshake -jar TestHTTPSClient-1.0-jar-with-dependencies.jar path-to-config.properties-file**
