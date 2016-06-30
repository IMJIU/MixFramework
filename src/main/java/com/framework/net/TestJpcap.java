package com.framework.net;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;

import net.sourceforge.jpcap.net.EthernetPacket;
import net.sourceforge.jpcap.net.IPPacket;
import net.sourceforge.jpcap.net.TCPPacket;

import com.sun.xml.internal.ws.api.message.Packet;

public class TestJpcap {

	public static void main(String[] args) throws IOException {
		SendPacket();
	} 
	
	// 获取网络接口列表
	public static void Demo() {
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		for (int i = 0; i < devices.length; i++) {
			System.out.println(i + ": " + devices[i].name + "(" + devices[i].description + ")");
			System.out.println(" datalink: " + devices[i].datalink_name + "(" + devices[i].datalink_description + ")");
			System.out.print(" MAC address:");
			for (byte b : devices[i].mac_address)
				System.out.print(Integer.toHexString(b & 0xff) + ":");
			System.out.println();
			for (NetworkInterfaceAddress a : devices[i].addresses)
				System.out.println(" address:" + a.address + " " + a.subnet + " " + a.broadcast);
		}
	} 
	
	// 使用回调方法, 从网络接口捕获数据包
	public static void CallBackReceiver() throws IOException {
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		int index = 1;
		JpcapCaptor captor = JpcapCaptor.openDevice(devices[index], 65535, false, 20);
		captor.processPacket(20, new PacketPrinter());
		// captor.loopPacket(20, new PacketPrinter());
		captor.close();
	} 
	
	// 使用逐个捕获方法, 从网络接口捕获数据包
	public static void OneByOneReceiver() throws IOException {
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		int index = 1;
		JpcapCaptor captor = JpcapCaptor.openDevice(devices[index], 65535, false, 20);
		// 设置过滤器
		captor.setFilter("ip and tcp", true);
		for (int i = 0; i < 10; i++)
			System.out.println(captor.getPacket());
	} 
	
	//
	public static void PacketWriteFile() throws IOException {
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		int index = 1;
		JpcapCaptor captor = JpcapCaptor.openDevice(devices[index], 65535, false, 20);
		JpcapWriter writer = JpcapWriter.openDumpFile(captor, "yanrong");
		for (int i = 0; i < 10; i++)
			writer.writePacket(captor.getPacket());
		writer.close();
	} 
	
	//
	public static void PacketReadFile() throws IOException {
		JpcapCaptor captor = JpcapCaptor.openFile("yanrong");
		Packet packet;
		while (true) {
			packet = captor.getPacket();
			if (packet == null) // || packet == Packet.EOF)
				break;
			System.out.println(packet.toString());
		}
	} 
	
	// 通过网络接口发送数据包
	public static void SendPacket() throws IOException {
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		JpcapSender sender = JpcapSender.openDevice(devices[1]);
		TCPPacket p = new TCPPacket(12, 23, 56, 78, false, false, false, false, true, true, true, true, 10, 10);
		p.setIPv4Parameter(0, false, false, false, 0, false, false, false, 0, 1010101, 100, IPPacket.IPPROTO_TCP, InetAddress.getByName("www.baidu.com"), InetAddress.getByName("localhost"));
		p.data = ("data").getBytes();
		EthernetPacket ether = new EthernetPacket();
		ether.frametype = EthernetPacket.ETHERTYPE_IP;
		ether.src_mac = new byte[] { (byte) 0, (byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5 };
		ether.dst_mac = new byte[] { (byte) 0, (byte) 6, (byte) 7, (byte) 8, (byte) 9, (byte) 10 };
		p.datalink = ether;
		sender.sendPacket(p);
		sender.close();
		System.out.println(p);
	}
}

/** 使用回调方法从网络获取数据包时用到 */
class PacketPrinter implements PacketReceiver {

	@Override
	public void receivePacket(Packet packet) {
		System.out.println(packet.toString());
	}
}