package com.utku.invoice_upload_server.business;

import com.utku.invoice_upload_server.Statics;
import com.utku.invoice_upload_server.entity.Customer;
import com.utku.invoice_upload_server.entity.Invoice;
import com.utku.invoice_upload_server.entity.InvoiceItems;
import com.utku.invoice_upload_server.entity.Item;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.StringReader;

public class XmlSaver {
    public boolean convertFromXml(String xmlData){
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try
        {

            builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlData)));

            doc.getDocumentElement().normalize();

            NodeList customer = doc.getElementsByTagName("customer");

            Node customerNode = customer.item(0);



            Element element = (Element) customerNode;
            Statics.invoice = new Invoice();
            Statics.customer = new Customer();

            String name = element.getElementsByTagName("name").item(0).getTextContent();
            String ssnNumber = element.getElementsByTagName("ssnNumber").item(0).getTextContent();

            Statics.customer.setNameSurname(name);
            Statics.customer.setSsNumber(ssnNumber);

            int customerId = Statics.database.saveCustomerToDB(Statics.customer);


            NodeList invoiceDataNodeList = doc.getElementsByTagName("invoiceData");
            Node invoiceDataNode = invoiceDataNodeList.item(0);

            Element invoiceElement = (Element) invoiceDataNode;

            String seri = invoiceDataNode.getAttributes().getNamedItem("seri").getTextContent();
            String number = invoiceDataNode.getAttributes().getNamedItem("number").getTextContent();
            double totalAmount = Double.parseDouble(invoiceElement.getElementsByTagName("totalAmount").item(0).getTextContent());
            double discount = Double.parseDouble(invoiceElement.getElementsByTagName("discount").item(0).getTextContent());
            double amountToPay = Double.parseDouble(invoiceElement.getElementsByTagName("amountToPay").item(0).getTextContent());

            Statics.invoice.setCustomerId(customerId);
            Statics.invoice.setDiscount(discount);
            Statics.invoice.setSeri(seri);
            Statics.invoice.setAmountToPay(amountToPay);
            Statics.invoice.setTotalAmount(totalAmount);
            Statics.invoice.setNumber(number);

            NodeList itemList = doc.getElementsByTagName("item");

            for (int temp = 0; temp < itemList.getLength(); temp++) {

                Node node = itemList.item(temp);

                if (node.getNodeType() == Node.ELEMENT_NODE) {

                    Element itemElement = (Element) node;
                    Statics.item = new Item();
                    Statics.item.setName(itemElement.getElementsByTagName("name").item(0).getTextContent());
                    Statics.item.setUnitPrice(Double.parseDouble(itemElement.getElementsByTagName("unitPrice").item(0).getTextContent()));
                    int itemId = Statics.database.saveItemToDB(Statics.item);

                    InvoiceItems invoiceItem = new InvoiceItems();
                    invoiceItem.setItemId(itemId);
                    invoiceItem.setQuantity(Integer.parseInt(itemElement.getElementsByTagName("quantity").item(0).getTextContent()));
                    invoiceItem.setAmount(Double.parseDouble(itemElement.getElementsByTagName("amount").item(0).getTextContent()));
                    Statics.invoiceItemsList.add(invoiceItem);
                }
            }

            if(Statics.database.addInvoice(Statics.invoice, Statics.invoiceItemsList)){
                Statics.resetVar();
                return true;
            }else{
                Statics.resetVar();
                return false;
            }
        }
        catch (Exception e)
        {
            Statics.resetVar();
            e.printStackTrace();
        }
        return false;
    }

}
