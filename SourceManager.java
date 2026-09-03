
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.*;
import java.io.*;
public class SourceManager implements ActionListener
{
  JFrame f=new JFrame("Manage Income Source");
  JTextField newAmount=new JTextField("Enter the new amount here or don't change this text AT ALL if you want to keep its amount...");
  JTextField newName=new JTextField("Enter the new name here or don't change this text AT ALL if you want to keep its name...");
  JTextField newID=new JTextField("Enter the new ID here or don't change this text AT ALL if you want to keep its id...");
  JButton submit=new JButton("Submit");
  JLabel header=new JLabel("This is the current info for the income source.");
  JLabel header2=new JLabel("You can change the data using the boxes below.");
  JLabel currentAmount=new JLabel();
  JLabel currentName=new JLabel();
  JLabel currentID=new JLabel();
  JButton close=new JButton("Close");
  JButton save=new JButton("Save");
  JButton remove=new JButton("Remove Income Source");
  IncomeSource is;
  Client cl;
  ClientDatabase database;
  JComboBox<IncomeSource> cb;
  ImageIcon appIcon=new ImageIcon("AppIcon.png");
  SourceManager (IncomeSource s, Client c, ClientDatabase db, JComboBox<IncomeSource> cb2)
  {
    is=s;
    cl=c;
    database=db;
    cb=cb2;
    f.setSize(600,600);
    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    f.setLayout(null);
    f.getContentPane().setBackground(new Color(0x57E69B));
    f.setIconImage(appIcon.getImage());
    header.setBounds(15,50,600,40);
    header.setFont(new Font("Calisto MT", Font.BOLD, 20));
    header2.setBounds(15,75,600,40);
    header2.setFont(new Font("Calisto MT", Font.BOLD, 20));
    currentAmount.setText("Current Amount: "+is.getAmount());
    currentName.setText("Current Name: "+is.getName());
    currentID.setText("Current ID: "+is.getID());
    currentAmount.setBounds(50,125,600,40);
    currentAmount.setFont(new Font("Calisto MT", Font.BOLD, 17));
    newAmount.setBounds(50,160,500,40);
    currentName.setBounds(50,225,600,40);
    currentName.setFont(new Font("Calisto MT", Font.BOLD, 17));
    newName.setBounds(50,260,500,40);
    currentID.setBounds(50,325,600,40);
    currentID.setFont(new Font("Calisto MT", Font.BOLD, 17));
    newID.setBounds(50,360,500,40);
    addFocusListener(newAmount);
    addFocusListener(newName);
    addFocusListener(newID);
    submit.setBounds(100,450,350,60);
    submit.addActionListener(this);
    close.setBounds(500,5,75,40);
    close.addActionListener(this);
    remove.setBounds(5,5,175,40);
    remove.addActionListener(this);
    save.setBounds(500,500,75,40);
    save.addActionListener(this);
    f.add(header);
    f.add(header2);
    f.add(currentAmount);
    f.add(newAmount);
    f.add(currentName);
    f.add(newName);
    f.add(currentID);
    f.add(newID); 
    f.add(submit);
    f.add(close);
    f.add(save);
    f.add(remove);
    f.setVisible(true);   
  }   
    @Override
    public void actionPerformed(ActionEvent e) 
    {
       if (e.getSource()==submit)
       {
         boolean uniqueID=true;
         boolean changedID=false;
         boolean changedName=false;
         boolean changedAmount=false;
         long id=-99;
         String name="";
         double amount=-99;
         if (!newID.getText().equalsIgnoreCase("Enter the new ID here or don't change this text AT ALL if you want to keep its id..."))
         {
             changedID=true;
             id=Long.parseLong(newID.getText());
         }
         if (!newName.getText().equalsIgnoreCase("Enter the new name here or don't change this text AT ALL if you want to keep its name..."))
         {
               changedName=true;
               name=newName.getText();
         }
         if (!newAmount.getText().equalsIgnoreCase("Enter the new amount here or don't change this text AT ALL if you want to keep its amount..."))
         {
               changedAmount=true;
               amount=Double.parseDouble(newAmount.getText());
         }
         if (changedID)
         {
             if (is.getID() != id)
             {
                 for (int i = 0; i < cl.listOfIncomeSources(); i++)
                 {
                     IncomeSource incomeS = cl.findIncomeSourceWithPosition(i);
                     if (incomeS.getID() == id)
                     {
                         uniqueID = false;
                     }
                 }
             }
         }
         if (changedID)
         {
             if (uniqueID)
             {
                 if (!is.setID(id))
                 {
                     JOptionPane.showMessageDialog(null,"ID was not changed, please check if value is empty or negative.", "ERROR", JOptionPane.ERROR_MESSAGE);
                 }
             }
             else
             {
                 JOptionPane.showMessageDialog(null,"ID not unique, please enter a different ID value", "ERROR", JOptionPane.ERROR_MESSAGE);
             }
         }
         if (changedName)
         {
             if (is.setName(name))
             {
                 JOptionPane.showMessageDialog(null,"Name was not changed, please check if value is empty.", "ERROR", JOptionPane.ERROR_MESSAGE);
             }
         }
         if (changedAmount)
         {
             if (!is.setAmount(amount))
             {
                 JOptionPane.showMessageDialog(null,"Amount was not changed, please check if value is empty or negative.", "ERROR", JOptionPane.ERROR_MESSAGE);
             }
         }
         if (changedAmount || changedName || changedID)
         {
             JOptionPane.showMessageDialog(null,"Changes were set successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
             currentAmount.setText("Current Amount: "+is.getAmount());
             currentName.setText("Current Name: "+is.getName());
             currentID.setText("Current ID: "+is.getID());
             newName.setText("Enter the new name here or don't change this text AT ALL if you want to keep its name...");
             newAmount.setText("Enter the new amount here or don't change this text AT ALL if you want to keep its amount...");
             newID.setText("Enter the new ID here or don't change this text AT ALL if you want to keep its id...");
         }
      }
      if (e.getSource()==close)
      {
        f.dispose();
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
          int answer=JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this income source?", "Removal",JOptionPane.YES_NO_CANCEL_OPTION);
          if (answer!=0)
          {
              return;
          }
          cl.removeIncomeSource(is);
          cl.setIncome();
          cb.removeItem(is);
          JOptionPane.showMessageDialog(null,"Income Source has been removed, this frame will now close.", "Removal Successful", JOptionPane.INFORMATION_MESSAGE);
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

