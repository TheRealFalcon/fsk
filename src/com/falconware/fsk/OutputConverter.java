package com.falconware.fsk;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;

public class OutputConverter implements Runnable {
	private ArrayBlockingQueue<Byte> mInputQueue;
	private ArrayBlockingQueue<byte[]> mOutputQueue;
	
	public OutputConverter() {
		mInputQueue = new ArrayBlockingQueue<Byte>(100, true);
		mOutputQueue = new ArrayBlockingQueue<byte[]>(100, true);
	}
	
	public void convertByte(byte data) {
		try {
			mInputQueue.put(data);
		} catch (InterruptedException e) {
			handleInterruption(e);
		}
	}
	
	public byte[] getByteList() {
		try {
			return mOutputQueue.take();
		} catch (InterruptedException e) {
			handleInterruption(e);
			return null;  //unreachable?
		}
//	    List<List<byte[]>> listList = new LinkedList<List<byte[]>>();
//		mOutputQueue.drainTo(listList);
//		return listList;
	}

	@Override
	public void run() {
		try {
			//Using SoftModem, the protocol is...
			// 1 start bit (LOW)
			// 8 data bits, LSB first
			// 1 stop bit (HIGH)
			// 1 push bit (HIGH)
			//TODO: Consider refactoring the following with a stategy pattern
			while (true) {
				byte currentByte = mInputQueue.take();
				int mask = 0x80;  //Start at left side
				List<Byte> convertedByteList = new LinkedList<Byte>();
				for (int counter=0; counter<11; counter++) {
					byte[] convertedData;
					
					// Start bit					
					if (counter==0) {
						convertedData = FreqGenerator.getLow();						
					}
					
					// 8 data bits
					else if (counter > 0 && counter < 9) {
						if ((currentByte & mask) == 0) {
							convertedData = FreqGenerator.getLow();
						}
						else {
							convertedData = FreqGenerator.getHigh();
						}
						mask = (mask>>1);
					}
					
					// Last 2 high bits
					else {
						convertedData = FreqGenerator.getHigh();
					}
					for (int index=0; index<convertedData.length; index++) {
						convertedByteList.add(convertedData[index]);
					}
					
				}
				mOutputQueue.put(toByteArray(convertedByteList));
			}
			
		} catch (InterruptedException e) {
			handleInterruption(e);
		}
	}
	
	//Move this somewhere better
    private byte[] toByteArray(List<Byte> list) {
    	byte[] ret = new byte[list.size()];
    	Iterator<Byte> iterator = list.iterator();
    	int i=0;
    	while (iterator.hasNext()) {
    		ret[i] = iterator.next();
    		i++;
    	}
//    	for (int i=0; i<ret.length; i++) {
//    		ret[i] = list.get(i);
//    	}
    	return ret;
    } 
	
	private void handleInterruption(InterruptedException e) {
		//handle by not handling :)
		e.printStackTrace();
		System.err.println("Converted thread interrupted!");
		System.err.println("You shouldn't be getting interrupted...");
		System.err.println("Exiting due to unexpected interrupt");
		throw new RuntimeException();
	}
}
