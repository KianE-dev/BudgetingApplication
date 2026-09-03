




/**
 * Write a description of class IncomeSource here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class IncomeSource
{
    private double amount;
    private String sourceName;
    private long sourceID;
    private boolean isValidSource=true;
    /**
     * Constructor for objects of class IncomeSource
     */
    public IncomeSource(long id, String name, double amt)
    {
        if (id<=0 || id>Long.MAX_VALUE)
        {
            id=0;
            isValidSource=false;
        }
        sourceID=id;
        if (name==null || name.isBlank())
        {
            name="Unknown";
            isValidSource=false;
        }
        sourceName=name;
        if (amt<=0)
        {
            amt=1;
            isValidSource=false;
        }
        amount=amt;    
    }
    public double getAmount() {return amount;}
    public long getID() {return sourceID;}
    public String getName() {return sourceName;}
    public boolean isValidSource() {return isValidSource;}
    public boolean setAmount(double amount)
    {
        if (amount<=0)
        {
            return false;
        }
        this.amount=amount;
        return true;
    }
    public boolean setID(long id)
    {
        if (id<=0 || id>Long.MAX_VALUE)
        {
            return false;
        }
        sourceID=id;
        return true;    
    }
    public boolean setName(String name)
    {
        if (name==null || name.isEmpty())
        {
            return false;
        }
        sourceName=name;
        return true;    
    }
    @Override
    public String toString()
    {
        return "Name: "+sourceName+"  ID: "+sourceID+"  Amount: $"+amount;
    }
}
