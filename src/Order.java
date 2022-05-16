import java.time.LocalDate;

public class Order {
    private int id;
    private int customerId;
    private String customerName;
    private int sallerId;
    private String sallerName;
    private float totalPrice;
    private LocalDate orderDate;

    Order(){
        setOrderId(0);
        setCustomerId(0);
        setCustomerName("");
        setSallerId(0);
        setSallerName("");
        setTotalPrice(0);
        setOrderDate();
    }

    Order(int id,int customerId, String customerName,int sallerId, String sallerName,float totalPrice){
        setOrderId(id);
        setCustomerId(customerId);
        setCustomerName(customerName);
        setSallerId(sallerId);
        setSallerName(sallerName);
        setTotalPrice(totalPrice);
        setOrderDate();
    }
    public void setOrderId(int id){
        this.id = id;
    }
    public void setCustomerId(int customerId){
        this.customerId = customerId;
    }
    public void setCustomerName(String customerName){
        this.customerName = customerName;
    }
    public void setSallerId(int sallerId){
        this.sallerId = sallerId;
    }
    public void setSallerName(String sallerName){
        this.sallerName = sallerName;
    }
    public void setTotalPrice(float total){
        this.totalPrice = total;
    }
    public void setOrderDate(){
        this.orderDate = LocalDate.now();
    }
    public int getOrderId(){
        return this.id;
    }
    public int getCustomerId(){
        return this.customerId;
    }
    public String getCustomerName(){
        return this.customerName;
    }
    public int getSallerId() {
        return this.sallerId;
    }
    public String getSallerName() {
        return this.sallerName;
    }
    public float getTotalPricel(){
        return this.totalPrice;
    }
    public LocalDate getOrderDate() {
        return this.orderDate;
    }
    public String toString() {
        String str;
        str = (String.valueOf(getOrderId())) + "," + String.valueOf(getCustomerId()) + "," + getCustomerName() + ",";
        str +=  String.valueOf(getSallerId()) + "," + getSallerName() + "," + String.valueOf(getTotalPricel()) + ","  + getOrderDate();
        return str;
    }



}
