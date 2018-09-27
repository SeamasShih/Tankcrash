package com.honhai.foxconn.tankcrash.network;

public interface TcpReceiveListener {
    void onTcpMessageReceive(String message);
}
