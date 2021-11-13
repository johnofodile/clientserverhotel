package com;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiClientServer extends Thread{
    static int portNr=16777;
            @Override
    public void run(){
                System.out.println((".............."));
                try {
                    establish();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println("...........");
            }


public void establish() throws IOException{
                ServerSocket serverSocket=null;
                try{
                    serverSocket=new ServerSocket(portNr);

                }catch(IOException e){
                    System.err.println("Error listening at" + portNr);
                    e.printStackTrace();
                }
                    Socket socket=null;
                while(true){
                    try{
                        assert serverSocket!=null;
                        socket=serverSocket.accept();
                        System.out.println("..............");
                        System.out.println("client node:" +socket.getInetAddress().getHostAddress());
                        System.out.println("...................");
                        TCPServer server=new TCPServer(socket);
                        server.start();
                    }catch(IOException e){
                        socket.close();
                        System.err.println("could not accept socket");
                        System.exit(-1);
                    }
                }
            }
}
