package com;
import java.io.*;

public class FileOperations {
    public FileOperations(){

    }

    public void write(String file,String roomType,String numberOfNights,String guestName,String guestAge,String country)  {
        try{
            BufferedWriter bw=new BufferedWriter(new FileWriter(file));
            bw.write(roomType);
            bw.write(numberOfNights);
            bw.write(guestName);
            bw.write(guestAge);
            bw.write(country);
            bw.close();
        }catch(Exception ex){
            return;
        }



    }
    public String read(String file){
        StringBuilder contentBuilder = new StringBuilder();

        try{

            BufferedReader br=new BufferedReader(new FileReader(file));
            String s;
           while((s=br.readLine())!=null){
               contentBuilder.append(s).append("\n");
               System.out.println(s);
           }

           br.close();
            return contentBuilder.toString();

        }catch(Exception ex){
            return null;
        }



    }
    public void sendFileToClient(String file,DataOutputStream output) throws IOException {
        FileInputStream fileIn = new FileInputStream(file);
        byte[] buf = new byte[Short.MAX_VALUE];
        int bytesRead;
        while( (bytesRead = fileIn.read(buf)) != -1 ) {
            output.writeShort(bytesRead);
            output.write(buf,0,bytesRead);
        }
        output.writeShort(-1);
        output.flush();
        fileIn.close();
    }
    public void receiveFile(String file,DataInputStream input) throws IOException {
        FileOutputStream fileOut = new FileOutputStream(file);
        byte[] buf = new byte[Short.MAX_VALUE];
        int bytesSent;
        while( (bytesSent = input.readShort()) != -1 ) {
            input.readFully(buf,0,bytesSent);
            fileOut.write(buf,0,bytesSent);
        }
        fileOut.close();
    }
} // end class






