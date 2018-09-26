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
    private static TankClient tankClient;
    private DatagramSocket socket;
    private InetAddress address;
    private ReceiveListener receiveListener;
    private int port;

    private TankClient(Object object, String ip, int port) {
        receiveListener = (ReceiveListener) object;
        this.port = port;

        try {
            socket = new DatagramSocket();
            address = InetAddress.getByName(ip);
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static TankClient getTankClient(Object object) {
        if (tankClient == null) {
            tankClient = new TankClient(object, MainActivity.serverIp, MainActivity.port);
        }
        return tankClient;
    }

    public void sendMessage(String message) {
        new Thread(() -> {
            String returnString;
            try {
                byte[] sendBuffer = message.toLowerCase().getBytes();
                byte[] receiveBuffer = new byte[512];

                DatagramPacket packet = new DatagramPacket(sendBuffer, sendBuffer.length, address, port);
                socket.send(packet);

                packet = new DatagramPacket(receiveBuffer, receiveBuffer.length);
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