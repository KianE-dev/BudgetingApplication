




/**
    This type of account has no restrictions on withdrawals and no threshold for split percentages but can go negative and has an Overdraft fee
 */
public class  CheckingAccount extends Account
{
    private boolean negative;
    private final double OVERDRAFT_FEE=30;
    /**
     * Constructor for objects of class CheckingAccount
     */
    public CheckingAccount(int accNum)
    {
        super(accNum);
        negative=false;
    }
    public void testBalance(double amount)
    {
        if (amount<0)
        {
            negative=false;
        }
        setBalance(amount);
    }
    @Override
    public void withdrawal (double amount)
    {
        if (amount<=0)
        {
            setValidWithdrawal(false);
            return;
        }
        setValidWithdrawal(true);
        setBalance(getBalance()-amount);
        if (getBalance()<0)
        {
            negative=true;
        }
        if (negative)
        {
            setBalance(getBalance()-OVERDRAFT_FEE);
        }
    }
    @Override
    public void calcSplitPercentage(double split)
    {
        if (split<=0 || split>100)
        {
            return;
        }
        setSplitPercentage(split/100.0);
        setValidSplit(true);
    }
    public boolean isNegative()
    {
        if (getBalance()<0)
        {
            negative=true;
            return true;
        }
        negative=false;
        return false;
    }
    @Override
    public String toString()
    {
        return super.toString()+"  (Checking)";
    }
}
