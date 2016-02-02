package com.falconware.fsk;

import android.media.AudioManager;
import android.media.AudioTrack;

public class FreqGenerator {
	private static final double HIGH_FREQUENCY = 2200.0;
	private static final double LOW_FREQUENCY = 1200.0;
	private static final int DURATION = 100;  //in milliseconds
	private static final int SAMPLE_RATE = AudioTrack.getNativeOutputSampleRate(AudioManager.STREAM_MUSIC);
	private static final int BUFFER_SIZE = SAMPLE_RATE * DURATION / 1000;
	private static final byte[] highSamples = getSamples(HIGH_FREQUENCY, SAMPLE_RATE);
	private static final byte[] lowSamples = getSamples(LOW_FREQUENCY, SAMPLE_RATE);
	
	public static int getSampleRate() {
		return SAMPLE_RATE;
	}
	
	public static int getBufferSize() {
		return BUFFER_SIZE;
	}
	
	public static byte[] getHigh() {
//		return getSamples(HIGH_FREQUENCY, SAMPLE_RATE);
		return highSamples;
	}
	
	public static byte[] getLow() {
//		return getSamples(LOW_FREQUENCY, SAMPLE_RATE);
		return lowSamples;
	}
	
	private static byte[] getSamples(double frequency, int sampleRate) {
		byte[] samples = new byte[BUFFER_SIZE];
		for (int i=0; i<samples.length; i++) {
			samples[i] = (byte) ((int) 128 * Math.sin(Math.PI * 2 * i / (sampleRate/frequency)) + 128);		
		}
		return samples;
	}
	

	
}
