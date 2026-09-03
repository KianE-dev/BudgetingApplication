


public class SpendingCategory
{
   private String name;
   private double spendingAmount;
   private boolean want;
   private double spendingThreshold=0;
   private boolean validCategory=true;
   private boolean validAmount=true;
   private final double MAX_PERCENT_OF_WANT_SPENDING=0.3;
   Client cl; 
   public SpendingCategory(String n, boolean w, Client c)
   {
     if (n==null || n.isEmpty())
     {
        n="Unknown";
        validCategory=false;
     }
     name=n;
     want=w;
     cl=c;
     if (want)
     {
        spendingThreshold=MAX_PERCENT_OF_WANT_SPENDING*cl.getIncome();
     }   
   }
   public void setAmount(double amount)
   {
      if (amount<=0)
      {
         validCategory=false;
      }
      else if (want && amount>spendingThreshold)
      {
         validAmount=false;
      }
      else
      {
         spendingAmount=amount;
      }         
   }
   public String getName() {return name;}
   public double getAmount() {return spendingAmount;}
   public boolean getLabel() {return want;}
   public boolean isValidCategory() {return validCategory;}
   public boolean isValidAmount() {return validAmount;}
   public boolean setName(String n)
   {
      if (n==null || n.isEmpty())
      {
         return false;
      }
      name=n;
      return true;   
   }
   public boolean setLabel(boolean l) {want=l; return want;}
   @Override
   public String toString()
   {
      String label= want ? "Want" : "Need";
      return "Name: "+name+"  Amount: $"+spendingAmount+"  Label: "+label;
   }
}
