import javax.swing.*;
import java.lang.Math;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;
// author: Elon Mathieson emath@brandeis.edu


/** BAC, calculates the blood alcohol concentration of a person for a specific time period, amount drank,
body weight, and percent alcohol in the drink
Uses a widget for sound and another widget being the slider
**/

public class PA05 extends JPanel{
  private double time = 0;
  private double ozAlc = 0;
  private double percAlc = 0;

  public PA05(){
    JPanel content = this;
    content.setLayout(new BorderLayout());


    // Making the header
    JPanel headerPanel = new JPanel();
    headerPanel.add(new JLabel("<html><h1> Blood Alcohol Calculator </h1></html"));
    content.add(headerPanel,BorderLayout.PAGE_START);

    JTextArea logTA = new JTextArea(
       "Drink Log\n"
       );
    JTextArea infoTA = new JTextArea(
     "Add History of Drinks Here"
    );
    content.add(infoTA,BorderLayout.LINE_END);

    JPanel inputForm = new JPanel();
		JTextField ounces = new JTextField(" ");
		JTextField percent = new JTextField(" ");
    JTextField hours = new JTextField(" ");
    JTextField weight = new JTextField(" ");
		JButton go = new JButton("calculate Blood Alcohol Level");
		JButton clear = new JButton("reset");
    JButton music = new JButton("Play");
		JLabel bloodAlc = new JLabel(" ");
    inputForm.setLayout(new GridLayout(3,2));
		inputForm.add(new JLabel("ounces:"));inputForm.add(ounces);
		inputForm.add(new JLabel("percent:")); inputForm.add(percent);
    inputForm.add(new JLabel("hours")); inputForm.add(hours);
    inputForm.add(new JLabel("weight")); inputForm.add(weight);
		inputForm.add(clear);inputForm.add(go);inputForm.add(music);
		inputForm.add(new JLabel("Rough Estimation of BAC:"));inputForm.add(bloodAlc);
    content.add(inputForm,BorderLayout.PAGE_END);

    JPanel panel1 = new JPanel();
    JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 400, 100);
    slider.setMajorTickSpacing(10);
    slider.setMinorTickSpacing(1);
    slider.setPaintTicks(true);
    slider.setPaintLabels(true);
    panel1.add(slider);
    content.add(panel1, BorderLayout.LINE_START);

    go.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
          double value = slider.getValue();
          String s1 = value+"";
          weight.setText(s1);
		      double ozAlc = Double.parseDouble(ounces.getText());
          double percAlc = Double.parseDouble(percent.getText());
		      double time = Double.parseDouble(hours.getText());
          double r = 0.695; // average between constant of males (.73) and females(.66)
          double w = value;
          double BACalc = ozAlc*5.14/w*r-.015*time;
          bloodAlc.setText(" " + BACalc);


    }
  });


		clear.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ounces.setText("");
				percent.setText("");
        hours.setText("");
				bloodAlc.setText(" ");
			}
		});



  music.addActionListener(new ActionListener(){
    public void actionPerformed(ActionEvent e){
    music();
    }
  });


 }

  private static void createAndShowGUI(){
      //Create and set up the window.
      JFrame frame = new JFrame("PA05");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      //Create and set up the content pane.
      JComponent newContentPane = new PA05();
      newContentPane.setOpaque(true); //content panes must be opaque
      frame.setContentPane(newContentPane);

      //Display the window.
      frame.pack();
      frame.setVisible(true);
  }
/**
* @param no parameters
* this is a music method that aids in the playing of music
*
*/
  public static void music() {
  try {
   File file = new File("Song.wav");
   Clip clip = AudioSystem.getClip();
   clip.open(AudioSystem.getAudioInputStream(file));
   clip.start();
   Thread.sleep(clip.getMicrosecondLength());
  } catch (Exception e) {
   System.err.println(e.getMessage());
  }
 }


public static void main(String[] args) {
    //Schedule a job for the event-dispatching thread:
    //creating and showing this application's GUI.
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
        public void run() {
            createAndShowGUI(); // basically creates the GUI and the frame apparently
        }
    });
   }

}
