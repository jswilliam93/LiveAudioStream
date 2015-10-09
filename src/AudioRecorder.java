import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;

public class AudioRecorder {
	TargetDataLine line;
	byte[] packet = new byte[64000];

	AudioFormat getAudioFormat() {
		// 4096 byte .. 44100 sample * 2 = 88200 byte
		float sampleRate = 22100;
		int sampleSizeInBits = 8;
		int channels = 2;
		boolean signed = true;
		boolean bigEndian = true;
		AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
		return format;
	}

	void start() {
		try {
			AudioFormat format = getAudioFormat();
			DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
			if (!AudioSystem.isLineSupported(info)) {
				System.out.println("Line not supported");
				System.exit(0);
			}
			line = (TargetDataLine) AudioSystem.getLine(info);
			line.open(format);
			line.start();
			System.out.println("Start capturing...");
			while (true) {
				line.read(packet, 0, packet.length);
				Client.UDPsend(packet);
				System.out.println("Packet sent!");
			}
		} catch (LineUnavailableException ex) {
			ex.printStackTrace();
		}
	}
}