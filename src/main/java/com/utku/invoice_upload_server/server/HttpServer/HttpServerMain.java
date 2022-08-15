package com.utku.invoice_upload_server.server.HttpServer;

import com.sun.net.httpserver.HttpServer;
import com.utku.invoice_upload_server.Statics;
import com.utku.invoice_upload_server.server.HttpServer.handler.QueryInvoiceHandler;
import com.utku.invoice_upload_server.server.HttpServer.handler.UploadInvoiceHandler;

import java.net.InetSocketAddress;

public class HttpServerMain {
    public void start() {
        try {
            HttpServer server = HttpServer.create(new InetSocketAddress(Statics.config.getHttpPort()), 0);
            System.out.println("HTTP server started at " + Statics.config.getHttpPort());
            server.createContext(Statics.config.getHttpQueryInvoice(), new QueryInvoiceHandler());
            server.createContext(Statics.config.getHttpUploadInvoice(), new UploadInvoiceHandler());
            server.setExecutor(null);
            server.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
