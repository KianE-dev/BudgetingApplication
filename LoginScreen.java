

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.*;
public class LoginScreen implements ActionListener
{
    JFrame f=new JFrame("Login Screen");
    JLabel title=new JLabel("Welcome to K's Budgeting!");
    JLabel money=new JLabel();
    JLabel report=new JLabel();
    JLabel disclaimer1=new JLabel("DISCLAIMER:");
    JLabel disclaimer2=new JLabel("<html><div style='width:300px;'>You are not required to enter ANY personal data "+
   "about your banking info or anything else personal in this application.</html>");
    JLabel disclaimer3=new JLabel("<html><div style='width:300px;'>This application is meant to SIMULATE your banking/expenses to provide a model on how you could improve your finanical stability in the real world.</html>");
    JLabel passwordLabel=new JLabel("Password ->");
    JLabel usernameLabel=new JLabel("Username ->");
    ImageIcon moneyIcon=new ImageIcon("money-illustration-isolated_23-2151568514.png");
    ImageIcon reportIcon=new ImageIcon("-IND-004-075-_APA_Headings__Formatting_Tips_and_Examples_Final.png");
    ImageIcon appIcon=new ImageIcon("AppIcon.png");
    JTextField username=new JTextField("Enter Username Here...");
    JPasswordField password=new JPasswordField("");
    JButton login=new JButton("Login");
    JButton forgotPassword=new JButton("Forgot Password/ID");
    JButton createNewAccount=new JButton("Create New Account");
    ClientDatabase db;
    LoginScreen(ClientDatabase db)
    {
        this.db=db;
        f.setSize(750,650);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(null);
        f.getContentPane().setBackground(new Color(0xFFFFFF));
        f.setIconImage(appIcon.getImage());
        title.setBounds(290,50,500,50);
        report.setBounds(300,295,360,270);
        report.setIcon(reportIcon);
        money.setBounds(75,0,200,133);
        money.setIcon(moneyIcon);
        title.setFont(new Font("Bodoni MT",Font.BOLD,30));
        username.setBounds(75,125,200,40);
        password.setBounds(75,200,200,40);
        usernameLabel.setBounds(0,125,200,40);
        passwordLabel.setBounds(0,200,200,40);
        login.setBounds(75,275,200,40);
        forgotPassword.setBounds(75,400,200,40);
        createNewAccount.setBounds(75,500,200,40);
        disclaimer1.setBounds(300,125,200,40);
        disclaimer2.setBounds(300,150,400,60);
        disclaimer3.setBounds(300,225,400,60);
        forgotPassword.addActionListener(this);
        login.addActionListener(this);
        createNewAccount.addActionListener(this);
        addFocusListener(username);
        addFocusListener(password);
        f.add(username);
        f.add(password);
        f.add(login);
        f.add(forgotPassword);
        f.add(createNewAccount);
        f.add(title);
        f.add(money);
        f.add(report);
        f.add(disclaimer1);
        f.add(disclaimer2);
        f.add(disclaimer3);
        f.add(usernameLabel);
        f.add(passwordLabel);
        f.setVisible(true);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if (e.getSource()==login)
        {
            boolean idVerified=false;
            boolean loginVerified=false;
            String user=username.getText();
            String pass=new String(password.getPassword());
            String profileName="";
            Client cl=null;
            for (int i=0; i<db.lengthOfDatabase(); i++)
            {
                Client c=db.findClientWithIndex(i);
                if (c.login(user, pass))
                {
                    profileName=c.getProfileName();
                    loginVerified=true;
                    cl=c;
                }    
            }
            if (loginVerified)
            {
                Long id=Long.valueOf(JOptionPane.showInputDialog("What is the ID for your account?"));
                for (int i=0; i<db.lengthOfDatabase(); i++)
                {
                    Client c=db.findClientWithIndex(i);
                    if (c.getID()==id)
                    {
                        idVerified=true;
                    }    
                }
                if (idVerified)
                {
                  JOptionPane.showMessageDialog(null, "Login was successful. Welcome, "+profileName+".", "Login Successful",JOptionPane.INFORMATION_MESSAGE);
                  f.dispose();
                  new MainMenu(cl,db);
                }        
                else
                {
                    JOptionPane.showMessageDialog(null,"Incorrect ID number!", "ERROR", JOptionPane.ERROR_MESSAGE);
                }    
            }
            else
            {
                JOptionPane.showMessageDialog(null,"Login failed. Check your username and password again.", "Login Unsuccessful", JOptionPane.INFORMATION_MESSAGE);
            }            
            
        }
        if (e.getSource()==forgotPassword)
        {
           new PasswordFrame(db);
        }
        if (e.getSource()==createNewAccount)
        {
            new AccountCreationFrame(db);
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
