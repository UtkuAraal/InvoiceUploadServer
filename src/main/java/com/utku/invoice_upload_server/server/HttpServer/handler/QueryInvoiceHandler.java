package com.utku.invoice_upload_server.server.HttpServer.handler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.utku.invoice_upload_server.Statics;
import com.utku.invoice_upload_server.entity.Invoice;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryInvoiceHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {

        Map<String, Object> parameters = new HashMap<String, Object>();
        URI requestedUri = exchange.getRequestURI();
        String query = requestedUri.getRawQuery();
        Statics.requestParser.parseQuery(query, parameters);

        String type = parameters.get("Type").toString();
        List<Invoice> invoices = null;

        if (type.equals("serial")) {
            invoices = Statics.database.findInvoiceBySerial(parameters.get("Serial").toString());
        } else if (type.equals("name")) {
            invoices = Statics.database.findInvoiceByName(parameters.get("Name").toString());
        }

        String response = "";
        int code;

        if (invoices == null) {
            response += "Bir hata olustu";
            code = 400;
        } else if (invoices.isEmpty()) {
            response += "Kayit bulunamadi!";
            code = 404;
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
            code = 200;
        }

        exchange.sendResponseHeaders(code, response.length());
        OutputStream os = exchange.getResponseBody();
        os.write(response.toString().getBytes());

        os.close();
    }
}
