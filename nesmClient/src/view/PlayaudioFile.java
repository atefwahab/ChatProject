/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.File;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.*;


/**
 *
 * @author atef
 */
public class PlayaudioFile extends Thread {
    
    //Opening a new file
    File audioFile;
    //an audio input stream that can read audio file in frames
    AudioInputStream audioStream;
    AudioFormat format;
    DataLine.Info info;
    Clip audioClip;
    
    public PlayaudioFile(URL fileName) {
        
        //audioFile=new File(fileName);
        
        
        try{
        //get the audioStream from file 
        //audioStream=AudioSystem.getAudioInputStream(audioFile);
        audioStream=AudioSystem.getAudioInputStream(fileName);
        //Getting audio format
        format = audioStream.getFormat();
        
        info = new DataLine.Info(Clip.class, format);
        
        //Obtain the Clip: 
         audioClip = (Clip) AudioSystem.getLine(info);
        
        //start the audio file
        audioClip.open(audioStream);
        audioClip.start();
           /* sleep(1000);
        audioClip.close();
        audioStream.close();
        */
        
        
        }catch(Exception ex){
        
            ex.printStackTrace();
        }
        
    
    }
    
  
}
