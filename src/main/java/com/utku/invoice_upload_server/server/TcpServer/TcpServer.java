package com.utku.invoice_upload_server.server.TcpServer;

import com.utku.invoice_upload_server.Statics;
import com.utku.invoice_upload_server.server.TcpServer.handler.TcpQueryInvoice;
import com.utku.invoice_upload_server.server.TcpServer.handler.TcpUploadInvoice;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TcpServer {
    private Socket socket = null;
    private ServerSocket server = null;
    private DataInputStream in = null;
    private DataOutputStream out;


    public TcpServer() {
        // starts server and waits for a connection
        try {
            server = new ServerSocket(Statics.config.getTcpPort());
            System.out.println("TCP server started");

            while (true) {
                socket = server.accept();
                System.out.println("Client accepted");

                // takes input from the client socket
                in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
                out = new DataOutputStream(socket.getOutputStream());

                short length = in.readShort();
                byte command = in.readByte();
                byte type = in.readByte();

                byte[] messageByte = new byte[length];
                boolean end = false;
                StringBuilder dataString = new StringBuilder(length);
                int totalBytesRead = 0;
                while (!end) {
                    int currentBytesRead = in.read(messageByte);
                    totalBytesRead = currentBytesRead + totalBytesRead;
                    if (totalBytesRead <= length) {
                        dataString
                                .append(new String(messageByte, 0, currentBytesRead, StandardCharsets.UTF_8));
                    } else {
                        dataString
                                .append(new String(messageByte, 0, length - totalBytesRead + currentBytesRead,
                                        StandardCharsets.UTF_8));
                    }
                    if (dataString.length() >= length) {
                        end = true;
                    }
                }

                String response = "";

                if (command == 1) {
                    response += new TcpUploadInvoice().uploadInvoice(type, dataString.toString());
                } else if (command == 2) {
                    response += new TcpQueryInvoice().queryInvoice(type, dataString.toString());
                } else {
                    response += "Hatali islem!";
                }


                byte[] responseInByte = response.getBytes(StandardCharsets.UTF_8);

                out.writeShort(responseInByte.length);
                out.writeByte(command);
                out.writeByte(type);
                out.write(responseInByte);


                // close connection
                socket.close();
                in.close();
                out.close();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
