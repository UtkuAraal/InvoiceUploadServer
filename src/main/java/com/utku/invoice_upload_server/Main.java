package com.utku.invoice_upload_server;

import com.utku.invoice_upload_server.server.HttpServer.HttpServerMain;
import com.utku.invoice_upload_server.server.TcpServer.TcpServer;


public class Main {
    public static void main(String[] args) {
        Statics statics = new Statics();
        HttpServerMain httpServer = new HttpServerMain();
        httpServer.start();

        TcpServer tcpServer = new TcpServer();

    }
}
