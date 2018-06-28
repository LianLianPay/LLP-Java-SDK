package com.lianpay.util;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ClientUDP{
    /**
     * 从序号生成器取回序列号
     * @param sRecBuf
     * @return
     * @throws IOException
     */
    public synchronized String ClientSendForUDP(byte[] prefix,
            String serviceId, String host, String sport)
    {
        String returnStr = null;
        DatagramSocket datagramSocket = null;
        try
        {
            datagramSocket = new DatagramSocket();
            byte[] buffer = serviceId.getBytes();
            int sendLength = prefix.length + buffer.length;
            byte[] sendByte = new byte[sendLength];
            for (int i = 0; i < prefix.length; i++)
            {
                sendByte[i] = prefix[i];
            }
            for (int i = prefix.length, j = 0; i < sendLength; i++, j++)
            {
                sendByte[i] = buffer[j];
            }
            InetAddress address = InetAddress.getByName(host);
            DatagramPacket packet = new DatagramPacket(sendByte, sendLength,
                    address, Integer.parseInt(sport));
            datagramSocket.setSoTimeout(30000);
            datagramSocket.send(packet);
            byte[] b = new byte[100];
            DatagramPacket packet2 = new DatagramPacket(b, 100);
            datagramSocket.receive(packet2);
            byte[] b2 = packet2.getData();
            int l = packet2.getLength();
            returnStr = new String(b2, 0, l);
        } catch (IOException e)
        {
            e.printStackTrace();
        } finally
        {
            datagramSocket.close();
        }
        return returnStr;
    }
}
