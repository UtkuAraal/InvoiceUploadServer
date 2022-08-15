package com.utku.invoice_upload_server.dataAccess;

import com.utku.invoice_upload_server.entity.Customer;
import com.utku.invoice_upload_server.entity.Invoice;
import com.utku.invoice_upload_server.entity.InvoiceItems;
import com.utku.invoice_upload_server.entity.Item;

import java.util.List;

public interface IDatabaseDal {
    List<Invoice> findInvoiceBySerial(String serial);
    List<Invoice> findInvoiceByName(String name);
    int saveCustomerToDB(Customer customer);
    int saveItemToDB(Item item);
    boolean addInvoice(Invoice invoice, List<InvoiceItems> items);
}
