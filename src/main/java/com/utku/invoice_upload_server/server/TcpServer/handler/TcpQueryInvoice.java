package com.utku.invoice_upload_server.server.TcpServer.handler;

import com.utku.invoice_upload_server.Statics;
import com.utku.invoice_upload_server.entity.Invoice;

import java.util.List;

public class TcpQueryInvoice {
    public String queryInvoice(byte type, String value) {
        List<Invoice> invoices = null;
        String response = "";
        if (type == 1) {
            invoices = Statics.database.findInvoiceBySerial(value);
        } else if (type == 2) {
            invoices = Statics.database.findInvoiceByName(value);
        }

        if (invoices == null) {
            response += "Bir hata olustu";

        } else if (invoices.isEmpty()) {
            response += "Kayit bulunamadi!";

        } else {
            response += invoices.size() + " adet fatura bulundu." + "\n";
            double total = 0;
            for (Invoice invoice : invoices) {
                response += invoice.getSeri() + "-" + invoice.getNumber() + "\t" + invoice.getAmountToPay() + "\n";
                total += invoice.getAmountToPay();
            }
            String totalFormat = Statics.decimalFormat.format(total).replace(",", ".");
            total = Double.parseDouble(totalFormat);
            response += "Toplam tutar : " + total + "\n";
        }
        return response;
    }

}
