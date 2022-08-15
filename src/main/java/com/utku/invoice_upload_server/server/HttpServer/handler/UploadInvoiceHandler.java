package com.utku.invoice_upload_server.server.HttpServer.handler;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.utku.invoice_upload_server.Statics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.utku.invoice_upload_server.server.HttpServer.handler.RequestParser.parseQuery;

public class UploadInvoiceHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        Headers header = exchange.getRequestHeaders();
        String type = header.get("Type").get(0);


        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
        BufferedReader br = new BufferedReader(isr);
        String body = "";

        while (br.ready()) {
            body += br.readLine() + "\n";
        }

        String response = "";
        int code;

        if (type.equals("json")) {
            if (Statics.jsonSaver.convertFromJson(body)) {
                response += "Fatura Kaydedildi! " + "\n";
                code = 200;
            } else {
                response += "Fatura Kaydedilemedi! " + "\n";
                code = 200;
            }
        } else if (type.equals("xml")) {
            if (Statics.xmlSaver.convertFromXml(body)) {
                response += "Fatura Kaydedildi! " + "\n";
                code = 200;
            } else {
                response += "Fatura Kaydedilemedi! " + "\n";
                code = 200;
            }
        } else {
            code = 400;
        }


        exchange.sendResponseHeaders(code, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.toString().getBytes());
        os.close();
    }
}
