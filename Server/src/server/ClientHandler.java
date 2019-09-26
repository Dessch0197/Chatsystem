package server;
import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;


/**Denne her klasser holder styr på de klienter der logger sig ind samt holder styr på protokollen.
 * Denne her klasse holder holder tråde adskillt fra hver forespørgelse.
 * Den klasse nedarver fra Thread klassen.
 *
 */

public class ClientHandler extends Thread {

    private Scanner scanner = new Scanner(System.in);
    private String username;
    private DataInputStream dis;
    private DataOutputStream dos;
    private Socket s;

    private boolean isloggedin;
    private boolean hasQuit = false;
    java.util.logging.Logger log = java.util.logging.Logger.getLogger("LogInformation");

    //min Constructor
    public ClientHandler(Socket s, DataInputStream dis,DataOutputStream dos, String username){
        this.dis = dis;
        this.dos = dos;
        this.s = s;
        this.isloggedin = true;
        this.username = username;
    }


    /**
     * Den er metode holder styr på hvad der skal ske når  klienten går ind på på de forskellige muligheder der findes
     * på protokollen.
     **/
    @Override
    public void run() {

        String recievedMSG;
            while(!hasQuit){
                try{
                    List<ClientHandler> clientListe = Server.getInstance().getVec();
                    recievedMSG = dis.readUTF();

                    switch (recievedMSG){
                        case "QUIT":
                            dos.flush();
                            s.close();
                            hasQuit = true;
                            for(int i = 0; i < clientListe.size(); i++){
                                if(this.username.equals(clientListe.get(i).getUsername())){
                                    clientListe.remove(i);
                                    i--;
                                    System.out.println(this.username + " - was removed");
                                }
                            }
                            break;
                        //DATA
                        default:
                            System.out.println(username + ": " + recievedMSG);
                            break;

                    }



                    for(int i = 0; i < clientListe.size(); i++){

                        if(!clientListe.get(i).getUsername().equals(this.username)){
                            clientListe.get(i).getDos().writeUTF(this.username + ": " + recievedMSG);
                        }

                    }


                }catch (IOException e){
                    e.printStackTrace();
                }
            }


        /*
        try {
            FileHandler fileHandler = new FileHandler("c:\\diverse\\log.txt", true);
            SimpleFormatter formatter = new SimpleFormatter();
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            fileHandler.setFormatter(formatter);
            log.addHandler(fileHandler);
            String msg = "";
            String secondUser;
            //secondUser = dis.readUTF();


        }catch (IOException e)
        {

        }
        log.info("test log");

         */
        /*
        while (!hasQuit){
            try {
                //gemmer input fra client i recived
                String recived = dis.readUTF();
                String[] tokens = recived.split("\\w");

                if (tokens.length ==0)
                {
                    dos.writeUTF("Prøv igen");
                    continue;
                }
                switch (tokens[0]){
                    case "JOIN":
                        name = tokens[1];
                        if (name.length()>12){
                            dos.writeUTF("J_ER");
                            continue;

                        }
                        Server.getInstance().addVec(this);
                        dos.writeUTF("J_OK");
                        break;
                    case "DATA":
                        System.out.println("MSG recieved");
                        break;
                    case "IMAV":
                        break;
                    case "QUIT":
                        dos.writeUTF("Farvel og tak"+ this.name);
                        dos.flush();
                        this.s.close();
                        hasQuit = true;

                        break;
                        default:
                            dos.writeUTF("Ikke en mulighed, prøv igen!");
                            break;
                }



    }catch(IOException e){

                System.out.printf(" Tabt forbindelsen");
                this.interrupt();
}


            try{
                s.close();

            }catch (IOException e)
            {
                System.out.printf("Forbindelsen er tabt");
            }

        }

         */
    }

    //Getter og setters
    public DataInputStream getDis()
    {
        return dis;
    }

    public void setDis(DataInputStream dis)
    {
        this.dis = dis;
    }

    public DataOutputStream getDos()
    {
        return dos;
    }

    public void setDos(DataOutputStream dos)
    {
        this.dos = dos;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }
}
