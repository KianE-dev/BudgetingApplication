


import java.util.*;
/**
 *Keeps track of Client's income partitions 
 */
public class Partition
{
    private ArrayList<Account> clientAccounts=new ArrayList<>();
    private boolean isValidSplit=false;
    private boolean isValidPartition=true;
    private Client cl;
    public Partition(Client c)
    {
        cl=c;
    }
    public void setPartitions()
    {
        double sum=cl.getIncome();    
        for (Account ac : clientAccounts)
        {
            double splitPercent=ac.getSplitPercentage();
            double splitAmount=splitPercent*sum;
            ac.setPayment(splitAmount);
        }
    }
    public Account getAccountByPosition(int pos)
    {
        if (pos<0 || pos>=clientAccounts.size())
        {
            return null;
        }
        return clientAccounts.get(pos);
    }
    public int numOfAccounts() {return clientAccounts.size();}

    public boolean addAccount(Account ac)
    {
        if (ac==null || !ac.isValidAccount())
        {
            return false; 
        }
        clientAccounts.add(ac);
        return true;
    }
    public void removeAccount(Account ac)
    {
        clientAccounts.remove(ac);
    }
    public boolean uniqueAccNum(int accNum)
    {
        boolean unique=true;
        for (int i=0; i<clientAccounts.size(); i++)
        {
            if (accNum==clientAccounts.get(i).getAccountNumber())
            {
                unique=false;
            }    
        }
        return unique;    
    }
    public boolean validSplit(Account ac)
    {    
        validateSplit();
        return isValidSplit();    
    }
    
    /**
     * Checks validity of split percentage
     */
    private void validateSplit()
    {
        double total=0;
        for (Account ac : clientAccounts)
        {
            total+=ac.getSplitPercentage();
        }
        isValidSplit=Math.abs(total-1) <= 1.0e-8;
    }
    public boolean isValidSplit() {return isValidSplit;}
    @Override
    public String toString()
    {
        String x="";
        for (Account ac : clientAccounts)
        {
            x+=ac.toString()+"\n";
        }
        return x;
    }
}
