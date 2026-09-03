

import java.awt.*;
import javax.swing.*;
public class LoadingBar
{
   JFrame f= new JFrame("Loading...");
   JProgressBar pb=new JProgressBar();
   ImageIcon icon=new ImageIcon("LoadingIcon.png");
   public LoadingBar()
   {
      f.setSize(500,500);
      f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      f.setLayout(null);
      f.getContentPane().setBackground(new Color(0x2AE06A));
      f.setIconImage(icon.getImage());
      pb.setValue(0);
      pb.setBounds(0,200,500,50);
      pb.setStringPainted(true);
      pb.setForeground(Color.blue);
      pb.setFont(new Font("Century",Font.BOLD,20));
      f.add(pb);
      f.setVisible(true);
      fill();
      f.dispose();
   }
   public void fill()
   {
     int count=0;
     while (count<=100)
     {
        pb.setValue(count);
        try
        {
            Thread.sleep(45);
        }
        catch (InterruptedException e)
        {
            System.out.println(e.getMessage());
        }
        count++;
     }
   }
}
