import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;

public class ExReport {
    private String fileOrder, fileProduct;
    private String date;
    private FileOrder orderFile;
    private DecimalFormat fm2;
    ExReport(){
        orderFile = new FileOrder();
        fm2 = new DecimalFormat("#,##0.00");
        setDate(String.valueOf(LocalDate.now()));
        setFile();
        writeReportOrder();
        writeReportProduct();
    }

    public void setDate(String date){
        this.date = date;
    }

    public void setFile(){

        this.fileOrder =  "reportfile/reportOrder"+this.date +".text";
        this.fileProduct = "reportfile/reportProduct"+this.date +".text";
        try {      
          File myObj = new File(fileOrder);
          if (myObj.createNewFile()) {
            System.out.println("File created: " + myObj.getName());
          }
          File myObj2 = new File(fileProduct);
          if (myObj2.createNewFile()) {
            System.out.println("File created: " + myObj.getName());
          }
        } catch (IOException e) {
           
          System.out.println("An error occurred.");
          e.printStackTrace();
          
        }

    }

    public void writeReportOrder(){
        try{
            

            StringBuffer inputBuffer = new StringBuffer();

            String orderData[][] = orderFile.getOrder(this.date);
            String line = "";
            float total = 0;
            line += "============================ Report Order" + this.date + " ===========================\n";
            for(int i =0; i < orderData.length; i++){
                line += "No. :" + i+1 + "\n";
                total += Float.valueOf(orderData[i][5]);
                line += "Saller : " + orderData[i][4] +"\n";
                line += "Customer : " + orderData[i][2] +"\n";
                line += "Order Total : " + fm2.format(Float.parseFloat(orderData[i][5]))  +"\n";
                line += "================================ Order Item   ===================================\n";
                String itemData[][] = orderFile.getOrderItem(Integer.valueOf(orderData[i][0]));
                line += "ID\t\t\tName\t\t\t\t\t\tAmount\t\tTotal\n";

                for(int j = 0; j < itemData.length; j++){
                    line +=   otherName(itemData[j][3])+"\t";
                    line +=   nameProduct(itemData[j][0])+"\t";
                    line +=   otherName(itemData[j][1])+"\t";
                    line +=   otherName( fm2.format(Float.parseFloat(itemData[j][2])))+"\t";

                    line += "\n";
                }
            line += "=================================================================================\n";

            }

            line += "Total : " + fm2.format(total)+"\n";
            line += "=================================================================================\n";
            
            inputBuffer.append(line);
            FileOutputStream fileOut = new FileOutputStream(this.fileOrder);
            fileOut.write(inputBuffer.toString().getBytes());
          // System.out.println("Problem reading file.");
            fileOut.close();

       
      } catch (Exception e) {
        // e.printStackTrace();
          System.out.println("Problem reading file.");
        
      }
    }
   

    public void writeReportProduct(){
      try {
        StringBuffer inputBuffer = new StringBuffer();
        String line = "";

        line += "============================ Report Order " + this.date + " ======================================\n";
        line += "ID\t\t\tName\t\t\t\t\t\tAmount\t\tPrice\t\tTotal\t\tdate\n";
       
        String product[][] = orderFile.getProductSale(this.date);
        float total = 0;
        int amount = 0;
        for (int i = 0; i < product.length;i++){
          // line += (i+1) + "\t";
          for (int j = 0; j < product[i].length; j++){
            line +=  (j == 1 ? nameProduct(product[i][j]) : otherName(product[i][j]))  + "\t";
            if(j==4){
              total += Float.valueOf(product[i][j]);
            }
            if(j == 2){
              amount += Integer.valueOf(product[i][j]);
            }
          }
          line += "\n";
        }
        line += "===========================================================================================\n";
        line += "Total Amount : " + (amount)+"\n";
        line += "Total Price : " + fm2.format(total)+"\n";
        line += "===========================================================================================\n";



        inputBuffer.append(line);

        FileOutputStream fileOut = new FileOutputStream(this.fileProduct);
        fileOut.write(inputBuffer.toString().getBytes());
        fileOut.close();

      }catch(Exception e){
        System.out.println("Problem reading file.");

      } 
      
    }

    public String otherName(String name){
      if(name.length() < 4){
        name +="\t\t";
      }else if(name.length() < 8){
        name +="\t";
      }
      return name;
    }

    public String nameProduct(String product){
     
      if(product.length() < 4){
        product +="\t\t\t\t\t\t";
      }else if(product.length() < 8){
        product +="\t\t\t\t\t";
      }else if(product.length() < 12){
        product +="\t\t\t\t";
      }else if(product.length() < 16){
        product +="\t\t\t";
      }else if(product.length() < 20){
        product +="\t\t";
      }else if(product.length() < 24){
        product +="\t";
      }else{
        
      }

      return product;
    }


    }





