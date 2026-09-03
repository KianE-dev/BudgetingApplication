
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.*;
import java.io.*;
public class CategoryManager implements ActionListener
{
  JFrame f=new JFrame("Manage Category");
  JTextField newAmount=new JTextField("Enter the new amount or don't change this text AT ALL if you want to keep the amount...");
  JTextField newName=new JTextField("Enter the new name or don't change this text AT ALL if you want to keep the name...");
  JCheckBox newLabel=new JCheckBox("Check here if you want this category to stay a want or uncheck it if you want to change to a need.");
  JButton submit=new JButton("Submit");
  JLabel header=new JLabel("This is the current info for the spending category.");
  JLabel header2=new JLabel("You can edit its data using the boxes below.");
  JLabel currentAmount=new JLabel();
  JLabel currentName=new JLabel();
  JLabel currentLabel=new JLabel();
  JButton close=new JButton("Close");
  JButton save=new JButton("Save");
  JButton remove=new JButton("Remove");
  SpendingCategory sc;
  ClientDatabase database;
  Client cl;
  JComboBox<SpendingCategory> cb;
  ImageIcon appIcon=new ImageIcon("AppIcon.png");
  CategoryManager(SpendingCategory s, Client c, ClientDatabase db, JComboBox<SpendingCategory> cb2)
  {
    cl=c;
    database=db;
    sc=s;
    cb=cb2;
    f.setSize(600,600);
    f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    f.setLayout(null);
    f.getContentPane().setBackground(new Color(0x57E69B));
    f.setIconImage(appIcon.getImage());
    header.setBounds(20,40,600,40);
    header.setFont(new Font("Calisto MT",Font.BOLD,18));
    header2.setBounds(20,75,600,40);
    header2.setFont(new Font("Calisto MT",Font.BOLD,18));
    currentAmount.setText("Current Amount: "+sc.getAmount());
    currentName.setText("Current Name: "+sc.getName());
    if (sc.getLabel())
    {
      currentLabel.setText("Current Label: Want");
    }
    else
    {
      currentLabel.setText("Current Label: Need");
    }
    currentAmount.setBounds(50,125,600,40);
    currentAmount.setFont(new Font("Calisto MT",Font.BOLD,16));
    newAmount.setBounds(50,160,500,40);
    currentName.setBounds(50,225,200,40);
    currentName.setFont(new Font("Calisto MT",Font.BOLD,16));
    newName.setBounds(50,260,500,40);
    addFocusListener(newName);
    addFocusListener(newAmount);
    currentLabel.setBounds(50,325,200,40);
    currentLabel.setFont(new Font("Calisto MT",Font.BOLD,16));
    newLabel.setBounds(0,360,600,40);
    submit.setBounds(100,410,350,60);
    submit.addActionListener(this);
    close.setBounds(500,5,75,40);
    close.addActionListener(this);
    save.setBounds(500,500,75,40);
    save.addActionListener(this);
    remove.setBounds(0,500,125,40);
    remove.addActionListener(this);
    f.add(header);
    f.add(header2);
    f.add(currentAmount);
    f.add(newAmount);
    f.add(currentName);
    f.add(newName);
    f.add(currentLabel);
    f.add(newLabel); 
    f.add(submit);
    f.add(close);
    f.add(remove);
    f.add(save);
    f.setVisible(true);   
  }   
    @Override
    public void actionPerformed(ActionEvent e) 
    {
      if (e.getSource()==submit)
      {
        boolean changedAmount=false;
        boolean changedName=false;
        double amount=-99;
        String name="";
        boolean label=newLabel.isSelected();
        if (!newAmount.getText().equalsIgnoreCase(("Enter the new amount or don't change this text AT ALL if you want to keep the amount...")))
        {
            changedAmount=true;
            amount=Double.parseDouble(newAmount.getText());
        }
        if (!newName.getText().equalsIgnoreCase(("Enter the new name or don't change this text AT ALL if you want to keep the name...")))
        {
            changedName=true;
            name=newName.getText();
        }
        if (changedAmount)
        {
            sc.setAmount(amount);
            if (!sc.isValidCategory())
            {
                JOptionPane.showMessageDialog(null, "The new spending amount for your category is less than or equal to 0.", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else if (!sc.isValidAmount())
            {
                JOptionPane.showMessageDialog(null, "The new spending amount for your category is invalid as it is greater than 30% of your income.", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "The spending amount for your category was changed.", "Success", JOptionPane.INFORMATION_MESSAGE);
                currentAmount.setText("Current Amount: "+sc.getAmount());
                newAmount.setText("Enter the new amount or don't change this text AT ALL if you want to keep the amount...");
            }
        }
        if (changedName)
        {
            if (!sc.setName(name))
            {
                JOptionPane.showMessageDialog(null, "The new name for your category is empty.", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "The name for your category was changed.", "Success", JOptionPane.INFORMATION_MESSAGE);
                currentName.setText("Current Name: "+sc.getName());
                newName.setText("Enter the new name or don't change this text AT ALL if you want to keep the name...");
            }
        }
        if (sc.getLabel()!=label)
        {
            String labelMessage=sc.setLabel(label) ? "want." : "need.";
            JOptionPane.showMessageDialog(null,"The label for your category is now a "+labelMessage, "Category Label", JOptionPane.INFORMATION_MESSAGE);
        }
        if (sc.getLabel())
        {
            currentLabel.setText("Current Label: Want");
        }
        else
        {
            currentLabel.setText("Current Label: Need");
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
          int answer=JOptionPane.showConfirmDialog(null, "Are you sure you want to remove this spending category?", "Removal",JOptionPane.YES_NO_CANCEL_OPTION);
          if (answer!=0)
          {
              return;
          }
          cl.removeSpendingCategory(sc);
          cb.removeItem(sc);
          JOptionPane.showMessageDialog(null,"Spending Category has been removed, this frame will now close.", "Removal Successful", JOptionPane.INFORMATION_MESSAGE);
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
