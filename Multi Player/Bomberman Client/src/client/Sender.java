package client;

import java.io.PrintWriter;

public class Sender {

    private Client client;
    private PrintWriter printWriter;

    Sender(Client client) {
        this.client=client;
        printWriter=client.getPrintWriter();
    }

    public void sendAction(int e){
        printWriter.print("ACTION ");
        printWriter.println(e);

    }
    public void sendMessage(String message){
        printWriter.print("MESSAGE ");
        printWriter.println(message);
    }

    public void exit() {
        printWriter.println("END ");
        client.setConnect(false);
    }
}
