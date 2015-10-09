import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class Server {
	@SuppressWarnings("resource")
	public static void main(String args[]) throws Exception {
		DatagramSocket serverSocket = new DatagramSocket(9876);
		byte[] receiveData = new byte[64000];
		byte[] sendData = new byte[64000];
		while (true) {
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			serverSocket.receive(receivePacket);
			AudioPlayer.play(receivePacket.getData());
			InetAddress IPAddress = receivePacket.getAddress();
			int port = receivePacket.getPort();
			sendData = "done".getBytes();
			DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
			serverSocket.send(sendPacket);
		}
	}
}
