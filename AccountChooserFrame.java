
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
public class AccountChooserFrame implements ActionListener
{
  JFrame f=new JFrame("Choose an Account");
  JLabel header=new JLabel("Use the drop-down menu below to select your desired account and click the submit button when your done.");
  JComboBox<Account> cb;
  JButton submit=new JButton("Submit");
  JButton close=new JButton("Close");
  Client cl;
  ClientDatabase db;
  MainMenu mainm;
  ImageIcon appIcon=new ImageIcon("AppIcon.png");
  public AccountChooserFrame(Client c, ClientDatabase db, MainMenu mm)
  {
    mainm=mm;
    cl=c;
    this.db=db;
    Account[] clientAccounts=new Account[cl.getPartition().numOfAccounts()];
    for (int i=0; i<clientAccounts.length; i++)
    {
      clientAccounts[i]=cl.getPartition().getAccountByPosition(i);
    }
    cb=new JComboBox<>(clientAccounts);
    f.setSize(625,400);
    f.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    f.setLayout(null);
    f.getContentPane().setBackground(new Color(0x57E69B));
    f.setIconImage(appIcon.getImage());
    header.setBounds(0,50,625,40);
    header.setFont(new Font("Calisto MT",Font.BOLD,12));
    cb.setBounds(25,100,425,50);
    submit.setBounds(460,100,100,50);
    close.setBounds(500,200,100,50);
    submit.addActionListener(this);
    close.addActionListener(this);
    f.add(header);
    f.add(cb);
    f.add(submit);
    f.add(close);
    f.setVisible(true);
  }
    @Override
    public void actionPerformed(ActionEvent e) 
    {
      if (e.getSource()==submit)
      {
        String choice=JOptionPane.showInputDialog("What do you want to see for this account? 1- Its Income Partition 2- Its Financial Goal Progress");
        if (choice==null || choice.isEmpty())
        {
            JOptionPane.showMessageDialog(null,"Please enter a value.", "ERROR", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (choice.equals("1"))
        {
            if (cl.listOfIncomeSources()==0)
            {
              JOptionPane.showMessageDialog(null,"You need to have at least 1 income source before you set up this account's income partition.", "ERROR", JOptionPane.ERROR_MESSAGE);
              return;
            }  
            new IncomePartitionFrame((Account)cb.getSelectedItem(), cl, db);
        }
        else if (choice.equals("2"))
        {
           if (cl.listOfIncomeSources()==0 || cl.getPayFrequency()==0)
           {
             JOptionPane.showMessageDialog(null,"Your income info needs to be set up before you see your financial goal.", "ERROR", JOptionPane.ERROR_MESSAGE);
             return;
           }
           new FinancialGoalFrame((Account)cb.getSelectedItem(),cl);  
        }
        else
        {
          JOptionPane.showMessageDialog(null,"Invalid value entered!", "ERROR", JOptionPane.ERROR_MESSAGE);
        } 
      }
      if (e.getSource()==close)
      {
        mainm.getFrame().setVisible(true);
        f.dispose();
      }  
    }  
}
