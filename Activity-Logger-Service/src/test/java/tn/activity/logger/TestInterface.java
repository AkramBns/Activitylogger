package tn.activity.logger;

import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;

public class TestInterface {

	public static void main(String[] args) {
		try {
			final InetAddress address = InetAddress.getByName("0.0.0.0");
			final Collection<NetworkInterface> interfaces = new ArrayList<NetworkInterface>();
			final Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
			while (networkInterfaces.hasMoreElements()) {
				NetworkInterface ne = networkInterfaces.nextElement();
				interfaces.add(ne);
				
				System.out.println(ne);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

}
