import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;


public class FileOrder {
    private String fileOrder, fileOrderItem;
    private FileReader orderReader, itemReader;
    private BufferedReader rederOrder, readerItem;
    private FileProduct fileProduct;
    FileOrder(){
        fileProduct = new FileProduct();
        fileOrder = "order.text";
        fileOrderItem = "orderItem.text";
        try {
        
          File myObj = new File(fileOrder);
          if (myObj.createNewFile()) {
            System.out.println("File created: " + myObj.getName());
          }
          File myObj2 = new File(fileOrderItem);
          if (myObj2.createNewFile()) {
            System.out.println("File created: " + myObj.getName());
          } 

          

        
        } catch (IOException e) {
           
          System.out.println("An error occurred.");
          e.printStackTrace();
          
        }
    
    }

  
    public int countOrder(String date){
      
      int lines = 0;
      try{
        
        orderReader = new FileReader(fileOrder);
        rederOrder = new BufferedReader(orderReader);
       
        String s1;
        while ((s1 = rederOrder.readLine()) != null){
          String order[] = s1.split(",");
                
                if(date.equals(order[6])){
                  lines++;
        } 
      }
      }catch (IOException e) {
      }
      
      return lines;
    }

    public String[][] getOrder(String date){
        String data[][];
  
        try {
          
          int lines = countOrder(date);
       
          orderReader = new FileReader(fileOrder);
          rederOrder = new BufferedReader(orderReader);
          
            if(lines == 0){

              data = new String[1][];
              data[0] = new String[7];
              data[0][0] = "0";
              data[0][1] = "0";
              data[0][2] = "none";
              data[0][3] = "0";
              data[0][4] = "none";
              data[0][5] = "0.00";
              data[0][6] = "0000-00-00";
            }else{
              
              data = new String[lines][];
              for(int i=0; i< lines; i++){
                data[i] = new String[7];
              }
             
              String s1;
              int i = 0;
             
              while ((s1 = rederOrder.readLine()) != null){
              
                String order[] = s1.split(",");
                
                if(date.equals(order[6])){
                  for(int j = 0; j < 7; j++){
                    data[i][j] = order[j];
                  }
                i++;
                }
                    
              } 
            }
          } catch (IOException e) {
            
            data = new String[1][];
            data[0] = new String[7];
            data[0][0] = "0";
            data[0][1] = "0";
            data[0][2] = "none";
            data[0][3] = "0";
            data[0][4] = "none";
            data[0][5] = "0.00";
            data[0][6] = "0000-00-00";
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
  
        return data;

    }
    
    public int getOrderLastId(){
      try{
       
        orderReader = new FileReader(fileOrder);
        rederOrder = new BufferedReader(orderReader);
        
        String line;
        int orderId = 0;
        while ((line = rederOrder.readLine() )!= null){
          String data[] = line.split(",");
          orderId = Integer.parseInt(data[0]);
        }
        return orderId;

      }catch(IOException e){
      }
     
      return 0;
    }

    
    public int getOrderItemLastId(){
      
      try{
      
        itemReader = new FileReader(fileOrderItem);
        readerItem = new BufferedReader(itemReader);
        
        String line;
        int orderId = 0;
        while ((line = readerItem.readLine() )!= null){
          String data[] = line.split(",");
          orderId = Integer.parseInt(data[0]);
        }
        return orderId;

      }catch(IOException e){

      }

    
      return 0;
    }



    
    public boolean addOrder(String order){     
      try{
        
        FileWriter fileWriter = new FileWriter(fileOrder,true);
        fileWriter.write(order);
        fileWriter.close();
        return true;
      }
      catch(IOException e){
        System.out.println("An error occurred.");
            e.printStackTrace();
        return false;

      }
    }

    public boolean addOrderItem(String item){     
      try{
       
        FileWriter fileWriter = new FileWriter(fileOrderItem,true);     
        fileWriter.write(item);
        fileWriter.close();
        return true;
      }
      catch(IOException e){
        System.out.println("An error occurred.");
            e.printStackTrace();
        return false;

      }
    }

    public int getCountDateOrder(){
      int count = 0;
      try{

         orderReader = new FileReader(fileOrder);
         rederOrder = new BufferedReader(orderReader);
        
         String line,date = "";
         while ((line = rederOrder.readLine() )!= null){
           String data[] = line.split(",");
           if(!date.equals((String)data[6])){
              date = data[6];
              count++;
           }
           
         }
         return count;
 
       }catch(IOException e){
       }
       return count;
    }

    public String[] getOrderDay(){
      String[] dateOrder;
      try{

        int count = getCountDateOrder();
        dateOrder = new String[count];
        
         orderReader = new FileReader(fileOrder);
         rederOrder = new BufferedReader(orderReader);
        
         String line;
         int i=0;
         String date = "";
         while ((line = rederOrder.readLine() )!= null){
           String data[] = line.split(",");
           if(!date.equals((String)data[6])){
             date = data[6];
            dateOrder[i] = data[6];
    
            i++;
           }
         }
         return dateOrder;
 
       }catch(IOException e){
       }
       dateOrder = new String[1];
       dateOrder[0] ="none";
       return dateOrder;
      
      
     }

     public int countOrderItem(int id){
      int count = 0;
      try{
      
         itemReader = new FileReader(fileOrderItem);
         readerItem = new BufferedReader(itemReader);
        
         String line;
         while ((line = readerItem.readLine() )!= null){
           String data[] = line.split(",");
           if(id == Integer.valueOf(data[1])){
              count++;
           }
         }
         return count;
 
       }catch(IOException e){
 
       }
     
       return 0;

     }

    public String[][] getOrderItem(int id){
      String item[][];
      try{

        item = new String[countOrderItem(id)][];
         
        
         itemReader = new FileReader(fileOrderItem);
         readerItem = new BufferedReader(itemReader);
         
         String line;
         int i = 0;
         while ((line = readerItem.readLine() )!= null){
           String data[] = line.split(",");
           if(id == Integer.valueOf(data[1])){
             item[i] = new String[4];
             item[i][0] = data[3];
             item[i][1] = data[4];
             item[i][2] = data[5];
             item[i][3] = data[2];

             i++;
           }
          
         }
         return item;
 
       }catch(IOException e){
 
       }
       item = new String[1][];
       item[0] = new String[3];
       item[0][0] = "error";
       item[0][1] = "error";
       item[0][2] = "error";

       return item;
    }




    public String [][] getProductSale(String date){
      
      String data[][];
  
      int countProduct = fileProduct.countProduct();
      
        if(countProduct == 0){
          data = new String[1][];
          data[0] = new String[6];
          data[0][0] = "0";
          data[0][1] = "none";
          data[0][2] = "0";
          data[0][3] = "0.00";
          data[0][3] = "0.00";
          data[0][4] = "0000-00-00";
        }else{
          data = new String[countProduct][];

          for(int i=0; i< countProduct; i++){
            data[i] = new String[6];
          }
          
          String productData[][] = fileProduct.getProduct(); 
          for(int i=0; i< productData.length; i++){
            data[i][0] = productData[i][0];
            data[i][1] = productData[i][1];
            data[i][2] = "0";
            data[i][3] = productData[i][3];
            data[i][4] = "0.00";
            data[i][5] = date;
          }
          String orderNow[][] = getOrder(date);
          for(int i=0; i < orderNow.length; i++){
            String item[][] = getOrderItem(Integer.valueOf(orderNow[i][0]));
            for(int j=0; j < item.length; j++){
              for(int n= 0; n < data.length; n++){
                if(data[n][0].equals(item[j][3])){
                  int amount = Integer.valueOf(data[n][2]) + Integer.valueOf(item[j][1]);
                  float price = Float.valueOf(data[n][4]) + Float.valueOf(item[j][2]);
                  data[n][2] = String.valueOf(amount);
                  data[n][4] = String.valueOf(price);
                }
              }
            }
          }
        }
         
            
  
        return data;

    }

    

    }





