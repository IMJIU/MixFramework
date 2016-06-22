package com.framework.net;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;

import net.sourceforge.jpcap.net.EthernetPacket;
import net.sourceforge.jpcap.net.TCPPacket;

import com.sun.xml.internal.ws.api.message.Packet;

class Tcpdump implements PacketReceiver {

	public void receivePacket(Packet packet) {
		// System.out.println(packet);

		if (packet instanceof TCPPacket) {
			TCPPacket tcpPacket = (TCPPacket) packet;
			if (tcpPacket.src_ip.getHostAddress().equals(getIp())) {
				EthernetPacket ethernetPacket = (EthernetPacket) packet.datalink;
				System.out.print("源IP：" + tcpPacket.src_ip + "  目的IP：" + tcpPacket.dst_ip + "发送端口：" + tcpPacket.src_port + " 接收端口：" + tcpPacket.dst_port + "\n");
				System.out.print("源MAC：" + ethernetPacket.getSourceAddress() + " 目的MAC：" + ethernetPacket.getDestinationAddress() + "\n");
				System.out.print("协议：" + tcpPacket.protocol + "\n");
				System.out.print("数据：\n");
				try {
					System.out.println(new String(tcpPacket.data, "utf-8"));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// for (int i = 0; i < tcpPacket.data.length; i++)
				// System.out.print((char) tcpPacket.data[i]);
			}
		}

	}

	public static String getIp() {
		InetAddress inet = null;
		try {
			inet = InetAddress.getLocalHost();
			String ip = inet.getHostAddress(); // 在某些linux机器上取到的ip是host绑定的本机ip，即127.0.0.1
			return ip;
		} catch (Exception e) {

		}
		return null;
	}
}