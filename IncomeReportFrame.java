import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
public class IncomeReportFrame implements ActionListener
{
  JFrame f=new JFrame("Income Report");
  JLabel grossIncomeLabel=new JLabel();
  JLabel highestIncome=new JLabel();
  JLabel lowestIncome=new JLabel();
  JLabel spending1=new JLabel();
  JLabel spending2=new JLabel();
  JLabel spendingCategories=new JLabel("Spending Categories:");
  JButton close=new JButton("Close");
  Client cl;
  ImageIcon appIcon=new ImageIcon("AppIcon.png");
  IncomeReportFrame(Client c)
  {
      cl=c;
      f.setSize(800,800);
      f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      f.setLayout(null);
      f.getContentPane().setBackground(new Color(0x57E69B));
      f.setIconImage(appIcon.getImage());
      double grossIncome=cl.getIncome();
      switch (cl.getPayFrequency())
      {
          case 7 -> grossIncome*=52;
          case 14 -> grossIncome*=26;
          case 15 -> grossIncome*=24;
          case 30 -> grossIncome*=12;
          case 120 -> grossIncome*=4;
          case 183 -> grossIncome*=2;
      }
      grossIncomeLabel.setText("Your Yearly Gross Income is: $"+grossIncome);
      grossIncomeLabel.setFont(new Font("Calisto MT",Font.PLAIN,20));
      grossIncomeLabel.setBounds(20,50,800,40);
      close.setBounds(700,25,75,40);
      close.addActionListener(this);
      IncomeSource highest=new IncomeSource(67,"Quiche",0);
      IncomeSource lowest=new IncomeSource(41,"Peanut Butta",Double.MAX_VALUE);
      for (int i=0; i<cl.listOfIncomeSources(); i++)
      {
          IncomeSource is=cl.findIncomeSourceWithPosition(i);
          if (is.getAmount()>highest.getAmount())
          {
              highest=is;
          }
          if (is.getAmount()<lowest.getAmount())
          {
              lowest=is;
          }
      }
      highestIncome.setText("Your highest income source is: "+highest.getName()+", getting $"+highest.getAmount()+" per in real life paycheck");
      lowestIncome.setText("Your lowest income source is: "+lowest.getName()+", getting $"+lowest.getAmount()+" per in real life paycheck");
      highestIncome.setFont(new Font("Calisto MT",Font.PLAIN,20));
      lowestIncome.setFont(new Font("Calisto MT",Font.PLAIN,20));
      highestIncome.setBounds(20,100,800,40);
      lowestIncome.setBounds(20,150,800,40);
      if (cl.getSpending()>cl.getIncome())
      {
          spending1.setText("Your spending is higher than your income.");
          spending2.setText("Consider lowering your spending amounts listed below.");
      }
      else if (cl.getSpending()==cl.getIncome())
      {
          spending1.setText("Your spending is equal to your income.");
          spending2.setText("Consider lowering your spending amounts listed below.");
      }
      else
      {
          spending1.setText("Your spending is lower than your income.");
          spending2.setText("Good job monitoring your spending! :D");
      }
      spending1.setBounds(20,200,800,40);
      spending2.setBounds(20,225,800,40);
      spendingCategories.setBounds(20,250,800,40);
      spending1.setFont(new Font("Calisto MT",Font.PLAIN,20));
      spending2.setFont(new Font("Calisto MT",Font.PLAIN,20));
      spendingCategories.setFont(new Font("Calisto MT",Font.PLAIN,20));
      int j=275;
      for (int i=0; i<cl.numOfSpendingCategories(); i++)
      {
          SpendingCategory sc=cl.findSpendingCategoryWithPosition(i);
          JLabel x=new JLabel("Category "+(i+1)+" -> "+sc);
          x.setBounds(20,j,800,40);
          x.setFont(new Font("Calisto MT",Font.PLAIN,20));
          f.add(x);
          j+=25;
      }
      f.add(grossIncomeLabel);
      f.add(highestIncome);
      f.add(lowestIncome);
      f.add(spending1);
      f.add(spending2);
      f.add(close);
      f.setVisible(true);
  }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource()==close)
        {
            f.dispose();
        }
    }
}
