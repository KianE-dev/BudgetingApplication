
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.*;
import java.time.*;
import javax.swing.*;
public class MonetaryAccountFrame implements ActionListener
{
    JFrame f=new JFrame("Monetary Account Creation");
    JTextField balance=new JTextField("Enter the balance of the account here...(No dollar sign)");
    JTextField goalAmount=new JTextField("Enter the amount of money you want this account to be at once the goal ending date is reached here...(No dollar sign)");
    JTextField goalEnding=new JTextField("Enter the date of when you want to complete your goal in the form of Year/MONTH/Day here...");
    JRadioButton savings=new JRadioButton("Savings Account");
    JRadioButton checking=new JRadioButton("Checking Account");
    ButtonGroup bg=new ButtonGroup();
    JLabel header=new JLabel("In this window, manage your current monetary accounts or add a new one with the boxes below.");
    JLabel accountDisclaimer1=new JLabel("You can create 2 different types of accounts, Checking and Savings, and they do not follow the standard conventions.");
    JLabel accountDisclaimer2=new JLabel(">Savings Account: Can only be withdrawn from 6 times, has a minimum income split percentage of 40% and can never be negative.");
    JLabel accountDisclaimer3=new JLabel(">Checking Account: Can be withdrawn from any number of times, has no minimum income split percentage and can go negative.");
    JLabel accountDisclaimer4=new JLabel("(Refer to Income Partitions tab for more information about split percentages)");
    JLabel accountNumRules=new JLabel(">Like your id for your account, the account number is unique from your other monetary accounts.");
    JLabel accountNumLabel=new JLabel("The account number is attached to the account when it's created, allowing for easier management.");
    JLabel goalGuidelines=new JLabel(">All of your monetary accounts have a goal attached to them to promote financial stability.");
    JButton submit=new JButton("Submit");
    JButton manageAccounts=new JButton("Manage Accounts");
    JButton saveButton=new JButton("Save");
    JButton closeButton=new JButton("Close");
    Client cl;
    ClientDatabase database;
    int accNum=((int)(Math.random()*100+999));
    boolean uniqueAccNum=false;
    JFrame f2=new JFrame("Choose an Account To Manage");
    JLabel header2=new JLabel("Use the drop-down menu below to select your desired account and click the submit button when you're done.");
    JComboBox<Account> cb=new JComboBox<>();
    JButton submit2=new JButton("Submit");
    JButton close2=new JButton("Close");
    ImageIcon appIcon=new ImageIcon("AppIcon.png");
    MonetaryAccountFrame(Client c, ClientDatabase db)
    {
        cl=c;
        database=db;
        f.setSize(800,750);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setLayout(null);
        f.getContentPane().setBackground(new Color(0x57E69B));
        f.setIconImage(appIcon.getImage());
        uniqueAccNum=false;
        while (!uniqueAccNum)
        {
           if (cl.getPartition().uniqueAccNum(accNum))
           {
              uniqueAccNum=true;
           }
           else
           {
              accNum=(int)(Math.random()*100+999);
           }   
        }
        accountNumLabel.setFont(new Font("Bodoni MT",Font.BOLD,16));
        header.setBounds(5,5,800,40);
        header.setFont(new Font("Calisto MT",Font.BOLD,15));
        accountDisclaimer1.setBounds(5,25,800,40);
        accountDisclaimer1.setFont(new Font("Calisto MT",Font.BOLD,12));
        accountDisclaimer2.setBounds(5,45,800,40);
        accountDisclaimer2.setFont(new Font("Calisto MT",Font.BOLD,12));
        accountDisclaimer3.setBounds(5,65,800,40);
        accountDisclaimer3.setFont(new Font("Calisto MT",Font.BOLD,12));
        accountDisclaimer4.setBounds(5,85,800,40);
        accountDisclaimer4.setFont(new Font("Calisto MT",Font.BOLD,15));
        accountNumRules.setBounds(20,120,750,40);
        accountNumRules.setFont(new Font("Calisto MT",Font.BOLD,15));
        accountNumLabel.setBounds(25,140,800,50);
        balance.setBounds(50,190,375,50);
        savings.setBounds(50,245,375,30);
        checking.setBounds(50,275,375,30);
        goalGuidelines.setBounds(50,305,750,40);
        goalGuidelines.setFont(new Font("Calisto MT",Font.BOLD,15));
        goalAmount.setBounds(50,350,700,50);
        goalEnding.setBounds(50,405,625,50);
        addFocusListener(balance);
        addFocusListener(goalAmount);
        addFocusListener(goalEnding);
        submit.setBounds(175,525,400,60);
        manageAccounts.setBounds(550,600,200,40);
        saveButton.setBounds(25,600,75,40);
        closeButton.setBounds(700,20,75,40);
        submit.addActionListener(this);
        manageAccounts.addActionListener(this);
        saveButton.addActionListener(this);
        closeButton.addActionListener(this);
        f.add(header);
        f.add(accountDisclaimer1);
        f.add(accountDisclaimer2);
        f.add(accountDisclaimer3);
        f.add(accountDisclaimer4);
        f.add(accountNumLabel);
        f.add(accountNumRules);
        f.add(balance);
        bg.add(savings);
        bg.add(checking);
        f.add(savings);
        f.add(checking);
        savings.setSelected(true);
        f.add(goalGuidelines);
        f.add(goalAmount);
        f.add(goalEnding);
        f.add(submit);
        f.add(manageAccounts);
        f.add(saveButton);
        f.add(closeButton);
        f.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource()==submit)
        {
            double bal=Double.parseDouble(balance.getText());
            double goalAm=Double.parseDouble(goalAmount.getText());
            String[] sections=goalEnding.getText().strip().split("/");
            LocalDate goalEnd=LocalDate.of(Integer.parseInt(sections[0]),Month.valueOf(sections[1]),Integer.parseInt(sections[2]));
            boolean uniqueNum=true;
            boolean save=savings.isSelected();
            boolean check=checking.isSelected();
            if (save)
            {
                SavingsAccount sAcc=new SavingsAccount(accNum);
                if (!sAcc.isValidAccount())
                {
                    JOptionPane.showMessageDialog(null,"Entered account number is invalid, please check if it is negative.", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                sAcc.testBalance(bal);
                if (!sAcc.isValidAccount())
                {
                    JOptionPane.showMessageDialog(null,"Entered balance is invalid, please check if it is negative.", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                sAcc.setGoal(goalAm,goalEnd);
                if (!sAcc.isValidGoalAmount())
                {
                    JOptionPane.showMessageDialog(null,"Entered goal amount is invalid, please check if it is negative or less than your balance.", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                if (!sAcc.isValidGoalEnding())
                {
                    JOptionPane.showMessageDialog(null,"Entered goal ending is invalid, please check if it is before or equal than today's date.", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                if (sAcc.isValidGoalAmount() && sAcc.isValidGoalEnding())
                {
                    if (cl.getPartition().numOfAccounts()==0)
                    {
                        if (cl.getIncome()!=0)
                        {
                            sAcc.setPayment(cl.getIncome());
                        }
                        sAcc.setSplitPercentage(1);
                    }
                    cl.getPartition().addAccount(sAcc);
                    cb.addItem(sAcc);
                    JOptionPane.showMessageDialog(null,"Checking account was added. You can create a new one or make changes through the Manage Accounts Button.", "Monetary Account Added", JOptionPane.INFORMATION_MESSAGE);
                    uniqueAccNum=false;
                    while (!uniqueAccNum)
                    {
                        if (cl.getPartition().uniqueAccNum(accNum))
                        {
                            uniqueAccNum=true;
                        }
                        else
                        {
                            accNum=(int)(Math.random()*100+999);
                        }
                    }
                }    
            }
            if (check)
            {
                CheckingAccount cAcc=new CheckingAccount(accNum);
                if (!cAcc.isValidAccount())
                {
                    JOptionPane.showMessageDialog(null,"Entered account number is invalid, please check if it is negative.", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                cAcc.testBalance(bal);
                if (cAcc.isNegative())
                {
                    JOptionPane.showMessageDialog(null,"Oh no! This account's balance is negative, let's try to fix that.", "Negative Account", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                cAcc.setGoal(goalAm,goalEnd);
                if (!cAcc.isValidGoalAmount())
                {
                    JOptionPane.showMessageDialog(null,"Entered goal amount is invalid, please check if it is negative or less than your balance.", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                if (!cAcc.isValidGoalEnding())
                {
                    JOptionPane.showMessageDialog(null,"Entered goal ending is invalid, please check if it is before or equal than today's date.", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
                if (cAcc.isValidGoalAmount() && cAcc.isValidGoalEnding())
                {
                    if (cl.getPartition().numOfAccounts()==0)
                    {
                        if (cl.getIncome()!=0)
                        {
                            cAcc.setPayment(cl.getIncome());
                        }
                        cAcc.setSplitPercentage(1);
                    }
                    cl.getPartition().addAccount(cAcc);
                    cb.addItem(cAcc);
                    JOptionPane.showMessageDialog(null,"Checking account was added, you can create a new account or make changes through the Manage Accounts button.", "Monetary Account Added", JOptionPane.INFORMATION_MESSAGE);
                    uniqueAccNum=false;
                    while (!uniqueAccNum)
                    {
                      if (cl.getPartition().uniqueAccNum(accNum))
                      {
                        uniqueAccNum=true;
                      }
                      else
                      {
                        accNum=(int)(Math.random()*100+999);
                      }   
                    }
                }
            }             
        }
        if (e.getSource()==manageAccounts)
        {
            if (cl.getPartition().numOfAccounts()==0)
            {
                JOptionPane.showMessageDialog(null, "No monetary account to manage!", "No Accounts", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Account[] clientAccounts=new Account[cl.getPartition().numOfAccounts()];
            for (int i=0; i<clientAccounts.length; i++)
            {
                clientAccounts[i]=cl.getPartition().getAccountByPosition(i);
            }
            cb=new JComboBox<>(clientAccounts);
            f2.setSize(680,400);
            f2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            f2.setLayout(null);
            f2.getContentPane().setBackground(new Color(0x57E69B));
            f2.setIconImage(appIcon.getImage());
            header2.setBounds(10,50,680,40);
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
            new MonetaryAccountManager((Account)cb.getSelectedItem(), cl,database,cb);
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
                  else 
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

