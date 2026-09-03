




/**
   This type of Account has a restricted # of withdrawals and a certain threshold for split percentages 
 */
public class SavingsAccount extends Account
{
    private final int MAX_WITHDRAWALS = 6;
    private int numOfWithdrawals;
    /**
     * Constructor for objects of class SavingsAccount
     */
    public SavingsAccount(int accNum)
    {
        super(accNum);
        numOfWithdrawals=0;
        setValidWithdrawal(true);
        setValidSplit(true);
    }
    public void testBalance(double amount)
    {
        if (amount<0)
        {
            setValidAccount(false);
            return;
        }
        setBalance(amount);
    }
    /**
       Withdrawals money out of the account with a cap
     */
    @Override
    public void withdrawal(double amount)
    {
        numOfWithdrawals++;
        if (amount>0)
        {
            if (numOfWithdrawals<=MAX_WITHDRAWALS)
            {
             double withdrawal=getBalance()-amount;
             if (withdrawal<0)
             {
                setValidWithdrawal(false);
                numOfWithdrawals--;
                return;
             }
             setValidWithdrawal(true);
             setBalance(withdrawal);
            }
            else
            {
                setValidWithdrawal(false);
            }
        }
        else
        {
            setValidWithdrawal(false);
            numOfWithdrawals--;
        }
    }
        @Override   
       public void calcSplitPercentage(double split)
       {
        if (split<=0 || split>100)
        {
            setValidSplit(false);
            return;
        }
        if (split>=40)
        {
            setSplitPercentage(split/100.0);
            setValidSplit(true);
        }
        else
        {
            setValidSplit(false);
        }
        
    }
    public int numberOfWithdrawals() {return numOfWithdrawals;}
    @Override
    public String toString()
    {
        return super.toString()+"  (Savings)";
    }
}
