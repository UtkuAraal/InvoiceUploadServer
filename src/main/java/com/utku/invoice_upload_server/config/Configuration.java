package com.utku.invoice_upload_server.config;

public class Configuration {
    private int httpPort;
    private String httpQueryInvoice;
    private String httpUploadInvoice;
    private int tcpPort;

    public Configuration() {
    }

    public Configuration(int httpPort, String httpQueryInvoice, String httpUploadInvoice, int tcpPort) {
        this.httpPort = httpPort;
        this.httpQueryInvoice = httpQueryInvoice;
        this.httpUploadInvoice = httpUploadInvoice;
        this.tcpPort = tcpPort;
    }

    public int getHttpPort() {
        return httpPort;
    }

    public void setHttpPort(int httpPort) {
        this.httpPort = httpPort;
    }

    public String getHttpQueryInvoice() {
        return httpQueryInvoice;
    }

    public void setHttpQueryInvoice(String httpQueryInvoice) {
        this.httpQueryInvoice = httpQueryInvoice;
    }

    public String getHttpUploadInvoice() {
        return httpUploadInvoice;
    }

    public void setHttpUploadInvoice(String httpUploadInvoice) {
        this.httpUploadInvoice = httpUploadInvoice;
    }

    public int getTcpPort() {
        return tcpPort;
    }

    public void setTcpPort(int tcpPort) {
        this.tcpPort = tcpPort;
    }
}
