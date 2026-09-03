

import java.util.*;
public class ClientDatabase 
{
    private ArrayList<Client> database;
    ClientDatabase()
    {
        database=new ArrayList<>();
    }
    public int lengthOfDatabase() {return database.size();}
    public boolean add(Client c)
    {
        if (c.isValidClient())
        {
          database.add(c);
          return true;
        }    
        return false;    
    }
    public void delete(Client c)
    {
        database.remove(c);
    }
    public Client findClientWithIndex(int pos)
    {
        if (pos<0 || pos>=database.size())
        {
            return null;
        }
        return database.get(pos);    
    }
    public Client findClientWithUserName(String user)
    {
        Client cl=null;
        for (Client c : database) 
        {
           if (c.getUsername().equals(user))
           {
              cl=c;
           } 
        }
        return cl;        
    }
    public boolean findClientWithID(long id)
    {
        boolean found=false;
        for (Client c : database)
        {
            if (id==c.getID())
            {
                found=true;
            }    
        }
        return found;    
    }
}
