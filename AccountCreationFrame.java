

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.time.*;
import java.util.Arrays;
import javax.swing.*;
public class AccountCreationFrame implements ActionListener
{
   JFrame accountCreation=new JFrame("Create New Account");
   JButton submit=new JButton("Submit");
   JTextField username=new JTextField("Enter Username Here...");
   JTextField profileName=new JTextField("Enter Profile Name Here..."); 
   JPasswordField password=new JPasswordField();
   JLabel header=new JLabel("Create a new account using the boxes below and hitting the submit button.");
   JLabel passwordRules1=new JLabel("Your Password must: ");
   JLabel passwordRules2=new JLabel("> Be between 12 and 16 characters.");
   JLabel passwordRules3=new JLabel("> Have at least 1 uppercase letter and 1 lowercase letter.");
   JLabel passwordRules4=new JLabel("> Have 1 number AND 1 unique symbol (ex:!@#$%^&*(){}).");
   JLabel idRules1=new JLabel("> ID is numeric and unique from all other accounts in the database.");
   JLabel idRules2=new JLabel("> You must remember your ID as you will need it to login");
   JLabel idLabel=new JLabel();
   JButton showPass=new JButton("Show Password");
   private ClientDatabase db;
   private boolean upperCase=false;
   private boolean lowerCase=false;
   private boolean number=false;
   private boolean uniqueSym=false;
   private boolean passValid=false;
   private boolean passwordIsVisible=false;
   String[] symbols=new String[]{"!","@","#","$","%","^","&","*","(",")","{","}"};
   ImageIcon appIcon=new ImageIcon("AppIcon.png");
   AccountCreationFrame(ClientDatabase database)
   {
      db=database;
      accountCreation.setSize(600,600);
      accountCreation.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      accountCreation.setLayout(null);
      accountCreation.getContentPane().setBackground(new Color(0x57E69B));
      accountCreation.setIconImage(appIcon.getImage());
      idLabel.setText("Your ID number will show up once you create the account.");
      idLabel.setFont(new Font("Bodoni MT",Font.BOLD,17));
      header.setBounds(100,50,600,40);
      username.setBounds(50,90,200,40);
      passwordRules1.setBounds(50,125,600,40);
      passwordRules2.setBounds(50,145,600,40);
      passwordRules3.setBounds(50,165,600,40);
      passwordRules4.setBounds(50,185,600,40);
      password.setBounds(50,220,200,40);
      password.setEchoChar('*');
      showPass.setBounds(275,220,175,40);
      profileName.setBounds(50,270,200,40);
      idRules1.setBounds(50,300,600,40);
      idRules2.setBounds(50,320,600,40);
      idLabel.setBounds(50,360,600,40);
      submit.setBounds(100,460,300,80);
      submit.addActionListener(this);
      showPass.addActionListener(this);
      accountCreation.add(header);
      accountCreation.add(username);
      accountCreation.add(passwordRules1);
      accountCreation.add(passwordRules2);
      accountCreation.add(passwordRules3);
      accountCreation.add(passwordRules4);
      accountCreation.add(profileName);
      accountCreation.add(password);
      accountCreation.add(showPass);
      accountCreation.add(profileName);
      accountCreation.add(idRules1);
      accountCreation.add(idRules2);
      accountCreation.add(idLabel);
      accountCreation.add(submit);
      addFocusListener(username);
      addFocusListener(profileName);
      accountCreation.setVisible(true);
   }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == submit)
        {
            String user = username.getText();
            String pass = password.getText();
            String prof = profileName.getText();
            if (pass.isEmpty())
            {
                JOptionPane.showMessageDialog(null, "The password field is empty.", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
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
                }
                number = false;
                lowerCase = false;
                upperCase = false;
                uniqueSym = false;
            }
            String invalidPassword = passValid ? "" : "Your entered password is invalid";
            if (invalidPassword.isEmpty())
            {
                long id = (long) ((int) (Math.random() * 1000 + 9999));
                while (db.findClientWithID(id))
                {
                    id = (long) ((int) (Math.random() * 1000 + 9999));
                }
                Client c = new Client(id, user, pass, prof);
                c.setReferenceDate(LocalDate.of(2020, Month.JANUARY, 1));
                c.setPartition(new Partition(c));
                if (db.add(c))
                {
                    JOptionPane.showMessageDialog(null, "Your account has been created. You can now close this window and login to your new account or create another one. Be sure to remember this ID number: " + id + ". You'll need it to login.", "Creation Successful", JOptionPane.INFORMATION_MESSAGE);
                    JOptionPane.showMessageDialog(null, "Be sure to remember this ID number: " + id + ". You'll need it to login.", "WRITE IT DOWN!", JOptionPane.INFORMATION_MESSAGE);
                }
                else
                {
                    JOptionPane.showMessageDialog(null, "Your account has not created. Please check if your username or profile name fields are empty.", "Creation Unsuccessful", JOptionPane.ERROR_MESSAGE);
                }
            }
            else
            {
                JOptionPane.showMessageDialog(null, invalidPassword + ".", "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
        if (e.getSource() == showPass)
        {
            if (passwordIsVisible)
            {
                password.setEchoChar('*');
            }
            else
            {
                password.setEchoChar((char) 0);
            }
            passwordIsVisible = !passwordIsVisible;
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
