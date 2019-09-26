import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;
import java.util.*;

/**
 * Denne her klasse skal kende til IP adressen eller hostnavn så den ved hvor serveren lytter
 */

public class Client {

    //porten som server lytter på.
    final static int serverPort = 4444;
    static boolean Running;

    public static void main(String[] args) throws UnknownHostException, IOException {

        Running = true;


        //Scanner bruges til input fra brugeren.
        Scanner scanner = new Scanner(System.in);

        //localhost = 127.0.0.1 os selv.
        InetAddress ip = InetAddress.getByName("localhost");

        //opret forbindelse til server
        Socket s = new Socket(ip, serverPort);


        //modtag besked fra server.
        DataInputStream dis = new DataInputStream(s.getInputStream());

        //Send besked til server
        DataOutputStream dos = new DataOutputStream(s.getOutputStream());

        System.out.println("Server og Client har fået forbindelse til hinanden, indtast brugernavn");
        String username = scanner.nextLine();
        dos.writeUTF(username);

        //laver en tråd som lytter om der kommer input fra server
        Thread sendMessage = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning()){
                    //læs besked der kommer
                    String msg = scanner.nextLine();
                    //DATA brugernavn: Besked her
                    if(msg.length() >= 4){
                        String cmd = msg.substring(0, 4);
                        switch(cmd){
                            case "DATA":
                                try{
                                    dos.writeUTF(msg.substring(4) + "\n");
                                }catch (IOException e){
                                    e.printStackTrace();
                                }
                                break;
                            case "QUIT":
                                try{
                                    dos.writeUTF(msg);
                                    dos.flush();
                                    dos.close();
                                    dis.close();
                                    s.close();
                                    setRunning(false);
                                }catch (IOException e){
                                    e.printStackTrace();
                                }
                                break;

                        }
                    }else {
                        System.out.println("Error command wrong");
                    }




                }
            }



        });

        //Laver en tråd der læser hvad der kommer ind
        Thread readMessage = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning()){
                    try {
                        //læs besked sendt til den her klient
                        String msg = dis.readUTF();
                        System.out.println(msg);
                    }catch (IOException e){

                        e.printStackTrace();
                    }
                }
            }
        });

        //Starter mine tråde her
        sendMessage.start();
        readMessage.start();


        while(isRunning()){

        }

    }

    public static boolean isRunning() {
        return Running;
    }

    public static void setRunning(boolean running) {
        Running = running;
    }
}

