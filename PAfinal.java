import javax.swing.*;
import java.lang.Math;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.*;
import javax.sound.sampled.*;
import javax.swing.*;
import java.util.*;
// author: Elon Mathieson emath@brandeis.edu


/** BAC, calculates the blood alcohol concentration of a person for a specific time period, amount drank,
body weight, and percent alcohol in the drink
Uses a widget for sound and another widget being the slider
**/

public class PAfinal extends JPanel{
  private double time = 0;
  private double ozAlc = 0;
  private double percAlc = 0;



  public PAfinal()throws FileNotFoundException, IOException{
    HashMap<String,String> drinks = new HashMap<>();
	
	File text=new File("Fav.txt");
	
    ArrayList<String> favChoices = new ArrayList<String>();
	
	Scanner favDrinks=new Scanner(text);
	while(favDrinks.hasNextLine()){
		String drink=favDrinks.next();
		System.out.println(drink);
		drinks.put(drink, favDrinks.next());
		favChoices.add(drink);
	}


    JPanel content = this;
    content.setLayout(new BorderLayout(5,5));

    JPanel headerPanel = new JPanel();
    headerPanel.add(new JLabel("<html><h1> Blood Alcohol Calculator </h1></html"));
    content.add(headerPanel,BorderLayout.PAGE_START);

    JPanel top = new JPanel();
    top.setLayout(new GridLayout(0,4));
    JTextArea drinkName = new JTextArea("Add Drink Name Here:");
    JTextArea drinkPerc = new JTextArea("Add Drink's Alcohol Percent Here:");
    JButton save = new JButton("Save");
	JButton get = new JButton("Get");
    top.add(drinkName);
    top.add(drinkPerc);
    top.add(save);
	top.add(get);
    //String[] favChoices = new String[] {};
    String[] array = favChoices.toArray(new String[favChoices.size()]);

     // add to the parent container (a JFrame):
    JComboBox<String> favList = new JComboBox(array);
    top.add(favList);
     // get the selected item:
    String selectedFav = (String) favList.getSelectedItem();
    content.add(top, BorderLayout.LINE_END);
    // content.add(save,BorderLayout.LINE_END);
    // content.add(infoTA,BorderLayout.LINE_END);

    JPanel inputForm = new JPanel();
    JCheckBox male = new JCheckBox("Male");
    JCheckBox female = new JCheckBox("Female");
		JTextField ounces = new JTextField(" ");
		JTextField percent = new JTextField(" ");
    JTextField hours = new JTextField(" ");
    JSlider slider = new JSlider(JSlider.HORIZONTAL, 0, 400, 200);
    slider.setMajorTickSpacing(100);
    slider.setMinorTickSpacing(50);
    slider.setPaintTicks(true);
    slider.setPaintLabels(true);

		JButton go = new JButton("Calculate Blood Alcohol Level");
		JButton clear = new JButton("Reset");
    JButton music = new JButton("Play");
		JLabel bloodAlc = new JLabel(" ");
    inputForm.setLayout(new GridLayout(0,2));
    inputForm.add(male);
    inputForm.add(female);
		inputForm.add(new JLabel("Ounces:"));inputForm.add(ounces);
		inputForm.add(new JLabel("Percent:")); inputForm.add(percent);
    inputForm.add(new JLabel("Hours:")); inputForm.add(hours);
    inputForm.add(new JLabel("Weight:")); inputForm.add(slider);
		inputForm.add(clear);inputForm.add(go);
    inputForm.add(music);inputForm.add(new JLabel("Rough Estimation of BAC:"));
    inputForm.add(bloodAlc);
    content.add(inputForm,BorderLayout.PAGE_END);

    go.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
          double value = slider.getValue();
		      double ozAlc = Double.parseDouble(ounces.getText());
          double percAlc = Double.parseDouble(percent.getText());
		      double time = Double.parseDouble(hours.getText());
          double r;
          if  (male.isSelected()){
            r = 0.73;
          }
          else {
            r = 0.66;
          }
          //double r = 0.695; // average between constant of males (.73) and females(.66)
          double w = value;
          double BACalc = ((ozAlc *(percAlc/100))*5.14/(w*r))-.015*time;
          if(BACalc > 0.100){
            bloodAlc.setText("Probably should slow down/stop BAC is:  " + BACalc);
          } else if(BACalc < 0.100){
          bloodAlc.setText("Have a good time! Responsibly! " + BACalc);
         }
    }
  });

  save.addActionListener(new ActionListener(){
    public void actionPerformed(ActionEvent e){

      String drink = drinkName.getText();
      String perc = drinkPerc.getText();
	  try{
		FileWriter record=new FileWriter(text, true);
		record.write("\n"+drink+" "+perc);
		System.out.println("Saved to File");
		record.close();
	  }catch(Exception IOException){
	  }
      drinks.put(drink,perc);
      System.out.println("Saved");

      favChoices.add(drink);
      favList.addItem(favChoices.get(favChoices.size() - 1));
      System.out.println(favChoices.toString());
      drinkName.setText("Name");
      drinkPerc.setText("Alc Content");

    }
  });
  
  get.addActionListener(new ActionListener(){
    public void actionPerformed(ActionEvent e){

      drinkName.setText((String) favList.getSelectedItem());
      drinkPerc.setText(drinks.get((String) favList.getSelectedItem()));
      percent.setText(drinks.get((String) favList.getSelectedItem()));
      System.out.println("Got");


    }
  });

		clear.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				ounces.setText("");
				percent.setText("");
        hours.setText("");
				bloodAlc.setText(" ");
        drinkName.setText("");
        drinkPerc.setText("");
			}
		});



  music.addActionListener(new ActionListener(){
    public void actionPerformed(ActionEvent e){
    music();
    }
  });


 }

  private static void createAndShowGUI()throws FileNotFoundException, IOException{
      //Create and set up the window.
      JFrame frame = new JFrame("PAfinal");
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      //Create and set up the content pane.
      JComponent newContentPane = new PAfinal();
      newContentPane.setOpaque(true); //content panes must be opaque
      frame.setContentPane(newContentPane);

      //Display the window.
      frame.pack();
      frame.setVisible(true);
      frame.pack();
      frame.setSize(800,525);
      frame.setLocation(100,100);

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


public static void main(String[] args) throws FileNotFoundException, IOException{
    //Schedule a job for the event-dispatching thread:
    //creating and showing this application's GUI.
    javax.swing.SwingUtilities.invokeLater(new Runnable() {
        public void run(){
			try{
				createAndShowGUI();
			}catch (Exception FileNotFoundException){
			} // basically creates the GUI and the frame apparently
        }
    });
   }

}
