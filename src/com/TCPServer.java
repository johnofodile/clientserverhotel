package com;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.logging.FileHandler;

public class TCPServer extends Thread {
    Socket socket = null;
    String filelink = "C:\\Users\\john ofodile\\Music\\practice\\src\\file\\output.txt";
    FileOperations fo = new FileOperations();

    String prompt3 = "Welcome to BookingHotel!,Our prices are as follows:The base rate (price)" +
            " for a single room per night is 400sek " +
             " Double room costs 50 percent more.Gusts older than 65 get 25% discount." +
            "The total price is calculated by multiplying the room rate by the number of nights " +
            "Please enter room type (single or double)";


    String filelocation = "C:\\Users\\john ofodile\\Desktop\\practice\\src\\file\\";

    public TCPServer(Socket socket) throws IOException {

        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            establish();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void establish() throws IOException {


        DataOutputStream outputstream = null;
        DataInputStream inputstream = null;
        BufferedReader reader = null;

        try {
            assert socket != null;
            outputstream = new DataOutputStream(socket.getOutputStream());
            inputstream = new DataInputStream(socket.getInputStream());
            reader = new BufferedReader(new InputStreamReader(inputstream));


            System.out.println("Socket accepted for: " + Thread.currentThread().getName());
            System.out.println("******************************");

            outputstream.writeBytes(prompt3 + "\r\n");


        } catch (SocketException e) {
            System.err.println("Client disconnected(1) at " + Thread.currentThread().getName());

        } catch (IOException e) {
            System.err.println("Error creating communication channel (Streams)");
            e.printStackTrace();
        }

        boolean running = true;
        while (running) {

            String numberOfNights = null;

            String guestName = null;
            String guestAge = null;


            String finalBill = null;
            String guestCountry;
            String yesOrNo;


            String roomType;
            try {
                assert inputstream != null;
                roomType = reader.readLine().trim();
                System.out.println(roomType);
                outputstream.writeBytes("Number of nights" + "\r\n");
                outputstream.flush();
                numberOfNights = reader.readLine().trim();
                outputstream.writeBytes(" enter GuestName" + "\r\n");
                outputstream.flush();
                guestName = reader.readLine();

                outputstream.writeBytes("enter the age" + "\r\n");
                outputstream.flush();
                guestAge = reader.readLine().trim();
                outputstream.writeBytes(" enter the country" + "\r\n");
                outputstream.flush();
                guestCountry = reader.readLine();

                int clientBill = hotelPrice(roomType, guestAge, numberOfNights );
                if (clientBill == 0) {
                    System.out.println("input was wrong");
                    break;

                } else {
                    finalBill = String.valueOf(clientBill);
                    outputstream.writeBytes("roomtype:"+roomType+"    numberofNights:"+numberOfNights+
                            "   guestName:"+guestName+"  guestage:"+guestAge+
                            "    guestCountry:"+guestCountry+"    your total bill is  " + finalBill + "sek" + "\r\n");
                    outputstream.flush();


                }
                outputstream.writeBytes("confirm everything with either yes or no" + "\r\n");
                outputstream.flush();
                yesOrNo = reader.readLine().trim();
                if (yesOrNo.trim().equalsIgnoreCase("yes")) {

                    fo.write(filelink, roomType, numberOfNights, guestName,guestAge,guestCountry );
                    fo.sendFileToClient(filelink, outputstream);
                    System.out.println("file sent to client");
                }else{

                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            outputstream.close();
            reader.close();
            System.out.println("=================================================================");
        } catch (NullPointerException e) {
            System.err.println("Null");

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            socket.close();

        }
    }


    static void logger(Socket socket) {
        System.err.println("Host name: " + socket.getInetAddress().getHostName().toString());
        System.err.println("Host address: " + socket.getInetAddress().toString());
    }


    private int hotelPrice(String roomType, String age, String numberOfNights) {
        int finalsPrice = 0;
        int startPrice = 0;

        try {
            int convertedAge = Integer.parseInt(age);
            int convertedNumberOfNights = Integer.parseInt(numberOfNights);
            if (roomType.trim().equalsIgnoreCase("single") && convertedAge < 65) {
                finalsPrice = 400 * convertedNumberOfNights;
                return finalsPrice;
            } else if (roomType.trim().equalsIgnoreCase("single") && convertedAge > 65) {
                startPrice = 400 * convertedNumberOfNights;

                finalsPrice = startPrice / 4;
                return finalsPrice;
            } else if (roomType.trim().equalsIgnoreCase("double") && convertedAge < 65) {
                finalsPrice = 600 * convertedNumberOfNights;


                return finalsPrice;


            } else if (roomType.trim().equalsIgnoreCase("double") && convertedAge > 65) {
                startPrice = 600 * convertedNumberOfNights;

                finalsPrice = startPrice / 4;
            }
        } catch (InputMismatchException e) {
            System.out.println("try to recheck all your input and make sure they are correct");
        }

        return 0;

    }
}





