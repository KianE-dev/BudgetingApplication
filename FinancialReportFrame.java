import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.*;
public class FinancialReportFrame implements ActionListener
{
   JFrame f=new JFrame("Financial Report");
   JLabel header=new JLabel("Welcome to Your Financial Report");
   JButton income=new JButton("Your Income");
   JButton accounts=new JButton("Your Accounts");
   JButton goals=new JButton("Your Goals");
   JButton close=new JButton("Close");
   Client cl;
   ImageIcon appIcon=new ImageIcon("AppIcon.png");
   FinancialReportFrame(Client c)
   {
       cl=c;
       f.setSize(500,500);
       f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       f.setLayout(null);
       f.getContentPane().setBackground(new Color(0x57E69B));
       f.setIconImage(appIcon.getImage());
       header.setBounds(25,50,500,40);
       header.setFont(new Font("Bodoni MT",Font.BOLD,25));
       income.setBounds(50,150,250,50);
       accounts.setBounds(50,210,250,50);
       goals.setBounds(50,270,250,50);
       close.setBounds(400,400,75,40);
       income.addActionListener(this);
       accounts.addActionListener(this);
       goals.addActionListener(this);
       close.addActionListener(this);
       f.add(header);
       f.add(income);
       f.add(accounts);
       f.add(goals);
       f.add(close);
       f.setVisible(true);
   }
    @Override
    public void actionPerformed(ActionEvent e)
    {
       if (e.getSource()==close)
       {
           f.dispose();
       }
       if (e.getSource()==income)
       {
           if (cl.listOfIncomeSources()==0)
           {
               JOptionPane.showMessageDialog(null,"You need to have at least 1 income source to get an Income report", "ERROR", JOptionPane.ERROR_MESSAGE);
               return;
           }
           if (cl.getPayFrequency()==0)
           {
               JOptionPane.showMessageDialog(null,"You need to have a set pay frequency to get an Income report", "ERROR", JOptionPane.ERROR_MESSAGE);
               return;
           }
           new IncomeReportFrame(cl);
       }
        if (e.getSource()==accounts)
        {
            if (cl.getPartition().numOfAccounts()==0)
            {
                JOptionPane.showMessageDialog(null,"You need to have at least one monetary account to get an Account report", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            new AccountReportFrame(cl);
        }
        if (e.getSource()==goals)
        {
            if (cl.getPartition().numOfAccounts()==0)
            {
                JOptionPane.showMessageDialog(null,"You need to have at least one monetary account to get a Goal report", "ERROR", JOptionPane.ERROR_MESSAGE);
                return;
            }
            new GoalReportFrame(cl);
        }
    }
}
