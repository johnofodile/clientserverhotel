package com;

import java.io.*;
import java.net.*;
import java.util.Scanner;


public class TCPClient {
   static String fileLink="C:\\Users\\john ofodile\\Music\\practice\\src\\file\\client.txt";




    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        FileOperations fileIn=new FileOperations();

        Socket client = null;
      final  int portnumber = 16777;

          DataInputStream in;
        BufferedReader br = null;
        DataOutputStream out=null;


            try {

                client = new Socket(InetAddress.getLocalHost(), portnumber);
                System.out.println("client socket is created" + client);
                out = new DataOutputStream(client.getOutputStream());

                in = new DataInputStream(client.getInputStream());
                br = new BufferedReader(new InputStreamReader(in));
                BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
                boolean running = true;
                String firstPrompt=br.readLine();

                System.out.println(firstPrompt);

                while (running) {
                    String roomType = stdin.readLine();
                    System.out.println(roomType);
                    out.writeBytes(roomType + "\r\n");
                    out.flush();
                    String nights = br.readLine();
                    System.out.println(nights);
                    String numberOfNights = stdin.readLine();
                    out.writeBytes(numberOfNights + "\r\n");
                    out.flush();
                    String guest = br.readLine();
                    System.out.println(guest);
                    String guestName = stdin.readLine();
                    out.writeBytes(guestName + "\r\n");
                    out.flush();
                    String age = br.readLine();
                    System.out.println(age);
                    String guestAge = stdin.readLine();
                    out.writeBytes(guestAge + "\r\n");
                    out.flush();
                    String country = br.readLine();
                    System.out.println(country);
                    String guestCountry = stdin.readLine();
                    out.writeBytes(guestCountry + "\r\n");
                    out.flush();

                    String finalPrice = br.readLine();
                    if (finalPrice == null) {
                        System.out.println("wrong input");
                    } else {
                        System.out.println(finalPrice);
                        String furtherConfirmation = br.readLine();
                        System.out.println(furtherConfirmation);
                        String yesOrNo = stdin.readLine().trim();
                        out.writeBytes(yesOrNo + "\r\n");
                        out.flush();

                        fileIn.receiveFile(fileLink,in);
                        String confirmation=fileIn.read(fileLink);
                        System.out.println("The client roomtype,numberofnights,name,age and address are printed jointly below" + "\r\n"+confirmation);



                    }
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try{
                out.close();
                br.close();
                client.close();
                    System.out.println("disconnected");

            } catch (IOException e) {
                    e.printStackTrace();
                }
            }
    }

    }

