

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.*;
import javax.swing.*;
public class CategoriesFrame implements ActionListener
{
   JFrame f=new JFrame("Spending Categories");
   JTextField nameField=new JTextField("Enter the name of the spending category here...");
   JTextField amountField=new JTextField("Enter the amount you spend on this category here...");
   JLabel header=new JLabel("In this window, add or manage the categories that you spend your money on regularly (monthly,weekly,etc.)");
   JLabel disclaimer1=new JLabel("Spending Categories are split into 2 labels: Wants and Needs.");
   JLabel disclaimer2=new JLabel("Wants: Entertainment like music and streaming subscriptions, luxury items, vacations etc.");
   JLabel disclaimer3=new JLabel("Needs: Utilities like light and water bill, groceries, rent/house payments, clothes, healthcare, etc.");
   JLabel disclaimer4=new JLabel("If you label a category as a want, you can only spend up to 30% of your income on it.");
   JCheckBox cb=new JCheckBox("Check here if this category is a want.");
   JButton submit=new JButton("Submit");
   JButton manageCategories=new JButton("Manage Spending Categories");
   JButton saveButton=new JButton("Save");
   JButton closeButton=new JButton("Close");
   Client cl;
   ClientDatabase database;
   JFrame f2=new JFrame("Choose a Category To Manage");
   JLabel header2=new JLabel("Use the drop-down menu below to select your desired spending category and click the submit button when you're done.");
   JComboBox<SpendingCategory> cb2=new JComboBox<>();
   JButton submit2=new JButton("Submit");
   JButton close2=new JButton("Close");
   ImageIcon appIcon=new ImageIcon("AppIcon.png");
   CategoriesFrame(Client c, ClientDatabase db)
   {
      cl=c;
      database=db;
      f.setSize(700,600);
      f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      f.setLayout(null);
      f.getContentPane().setBackground(new Color(0x57E69B));
      f.setIconImage(appIcon.getImage());
      header.setBounds(10,30,700,40);
      header.setFont(new Font("Calisto MT",Font.BOLD,14));
      disclaimer1.setBounds(25,50,700,40);
      disclaimer1.setFont(new Font("Calisto MT",Font.BOLD,15));
      disclaimer2.setBounds(25,75,700,40);
      disclaimer2.setFont(new Font("Calisto MT",Font.BOLD,15));
      disclaimer3.setBounds(25,100,700,40);
      disclaimer3.setFont(new Font("Calisto MT",Font.BOLD,15));
      disclaimer4.setBounds(25,125,700,40);
      disclaimer4.setFont(new Font("Calisto MT",Font.BOLD,17));
      nameField.setBounds(50,170,300,50);
      amountField.setBounds(50,220,300,50);
      addFocusListener(nameField);
      addFocusListener(amountField);
      cb.setBounds(50,270,250,50);
      submit.setBounds(100,420,400,60);
      manageCategories.setBounds(400,500,250,40);
      saveButton.setBounds(20,500,75,40);
      closeButton.setBounds(600,0,75,40);
      saveButton.addActionListener(this);
      closeButton.addActionListener(this);
      manageCategories.addActionListener(this);
      submit.addActionListener(this);
      f.add(manageCategories);
      f.add(header);
      f.add(disclaimer1);
      f.add(disclaimer2);
      f.add(disclaimer3);
      f.add(disclaimer4);
      f.add(nameField);
      f.add(amountField);
      f.add(cb);
      f.add(submit);
      f.add(saveButton);
      f.add(closeButton);
      f.setVisible(true);
   }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource()==submit)
        {
           SpendingCategory sc=new SpendingCategory(nameField.getText(),cb.isSelected(),cl);
           if (!sc.isValidCategory())
           {
             JOptionPane.showMessageDialog(null,"The name for your spending category is empty", "ERROR", JOptionPane.ERROR_MESSAGE);
             return;
           }
           sc.setAmount(Double.parseDouble(amountField.getText()));
           if (!sc.isValidCategory())
           {
             JOptionPane.showMessageDialog(null,"The spending amount for your category is less than or equal to 0.", "ERROR", JOptionPane.ERROR_MESSAGE);
             return;
           }
           if (!sc.isValidAmount())
           {
             JOptionPane.showMessageDialog(null,"The spending amount for your category is invalid as it is a want and greater than 30% of your income.", "ERROR", JOptionPane.ERROR_MESSAGE);
             return;
           }
           if (sc.isValidCategory() && sc.isValidAmount())
           {
             cl.addSpendingCategory(sc);
             cb2.addItem(sc);
             JOptionPane.showMessageDialog(null,"Spending category was added. You can now add another one, change its data with the Manage Categories button or close this window.", "Category Added", JOptionPane.INFORMATION_MESSAGE);
           }
        }
        if (e.getSource()==manageCategories)
        {
            if (cl.numOfSpendingCategories()==0)
            {
                JOptionPane.showMessageDialog(null, "No spending category to manage!", "No Sources", JOptionPane.WARNING_MESSAGE);
                return;
            }
            SpendingCategory[] clientSpendingCategories=new SpendingCategory[cl.numOfSpendingCategories()];
            for (int i=0; i<clientSpendingCategories.length; i++)
            {
                clientSpendingCategories[i]=cl.findSpendingCategoryWithPosition(i);
            }
            cb2=new JComboBox<>(clientSpendingCategories);
            f2.setSize(680,400);
            f2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            f2.setLayout(null);
            f2.getContentPane().setBackground(new Color(0x57E69B));
            f2.setIconImage(appIcon.getImage());
            header2.setBounds(0,50,725,40);
            cb2.setBounds(25,100,425,50);
            submit2.setBounds(460,100,100,50);
            close2.setBounds(500,200,100,50);
            submit2.addActionListener(this);
            close2.addActionListener(this);
            f2.add(header2);
            f2.add(cb2);
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
            new CategoryManager((SpendingCategory)cb2.getSelectedItem(),cl,database,cb2);
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
