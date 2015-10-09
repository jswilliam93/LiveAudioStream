import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class Client {
	static AudioRecorder recorder = new AudioRecorder();
	static DatagramSocket clientSocket;
	
	@SuppressWarnings("resource")
	public static void main(String args[]) throws Exception {
		clientSocket = new DatagramSocket();
		System.out.println("Type in 'talk' or 'exit'");
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		String input = in.readLine();
		DatagramSocket clientSocket = new DatagramSocket();
		if (input.equals("talk")) {
			System.out.println("Start talking...");
			recorder.start();
			byte[] receiveData = new byte[64000];
			DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
			clientSocket.receive(receivePacket);
			String fromServer = new String(receivePacket.getData(), 0, receivePacket.getLength());
			System.out.println(fromServer);
			while (!fromServer.equals("done"))
				;
			main(args);
		} else {
			clientSocket.close();
			return;
		}
	}
	public static void UDPsend(byte[] packet){
		try{
		DatagramPacket sendPacket = new DatagramPacket(packet, packet.length, InetAddress.getByName("localhost"), 9876);
		clientSocket.send(sendPacket);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}