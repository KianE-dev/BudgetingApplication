
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.time.*;
import javax.swing.*;
import java.io.*;
public class MonetaryAccountManager implements ActionListener
{
  JFrame f=new JFrame("Manage Monetary Account");
  JLabel header=new JLabel("This is the current info for the account. You can change the data using the boxes and buttons below.");
  JLabel currentBalance=new JLabel();
  JLabel currentAccountNum=new JLabel();
  JLabel currentGoalAmount=new JLabel();   
  JLabel currentGoalEnding=new JLabel();
  JLabel numOfWithdrawals=new JLabel();
  JTextField deposit=new JTextField("Enter the amount of money you want to deposit from this account here...");
  JTextField withdrawal=new JTextField("Enter the amount of money you want to withdrawal from this account here...");
  JCheckBox dep=new JCheckBox("Check here if you want to do a deposit");
  JCheckBox withdr=new JCheckBox("Check here if you want to do a withdrawal");
  JTextField newAccNum=new JTextField("Enter the new account number here or don't change this text AT ALL if you want to keep its account number...");
  JTextField newGoalAmount=new JTextField("Enter the new goal amount here or don't change this text AT ALL if you want to keep its goal amount...");
  JTextField newGoalEnding=new JTextField("Enter the new goal ending date here or don't change this text AT ALL if you want to keep its current date...");
  JButton submit=new JButton("Submit");
  JButton close=new JButton("Close");
  JButton save=new JButton("Save");
  JButton remove=new JButton("Remove");
  JButton changeBal=new JButton("Change Balance");
  Account ac;
  Client cl;
  ClientDatabase database;
  JComboBox<Account> cb;
  ImageIcon appIcon=new ImageIcon("AppIcon.png");
  MonetaryAccountManager(Account acc, Client c, ClientDatabase db, JComboBox<Account> cb2)
  {
    ac=acc;
    cl=c;
    database=db;
    cb=cb2;
    f.setSize(650,750);
    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    f.setLayout(null);
    f.getContentPane().setBackground(new Color(0x57E69B));
    f.setIconImage(appIcon.getImage());
    header.setBounds(20,0,600,40);
    currentBalance.setText("Current Balance: "+ac.getBalance());
    currentAccountNum.setText("Current Account Number: "+ac.getAccountNumber());
    currentGoalAmount.setText("Current Goal Amount: "+ac.getGoalAmount());
    currentGoalEnding.setText("Current Goal Ending Date: "+ac.getGoalEnding());
    if (ac instanceof SavingsAccount)
    {
      SavingsAccount sac=(SavingsAccount)ac;
      numOfWithdrawals.setText("Current Number of Withdrawals: "+sac.numberOfWithdrawals());
    }  
    currentBalance.setBounds(25,25,600,40);
    deposit.setBounds(25,55,500,40);
    withdrawal.setBounds(25,95,500,40);
    dep.setBounds(25,155,500,40);
    withdr.setBounds(25,185,500,40);
    changeBal.setBounds(25,250,325,40);
    currentAccountNum.setBounds(25,300,600,40);
    newAccNum.setBounds(25,335,600,40);
    currentGoalAmount.setBounds(25,400,600,40);
    newGoalAmount.setBounds(25,435,600,40);
    currentGoalEnding.setBounds(25,500,600,40);
    newGoalEnding.setBounds(25,535,600,40);
    addFocusListener(deposit);
    addFocusListener(withdrawal);
    addFocusListener(newAccNum);
    addFocusListener(newGoalAmount);
    addFocusListener(newGoalEnding);
    submit.setBounds(150,600,350,50);
    submit.addActionListener(this);
    changeBal.addActionListener(this);
    close.addActionListener(this);
    close.setBounds(540,50,75,40);
    save.addActionListener(this);
    save.setBounds(530,600,75,40);
    remove.addActionListener(this);
    remove.setBounds(10,600,125,40);
    f.add(header);
    f.add(currentBalance);
    f.add(deposit);
    f.add(dep);
    f.add(withdrawal);
    f.add(withdr);
    f.add(changeBal);
    f.add(currentAccountNum);
    f.add(newAccNum);
    f.add(currentGoalAmount);
    f.add(newGoalAmount);
    f.add(currentGoalEnding);
    f.add(newGoalEnding); 
    f.add(submit);
    f.add(save);
    f.add(close);
    f.add(remove);
    f.setVisible(true);
  }

