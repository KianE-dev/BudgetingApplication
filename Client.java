



import java.time.*;
import java.util.*;
/**
 * Write a description of class Client here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Client
{
    private String username;
    private String password;
    private String profileName;
    private long clientId;
    private int payFrequency=0;
    private boolean isValidClient;
    private LocalDate referenceDate;
    private Partition clientPartition;
    private ArrayList<IncomeSource> clientIncomeSources=new ArrayList<>();
    private ArrayList<SpendingCategory> clientSpendingCategories=new ArrayList<>();
    private double income;
     /**
     * Constructor for objects of class Client
     */
    public Client(long id, String user, String pass, String prof)
    {
        isValidClient=true;
        if (id<0 || id>Long.MAX_VALUE)
        {
            id=0;
            isValidClient=false;
        }
        clientId=id;
        if (user==null || user.isBlank())
        {
            user="Unknown";
            isValidClient=false;
        }
        username=user;
        if (pass==null || pass.isBlank())
        {
            pass="Unknown";
            isValidClient=false;
        }
        password=pass;
        if (prof==null || prof.isBlank())
        {
            prof="Unknown";
            isValidClient=false;
        }
        profileName=prof;
        clientPartition=null;
        income=0;
        referenceDate=null;
    }
    public long getID() {return clientId;}
    public String getUsername() {return username;}
    public String getPassword() {return password;}
    public String getProfileName() {return profileName;}
    public Partition getPartition() {return clientPartition;}
    public double getIncome() {return income;}
    public boolean isValidClient() {return isValidClient;}
    public int getPayFrequency() {return payFrequency;}
    public LocalDate getReferenceDate() {return referenceDate;}
    public boolean setReferenceDate(LocalDate ref)
    {
       if (ref.isAfter(LocalDate.now()))
       {
         return false;
       }
       referenceDate=ref;
       return true; 
    }
    public boolean setPayFrequency(int pf)
      {
        if (pf<=0)
        {
            return false;
        }
        payFrequency=pf;
        return true;
      }
    public boolean setPartition(Partition partition)
    {
        if (partition==null)
        {
            return false;
        }
        clientPartition=partition;
        return true;
    }
    public void setIncome()
    {
        income=0;
        for (IncomeSource sc : clientIncomeSources)
        {
            income+=sc.getAmount();
        }    
    }
    public boolean setUsername(String user) 
    {
        if (user==null || user.isEmpty())
        {
            return false;
        }
        username=user;
        return true;    
    }
    public boolean setProfileName(String prof) 
    {
        if (prof==null || prof.isEmpty())
        {
            return false;
        }
        profileName=prof;
        return true;    
    }
    public boolean setID(long id) 
    {
        if (id<=0)
        {
            return false;
        }    
        clientId=id;
        return true;
    }
    public boolean login(String user, String pass) {return user.equals(username) && pass.equals(password);}
    public void setPassword(String pass) {password=pass;}
    public boolean addIncomeSource(IncomeSource is)
    {
        if (!is.isValidSource())
        {
            return false;
        }
        clientIncomeSources.add(is);
        return true;    
    }
    public void removeIncomeSource(IncomeSource is)
    {
        clientIncomeSources.remove(is);
    }
    public IncomeSource findIncomeSourceWithPosition(int pos)
    {
       IncomeSource sc=null;
       if (pos>=0 && pos<clientIncomeSources.size())
       {
          sc=clientIncomeSources.get(pos);
       }
       return sc; 
    }
    public boolean uniqueSourceID(long id)
    {
       boolean unique=true;
       for (int i=0; i<listOfIncomeSources(); i++)
       {
          if (id==clientIncomeSources.get(i).getID())
          {
              unique=false;
          }  
       }
       return unique;
    }
    public int listOfIncomeSources() {return clientIncomeSources.size();}
    public boolean addSpendingCategory(SpendingCategory sc) 
    {
        if (!sc.isValidCategory())
        {
            return false;
        }
        clientSpendingCategories.add(sc);
        return true;    
    }
    public void removeSpendingCategory(SpendingCategory sc)
    {
        clientSpendingCategories.remove(sc);
    }
    public SpendingCategory findSpendingCategoryWithPosition(int pos)
    {
       SpendingCategory sc=null;
       if (pos>=0 && pos<clientSpendingCategories.size())
       {
          sc=clientSpendingCategories.get(pos);
       }
       return sc; 
    }
    public SpendingCategory findSpendingCategoryWithName(String name)
    {
       SpendingCategory sc=null;
       for (SpendingCategory s: clientSpendingCategories)
       {
         if (s.getName().equals(name))
         {
            sc=s;
         }   
       } 
       return sc; 
    }
    public int numOfSpendingCategories() {return clientSpendingCategories.size();}  
    public double getSpending()
    {
        double spending=0;
        for (SpendingCategory sc : clientSpendingCategories)
        {
            spending+=sc.getAmount();
        }
        return spending;    
    }
    
    

}
