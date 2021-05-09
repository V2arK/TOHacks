import java.io.File;
//import javafx.scene.media.*;


import java.io.IOException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

 class AudioPlayer extends Thread{
		 byte tempBuffer[] = new byte[10000];
		 AudioFormat audioFormat;
		 Clip clip;
		 AudioInputStream ais = null;
		 File audio;
		 SourceDataLine sourceDataLine;
		 boolean stopPlayBack = false;
		//define area/ sound relevent
		 
		 
		
		public  AudioPlayer (String Address, boolean loop) {
			
			//first input: Address of the file.
			//second input: loop or not.
			
			try{
	            audio = new File(Address);
	            clip = AudioSystem.getClip();
	            ais = AudioSystem.getAudioInputStream(audio);    
	            //getting the input of the background music.
	            
	            audioFormat = ais.getFormat();
	    		//get the format of the audio clip.
	    		
	    		DataLine.Info dataLineInfo = 
	    				new DataLine.Info(
	    						SourceDataLine.class, 
	    						audioFormat
	    						);
	    		//get the DataLine info.
	    		
	    		sourceDataLine = 
	    				(SourceDataLine)AudioSystem.getLine(
	    						dataLineInfo
	    						);
	    		play(loop);

	        }
	        catch (IOException e){
	            e.printStackTrace();
	        } 
			catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
			catch (UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//load the audio file.
							
			
		}
		
		public  void play(boolean loop) {
			
			try {
				
				sourceDataLine.open(audioFormat);
				
				while(loop) {
					
					sourceDataLine.start();
					
					int cnt;
					  //Keep looping until the input read method
				      // returns -1 for empty stream causing
				      // stopPlayback to switch from false to
				      // true.
				      while((cnt = ais.read(
				           tempBuffer,0,tempBuffer.length)) != -1
				                       && stopPlayBack == false){
				        if(cnt > 0){
				          //Write data to the internal buffer of
				          // the data line where it will be
				          // delivered to the speaker.
				          sourceDataLine.write(
				                             tempBuffer, 0, cnt);
				        }//end if
				      }//end while
				      //Block and wait for internal buffer of the
				      // data line to empty.
				      sourceDataLine.drain();
				      sourceDataLine.close();
				}
			      //Prepare to playback another file
			}
			catch(Exception e) {
				
				e.printStackTrace();
				//System.exit(0);
			}
			
		}
	}
	//a inner class.