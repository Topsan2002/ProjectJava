public class OrderItem {
    private int id;
    private int orderId;
    private int productId;
    private String productName;
    private int amount;
    private float price;

    OrderItem(){
        setId(0);
        setOrderId(0);
        setProductName("");
        setAmount(0);
        setPrice(0);
    }
    OrderItem(int id, int orderId, int productId, String productName, int amount, float price){
        setId(id);
        setOrderId(orderId);
        setProductId(productId);
        setProductName(productName);
        setAmount(amount);
        setPrice(price);
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setOrderId(int orderId){
        this.orderId = orderId;
    }
    public void setProductId(int productId){
        this.productId = productId;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public void setAmount(int amount) {
        this.amount = amount;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public int getId(){
        return this.id;
    }
    public int getOrderId(){
        return this.orderId;
    }
    public int getProductId(){
        return this.productId;
    }
    public String getProductName(){
        return this.productName;
    }
    public int getAmount(){
        return this.amount;
    }
    public float getPrice(){
        return this.price;
    }
    public String toString(){
        String str = "";
        str += String.valueOf(getId()) + "," + String.valueOf(getOrderId()) + ","+ String.valueOf(getProductId()) + ","  + getProductName() + "," + String.valueOf(getAmount()) + "," + String.valueOf(getPrice());
        return str;
    }




}
