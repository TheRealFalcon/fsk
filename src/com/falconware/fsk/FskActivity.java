package com.falconware.fsk;

import android.app.Activity;
import android.os.Bundle;

public class FskActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        System.out.println("Starting");
        
        FskConverter converter = new FskConverter();
                
        //FskPlayer player = new FskPlayer(converter);
        Thread player = new Thread(new FskPlayer(converter));
        player.setDaemon(true);
        player.start();
        
        try {
        	converter.putString("Hello World!");
        } catch (InterruptedException e) {
        	e.printStackTrace();
        	System.err.println("Shouldn't get interrupted here...");
        	throw new RuntimeException();
        }
        
    } 

}