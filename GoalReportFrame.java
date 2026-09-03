import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
public class GoalReportFrame implements ActionListener
{
    JFrame f=new JFrame("Your Financial Goal Report");
    JLabel reachable=new JLabel();
    JLabel unreachable=new JLabel();
    JLabel averageLength=new JLabel();
    JLabel accountGoals=new JLabel("All accounts goal ending date and reachability:");
    JButton closeButton=new JButton("Close");
    Client cl;
    ImageIcon appIcon=new ImageIcon("AppIcon.png");
    GoalReportFrame(Client c)
    {
        cl=c;
        f.setSize(800,800);
        f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        f.setLayout(null);
        f.getContentPane().setBackground(new Color(0x57E69B));
        f.setIconImage(appIcon.getImage());
        closeButton.setBounds(710,0,75,40);
        closeButton.addActionListener(this);
        int countReachable=0;
        int countUnreachable=0;
        long averageLen=0;
        for (int i=0; i<cl.getPartition().numOfAccounts(); i++)
        {
            Account acc=cl.getPartition().getAccountByPosition(i);
            if (calcReachability(acc))
            {
                countReachable++;
            }
            else
            {
                countUnreachable++;
            }
            averageLen+=ChronoUnit.DAYS.between(LocalDate.now(),acc.getGoalEnding());
        }
        reachable.setText("Number of goals reachable w/ only pay frequency cycle: "+countReachable);
        unreachable.setText("Number of goals unreachable w/ only pay frequency cycle: "+countUnreachable);
        averageLen/=cl.getPartition().numOfAccounts();
        averageLength.setText("Average length of goals in days: "+averageLen);
        reachable.setBounds(25,25,800,40);
        reachable.setFont(new Font("Calisto MT",Font.PLAIN, 20));
        unreachable.setBounds(25,75,800,40);
        unreachable.setFont(new Font("Calisto MT",Font.PLAIN, 20));
        averageLength.setBounds(25,125,800,40);
        averageLength.setFont(new Font("Calisto MT",Font.PLAIN, 20));
        accountGoals.setBounds(25,175,800,40);
        accountGoals.setFont(new Font("Calisto MT",Font.PLAIN, 20));
        f.add(reachable);
        f.add(unreachable);
        f.add(averageLength);
        f.add(accountGoals);
        int j=200;
        for (int i=0; i<cl.getPartition().numOfAccounts(); i++)
        {
            Account acc=cl.getPartition().getAccountByPosition(i);
            DateTimeFormatter format = DateTimeFormatter.ofPattern("MMMM dd, yyyy");
            String dateFormatted = acc.getGoalEnding().format(format);
            JLabel x=new JLabel("Account Number-> "+acc.getAccountNumber()+": Goal Ending Date: "+dateFormatted);
            String reachability=calcReachability(acc) ? "Yes" : "No";
            JLabel y=new JLabel("Reachable?: "+reachability);
            x.setFont(new Font("Calisto MT",Font.PLAIN, 20));
            y.setFont(new Font("Calisto MT",Font.PLAIN, 20));
            x.setBounds(25,j,800,40);
            y.setBounds(25,j+25,800,40);
            f.add(x);
            f.add(y);
            j+=50;
        }
        f.add(closeButton);
        f.setVisible(true);
    }
    public boolean calcReachability(Account ac)
    {
        long daysSince=ChronoUnit.DAYS.between(cl.getReferenceDate(), LocalDate.now());
        long payFreq=cl.getPayFrequency();
        long daysUntilNext=payFreq-daysSince;
        if (daysUntilNext < 0)
        {
            daysUntilNext= payFreq-(daysSince % payFreq);
        }
        LocalDate day= LocalDate.now().plusDays(daysUntilNext);
        double bal= ac.getBalance();
        if (!day.isAfter(ac.getGoalEnding()))
        {
            bal+= ac.getPayment();
        }
        while (!day.isAfter(ac.getGoalEnding()))
        {
            day= day.plusDays(payFreq);
            bal+= ac.getPayment();
        }
        return bal>=ac.getGoalAmount();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        f.dispose();
    }
}
