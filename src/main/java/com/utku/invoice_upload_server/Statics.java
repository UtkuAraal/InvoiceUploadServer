package com.utku.invoice_upload_server;

import com.utku.invoice_upload_server.business.JsonSaver;
import com.utku.invoice_upload_server.business.XmlSaver;
import com.utku.invoice_upload_server.config.Configuration;
import com.utku.invoice_upload_server.config.ConfigurationManager;
import com.utku.invoice_upload_server.dataAccess.IDatabaseDal;
import com.utku.invoice_upload_server.dataAccess.SqliteDatabase;
import com.utku.invoice_upload_server.entity.Customer;
import com.utku.invoice_upload_server.entity.Invoice;
import com.utku.invoice_upload_server.entity.InvoiceItems;
import com.utku.invoice_upload_server.entity.Item;
import com.utku.invoice_upload_server.server.HttpServer.handler.RequestParser;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class Statics {
    public static final IDatabaseDal database = new SqliteDatabase();

    public static final Configuration config = new ConfigurationManager().getConfig();

    public static final RequestParser requestParser = new RequestParser();

    public static final DecimalFormat decimalFormat = new DecimalFormat("#.##");

    public static Invoice invoice = null;

    public static List<InvoiceItems> invoiceItemsList = new ArrayList<>();

    public static Customer customer = null;

    public static Item item = null;

    public static JsonSaver jsonSaver = new JsonSaver();

    public static void resetVar() {
        Statics.invoice = null;
        Statics.item = null;
        Statics.customer = null;
        Statics.invoiceItemsList = new ArrayList<>();
    }

    public static XmlSaver xmlSaver = new XmlSaver();


}
