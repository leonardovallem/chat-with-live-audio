package com.djvl.tpredes.server;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.TargetDataLine;
import java.io.ByteArrayOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UdpServer {
    private static UdpServer instance;
    private static Thread thread;
    private static int portaServidor = 6789;

    public static UdpServer getInstance() {
        if (instance == null) instance = new UdpServer();
        return instance;
    }

    public static Thread getThread() {
        if (thread == null) thread = new Thread(() -> {
            try {
                System.out.println("[Thread-" + Thread.activeCount() + "] Initializing UDP Server..");
                getInstance().run();
                System.out.println("[Thread-" + Thread.activeCount() + "] Finishing UDP Server..");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
        return thread;
    }

    public void run() throws Exception {
        AudioFormat format = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, true);
        TargetDataLine microphone;
        SourceDataLine speakers;
        try {
            microphone = AudioSystem.getTargetDataLine(format);

            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            microphone = (TargetDataLine) AudioSystem.getLine(info);
            microphone.open(format);

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int numBytesRead;
            int CHUNK_SIZE = 1024;
            byte[] data = new byte[microphone.getBufferSize() / 5];
            microphone.start();

            DataLine.Info dataLineInfo = new DataLine.Info(SourceDataLine.class, format);
            speakers = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
            speakers.open(format);
            speakers.start();

            // Configure the ip and port
            String hostname = "localhost";

            InetAddress address = InetAddress.getByName(hostname);
            DatagramSocket socket = new DatagramSocket();
            for (; ; ) {
                numBytesRead = microphone.read(data, 0, CHUNK_SIZE);
                //  bytesRead += numBytesRead;
                // write the mic data to a stream for use later
                out.write(data, 0, numBytesRead);
                // write mic data to stream for immediate playback
                speakers.write(data, 0, numBytesRead);
                DatagramPacket request = new DatagramPacket(data, numBytesRead, address, portaServidor);
                socket.send(request);
            }
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }
}
