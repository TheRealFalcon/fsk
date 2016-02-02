package com.falconware.fsk;


public class FskConverter {
	private OutputConverter mOutputConverter;

	public FskConverter() {
		mOutputConverter = new OutputConverter();
		Thread powerhouse = new Thread(mOutputConverter);
		powerhouse.setDaemon(true);
		powerhouse.start();
	}
	
	
	public void putString(String stringData) throws InterruptedException {
		byte[] data = stringData.getBytes();
		for (int stringIndex=0; stringIndex<data.length; stringIndex++) {
			mOutputConverter.convertByte(data[stringIndex]);
		}
	}
	
	public byte[] getByteStream() {
		return mOutputConverter.getByteList();
		
	}
	
	//Move this somewhere better
//    private byte[] toByteArray(List<Byte> list) {
//    	byte[] ret = new byte[list.size()];
//    	for (int i=0; i<ret.length; i++) {
//    		ret[i] = list.get(i);
//    	}
//    	return ret;
//    } 
	
	
}
