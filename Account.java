import java.time.*;
public abstract class Account
{
    private double balance;
    private double payment;
    private int accountNumber;
    private double splitPercentage;
    private double goal;
    private LocalDate goalEnding;
    private boolean isValidAccount;
    private boolean isValidWithdrawal;
    private boolean isValidSplit;
    private boolean isValidGoalAmount;
    private boolean isValidGoalEnding;
    public Account(int accNum)
    {
        if (accNum<0)
        {
            accNum=0;
            isValidAccount=false;
        }
        accountNumber=accNum;
        balance=0;
        goal=0;
        isValidAccount=true;
        isValidGoalAmount=true;
        isValidGoalEnding=true;
        isValidWithdrawal=true;
        isValidSplit=true;
        payment=0;
        splitPercentage=0;
    }
    public void setBalance(double amount) {balance=amount;}

    public boolean deposit(double amount)
    {
        if (amount<=0)
        {
            return false;
        }
        balance+=amount;
        return true;
    }
    public abstract void withdrawal(double amount);

    public double getBalance() {return balance;}

    public double getSplitPercentage() {return splitPercentage;}

    public double getPayment() {return payment;}

    public boolean isValidAccount() {return isValidAccount;}

    public boolean isValidWithdrawal() {return isValidWithdrawal;}

    public boolean isValidSplit() {return isValidSplit;}

    public boolean isValidGoalAmount() {return isValidGoalAmount;}

    public boolean isValidGoalEnding() {return isValidGoalEnding;}

    public void setValidAccount(boolean validity) {isValidAccount=validity;}

    public void setValidWithdrawal(boolean validity) {isValidWithdrawal=validity;}

    public void setValidSplit(boolean validity) {isValidSplit=validity;}
    
    public abstract void calcSplitPercentage(double split);

    public void setSplitPercentage(double split) {splitPercentage=split;}

    public void setPayment(double amount) {payment=amount;}

    public int getAccountNumber() {return accountNumber;}

    public boolean setAccountNumber(int accNum)
    {
        if (accNum<0)
        {
            return false;
        }
        accountNumber=accNum;
        return true;    
    }

    public void setGoal(double g, LocalDate endingDate)
    {
        if (g<0 || g<=balance)
        {
            isValidGoalAmount=false;
            return;
        }
        goal=g;
        if (endingDate==null || endingDate.isBefore(LocalDate.now()) || endingDate.isEqual(LocalDate.now()))
        {
            isValidGoalEnding=false;
            return;
        }
        goalEnding=endingDate;
    }

    private String completedGoal()
    {
        String verdict="Goal was not completed.";
        if (LocalDate.now().isBefore(goalEnding))
        {
            verdict="Goal ending has not been reached yet.";
        }
        else if (LocalDate.now().isEqual(goalEnding))
        {
            if (balance==goal)
            {
                verdict="Goal has been reached!";
            }
            else if (balance>goal)
            {
                verdict="Goal has been exceeded, Great job!";
            }
        }
        return verdict;
    }

    public String getMessageVerdict() {return completedGoal();}
    
    public LocalDate getGoalEnding() {return goalEnding;}

    public double getGoalAmount() {return goal;}
    
    public void addToBalance()
    {
        balance+=payment;
    }
    
    @Override
    public String toString()
    {
        return "Account Number: "+accountNumber+"  Balance: $"+balance+" Income Split: "+(splitPercentage*100)+"%";
    }
}
