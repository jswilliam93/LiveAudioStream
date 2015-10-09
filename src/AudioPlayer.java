import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class AudioPlayer {
	public static AudioFormat getAudioFormat() {
		float sampleRate = 22100;
		int sampleSizeInBits = 8;
		int channels = 2;
		boolean signed = true;
		boolean bigEndian = true;
		AudioFormat format = new AudioFormat(sampleRate, sampleSizeInBits, channels, signed, bigEndian);
		return format;
	}

	public static void play(byte[] packet) {
		try {
			DataLine.Info info = new DataLine.Info(SourceDataLine.class, getAudioFormat());
			SourceDataLine audioLine = (SourceDataLine) AudioSystem.getLine(info);
			audioLine.open(getAudioFormat());
			audioLine.start();
			System.out.println("Started Playing...");
			audioLine.write(packet, 0, packet.length);
			audioLine.drain();
			audioLine.close();
			System.out.println("Finished Playing...");
		} catch (LineUnavailableException ex) {
			System.out.println("Audio line for playing back is unavailable.");
			ex.printStackTrace();
		}
	}
}