  @Override
    public void actionPerformed(ActionEvent e) 
    {
      if (e.getSource()==changeBal)
      {
        if (ac instanceof SavingsAccount)
        {
           if (dep.isSelected())
           {
              double amount=Double.parseDouble(deposit.getText());
              if (!ac.deposit(amount))
              {
                JOptionPane.showMessageDialog(null,"Entered value was not deposited, please check if value is less than 1", "ERROR", JOptionPane.ERROR_MESSAGE);
              }
              else
              {
                JOptionPane.showMessageDialog(null,"Entered value was deposited, your balance for this account is now $"+ac.getBalance(), "Success", JOptionPane.INFORMATION_MESSAGE);
                currentBalance.setText("Current Balance: "+ac.getBalance());
              }    
           }
           if (withdr.isSelected()) 
           {
              double amount=Double.parseDouble(withdrawal.getText());
              ac.withdrawal(amount);
              if (!ac.isValidWithdrawal())
              {
                JOptionPane.showMessageDialog(null,"Entered value was not withdrawn, please check if value is less than 0, number of withdrawals has been reached or value is greater than account balance.", "ERROR", JOptionPane.ERROR_MESSAGE);
              }
              else
              {
                JOptionPane.showMessageDialog(null,"Entered value was withdrawn, your balance for this account is now $"+ac.getBalance()+". Your number of withdrawals are now "+((SavingsAccount) ac).numberOfWithdrawals(), "Success", JOptionPane.INFORMATION_MESSAGE);
                currentBalance.setText("Current Balance: "+ac.getBalance());
              }    
           }
           if (!dep.isSelected() & !withdr.isSelected()) 
           {
              JOptionPane.showMessageDialog(null,"No checkbox was selected.", "ERROR", JOptionPane.ERROR_MESSAGE);
           } 
        }
        else
        {
          if (dep.isSelected())
           {
              Double amount=Double.valueOf(deposit.getText());
              if (!ac.deposit(amount))
              {
                JOptionPane.showMessageDialog(null,"Entered value was not deposited, please check if value is less than 1", "ERROR", JOptionPane.ERROR_MESSAGE);
              }
              else
              {
                JOptionPane.showMessageDialog(null,"Entered value was deposited, your balance for this account is now $"+ac.getBalance(), "Success", JOptionPane.INFORMATION_MESSAGE);
                currentBalance.setText("Current Balance: "+ac.getBalance());
              }    
           }
           if (withdr.isSelected()) 
           {
              Double amount=Double.valueOf(withdrawal.getText());
              ac.withdrawal(amount);
              if (!ac.isValidWithdrawal())
              {
                JOptionPane.showMessageDialog(null,"Entered value was not withdrawn, please check if value is less than 1.", "ERROR", JOptionPane.ERROR_MESSAGE);
              }
              else
              {
                JOptionPane.showMessageDialog(null,"Entered value was withdrawn, your balance for this account is now $"+ac.getBalance(), "Success", JOptionPane.INFORMATION_MESSAGE);
                currentBalance.setText("Current Balance: "+ac.getBalance());
                CheckingAccount cac=(CheckingAccount)ac;
                if (cac.isNegative())
                {
                  JOptionPane.showMessageDialog(null,"Entered value made your checking account negative!" , "Overdraft!", JOptionPane.WARNING_MESSAGE);
                }  
              }    
           }
           if (!dep.isSelected() & !withdr.isSelected()) 
           {
              JOptionPane.showMessageDialog(null,"No checkbox was selected.", "ERROR", JOptionPane.ERROR_MESSAGE);
           }
        }    
      }  
       if (e.getSource()==submit)
       {
         boolean uniqueAccNum=true;
         boolean changedAccNum=false;
         boolean changedGoalAm=false;
         boolean changedGoalEnd=false;
         int accNum=-99;
         double goalAmount=-99;
         LocalDate goalEnd=LocalDate.of(2020,Month.JANUARY,1);
         if (!newAccNum.getText().equalsIgnoreCase("Enter the new account number here or don't change this text AT ALL if you want to keep its account number..."))
         {
             changedAccNum=true;
             accNum=Integer.parseInt(newAccNum.getText());
         }
         if (!newGoalAmount.getText().equalsIgnoreCase("Enter the new goal amount here or don't change this text AT ALL if you want to keep its goal amount..."))
         {
             changedGoalAm=true;
             goalAmount=Double.parseDouble(newGoalAmount.getText());
         }
         if (!newGoalEnding.getText().equalsIgnoreCase("Enter the new goal ending date here or don't change this text AT ALL if you want to keep its current date..."))
         {
             changedGoalEnd=true;
             String[] sections=newGoalEnding.getText().strip().split("/");
             goalEnd=LocalDate.of(Integer.parseInt(sections[0]),Month.valueOf(sections[1]),Integer.parseInt(sections[2]));
         }
           if (changedAccNum)
           {
             if (ac.getAccountNumber() != accNum)
             {
                 for (int i = 0; i < cl.getPartition().numOfAccounts(); i++)
                 {
                     Account ac = cl.getPartition().getAccountByPosition(i);
                     if (ac.getAccountNumber() == accNum)
                     {
                         uniqueAccNum = false;
                     }
                 }
             }
             if (uniqueAccNum)
             {
                 ac.setAccountNumber(accNum);
                 if (!ac.isValidAccount())
                 {
                     JOptionPane.showMessageDialog(null, "Account number was not set, please check if value is negative.", "ERROR", JOptionPane.ERROR_MESSAGE);
                     return;
                 }
                 else
                 {
                     JOptionPane.showMessageDialog(null, "Account number was set.", "Success", JOptionPane.INFORMATION_MESSAGE);
                     currentAccountNum.setText("Current Account Number: " + ac.getAccountNumber());
                     newAccNum.setText("Enter the new account number here or don't change this text AT ALL if you want to keep its account number...");
                 }
             }
             else
             {
                 JOptionPane.showMessageDialog(null, "Account number not unique, please enter a different account number value", "ERROR", JOptionPane.ERROR_MESSAGE);
             }
           }
            if (changedGoalAm)
            {
                ac.setGoal(goalAmount,ac.getGoalEnding());
                if (!ac.isValidGoalAmount())
                {
                    JOptionPane.showMessageDialog(null, "Goal amount was not set, please check if value is negative or less than your balance.", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Goal amount was set.", "Success", JOptionPane.INFORMATION_MESSAGE) ;
                    currentGoalAmount.setText("Current Goal Amount: "+ac.getGoalAmount());
                    newGoalAmount.setText("Enter the new goal amount here or don't change this text AT ALL if you want to keep its goal amount...");
                }
            }
           if (changedGoalEnd)
           {
               ac.setGoal(ac.getGoalAmount(),goalEnd);
               if (!ac.isValidGoalEnding())
               {
                   JOptionPane.showMessageDialog(null, "Goal ending date was not set, please check if it is before or equal than today's date.", "ERROR", JOptionPane.ERROR_MESSAGE);
               }
               else
               {
                   JOptionPane.showMessageDialog(null, "Goal ending date was set.", "Success", JOptionPane.INFORMATION_MESSAGE) ;
                   currentGoalEnding.setText("Current Goal Ending Date: "+ac.getGoalEnding());
                   newGoalEnding.setText("Enter the new goal ending date here or don't change this text AT ALL if you want to keep its current date...");
               }
           }
      }
      if (e.getSource()==save)
      {
        try
         {
            PrintWriter printer=new PrintWriter("ClientList.csv");
            for (int i=0; i<database.lengthOfDatabase(); i++)
            {
               Client client=database.findClientWithIndex(i);
               printer.print(client.getID()+",");
               printer.print(client.getUsername()+",");
               printer.print(client.getPassword()+",");
               printer.print(client.getProfileName()+",");
               printer.print(client.getReferenceDate().getYear()+",");
               printer.print(client.getReferenceDate().getMonth()+",");
               printer.print(client.getReferenceDate().getDayOfMonth()+",");
               for (int j=0; j<client.listOfIncomeSources(); j++)
               {
                  IncomeSource is=client.findIncomeSourceWithPosition(j);
                  printer.print(is.getID()+",");
                  printer.print(is.getName()+",");
                  printer.print(is.getAmount()+",");
                  if (j==client.listOfIncomeSources()-1)
                  {
                    printer.print(client.getPayFrequency()+",");
                  }   
               }
               printer.print("EIS>,");   
               for (int j=0; j<client.numOfSpendingCategories(); j++)
               {
                  SpendingCategory sc=client.findSpendingCategoryWithPosition(j);
                  printer.print(sc.getName()+",");
                  printer.print(sc.getLabel()+",");
                  printer.print(sc.getAmount()+",");
               }
               printer.print("ESC>,");
               for (int j=0; j<client.getPartition().numOfAccounts(); j++)
               {
                  Account acc=client.getPartition().getAccountByPosition(j);
                  if (acc instanceof SavingsAccount)
                  {
                     printer.print("S,");
                  }
                  else if (acc instanceof CheckingAccount)
                  {
                     printer.print("C,");
                  }
                  printer.print(acc.getAccountNumber()+",");
                  printer.print(acc.getBalance()+",");
                  printer.print(acc.getGoalAmount()+",");
                  printer.print(acc.getGoalEnding().getYear()+",");
                  printer.print(acc.getGoalEnding().getMonth()+",");
                  printer.print(acc.getGoalEnding().getDayOfMonth()+",");
                  printer.print(acc.getSplitPercentage()+",");
                  printer.print(acc.getPayment()+",");
               }
               printer.println("EA");
            }
            JOptionPane.showMessageDialog(null,"Data was successfully saved!", "Data Saved", JOptionPane.INFORMATION_MESSAGE);
            printer.close();   
         }
         catch (FileNotFoundException ex)
         {
            JOptionPane.showMessageDialog(null,"Database file not found! Please close the program and make sure ClientList.csv is in the folder.", "FILE NOT FOUND",JOptionPane.ERROR_MESSAGE);
         }
      }
      if (e.getSource()==remove)
      {
          int answer=JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this monetary account?", "Removal",JOptionPane.YES_NO_CANCEL_OPTION);
          if (answer!=0)
          {
              return;
          }
          cl.getPartition().removeAccount(ac);
          cb.removeItem(ac);
          JOptionPane.showMessageDialog(null,"Monetary Account has been removed, this frame will now close.", "Removal Successful", JOptionPane.INFORMATION_MESSAGE);
          f.dispose();
      }
      if (e.getSource()==close)
      {
          f.dispose();
      }
    }
    public void addFocusListener(JTextField jtf)
    {
        jtf.addFocusListener(new FocusAdapter()
        {
            @Override
            public void focusGained(FocusEvent e)
            {
                jtf.selectAll();
            }
        });
    }
}
