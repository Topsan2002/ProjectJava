
import java.time.LocalDateTime;


public class Product {
    private int id;
 
    private String name;
    private int amount;
    private float price;
    private LocalDateTime  created;

    Product(){
        setId(0);
        setName("");
        setAmount(0);
        setPrice(0.0f);
        setCreated();
    }

    Product(int id, String name, int amount, float price){
        setId(id);
        setName(name);
        setAmount(amount);
        setPrice(price);
        setCreated();
    }

    public void setId(int id){
        this.id = id;
    }
   

    public void setName(String name){
        this.name = name;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setCreated() {
        this.created = LocalDateTime.now();
    }

   public int getId(){
       return this.id;
   }
   

   public String getName() {
       return this.name;
   }

   public int getAmount() {
       return this.amount;
   }

   public float getPrice() {
       return this.price;
   }

   public LocalDateTime getCreated() {
       return this.created;
   }

public String getProduct(){
    return this.id + "," + this.name + "," + this.amount + "," + this.price;
}
public String toString(){
    return this.id + "," + this.name + "," + this.amount + "," + this.price + "," + this.created;

}

    

}
