
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.*;
import java.time.*;
import javax.swing.*;
public class UserProfileFrame2 implements ActionListener
{
   JFrame f=new JFrame("User Profile");
   ImageIcon icon=new ImageIcon("UserProfileIcon.jpg");
   JButton submit=new JButton("Submit");
   JButton closeButton=new JButton("Close");
   JButton saveButton=new JButton("Save");
   JButton delete=new JButton("Delete");
   JTextField newUsername=new JTextField("Enter new username here or don't change this text AT ALL if you want to keep your username...");
   JTextField newProfileName=new JTextField("Enter new profile name here or don't change this text AT ALL if you want to keep your profile name...");
   JTextField newPassword=new JTextField("Enter new password here or don't change this text AT ALL of you want to keep your password...");
   JTextField newId=new JTextField("Enter new ID here or don't change this text AT ALL if you want to keep your ID...");
   JTextField newReferenceDate=new JTextField("Enter new reference date here or don't change this text AT ALL if you want to keep your current date...");
   JLabel currentUsername=new JLabel();
   JLabel currentPassword=new JLabel();
   JLabel currentProfileName=new JLabel();
   JLabel currentID=new JLabel();
   JLabel currentReferenceDate=new JLabel();
   JLabel referenceDateDisclaimer=new JLabel("Reference Date must be today or later and in the form of Year/MONTH/Day.");
   JLabel header=new JLabel("Edit your account using the boxes below and hitting the submit button.");
   JLabel passwordRules1=new JLabel("Your New password must: ");
   JLabel passwordRules2=new JLabel("> Be between 12 and 16 characters.");
   JLabel passwordRules3=new JLabel("> Have at least 1 uppercase letter and 1 lowercase letter.");
   JLabel passwordRules4=new JLabel("> Have 1 number AND 1 unique symbol (ex:!@#$%^&*(){}).");
   JLabel idRules1=new JLabel("> ID must be numeric and cannot be the same as any other account in the database.");
   JLabel idRules2=new JLabel("> A message will pop up if your entered ID value matches any ID value currently in the database.");
   Client cl;
   ClientDatabase database;
   boolean upperCase=false;
   boolean lowerCase=false;
   boolean number=false;
   boolean uniqueSym=false;
   boolean passValid=true;
   String[] symbols=new String[]{"!","@","#","$","%","^","&","*","(",")","{","}"};
   UserProfileFrame2(Client c, ClientDatabase db)
   {
      cl=c;
      database=db;
      f.setSize(650,725);
      f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      f.setLayout(null);
      f.getContentPane().setBackground(new Color(0x57E69B));
      currentUsername.setText("Current Username: "+cl.getUsername());
      f.setIconImage(icon.getImage());
      currentPassword.setText("Current Password: "+cl.getPassword());
      currentProfileName.setText("Current Profile Name: "+cl.getProfileName());
      currentID.setText("Current ID: "+cl.getID());
      currentReferenceDate.setText("Current Reference Date: "+cl.getReferenceDate());
      header.setBounds(25,25,600,40);
      header.setFont(new Font("Calisto MT",Font.BOLD,17));
      currentUsername.setBounds(50,55,600,40);
      currentUsername.setFont(new Font("Calisto MT",Font.BOLD,15));
      newUsername.setBounds(50,90,550,40);
      passwordRules1.setBounds(50,150,600,40);
      passwordRules1.setFont(new Font("Calisto MT",Font.BOLD,15));
      passwordRules2.setBounds(50,170,600,40);
      passwordRules2.setFont(new Font("Calisto MT",Font.BOLD,13));
      passwordRules3.setBounds(50,190,600,40);
      passwordRules3.setFont(new Font("Calisto MT",Font.BOLD,13));
      passwordRules4.setBounds(50,210,600,40);
      passwordRules4.setFont(new Font("Calisto MT",Font.BOLD,13));
      currentPassword.setBounds(50,230,600,40);
      currentPassword.setFont(new Font("Calisto MT",Font.BOLD,15));
      newPassword.setBounds(50,260,550,40);
      currentProfileName.setBounds(50,310,600,40);
      currentProfileName.setFont(new Font("Calisto MT",Font.BOLD,15));
      newProfileName.setBounds(50,340,550,40);
      idRules1.setBounds(50,370,600,40);
      idRules1.setFont(new Font("Calisto MT",Font.BOLD,14));
      idRules2.setBounds(50,390,600,40);
      idRules2.setFont(new Font("Calisto MT",Font.BOLD,13));
      currentID.setBounds(50,410,600,40);
      currentID.setFont(new Font("Calisto MT",Font.BOLD,15));
      newId.setBounds(50,440,500,40);
      currentReferenceDate.setBounds(50,480,600,40);
      currentReferenceDate.setFont(new Font("Calisto MT",Font.BOLD,15));
      referenceDateDisclaimer.setBounds(50,500,600,40);
      referenceDateDisclaimer.setFont(new Font("Calisto MT",Font.BOLD,15));
      newReferenceDate.setBounds(50,530,575,40);
      addFocusListener(newUsername);
      addFocusListener(newProfileName);
      addFocusListener(newPassword);
      addFocusListener(newId);
      addFocusListener(newReferenceDate);
      submit.setBounds(125,580,300,60);
      closeButton.setBounds(550,0,75,40);
      saveButton.setBounds(525,600,75,40);
      delete.setBounds(20,595,100,40);
      submit.addActionListener(this);
      closeButton.addActionListener(this);
      saveButton.addActionListener(this);
      delete.addActionListener(this);
      f.add(currentUsername);
      f.add(currentPassword);
      f.add(currentID);
      f.add(currentProfileName);
      f.add(newId);
      f.add(newPassword);
      f.add(newProfileName);
      f.add(newUsername);
      f.add(header);
      f.add(passwordRules1);
      f.add(passwordRules2);
      f.add(passwordRules3);
      f.add(passwordRules4);
      f.add(idRules1);
      f.add(idRules2);
      f.add(currentReferenceDate);
      f.add(referenceDateDisclaimer);
      f.add(newReferenceDate);
      f.add(submit);
      f.add(saveButton);
      f.add(closeButton);
      f.add(delete);
      f.setVisible(true);
   }
    @Override
    public void actionPerformed(ActionEvent e) 
    {
      if (e.getSource()==submit)
      {
         boolean changedUser=false;
         boolean changedPass=false;
         boolean changedProfile=false;
         boolean changedID=false;
         boolean changedRefDate=false;
         String user="";
         String pass="";
         String profile="";
         long id=-99;
         String[] sections=new String[0];
         String invalidPassword="";
          if (!newUsername.getText().equalsIgnoreCase("Enter new username here or don't change this text AT ALL if you want to keep your username..."))
          {
              changedUser=true;
              user=newUsername.getText();
          }
          if (!newPassword.getText().equalsIgnoreCase("Enter new password here or don't change this text AT ALL if you want to keep your password..."))
          {
              changedPass=true;
              pass=newPassword.getText();
          }
          if (!newProfileName.getText().equalsIgnoreCase("Enter new profile name here or don't change this text AT ALL if you want to keep your profile name..."))
          {
              changedProfile=true;
              profile=newProfileName.getText();
          }
          if (!newId.getText().equalsIgnoreCase("Enter new ID here or don't change this text AT ALL if you want to keep your ID..."))
          {
              changedID=true;
              id=Long.parseLong(newId.getText());
          }
          if (!newReferenceDate.getText().equalsIgnoreCase("Enter new reference date here or don't change this text AT ALL if you want to keep your current date..."))
          {
              changedRefDate=true;
              sections=newReferenceDate.getText().strip().split("/");
          }
          if (changedPass)
          {
              if (pass == null || pass.isEmpty())
              {
                  JOptionPane.showMessageDialog(null, "Password is empty. Please enter a password.", "ERROR", JOptionPane.ERROR_MESSAGE);
              }
              else if (!pass.equals(cl.getPassword()))
              {
                  if (pass.length() >= 12 && pass.length() <= 16)
                  {
                      for (int i = 0; i < pass.length(); i++)
                      {
                          char character = pass.charAt(i);
                          if (Character.isDigit(character))
                          {
                              number = true;
                          }
                          if (Character.isLowerCase(character))
                          {
                              lowerCase = true;
                          }
                          if (Character.isUpperCase(character))
                          {
                              upperCase = true;
                          }
                          String character2 = "" + character;
                          for (String symbol : symbols)
                          {
                              if (character2.equals(symbol))
                              {
                                  uniqueSym = true;
                              }
                          }
                      }
                      if (number && lowerCase && upperCase && uniqueSym)
                      {
                          passValid = true;
                          cl.setPassword(pass);
                      }
                      else
                      {
                          passValid=false;
                      }
                      number = false;
                      lowerCase = false;
                      upperCase = false;
                      uniqueSym = false;
                  }
              }
              else
              {
                  passValid = true;
                  cl.setPassword(pass);
              }
              invalidPassword= passValid ? "" : "Your entered password is invalid";
          }
        if (invalidPassword.isEmpty())
        {
            if (changedUser)
            {
                if (!cl.setUsername(user))
                {
                    JOptionPane.showMessageDialog(null, "Username is empty. Please enter a username.", "ERROR", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            if (changedProfile)
            {
                if (!cl.setProfileName(profile))
                {
                    JOptionPane.showMessageDialog(null, "Profile Name is empty. Please enter a profile name.", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
            if (changedID)
            {
                if (id != cl.getID())
                {
                    boolean uniqueID = true;
                    for (int i = 0; i < database.lengthOfDatabase(); i++)
                    {
                        if (id == database.findClientWithIndex(i).getID())
                        {
                            uniqueID = false;
                        }
                    }
                    if (!uniqueID)
                    {
                        JOptionPane.showMessageDialog(null, "ID was not set due to it already being assigned to another Client. Please enter a new value.", "ERROR", JOptionPane.ERROR_MESSAGE);
                    }
                    cl.setID(id);
                }
                else
                {
                    cl.setID(id);
                }
            }
            if (changedRefDate)
            {
                if (!cl.setReferenceDate(LocalDate.of(Integer.parseInt(sections[0]), Month.valueOf(sections[1]), Integer.parseInt(sections[2]))))
                {
                    JOptionPane.showMessageDialog(null, "Reference date is after today's date. Please enter a different date.", "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
            if (changedUser || changedPass || changedID || changedProfile || changedRefDate)
            {
                JOptionPane.showMessageDialog(null,"The changes were set successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
            currentUsername.setText("Current Username: "+cl.getUsername());
            currentPassword.setText("Current Password: "+cl.getPassword());
            currentProfileName.setText("Current Profile Name: "+cl.getProfileName());
            currentID.setText("Current ID: "+cl.getID());
            currentReferenceDate.setText("Current Reference Date: "+cl.getReferenceDate());
            newUsername.setText("Enter new username here or don't change this text AT ALL if you want to keep your username...");
            newProfileName.setText("Enter new profile name here or don't change this text AT ALL if you want to keep your profile name...");
            newPassword.setText("Enter new password here or don't change this text AT ALL of you want to keep your password...");
            newId.setText("Enter new ID here or don't change this text AT ALL if you want to keep your ID...");
            newReferenceDate.setText("Enter new reference date here or don't change this text AT ALL if you want to keep your current date...");
        }
        else
        {
            JOptionPane.showMessageDialog(null, invalidPassword, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
      }   
      if (e.getSource()==closeButton)
      {
         f.dispose();
      }
      if (e.getSource()==delete)
      {
          int answer=JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this account?", "Removal",JOptionPane.YES_NO_CANCEL_OPTION);
          if (answer!=0)
          {
              return;
          }
          database.delete(cl);
          save();
          JOptionPane.showMessageDialog(null,"Your account has been deleted, you will now be transferred back to the login screen.", "Deletion Successful", JOptionPane.INFORMATION_MESSAGE);
          Frame[] frames=Frame.getFrames();
          for (Frame frame : frames)
          {
              frame.dispose();
          }
          new LoginScreen(database);
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
    public void save()
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
                printer.println("EA");
            }
            printer.close();
        }
        catch (FileNotFoundException ex)
        {
            JOptionPane.showMessageDialog(null,"Database file not found! Please close the program and make sure ClientList.csv is in the folder.", "FILE NOT FOUND",JOptionPane.ERROR_MESSAGE);
        }
    }
}
