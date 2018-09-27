package com.honhai.foxconn.tankcrash.network;

import com.honhai.foxconn.tankcrash.MainActivity;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class TankClient {
    private final String TAG = "TankClient";

    private static TankClient tankClient;
    private static ReceiveListener receiveListener;
    private DatagramSocket socket;
    private InetAddress address;
    private int port;

    private TankClient(String ip, int port) {
        this.port = port;

        try {
            socket = new DatagramSocket();
            address = InetAddress.getByName(ip);
            startReceiveMessage();
        } catch (SocketException | UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public static TankClient getTankClient(Object object) {
        if (tankClient == null) {
            tankClient = new TankClient(MainActivity.serverIp, MainActivity.port);
        }
        receiveListener = (ReceiveListener) object;
        return tankClient;
    }

    private void startReceiveMessage() {
        byte[] receiveBuffer = new byte[512];

        new Thread(() -> {
            while (true) {
                String returnString;
                DatagramPacket packet = new DatagramPacket(receiveBuffer, receiveBuffer.length);
                try {
                    socket.receive(packet);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                returnString = new String(packet.getData(), 0, packet.getLength());
                receiveListener.onMessageReceive(returnString);

                if (returnString.equals("end")) {
                    break;
                }
            }
        }).start();
    }

    public void sendMessage(String message) {
        new Thread(() -> {
            try {
                byte[] sendBuffer = message.toLowerCase().getBytes();

                DatagramPacket packet = new DatagramPacket(sendBuffer, sendBuffer.length, address, port);
                socket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (message.equals("end")) {
                closeSocket();
            }
        }).start();
    }

    private void closeSocket() {
        socket.close();
    }
}