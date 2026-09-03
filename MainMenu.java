

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import javax.swing.*;
public class MainMenu implements ActionListener
{
   JFrame menu=new JFrame("Main Menu");
   MenuBar mb=new MenuBar();
   Menu optionsMenu=new Menu("More Options");
   MenuItem saveItem=new MenuItem("Save Data");
   MenuItem logoutItem=new MenuItem("Logout");
   MenuItem exitItem=new MenuItem("Exit Program");
   JButton incomeInfo=new JButton("Income Frequency and Sources"); 
   JButton monetaryAccounts=new JButton("Manage Monetary Accounts"); // Setting up balance, account number and goals ONLY
   JButton partitions=new JButton("Income Partitions"); // Splits ONLY and requires >=1 income source and >=1 Monetary Account
   JButton catagories=new JButton("Spending Categories"); // Requires >=1 Income Source
   JButton financialGoals=new JButton("View Financial Goals"); // Requires >=1 Monetary account
   JButton profile=new JButton("User Profile");
   JButton finanicalReport=new JButton("My Financial Report"); // Submenus require >=1 Spending Category, >=1 Income Source, a set pay frequency and >=1 Account respectively
   Client cl;
   ClientDatabase database;
   JLabel header=new JLabel();
   JLabel money=new JLabel();
   ImageIcon moneyIcon=new ImageIcon("Screenshot 2026-01-29 101004.png");
   ImageIcon appIcon=new ImageIcon("AppIcon.png");
   JButton payday=new JButton("Is It Payday?"); // Requires >=1 account and a set pay frequency
   MainMenu(Client cl,ClientDatabase db)
   {
     this.cl=cl;
     database=db;
     menu.setSize(1000,800);
     menu.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
     menu.setLayout(null);
     menu.getContentPane().setBackground(new Color(0x57E69B));
     menu.setIconImage(appIcon.getImage());
     saveItem.addActionListener(this);
     logoutItem.addActionListener(this);
     exitItem.addActionListener(this);
     optionsMenu.addSeparator();
     optionsMenu.add(saveItem);
     optionsMenu.addSeparator();
     optionsMenu.add(logoutItem);
     optionsMenu.addSeparator();
     optionsMenu.add(exitItem);
     optionsMenu.addSeparator();
     mb.add(optionsMenu);
     menu.setMenuBar(mb);
     money.setIcon(moneyIcon);
     money.setBounds(530,100,364,405);
     header.setText("Welcome, "+cl.getProfileName()+".");
     header.setBounds(50,50,1000,40);
     header.setFont(new Font("Century", Font.BOLD, 30));
     incomeInfo.setBounds(50,100,400,60);
     monetaryAccounts.setBounds(50,170,400,60);
     partitions.setBounds(50,240,400,60);
     catagories.setBounds(50,310,400,60);
     financialGoals.setBounds(50,380,400,60);
     profile.setBounds(50,450,400,60);
     finanicalReport.setBounds(300,540,500,80);
     payday.setBounds(850,600,130,40);
     incomeInfo.addActionListener(this);
     monetaryAccounts.addActionListener(this);
     partitions.addActionListener(this);
     catagories.addActionListener(this);
     financialGoals.addActionListener(this);
     profile.addActionListener(this);
     finanicalReport.addActionListener(this);
     payday.addActionListener(this);
     menu.add(header);
     menu.add(money);
     menu.add(incomeInfo);
     menu.add(monetaryAccounts);
     menu.add(partitions);
     menu.add(catagories);
     menu.add(financialGoals);
     menu.add(profile);
     menu.add(finanicalReport);
     menu.add(payday);
     menu.setVisible(true);
   }
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource()==logoutItem)
        {
          save();
          Frame[] frames=Frame.getFrames();
          for (Frame frame : frames)
          {
              frame.dispose();
          }
          new LoginScreen(database);
        }
        if (e.getSource()==exitItem)
        {
          checkPayday();
          save();
          System.exit(0);
        }
        if (e.getSource()==incomeInfo)
        {
           new IncomeInfo(cl,database);
        }
        if (e.getSource()==monetaryAccounts)
        {
           new MonetaryAccountFrame(cl,database);
        }
        if (e.getSource()==catagories)
        {
          if (cl.listOfIncomeSources()==0)
          {
              JOptionPane.showMessageDialog(null, "Your income info needs to be set up before you set up your spending categories.", "Action Required", JOptionPane.INFORMATION_MESSAGE);
          }    
          else
          {
            new CategoriesFrame(cl,database);
          }    
        }
        if (e.getSource()==partitions)
        {
          if (cl.listOfIncomeSources()==0)
          {
             JOptionPane.showMessageDialog(null, "Your income info needs to be set up before you set up your income partitions.", "Action Required", JOptionPane.INFORMATION_MESSAGE);
             return;
          }
          if (cl.getPartition().numOfAccounts()==0)
          {
             JOptionPane.showMessageDialog(null, "You need to have atleast one monetary account set up before you set up your income partitions.", "Action Required", JOptionPane.INFORMATION_MESSAGE);
             return;
          }
          new AccountChooserFrame(cl,database,this);
          menu.setVisible(false);    
        }
        if (e.getSource()==profile)
        {
          new UserProfileFrame2(cl,database);
        }
        if (e.getSource()==financialGoals)
        {
          if (cl.getPartition().numOfAccounts()==0)
          {
            JOptionPane.showMessageDialog(null, "You need to have atleast one monetary account set up before you see your financial goals.", "Action Required", JOptionPane.INFORMATION_MESSAGE);
            return;
          }
          new AccountChooserFrame(cl,database,this);
          menu.setVisible(false);
        }
        if (e.getSource()==finanicalReport)
        {
            new FinancialReportFrame(cl);
        }
        if (e.getSource()==payday)
        {
          
          if (cl.getIncome()==0 || cl.getPayFrequency()==0)
          {
            JOptionPane.showMessageDialog(null,"Income info needs to be set up before you can recieve your simulated paycheck.", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
          } 
          if (cl.getPartition().numOfAccounts()==0)
          {
            JOptionPane.showMessageDialog(null,"No monetary accounts set up, please create an account for the money to distributed to it.", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
          }
          double splits=0;
          for (int i=0; i<cl.getPartition().numOfAccounts(); i++)
          {
             splits+=cl.getPartition().getAccountByPosition(i).getSplitPercentage();
          }
          if (splits==0)
          {
            JOptionPane.showMessageDialog(null,"No income partitions set up, please create income splits for your account/s.", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
          }
          if (cl.getReferenceDate()==LocalDate.of(2020,Month.JANUARY,1))
          {
            JOptionPane.showMessageDialog(null,"No reference date set up, please go to User Profile Tab to set up your reference date.", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
          }    
          long daysBetween=ChronoUnit.DAYS.between(cl.getReferenceDate(),LocalDate.now());   
          if (daysBetween==cl.getPayFrequency())
          {
            JOptionPane.showMessageDialog(null,"Today is Payday!, Your income has been distributed throughout your accounts based on your income splits.", "PAYDAY!!!!", JOptionPane.INFORMATION_MESSAGE);
            for (int i=0; i<cl.getPartition().numOfAccounts(); i++)
            {
              cl.getPartition().getAccountByPosition(i).addToBalance();
            }  
            cl.setReferenceDate(LocalDate.now());
          }
          else if (daysBetween<cl.getPayFrequency() && daysBetween>=0)
          {
            if (daysBetween==1)
            {
              JOptionPane.showMessageDialog(null,"Today is not Payday, you have "+(cl.getPayFrequency()-daysBetween)+" day left until your next paycheck.", "Not Yet", JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
              JOptionPane.showMessageDialog(null,"Today is not Payday, you have "+(cl.getPayFrequency()-daysBetween)+" days left until your next paycheck.", "Not Yet", JOptionPane.INFORMATION_MESSAGE);  
            }
          }
          else
          {
             JOptionPane.showMessageDialog(null,"Your reference date is invalid. Please change this in the User Profile immediately.", "ERROR", JOptionPane.ERROR_MESSAGE);
          }     
        }
      if (e.getSource()==saveItem)
      {
         try (PrintWriter printer=new PrintWriter("ClientList.csv"))
         {
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
                  if (j==client.listOfIncomeSources()-1)
                  {
                    printer.print(is.getID()+",");
                    printer.print(is.getName()+",");
                    printer.print(is.getAmount()+",");
                    printer.print(client.getPayFrequency()+",");
                  }
                  else
                  {
                    printer.print(is.getID()+",");
                    printer.print(is.getName()+",");
                    printer.print(is.getAmount()+",");
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
         }
         catch (FileNotFoundException ex)
         {
            JOptionPane.showMessageDialog(null,"ClientList.csv was not found. Please make sure that the file is in the ClassesFolder folder", "ERROR",JOptionPane.ERROR_MESSAGE);
         }
      }          
    }
    public JFrame getFrame() {return menu;}
    public void checkPayday()
    {
        if (cl.getIncome()==0 || cl.getPayFrequency()==0)
        {
            return;
        } 
        if (cl.getPartition().numOfAccounts()==0)
        {
            return;
        }
        double splits=0;
        for (int i=0; i<cl.getPartition().numOfAccounts(); i++)
        {
             splits+=cl.getPartition().getAccountByPosition(i).getSplitPercentage();
        }
        if (splits==0)
        {
            return;
        }
        if (cl.getReferenceDate()==LocalDate.of(2020,Month.JANUARY,1))
        {
            return;
        }    
        long daysBetween=ChronoUnit.DAYS.between(cl.getReferenceDate(),LocalDate.now());   
        if (daysBetween==cl.getPayFrequency())
        {
            JOptionPane.showMessageDialog(null,"Today is Payday!, Your income has been distributed throughout your accounts based on your income splits.", "PAYDAY!!!!", JOptionPane.INFORMATION_MESSAGE);
            for (int i=0; i<cl.getPartition().numOfAccounts(); i++)
            {
              cl.getPartition().getAccountByPosition(i).addToBalance();
            }  
            cl.setReferenceDate(LocalDate.now());
        }
        else if (daysBetween<cl.getPayFrequency() && daysBetween>=0)
        {
            JOptionPane.showMessageDialog(null,"Reminder, you have "+(cl.getPayFrequency()-daysBetween)+" days left until next paycheck.", "Reminder for Payday", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    public void save()
    {
        try (PrintWriter printer=new PrintWriter("ClientList.csv"))
        {
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
                    if (j==client.listOfIncomeSources()-1)
                    {
                        printer.print(is.getID()+",");
                        printer.print(is.getName()+",");
                        printer.print(is.getAmount()+",");
                        printer.print(client.getPayFrequency()+",");
                    }
                    else
                    {
                        printer.print(is.getID()+",");
                        printer.print(is.getName()+",");
                        printer.print(is.getAmount()+",");
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
        }
        catch (FileNotFoundException ex)
        {
            JOptionPane.showMessageDialog(null,"Database file not found! Please close the program and make sure ClientList.csv is in the folder.", "FILE NOT FOUND",JOptionPane.ERROR_MESSAGE);
        }
    }
}
