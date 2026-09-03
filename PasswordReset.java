

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class PasswordReset implements ActionListener
{
   JFrame passwordReset=new JFrame("Reset Password");
   JTextField newPassword=new JTextField("Enter New Password Here...");
   JTextField confirmPassword=new JTextField("Confirm New Password...");
   JButton changePassword=new JButton("Change Password");
   JLabel passwordGuideLines1=new JLabel("Your New Password must: ");
   JLabel passwordGuideLines2=new JLabel("> Be between 12 and 16 characters.");
   JLabel passwordGuideLines3=new JLabel("> Have at least 1 uppercase letter and 1 lowercase letter.");
   JLabel passwordGuideLines4=new JLabel("> Have 1 number AND 1 unique symbol (ex:!@#$%^&*(){}).");
   boolean upperCase=false;
   boolean lowerCase=false;
   boolean number=false;
   boolean uniqueSym=false;
   boolean newPassValid=false;
   boolean confPassValid=false;
   String[] symbols=new String[]{"!","@","#","$","%","^","&","*","(",")","{","}"};
   Client cl;
   ImageIcon appIcon=new ImageIcon("AppIcon.png");
   PasswordReset(Client cl)
   {
      this.cl=cl;
      passwordReset.setSize(500,500);
      passwordReset.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      passwordReset.setLayout(null);
      passwordReset.getContentPane().setBackground(new Color(0x57E69B));
      passwordReset.setIconImage(appIcon.getImage());
      newPassword.setBounds(75,145,200,40);
      confirmPassword.setBounds(75,245,200,40);
      changePassword.setBounds(75,345,200,40);
      changePassword.addActionListener(this);
      addFocusListener(newPassword);
      addFocusListener(confirmPassword);
      passwordGuideLines1.setBounds(50,30,500,40);
      passwordGuideLines2.setBounds(75,60,500,40);
      passwordGuideLines3.setBounds(75,80,500,40);
      passwordGuideLines4.setBounds(75,100,500,40);
      passwordGuideLines1.setFont(new Font("Century",Font.BOLD, 25));
      passwordReset.add(passwordGuideLines1);
      passwordReset.add(passwordGuideLines2);
      passwordReset.add(passwordGuideLines3);
      passwordReset.add(passwordGuideLines4);
      passwordReset.add(newPassword);
      passwordReset.add(confirmPassword);
      passwordReset.add(changePassword);
      passwordReset.setVisible(true);
   }
   @Override
    public void actionPerformed(ActionEvent e)
    {
      if (e.getSource()==changePassword)
      {
        String newPass=newPassword.getText();
        String confPass=confirmPassword.getText();
        if (newPass==null || newPass.isBlank())
        {
            JOptionPane.showMessageDialog(null,"Your new password is empty.","ERROR",JOptionPane.ERROR_MESSAGE);
        }
        else if (newPass.length()>=12 && newPass.length()<=16)
        {
          for (int i=0; i<newPass.length(); i++)
          {
            char character=newPass.charAt(i);
            if (Character.isDigit(character))
            {
              number=true;
            }
            if (Character.isLowerCase(character))
            {
              lowerCase=true;
            }
            if (Character.isUpperCase(character))
            {
              upperCase=true;
            }
            String character2=""+character;
            for (String symbol : symbols) 
            {
              if (character2.equals(symbol)) 
              {
                uniqueSym=true;
              }
            }    
          }
          if (number && lowerCase && upperCase && uniqueSym)
          {
            newPassValid=true;
          }
          else
          {
            JOptionPane.showMessageDialog(null,"Your new password is invalid.","ERROR",JOptionPane.ERROR_MESSAGE);
          }      
        }
        else
        {
           JOptionPane.showMessageDialog(null,"Your new password is too short or too long.","ERROR",JOptionPane.ERROR_MESSAGE);
        }
        number=false;
        lowerCase=false;
        upperCase=false;
        uniqueSym=false;
        if (confPass==null || confPass.isBlank())
        {
            JOptionPane.showMessageDialog(null,"The password you typed in the second box is empty.","ERROR",JOptionPane.ERROR_MESSAGE);
        }
        else if (confPass.length()>=12 && confPass.length()<=16)
        {
          for (int i=0; i<confPass.length(); i++)
          {
            char character=confPass.charAt(i);
            if (Character.isDigit(character))
            {
              number=true;
            }
            if (Character.isLowerCase(character))
            {
              lowerCase=true;
            }
            if (Character.isUpperCase(character))
            {
              upperCase=true;
            }
            String character2=""+character;
            for (String symbol : symbols) 
            {
              if (character2.equals(symbol)) 
              {
                uniqueSym=true;
              }
            }    
          }
          if (number && lowerCase && upperCase && uniqueSym)
          {
            confPassValid=true;
          }
          else
          {
            JOptionPane.showMessageDialog(null,"Your 2nd password is invalid.","ERROR",JOptionPane.ERROR_MESSAGE);
          } 
        }
        else
        {
            JOptionPane.showMessageDialog(null,"Your 2nd password is too short or too long.","ERROR",JOptionPane.ERROR_MESSAGE);
        }    
        if (newPassValid==confPassValid && newPass.equals(confPass))
        {
            JOptionPane.showMessageDialog(null,"Your password has been reset to "+newPass+"."+"This window will now close.","Success!",JOptionPane.INFORMATION_MESSAGE);
            cl.setPassword(newPass);
            passwordReset.dispose();
        }
        else
        {
            JOptionPane.showMessageDialog(null,"The passwords do not match.","ERROR",JOptionPane.ERROR_MESSAGE); 
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

