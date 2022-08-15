package com.utku.invoice_upload_server.server.TcpServer.handler;

import com.utku.invoice_upload_server.Statics;

public class TcpUploadInvoice {
    public String uploadInvoice(byte type, String StringFile) {
        String response = "";
        if (type == 1) {
            if (Statics.xmlSaver.convertFromXml(StringFile)) {
                response += "Fatura Kaydedildi! " + "\n";
            } else {
                response += "Fatura Kaydedilemedi! " + "\n";
            }

        } else if (type == 2) {
            if (Statics.jsonSaver.convertFromJson(StringFile)) {
                response += "Fatura Kaydedildi! " + "\n";
            } else {
                response += "Fatura Kaydedilemedi! " + "\n";
            }
        } else {
            response += "Fatura Kaydedilemedi! " + "\n";
        }
        return response;
    }
}
