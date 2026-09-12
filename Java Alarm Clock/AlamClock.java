import javax.sound.sampled.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.time.LocalTime;
import java.util.Scanner;

public class AlamClock implements java.lang.Runnable {

    private final LocalTime alarmTime;
    private final String filePath;
    private final Scanner scanner;
    AlamClock(LocalTime alarmTime, String filePath, Scanner scanner){
        this.alarmTime = alarmTime;
        this.filePath = filePath;
        this.scanner = scanner;
    }

    @Override
    public void run(){
        try{
            while (LocalTime.now().isBefore(alarmTime)) {
                Thread.sleep(1000);

                LocalTime now = LocalTime.now();

                /*

                int hours = now.getHour();
                int minutes = now.getMinute();
                int seconds = now.getSecond();

                System.out.printf("\r%02d:%02d:%02d", hours, minutes, seconds);

                 */

                System.out.printf("\r%02d:%02d:%02d", now.getHour(), now.getMinute(), now.getSecond());
            }
        }
        catch (InterruptedException e){
            System.out.println("The thread was interrupted");
        }

        System.out.println("\nAlarm noises");

        playSound(filePath);
    }

    private void playSound(String filePath){

        File audioFile = new File(filePath);

        try (AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile)){
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            System.out.print("Press enter to stop the alarm: ");
            scanner.nextLine();
            clip.stop();

            scanner.close();
        }
        catch (UnsupportedAudioFileException e){
            System.out.println("Unsupported file format");
        }
        catch (LineUnavailableException e){
            System.out.println("Audio is not available");
        }
        catch (IOException e) {
            System.out.println("Error reading file");
        }
    }
}
