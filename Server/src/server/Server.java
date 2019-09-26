package server;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.Vector;

public class Server {
    /**
     * Den her klasse lytter til en port og afventer til der er en klient der logger sig på
     * Man starter med at køre Server før man starter client
     * Denne her klasse static fordi der kun skal være én Server, der skal ikke kunne laves nye objekter af den.
     * Her har jeg brugt det pattern der hedder Singelton pattern.
     **/

    private static Server instance;

    //vector bruges til at gemme aktive clients
    private static Vector<ClientHandler> vec = new Vector();
    private Scanner scanner = new Scanner(System.in);
    private static ServerSocket serverSocket;
    private static Socket socket;

    private Server()
    {

    }

    public static Server getInstance()
    {
        if(instance == null)
        {
            instance = new Server();
        }
        return instance;
    }

    //min Constructor- private så andre klasse ikke kan lave et nyt objekt af Serveren.

    //Den her metode tilføjer klienter til min vector - laver en liste af mine klienter
    public void addVec(ClientHandler client)
    {
       vec.add(client);
        System.out.println("Du har tilføjet en ny klient");
    }

    public Vector<ClientHandler> getVec() {
        return vec;
    }

    public static void main(String[] args) throws IOException {

        //For at skabe server applicationen, skal oprette en instance af ServerSocket
        //klassen.
        // Opret socket og lytter til den port der hedder 4444.
        serverSocket = new ServerSocket(4444);
        try {

            //afvent på klient forbinder.
            while (true) {

                //socket objketet modtager en klient og acceptere
                socket = serverSocket.accept();
                System.out.println("Der er blevet accepteret en ny klient" + socket);

                //input / output
                DataInputStream input = new DataInputStream(socket.getInputStream());
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());

                String username = input.readUTF();


                //Opretter et nyt objekt af clientHandler og så tilføjer jeg clientHandler til min liste
                ClientHandler clientHandler = new ClientHandler(socket, input, output, username);
                Server.getInstance().addVec(clientHandler);
                clientHandler.start();


                /*//laver et nyt objekt af typen  tråd som lytter om der kommer input fra server, og starter min tråd
                Thread sendMessage = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (true){
                            //læs besked der kommer
                            String msg = scanner.nextLine();
                            try {
                                //skriv på output stream
                                output.writeUTF(msg);
                                output.flush();

                            }catch (IOException e){
                                e.printStackTrace();
                            }
                        }
                    }



                });


                //Starter mine tråde med start()metoden
                sendMessage.start();
                readMessage.start();

                while (true){

                }



            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                socket.close();


            }catch (Exception e){
            }
        }

            }*/
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}