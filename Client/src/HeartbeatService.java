/*
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class HeartbeatService implements Runnable {

    public HeartbeatService() {

        this.beat = beat;
        DataOutputStream dos;
    }

    Thread beat = new Thread(new Runnable() {
        @Override
        public void run() {
            while (true) {
                // Siger at der stadig er heartbeat hvert halve sekund
                dos.writeUTF("IMAV");
                try {
                    beat.sleep(500);
                } catch (InterruptedException ie) {

                }
            }

        }
    });
}*/
