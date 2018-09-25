package com.honhai.foxconn.tankcrash.Network;

import android.content.Context;

import com.honhai.foxconn.tankcrash.MainActivity;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class TankClient {
    private DatagramSocket socket;
    private InetAddress address;
    private ReceiveListener receiveListener;
    private int port;

    public TankClient(Context context, String ip, int port) {
        receiveListener = (ReceiveListener) context;
        this.port = port;

        try {
            socket = new DatagramSocket();
            address = InetAddress.getByName(ip);
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        new Thread(() -> {
            String returnString;
            try {
                byte[] receiveBuffer = message.toLowerCase().getBytes();
                byte[] sendBuffer = new byte[1024];

                DatagramPacket packet = new DatagramPacket(receiveBuffer, receiveBuffer.length, address, port);
                socket.send(packet);
                packet = new DatagramPacket(sendBuffer, sendBuffer.length);
                socket.receive(packet);
                returnString = new String(packet.getData(), 0, packet.getLength());
            } catch (IOException e) {
                e.printStackTrace();
                returnString = "Exception happen";
            }
            receiveListener.onMessageReceive(returnString);

            if (message.equals("end")) {
                closeSocket();
            }
        }).start();
    }

    private void closeSocket() {
        socket.close();
    }
}