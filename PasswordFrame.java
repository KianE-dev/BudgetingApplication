

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.*;
public class PasswordFrame implements ActionListener
{
    JFrame passwordFrame=new JFrame("Forgot Password");;
    JTextField userField=new JTextField("Enter Username Here...");
    JButton submitUser=new JButton("Submit");
    JLabel prompt=new JLabel("Enter your username in the box below to prompt a password reset or to retrieve your ID.");
    ClientDatabase db;
    ImageIcon appIcon=new ImageIcon("AppIcon.png");
    PasswordFrame(ClientDatabase db)
    {
      this.db=db;
      passwordFrame.setSize(550,400);
      passwordFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
      passwordFrame.setLayout(null);
      passwordFrame.getContentPane().setBackground(new Color(0x57E69B));
      passwordFrame.setIconImage(appIcon.getImage());
      prompt.setBounds(0,75,500,40);
      userField.setBounds(75, 150, 200, 40);
      addFocusListener(userField);
      submitUser.setBounds(75, 225, 200, 40);
      submitUser.addActionListener(this);
      passwordFrame.add(userField);
      passwordFrame.add(submitUser);
      passwordFrame.add(prompt);
      passwordFrame.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource()==submitUser)
        {
            boolean foundUser=false;
            String user=userField.getText();
            Client cl=null;
            for (int i=0; i<db.lengthOfDatabase(); i++)
            {
               Client c=db.findClientWithUserName(user);
               if (c!=null)
               {
                foundUser=true;
                cl=c;
               } 
            }
            if (foundUser)
            {
                JOptionPane.showMessageDialog(null,"Account with username "+cl.getUsername()+" was found. Your ID is "+ cl.getID()+". The next window will show you the steps for a password reset.", "Account found", JOptionPane.INFORMATION_MESSAGE);
                passwordFrame.dispose();
                new PasswordReset(cl);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "No accounts match that username. Please check your entered value again.", "Account Not Found", JOptionPane.ERROR_MESSAGE);
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
