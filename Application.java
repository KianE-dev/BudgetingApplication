

import java.io.*;
import java.time.*;
import java.util.*;
import javax.swing.*;
public class Application 
{
    private static ClientDatabase database;
    public static void main(String[] args) 
    {
        Application a1=new Application();
        new LoadingBar();
        a1.addToDatabase();
        new LoginScreen(database);
    }
    public void addToDatabase()
    {
        database=new ClientDatabase();
        loadDatabase(database);
    }
    public void loadDatabase(ClientDatabase db)
    {
       try
       {
         File f=new File("ClientList.csv");
         Scanner input=new Scanner(f);
         load(input, db);
         input.close();
       }
       catch (FileNotFoundException ex)
       {
         JOptionPane.showMessageDialog(null,"Database file not found! Please close the program.", "FILE NOT FOUND",JOptionPane.ERROR_MESSAGE);
       } 
    }
    public void load(Scanner input, ClientDatabase db)
    {
        while (input.hasNextLine())
        {
            String line=input.nextLine();
            String[] sections=line.strip().split(",");
            Client c;
            int clientId=Integer.parseInt(sections[0]);
            String userName=sections[1];
            String password=sections[2];
            String profileName=sections[3];
            c=new Client(clientId,userName,password,profileName);
            c.setReferenceDate(LocalDate.of(Integer.parseInt(sections[4]),Month.valueOf(sections[5]),Integer.parseInt(sections[6])));
            c.setPartition(new Partition(c));
            int i=7;
            if (!sections[i].equals("EIS>"))
            {
              while (i<sections.length && !sections[i].equals("EIS>")) 
              {
                long id=Long.parseLong(sections[i++]);
                String name=sections[i++];
                double amount=Double.parseDouble(sections[i++]);
                c.addIncomeSource(new IncomeSource(id, name, amount));
                c.setIncome();
                if (sections[i+1].equals("EIS>") && !sections[i].equals("0"))
                {
                    c.setPayFrequency(Integer.parseInt(sections[i++]));
                }    
              }
            }
            i++;
            if (!sections[i].equals("ESC>")) 
            {
              while (i < sections.length && !sections[i].equals("ESC>")) 
              {
                String name=sections[i++];
                boolean label=Boolean.parseBoolean(sections[i++]);
                double amount=Double.parseDouble(sections[i++]);
                SpendingCategory sc=new SpendingCategory(name, label, c);
                sc.setAmount(amount);
                c.addSpendingCategory(sc);
              }
            }
            i++;
            if (!sections[i].equals("EA")) 
            {
              while (i<sections.length && !sections[i].equals("EA")) 
              {
                String type=sections[i++];
                if (type.equals("S"))
                {
                  SavingsAccount sacc=new SavingsAccount(Integer.parseInt(sections[i++]));
                  sacc.setBalance(Double.parseDouble(sections[i++]));
                  double goalAmt=Double.parseDouble(sections[i++]);
                  int year=Integer.parseInt(sections[i++]);
                  Month m=Month.valueOf(sections[i++]);
                  int day=Integer.parseInt(sections[i++]);
                  sacc.setGoal(goalAmt, LocalDate.of(year, m, day));
                  if (!sections[i].equals("0") && !sections[i].equals("EA")) 
                  {
                    sacc.setSplitPercentage(Double.parseDouble(sections[i++]));
                  }
                  if (!sections[i].equals("0") && !sections[i].equals("EA"))
                  {
                      sacc.setPayment(Double.parseDouble(sections[i++]));
                  }
                  c.getPartition().addAccount(sacc);
                }    
                else if (type.equals("C"))
                {
                  CheckingAccount cacc=new CheckingAccount(Integer.parseInt(sections[i++]));
                  cacc.setBalance(Double.parseDouble(sections[i++]));
                  double goalAmt=Double.parseDouble(sections[i++]);
                  int year=Integer.parseInt(sections[i++]);
                  Month m=Month.valueOf(sections[i++]);
                  int day=Integer.parseInt(sections[i++]);
                  cacc.setGoal(goalAmt, LocalDate.of(year, m, day));
                  if (!sections[i].equals("0") && !sections[i].equals("EA")) 
                  {
                    cacc.setSplitPercentage(Double.parseDouble(sections[i++]));
                  }
                  if (!sections[i].equals("0") && !sections[i].equals("EA"))
                  {
                      cacc.setPayment(Double.parseDouble(sections[i++]));
                  }
                  c.getPartition().addAccount(cacc);
                }    
              }
            }        
            db.add(c);                  
        }
    }
}
 