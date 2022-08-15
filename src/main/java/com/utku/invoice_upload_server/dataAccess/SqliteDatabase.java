package com.utku.invoice_upload_server.dataAccess;

import com.utku.invoice_upload_server.entity.Customer;
import com.utku.invoice_upload_server.entity.Invoice;
import com.utku.invoice_upload_server.entity.InvoiceItems;
import com.utku.invoice_upload_server.entity.Item;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqliteDatabase implements IDatabaseDal{

    public Connection databaseLink;
    public Statement statement;

    public SqliteDatabase(){
        String dbName = "upload_system.db";
        String url = "jdbc:sqlite:upload_system.db";

        try{


            File file = new File ("upload_system.db");

            if(file.exists())
            {
                System.out.println("This database already exists.");
                databaseLink = DriverManager.getConnection(url);
                statement = databaseLink.createStatement();

            }
            else{
                System.out.println("Creating database!");
                databaseLink = DriverManager.getConnection(url);
                statement = databaseLink.createStatement();
                String createCustomersql = "CREATE TABLE `customer` (" +
                        "`id`INTEGER," +
                        "`name`TEXT," +
                        "`ssnNumber`TEXT," +
                        "PRIMARY KEY(`id`)" +
                        ")";

                String createInvoicesql = "CREATE TABLE 'invoice' (" +
                        "`id`INTEGER," +
                        "`seri`TEXT," +
                        "`number`NUMERIC," +
                        "`totalAmount`TEXT," +
                        "`discount`TEXT," +
                        "`amountToPay`TEXT," +
                        "`customerId`INTEGER," +
                        "PRIMARY KEY(`id`)" +
                        ")";

                String createInvoiceItemssql = "CREATE TABLE `invoiceItems` (" +
                        "`invoiceId`INTEGER," +
                        "`itemId`INTEGER," +
                        "`quantity`INTEGER," +
                        "`amount`TEXT" +
                        ")";

                String createItemsql = "CREATE TABLE `item` (" +
                        "`id`INTEGER," +
                        "`name`TEXT," +
                        "`unitPrice`TEXT," +
                        "PRIMARY KEY(`id`)" +
                        ")";

                statement.executeUpdate(createCustomersql);
                statement.executeUpdate(createInvoicesql);
                statement.executeUpdate(createInvoiceItemssql);
                statement.executeUpdate(createItemsql);
                statement.close();

            }
            System.out.println("Connected");

        }catch (Exception e){
            e.printStackTrace();
        }

    }


    public List<Invoice> findInvoiceBySerial(String serial){
        List<Invoice> invoiceList = new ArrayList<>();
        try{
            Statement statement = databaseLink.createStatement();
            ResultSet rs = statement.executeQuery( "SELECT * FROM invoice WHERE seri = '"+ serial + "'" );



            while ( rs.next() ) {
                Invoice invoice = new Invoice();
                int id = rs.getInt("id");
                invoice.setId(id);
                String seri = rs.getString("seri");
                invoice.setSeri(seri);
                String number = rs.getString("number");
                invoice.setNumber(number);
                String totalAmount = rs.getString("totalAmount");
                invoice.setTotalAmount(Double.parseDouble(totalAmount));
                String discount = rs.getString("discount");
                invoice.setDiscount(Double.parseDouble(discount));
                String amountToPay = rs.getString("amountToPay");
                invoice.setAmountToPay(Double.parseDouble(amountToPay));
                int customerId = rs.getInt("customerId");
                invoice.setCustomerId(customerId);
                invoiceList.add(invoice);

            }
            rs.close();
            statement.close();

            return invoiceList;
        }catch (Exception e){
            e.printStackTrace();
        }
        return invoiceList;
    }


    public List<Invoice> findInvoiceByName(String name){
        List<Invoice> invoiceList = new ArrayList<>();
        try{
            Statement statement = databaseLink.createStatement();
            ResultSet rs = statement.executeQuery( "SELECT invoice.id, invoice.seri, invoice.number, invoice.totalAmount, invoice.discount, invoice.amountToPay, invoice.customerId FROM invoice INNER JOIN customer ON invoice.customerId = customer.id WHERE name = '" + name + "'");

            while (rs.next()){
                Invoice invoice = new Invoice();
                int id = rs.getInt("id");
                invoice.setId(id);
                String seri = rs.getString("seri");
                invoice.setSeri(seri);
                String number = rs.getString("number");
                invoice.setNumber(number);
                String totalAmount = rs.getString("totalAmount");
                invoice.setTotalAmount(Double.parseDouble(totalAmount));
                String discount = rs.getString("discount");
                invoice.setDiscount(Double.parseDouble(discount));
                String amountToPay = rs.getString("amountToPay");
                invoice.setAmountToPay(Double.parseDouble(amountToPay));
                int customerId = rs.getInt("customerId");
                invoice.setCustomerId(customerId);
                invoiceList.add(invoice);

            }
            rs.close();
            statement.close();

            return invoiceList;
        }catch (Exception e){
            e.printStackTrace();
        }
        return invoiceList;
    }

    public int saveCustomerToDB(Customer customer){
        int customerId = 0;
        try {

            String sql = "SELECT id FROM customer WHERE ssnNumber = '" + customer.getSsNumber()+ "'";
            Statement statement = databaseLink.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if(rs.isBeforeFirst()){
                customerId = rs.getInt("id");
            }else{
                String sqlInsert = "INSERT INTO customer (name, ssnNumber) VALUES(?, ?)";
                int generatedKey = 0;

                PreparedStatement ps = null;
                ps = databaseLink.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, customer.getNameSurname());
                ps.setString(2, customer.getSsNumber());
                ps.execute();
                ResultSet rsCustomer = ps.getGeneratedKeys();
                if (rsCustomer.next()) {
                    generatedKey = rsCustomer.getInt(1);
                }
                customerId = generatedKey;
            }


            return customerId;
        }catch (Exception e){
            e.printStackTrace();
        }
        return customerId;
    }

    public int saveItemToDB(Item item){
        int itemId = 0;
        try {

            String sql = "SELECT id FROM item WHERE name = '" + item.getName() + "' AND unitPrice = '" + String.valueOf(item.getUnitPrice()) + "'";
            Statement statement = databaseLink.createStatement();
            ResultSet rs = statement.executeQuery(sql);
            if(rs.isBeforeFirst()){
                itemId = rs.getInt("id");
            }else{
                String sqlInsert = "INSERT INTO item (name, unitPrice) VALUES(?, ?)";
                int generatedKey = 0;

                PreparedStatement ps = null;
                ps = databaseLink.prepareStatement(sqlInsert, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, item.getName());
                ps.setString(2, String.valueOf(item.getUnitPrice()));
                ps.execute();
                ResultSet rsItem = ps.getGeneratedKeys();
                if (rsItem.next()) {
                    generatedKey = rsItem.getInt(1);
                }
                itemId = generatedKey;
            }

            return itemId;
        }catch (Exception e){
            System.out.println(e);
        }
        return itemId;
    }

    public boolean addInvoice(Invoice invoice, List<InvoiceItems> items) {
        try{
            String sql = "INSERT INTO invoice (seri, number, totalAmount, discount, amountToPay, customerId) VALUES(?, ?, ?, ?, ?, ?)";
            int generatedKey = 0;

            PreparedStatement ps = null;
            ps = databaseLink.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, invoice.getSeri());
            ps.setString(2, invoice.getNumber());
            ps.setString(3, String.valueOf(invoice.getTotalAmount()));
            ps.setString(4, String.valueOf(invoice.getDiscount()));
            ps.setString(5, String.valueOf(invoice.getAmountToPay()));
            ps.setInt(6, invoice.getCustomerId());
            ps.execute();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                generatedKey = rs.getInt(1);
            }


            String itemSql = "INSERT INTO invoiceItems (invoiceId, itemId, quantity, amount) VALUES(?, ?, ?, ?)";
            for(InvoiceItems invoiceItem : items){
                ps = databaseLink.prepareStatement(itemSql);
                ps.setInt(1, generatedKey);
                ps.setInt(2, invoiceItem.getItemId());
                ps.setInt(3, invoiceItem.getQuantity());
                ps.setString(4, String.valueOf(invoiceItem.getAmount()));
                ps.execute();
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }

        return false;

    }

}
