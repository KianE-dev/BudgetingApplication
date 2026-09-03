
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.*;
import javax.swing.*;
public class IncomePartitionFrame implements ActionListener
{
    JFrame f=new JFrame("Income Partition");
    JLabel header=new JLabel("In this window, you set up the percentage of your income that this account gets per income frequency cycle.");
    JLabel disclaimer1=new JLabel(">In this simulated version of your actual pay cycle, all of your entered income sources' amounts get added up and split throughout your accounts.");
    JLabel disclaimer2=new JLabel(">Through this system, you get paid from all of your sources at once so you can manage your money coming in more easily.");
    JLabel disclaimer3=new JLabel(">Your accounts have to add up to 100% distribution of income");
    JLabel disclaimer4=new JLabel("-For example, if you have 2 accounts, their split percentages have to add up to 100% (36% and 64%) or (60% and 40%)");
    JLabel disclaimer5=new JLabel("If they do not add up to 100%, it will cause many errors in the pay frequency cycle!!!");
    JLabel splitDisclaimer=new JLabel();
    JLabel currentSplit=new JLabel();
    JTextField split=new JTextField("Enter the income partition here... (Percentage w/o percent sign)");
    JButton submit=new JButton("Submit");
    JButton saveButton=new JButton("Save");
    JButton closeButton=new JButton("Close");
    Account ac;
    Client cl;
    ClientDatabase database;
    ImageIcon appIcon=new ImageIcon("AppIcon.png");
    IncomePartitionFrame(Account acc, Client c, ClientDatabase db)
    {
        ac=acc;
        cl=c;
        database=db;
        if (ac instanceof SavingsAccount)
        {
            splitDisclaimer.setText("Since this is a savings account, the minimum income split is 40% to promote financial stability.");
        }
        else
        {
            splitDisclaimer.setText("Since this is a checking account, there is no minimum income split but again, it must be complementary to your other accounts.");
        }
        currentSplit.setText("Current Income Split: "+ac.getSplitPercentage()*100+"%");
        f.setSize(875,500);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setLayout(null);
        f.getContentPane().setBackground(new Color(0x57E69B));
        f.setIconImage(appIcon.getImage());
        header.setBounds(15,50,875,40);
        header.setFont(new Font("Calisto MT",Font.BOLD,15));
        disclaimer1.setBounds(25,75,875,40);
        disclaimer1.setFont(new Font("Calisto MT",Font.BOLD,12));
        disclaimer2.setBounds(25,100,800,40);
        disclaimer2.setFont(new Font("Calisto MT",Font.BOLD,13));
        disclaimer3.setBounds(25,125,800,40);
        disclaimer3.setFont(new Font("Calisto MT",Font.BOLD,14));
        disclaimer4.setBounds(30,150,800,40);
        disclaimer4.setFont(new Font("Calisto MT",Font.BOLD,13));
        disclaimer5.setBounds(25,180,800,40);
        disclaimer5.setFont(new Font("Calisto MT",Font.BOLD,20));
        splitDisclaimer.setBounds(40,225,800,40);
        splitDisclaimer.setFont(new Font("Calisto MT",Font.BOLD,14));
        currentSplit.setBounds(40,250,800,40);
        currentSplit.setFont(new Font("Calisto MT",Font.BOLD,14));
        split.setBounds(40,290,500,50);
        addFocusListener(split);
        submit.setBounds(150,400,400,60);
        saveButton.setBounds(775,400,75,40);
        closeButton.setBounds(775,25,75,40);
        saveButton.addActionListener(this);
        closeButton.addActionListener(this);
        submit.addActionListener(this);
        f.add(header);
        f.add(disclaimer1);
        f.add(disclaimer2);
        f.add(disclaimer3);
        f.add(disclaimer4);
        f.add(disclaimer5);
        f.add(splitDisclaimer);
        f.add(currentSplit);
        f.add(split);
        f.add(submit);
        f.add(closeButton);
        f.add(saveButton);
        f.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource()==submit)
        {
           double splitPercent=Double.parseDouble(split.getText());
           ac.calcSplitPercentage(splitPercent);
           if (!ac.isValidSplit())
           {
             JOptionPane.showMessageDialog(null,"Income split was not set, please check if your percentage is negative or less than 40 if it is a savings account.", "ERROR", JOptionPane.ERROR_MESSAGE);
             return;
           }
           if (!cl.getPartition().validSplit(ac))
           {
              if (cl.getPartition().numOfAccounts()>1)
              {
                cl.getPartition().setPartitions();
                JOptionPane.showMessageDialog(null,"Entered income split does not complement rest of accounts to make 100% percentage, please go back to the account chooser frame to view or change the splits in the other accounts.", "Warning!", JOptionPane.WARNING_MESSAGE);
              }
              else
              {
                cl.getPartition().setPartitions();
                 JOptionPane.showMessageDialog(null,"Entered income split does not equal 100 and will not recieve full amount of income per pay frequency cycle. Please change this amount or add new accounts to make up for the missing percentage.", "Warning!", JOptionPane.WARNING_MESSAGE);
              }     
           }
           else
           {
             cl.getPartition().setPartitions();
             JOptionPane.showMessageDialog(null,"Income split was set. This account will recieve $"+ac.getPayment()+" per pay frequency cycle.", "Success", JOptionPane.INFORMATION_MESSAGE);
           } 
           currentSplit.setText("Current Income Split: "+ac.getSplitPercentage());  
        }
        if (e.getSource()==closeButton)
        {
         f.dispose();
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
               printer.print("EA");
               printer.println("");
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
