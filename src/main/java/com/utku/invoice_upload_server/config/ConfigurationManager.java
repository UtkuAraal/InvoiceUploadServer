package com.utku.invoice_upload_server.config;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class ConfigurationManager {
    public Configuration getConfig(){
        JSONParser jsonParser = new JSONParser();
        Configuration configuration = new Configuration();

        try (FileReader reader = new FileReader("src/main/resources/configuration.json"))
        {
            Object obj = jsonParser.parse(reader);

            JSONObject config = (JSONObject) obj;

            long httpPortLong = (long) config.get("httpPort");
            int httpPort = (int) httpPortLong;

            String httpQuery = (String) config.get("httpQueryInvoice");

            String httpUpload = (String) config.get("httpUploadInvoice");

            long tcpPortLong = (long) config.get("tcpPort");
            int tcpPort = (int) tcpPortLong;


            configuration.setHttpPort(httpPort);
            configuration.setHttpQueryInvoice(httpQuery);
            configuration.setHttpUploadInvoice(httpUpload);
            configuration.setTcpPort(tcpPort);


        } catch (Exception e) {
            e.printStackTrace();
        }
        return configuration;
    }
}
