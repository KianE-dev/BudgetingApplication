
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import javax.swing.*;
public class IncomeInfo implements ActionListener
{
   JFrame f=new JFrame("Income Information");
   JLabel header=new JLabel("In this window, manage your current income sources or add new ones with the boxes below.");
   JLabel idRules=new JLabel(">Like your id for your account, income sources have unique ids from your other income sources.");
   JLabel idLabel=new JLabel("The ID is attached to the income source when it is created, allowing for easier management.");
   JLabel payFreqRules=new JLabel(">Enter the pay frequency from the income source you earn the MOST from. Weekly=7,BiWeekly=14,Monthly=30");
   JLabel nameGuide=new JLabel(">The name can be the name of the company you work for or some other side job like dog walking.");
   JLabel currentPayFreq=new JLabel();
   JTextField payFreq=new JTextField("Enter the pay frequency here..."); 
   JTextField sourceName=new JTextField("Enter the name for the income source here..."); 
   JTextField sourceAmount=new JTextField("Enter the average amount received per paycheck from the source here... (No dollar sign)");
   JButton addSource=new JButton("Add Income Source");
   JButton setFreq=new JButton("Set Pay Frequency");
   JButton saveButton=new JButton("Save");
   JButton closeButton=new JButton("Close");
   JButton manageSources=new JButton("Manage Income Sources");
   Client cl;
   ClientDatabase database;
   long id=(long)((int)(Math.random()*10+99));
   boolean uniqueID=false;
   JFrame f2=new JFrame("Choose a Source To Manage");
   JLabel header2=new JLabel("Use the drop-down menu below to select your desired income source and click the submit button when you're done.");
   JComboBox<IncomeSource> cb=new JComboBox<>();
   JButton submit2=new JButton("Submit");
   JButton close2=new JButton("Close");
   ImageIcon appIcon=new ImageIcon("AppIcon.png");
   IncomeInfo (Client c, ClientDatabase db)
   {
      cl=c;
      database=db;
      f.setSize(700,600);
      f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      f.setLayout(null);
      f.getContentPane().setBackground(new Color(0x57E69B));
      f.setIconImage(appIcon.getImage());
      uniqueID=false;
      while (!uniqueID)
      {
         if (cl.uniqueSourceID(id))
         {
            uniqueID=true;
         }
         else
         {
            id=(long)((int)(Math.random()*10+99));
         }   
      }
      idLabel.setFont(new Font("Bodoni MT",Font.BOLD,15));
      currentPayFreq.setText("Current Pay Frequency: "+cl.getPayFrequency());
      header.setBounds(5,50,700,40);
      header.setFont(new Font("Calisto MT",Font.BOLD,16));
      idRules.setBounds(15,90,700,40);
      idRules.setFont(new Font("Calisto MT",Font.BOLD,15));
      idLabel.setBounds(25,125,700,40);
      nameGuide.setBounds(25,165,700,40);
      nameGuide.setFont(new Font("Calisto MT",Font.BOLD,15));
      sourceName.setBounds(50,200,250,50);
      sourceAmount.setBounds(50,250,500,50);
      payFreqRules.setBounds(20,300,700,40);
      payFreqRules.setFont(new Font("Calisto MT",Font.BOLD,13));
      currentPayFreq.setBounds(50,325,700,40);
      currentPayFreq.setFont(new Font("Calisto MT",Font.BOLD,15));
      payFreq.setBounds(50,360,250,50);
      addFocusListener(sourceName);
      addFocusListener(sourceAmount);
      addFocusListener(payFreq);
      setFreq.setBounds(315,360,250,40);
      addSource.setBounds(100,450,400,60);
      manageSources.setBounds(425,525,250,40);
      closeButton.setBounds(600,15,75,40);
      saveButton.setBounds(20,525,75,40);
      addSource.addActionListener(this);
      manageSources.addActionListener(this);
      saveButton.addActionListener(this);
      closeButton.addActionListener(this);
      setFreq.addActionListener(this);
      f.add(header);
      f.add(idRules);
      f.add(idLabel);
      f.add(sourceName);
      f.add(nameGuide);
      f.add(sourceAmount);
      f.add(payFreqRules);
      f.add(currentPayFreq);
      f.add(payFreq);
      f.add(setFreq);
      f.add(addSource);
      f.add(manageSources);
      f.add(closeButton);
      f.add(saveButton);
      f.setVisible(true);
   } 
    @Override
    public void actionPerformed(ActionEvent e) 
    {
      if (e.getSource()==addSource)
      {
         String name=sourceName.getText();
         double amount=Double.parseDouble(sourceAmount.getText()); 
         IncomeSource is=new IncomeSource(id,name,amount);
         if (!cl.addIncomeSource(is))
         {
            JOptionPane.showMessageDialog(null,"Income Source was not added, please check for empty values.", "ERROR", JOptionPane.ERROR_MESSAGE);
         }
         else
         {
            cl.setIncome();
            cb.addItem(is);
            JOptionPane.showMessageDialog(null,"Income source was added. You can now add another one or change its data with the Manage Sources button.", "Source Added", JOptionPane.INFORMATION_MESSAGE);
            uniqueID=false;
            while (!uniqueID)
            {
              if (cl.uniqueSourceID(id))
              {
                uniqueID=true;
              }
              else
              {
                 id=(long)((int)(Math.random()*10+99));
              }   
            }
            
         }
      }
      if (e.getSource()==setFreq)
      {
         int payFrequency=Integer.parseInt(payFreq.getText());
         if (!cl.setPayFrequency(payFrequency))
         {
            JOptionPane.showMessageDialog(null,"Pay frequency was not set, please check that the value is not empty or less than 1.", "ERROR", JOptionPane.ERROR_MESSAGE);
         }
         else
         {
            JOptionPane.showMessageDialog(null,"Pay frequency was set.", "Success", JOptionPane.INFORMATION_MESSAGE);
            currentPayFreq.setText("Current Pay Frequency: "+cl.getPayFrequency());
         }   
      }   
      if (e.getSource()==manageSources)
      {
         if (cl.listOfIncomeSources()==0)
         {
            JOptionPane.showMessageDialog(null, "No income source to manage!", "No Sources", JOptionPane.WARNING_MESSAGE);
            return;
         }
         IncomeSource[] clientSources=new IncomeSource[cl.listOfIncomeSources()];
         for (int i=0; i<clientSources.length; i++)
         {
            clientSources[i]=cl.findIncomeSourceWithPosition(i);
         }
         cb=new JComboBox<>(clientSources);
         f2.setSize(680,400);
         f2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
         f2.setLayout(null);
         f2.getContentPane().setBackground(new Color(0x57E69B));
         f2.setIconImage(appIcon.getImage());
         header2.setBounds(0,50,680,40);
         cb.setBounds(25,100,425,50);
         submit2.setBounds(460,100,100,50);
         close2.setBounds(500,200,100,50);
         submit2.addActionListener(this);
         close2.addActionListener(this);
         f2.add(header2);
         f2.add(cb);
         f2.add(submit2);
         f2.add(close2);
         f2.setVisible(true);
      }
      if (e.getSource()==closeButton)
      {
         f.dispose();
      }
      if (e.getSource()==close2)
      {
         f2.dispose();
      }
      if (e.getSource()==submit2)
      {
         new SourceManager((IncomeSource)cb.getSelectedItem(), cl,database,cb);
      }
      if (e.getSource()==saveButton)
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
