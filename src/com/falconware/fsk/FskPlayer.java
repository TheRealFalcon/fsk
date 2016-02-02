package com.falconware.fsk;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

public class FskPlayer implements Runnable {
	private FskConverter mConverter;
	//private AudioTrack mTrack;
	
	public FskPlayer(FskConverter converter) {
		mConverter = converter;
	}

	@Override
	public void run() {
		byte[] firstStream = mConverter.getByteStream();
		
		int sampleRate = FreqGenerator.getSampleRate(); 
		//int streamingBufferSize = AudioTrack.getMinBufferSize(sampleRate, AudioFormat.CHANNEL_OUT_MONO, AudioManager.STREAM_MUSIC)*20;
		//System.out.println(streamingBufferSize);
		AudioTrack track = new AudioTrack(AudioManager.STREAM_MUSIC, sampleRate, AudioFormat.CHANNEL_OUT_MONO, 
				AudioFormat.ENCODING_PCM_8BIT, firstStream.length, AudioTrack.MODE_STREAM); 
        track.setPlaybackRate(sampleRate);
		
		track.write(firstStream, 0, firstStream.length); 
		track.play();
		while (true) {
			byte[] stream = mConverter.getByteStream();
			track.write(stream, 0, stream.length);
			//track.play();
		}
	}

}
