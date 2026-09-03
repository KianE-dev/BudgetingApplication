import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AccountReportFrame implements ActionListener
{
    JFrame f=new JFrame("Account Report");
    JLabel negativeBalances=new JLabel();
    JLabel lowBalances =new JLabel();
    JLabel splits=new JLabel("Amounts each account receives per pay frequency cycle:");
    JLabel otherInfo =new JLabel("All other info about your accounts:");
    JButton close=new JButton("Close");
    Client cl;
    ImageIcon appIcon=new ImageIcon("AppIcon.png");
    AccountReportFrame(Client c)
    {
        cl=c;
        f.setSize(800,800);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setLayout(null);
        f.getContentPane().setBackground(new Color(0x57E69B));
        f.setIconImage(appIcon.getImage());
        int countNeg=0;
        int countLow=0;
        for (int i=0; i<cl.getPartition().numOfAccounts(); i++)
        {
            Account ac=cl.getPartition().getAccountByPosition(i);
            if (ac instanceof CheckingAccount)
            {
                if (((CheckingAccount) ac).isNegative())
                {
                    countNeg++;
                }
                if (ac.getBalance()>0 && ac.getBalance()<=500)
                {
                    countLow++;
                }
            }
            else if (ac instanceof SavingsAccount)
            {
                if (ac.getBalance()>0 && ac.getBalance()<=500)
                {
                    countLow++;
                }
            }
        }
        negativeBalances.setText("Checking Accounts with a negative balance: "+countNeg);
        negativeBalances.setFont(new Font("Calisto MT",Font.PLAIN,20));
        negativeBalances.setBounds(20,50,800,40);
        lowBalances.setText("Accounts with a low balance: "+countLow);
        lowBalances.setFont(new Font("Calisto MT",Font.PLAIN,20));
        lowBalances.setBounds(20,100,800,40);
        splits.setFont(new Font("Calisto MT",Font.PLAIN,20));
        splits.setBounds(20,150,800,40);
        int j=175;
        for (int k=0; k<cl.getPartition().numOfAccounts(); k++)
        {
            Account acc=cl.getPartition().getAccountByPosition(k);
            JLabel x=new JLabel("Account Number: "+acc.getAccountNumber()+" -> $"+acc.getPayment()+" ("+(acc.getSplitPercentage()*100)+"%)");
            JLabel x2=new JLabel(""+acc);
            x.setBounds(20,j,800,40);
            x.setFont(new Font("Calisto MT",Font.PLAIN,20));
            f.add(x);
            x2.setBounds(20,j+125,800,40);
            x2.setFont(new Font("Calisto MT",Font.PLAIN,20));
            f.add(x2);
            j+=25;
        }
        otherInfo.setFont(new Font("Calisto MT",Font.PLAIN,20));
        otherInfo.setBounds(20,j+25,800,40);
        close.setBounds(700,25,75,40);
        close.addActionListener(this);
        f.add(negativeBalances);
        f.add(lowBalances);
        f.add(splits);
        f.add(otherInfo);
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
    }
}
