/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaiTapLTM;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.security.krb5.internal.HostAddress;

/**
 *
 * @author Admin
 */
public class Server {

    private int port;

    public static ArrayList<Socket> ListSK;

    public Server(int port) {

        this.port = port;

    }

    private void excute() throws IOException {
        ServerSocket server = new ServerSocket(port);
        WriteServer write = new WriteServer();
        write.start();
        System.out.println("Server is listening...");
        while (true) {

            Socket socket = server.accept();
            System.out.println("Đã kết nối với" + socket);
            Server.ListSK.add(socket);
            ReadServer read = new ReadServer(socket);
            read.start();

        }
    }

    public static void main(String[] args) throws IOException {
        Server.ListSK = new ArrayList<>();
        Server sv = new Server(15797);
        sv.excute();

    }

    class ReadServer extends Thread {

        private Socket socket;

        public ReadServer(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            DataInputStream dis = null;

            try {
                dis = new DataInputStream(socket.getInputStream());
                while (true) {
                    String sms = dis.readUTF();
                    if (sms.equalsIgnoreCase("1")) {
                        System.out.println("one");

                    } else if (sms.equalsIgnoreCase("2")) {
                        System.out.println("tow");

                    } else if (sms.equalsIgnoreCase("3")) {
                        System.out.println("three");

                    } else if (sms.equalsIgnoreCase("4")) {
                        System.out.println("four");

                    } else if (sms.equalsIgnoreCase("5")) {
                        System.out.println("five");

                    } else if (sms.equalsIgnoreCase("6")) {
                        System.out.println("Six");

                    } else if (sms.equalsIgnoreCase("7")) {
                        System.out.println("Seven");

                    } else if (sms.equalsIgnoreCase("8")) {
                        System.out.println("eight");

                    } else if (sms.equalsIgnoreCase("9")) {
                        System.out.println("Nine");

                    } else if (sms.equalsIgnoreCase("end")) {
                        Server.ListSK.remove(socket);
                        System.out.println("good bye " + socket);
                        dis.close();
                        socket.close();
                        continue;
                    } else {
                        System.out.println(sms);
                    }
                    for (Socket item : Server.ListSK) {
                        if (item.getPort() != socket.getPort()) {
                            DataOutputStream dos = new DataOutputStream(item.getOutputStream());
                            dos.writeUTF(sms);
                        }

                    }

                }
            } catch (Exception e) {
                try {

                    socket.close();
                } catch (IOException ex) {
                    System.out.println("Ngắt kết nối Server");
                }

            }
        }
    }

    class WriteServer extends Thread {

        @Override
        public void run() {
            DataOutputStream dos = null;
            Scanner sc = new Scanner(System.in);
            while (true) {
                String sms = sc.nextLine();
                try {
                    for (Socket item : Server.ListSK) {
                        dos = new DataOutputStream(item.getOutputStream());

                        dos.writeUTF(sms);
                    }
                } catch (IOException ex) {

                }

            }

        }

    }
}
