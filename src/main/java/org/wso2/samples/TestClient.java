package org.wso2.samples;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.security.KeyStore;
import java.util.Properties;
import java.util.Scanner;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;

public class TestClient {
    static String host;

    static int port;

    static String keyspath;

    static String password;

    static String context;

    static String payload;

    static int length;

    static String authorization;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        //System.out.print("Enter the path to config.properties file : ");
        String path = args[0];
        Properties prop = new Properties();
        File initialFile = new File(path);
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(initialFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        if (inputStream != null) {
            try {
                prop.load(inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("property file is not found");
        }
        host = prop.getProperty("host");
        port = Integer.parseInt(prop.getProperty("port"));
        context = prop.getProperty("context");
        authorization = prop.getProperty("authorization");
        payload = prop.getProperty("payload");
        keyspath = prop.getProperty("keyspath");
        password = prop.getProperty("password");
        length = payload.length();
        TestClient client = new TestClient();
        client.run();
    }

    private SSLContext createSSLContext() {
        try {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(new FileInputStream(keyspath), password.toCharArray());
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance("SunX509");
            keyManagerFactory.init(keyStore, password.toCharArray());
            KeyManager[] km = keyManagerFactory.getKeyManagers();
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance("SunX509");
            trustManagerFactory.init(keyStore);
            TrustManager[] tm = trustManagerFactory.getTrustManagers();
            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(km, tm, null);
            return sslContext;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public void run() {
        SSLContext sslContext = createSSLContext();
        try {
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            SSLSocket sslSocket = (SSLSocket)sslSocketFactory.createSocket(host, port);
            System.out.println("SSL client started");
            ClientThread(sslSocket);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    static void ClientThread(SSLSocket sslSocket) {
        sslSocket.setEnabledCipherSuites(sslSocket.getSupportedCipherSuites());
        try {
            sslSocket.startHandshake();
            SSLSession sslSession = sslSocket.getSession();
            System.out.println("SSLSession :");
            System.out.println("\tProtocol : " + sslSession.getProtocol());
            System.out.println("\tCipher suite : " + sslSession.getCipherSuite());
            InputStream inputStream = sslSocket.getInputStream();
            OutputStream outputStream = sslSocket.getOutputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(outputStream));
            printWriter.print("POST " + context + " HTTP/1.1\r\n");
            printWriter.print("Accept: */*\r\n");
            printWriter.print("Content-Type: application/json\r\n");
            printWriter.print("Authorization: Basic "+authorization+"\r\n");
            printWriter.print("Content-Length: "+length+"\r\n");
            printWriter.print("Connection: Keep-Alive\r\n");
            printWriter.print("Host: "+host+"\r\n");
            printWriter.print("\r\n");
            printWriter.print(payload + "\r\n");
            printWriter.print("\r\n");
            printWriter.flush();
            String line = null;
            char[] buf = new char[100];
            StringBuilder outt = new StringBuilder();
            while (true) {
                try {
                    int read = bufferedReader.read(buf);
                    outt.append(buf, 0, read);
                    if (read < 100)
                        break;
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println(outt);
            sslSocket.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
