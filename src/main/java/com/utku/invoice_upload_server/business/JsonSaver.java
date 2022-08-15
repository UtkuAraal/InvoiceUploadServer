package com.utku.invoice_upload_server.business;

import com.utku.invoice_upload_server.Statics;
import com.utku.invoice_upload_server.entity.Customer;
import com.utku.invoice_upload_server.entity.Invoice;
import com.utku.invoice_upload_server.entity.InvoiceItems;
import com.utku.invoice_upload_server.entity.Item;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.util.ArrayList;
import java.util.List;

public class JsonSaver {
    public boolean convertFromJson(String invoiceString){
        try{
            JSONParser parser = new JSONParser();
            JSONObject json = (JSONObject) parser.parse(invoiceString);

            JSONObject uploadSystem = (JSONObject) json.get("uploadSystem");

            JSONObject customerInfo = (JSONObject) uploadSystem.get("customer");
            JSONObject invoiceData = (JSONObject) uploadSystem.get("invoiceData");

            Statics.invoice = new Invoice();
            Statics.customer = new Customer();

            Statics.customer.setNameSurname(customerInfo.get("name").toString());
            Statics.customer.setSsNumber(customerInfo.get("ssnNumber").toString());

            int customerId = Statics.database.saveCustomerToDB(Statics.customer);

            Statics.invoice.setCustomerId(customerId);
            Statics.invoice.setSeri(invoiceData.get("seri").toString());
            Statics.invoice.setNumber(invoiceData.get("number").toString());
            Statics.invoice.setTotalAmount(Double.parseDouble(invoiceData.get("totalAmount").toString()));
            Statics.invoice.setDiscount(Double.parseDouble(invoiceData.get("discount").toString()));
            Statics.invoice.setAmountToPay(Double.parseDouble(invoiceData.get("amountToPay").toString()));

            List<JSONObject> itemsArray = (List<JSONObject>) invoiceData.get("item");

            for(JSONObject item : itemsArray){
                Statics.item = new Item();
                Statics.item.setName(item.get("name").toString());
                Statics.item.setUnitPrice(Double.parseDouble(item.get("unitPrice").toString()));
                int itemId = Statics.database.saveItemToDB(Statics.item);

                InvoiceItems invoiceItem = new InvoiceItems();
                invoiceItem.setItemId(itemId);
                invoiceItem.setAmount(Double.parseDouble(item.get("amount").toString()));
                invoiceItem.setQuantity(Integer.parseInt(item.get("quantity").toString()));
                Statics.invoiceItemsList.add(invoiceItem);

            }

            if(Statics.database.addInvoice(Statics.invoice, Statics.invoiceItemsList)){
                Statics.resetVar();
                return true;
            }else{
                Statics.resetVar();
                return false;
            }



        }catch (Exception e){
            e.printStackTrace();
            Statics.resetVar();
        }
        return false;
    }
}
