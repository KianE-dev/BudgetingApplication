import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
public class FinancialGoalFrame implements ActionListener
{
   JFrame f=new JFrame("Financial Goal");
   JLabel header=new JLabel("This account's goal is:");
   JLabel goal=new JLabel();
   JLabel verdict=new JLabel();
   JLabel reachability=new JLabel();
   JButton closeButton=new JButton("Close");
   Account ac;
   Client cl;
   ImageIcon appIcon=new ImageIcon("AppIcon.png");
   public FinancialGoalFrame(Account acc, Client cl)
   {
       ac=acc;
       this.cl=cl;
       f.setSize(700, 400);
       f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       f.setLayout(null);
       f.getContentPane().setBackground(new Color(0x57E69B));
       f.setIconImage(appIcon.getImage());
       DateTimeFormatter format = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
       String dateFormatted = ac.getGoalEnding().format(format);
       if (ac instanceof SavingsAccount)
       {
           goal.setText("I want $" +ac.getGoalAmount()+ " in this savings account by "+dateFormatted);
       }
       else
       {
           goal.setText("I want $"+ac.getGoalAmount()+" in this checking account by "+dateFormatted);
       }
       verdict.setText(ac.getMessageVerdict());
       long daysSince=ChronoUnit.DAYS.between(cl.getReferenceDate(), LocalDate.now());
       long payFreq=cl.getPayFrequency();
       long daysUntilNext=payFreq-daysSince;
       if (daysUntilNext < 0)
       {
           daysUntilNext= payFreq-(daysSince % payFreq);
       }
       LocalDate day= LocalDate.now().plusDays(daysUntilNext);
       double bal= ac.getBalance();
       if (!day.isAfter(ac.getGoalEnding()))
       {
           bal+= ac.getPayment();
       }
       while (!day.isAfter(ac.getGoalEnding()))
       {
           day= day.plusDays(payFreq);
           bal+= ac.getPayment();
       }
      switch (ac.getMessageVerdict())
      {
          case "Goal has been reached!" -> reachability.setText("Reachability: Goal was reached. ");
          case "Goal ending has not been reached yet." ->
          {
              if (bal<ac.getGoalAmount())
              {
                   reachability.setText("Reachability: This goal is not reachable with only your pay frequency cycle because you will be $"+(ac.getGoalAmount()-bal)+" short.");
              }
               else
               {
                   reachability.setText("Reachability: This goal is reachable with only your pay frequency cycle.");
               }
           }
           case "Goal has been exceeded, Great job!" -> reachability.setText("Reachability: This goal was exceeded. Congratulations :D");
           default -> reachability.setText("Reachability: This goal was not reached. T_T");
       }
       header.setBounds(10,25,600,40);
       header.setFont(new Font("Bodoni MT",Font.BOLD,30));
       goal.setBounds(25,75,700,40);
       goal.setFont(new Font("Calisto MT",Font.BOLD,22));
       verdict.setBounds(45,125,600,40);
       verdict.setFont(new Font("Calisto MT",Font.BOLD,20));
       reachability.setBounds(25,160,700,40);
       reachability.setFont(new Font("Calisto MT",Font.BOLD,13));
       closeButton.setBounds(610,0,75,40);
       closeButton.addActionListener(this);
       f.add(header);
       f.add(goal);
       f.add(verdict);   
       f.add(reachability);
       f.add(closeButton);
       f.setVisible(true);     
   }

    @Override
    public void actionPerformed(ActionEvent e)
    {
       f.dispose();
    }
}
