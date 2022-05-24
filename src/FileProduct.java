import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileProduct {
    private String fileProduct;
    private FileReader productReader;
    private BufferedReader readerPro;
    FileProduct(){
      // add file name
        fileProduct = "product.text";
        try {
          //check file if don't have file is created file
          File myObj = new File(fileProduct);
          if (myObj.createNewFile()) {
            // System.out.println("File created: " + myObj.getName());
          } 
        
        } catch (IOException e) {
           
          System.out.println("An error occurred.");
          e.printStackTrace();
        }
    
    }

    //user for count row product
    public int countProduct(){
      // set variable line is 0
      int lines = 0;
      try{
        // instantiate variable
        productReader = new FileReader(fileProduct);
        readerPro = new BufferedReader(productReader);
        //use while loop to count line and check lind is don't null
        while (readerPro.readLine() != null) lines++;
      }catch (IOException e) {
      }
      
      return lines;
    }
    //use for check id product befor add product
    public boolean checkId(int id){
      //get product data form method getProduct and add to product variable
      String product[][] = getProduct();
      //use for loop to loop data 
      for (int i = 0; i < product.length; i++){
        //check product id if id is have return false don't have return true
        if(id == Integer.parseInt(product[i][0])){
          return false;
        }
      } 
      return true;
    }

    public String[][] getProduct(){
        String data[][];
  
        try {
          //get data form method countProduct and add to lines variable 
          int lines = countProduct();
         // instantiate variable
          productReader = new FileReader(fileProduct);
          readerPro = new BufferedReader(productReader);
          //check line == 0
            if(lines == 0){
              //set data for return 
              data = new String[1][];
              data[0] = new String[4];
              data[0][0] = "0";
              data[0][1] = "None";
              data[0][2] = "0";
              data[0][3] = "0.0";

            }else{
              //set variable 2d array
              data = new String[lines][];
              for(int i=0; i< lines; i++){
                data[i] = new String[4];
              }
              //set variable Strig s1 ,int i = 0
              String s1;
              int i = 0;
              //use loop while check line in file don't null and set s1 = this line 
              while ((s1 = readerPro.readLine()) != null){
                //use split for sub string and add it to array variable
                // 1,50,450
                // product[0] = 1;
                String product[] = s1.split(",");
                //use for loop to add data to array variable 2d 
                    for(int j = 0; j < 4; j++){
                      data[i][j] = product[j];
                    }
                i++;
              } 
            }
          } catch (IOException e) {
             //set data for return for have error
            data = new String[1][];
            data[0] = new String[4];
            data[0][0] = "Error";
            data[0][1] = "Error";
            data[0][2] = "Error";
            data[0][3] = "Error";
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
  
        return data;


    }

    //use to write product to file
    public boolean addProduct(String product){     
      try{
        //open the file
        FileWriter fileWriter = new FileWriter(fileProduct,true);
        //write the product to file
        fileWriter.write(product);
        //close the file
        fileWriter.close();
        return true;
      }
      catch(IOException e){
        System.out.println("An error occurred.");
            e.printStackTrace();
        return false;

      }
    }

    //use to edit Product
    public boolean editProduct(int id, String product){
      try {
        // input the (modified) file content to the StringBuffer "input"
        //open the file
        BufferedReader file = new BufferedReader(new FileReader(fileProduct));
        StringBuffer inputBuffer = new StringBuffer();
        // create line variable for add line data in while lop
        String line;
        // +="fdskl;"
        while ((line = file.readLine()) != null) {
          //use split for sub string and add it to array variable
          String data[] = line.split(",");
          //afer check id form data[0] == id form id parameter
          if(Integer.parseInt(data[0]) == id){
            // replace the line here 
              line = product;  
          }
          //add to string buffer
          inputBuffer.append(line);
          inputBuffer.append('\n');
        }
        file.close();
              
        // write the new string with the replaced line OVER the same file
        FileOutputStream fileOut = new FileOutputStream(fileProduct);
        fileOut.write(inputBuffer.toString().getBytes());
        fileOut.close();
        return true;
      } catch (Exception e) {
          System.out.println("Problem reading file.");
          return false;
      }
    }


    public boolean deleteProduct(int id){
      try {
        // input the (modified) file content to the StringBuffer "input"
        //open the file
        BufferedReader file = new BufferedReader(new FileReader(fileProduct));
        StringBuffer inputBuffer = new StringBuffer();

         // create line variable for add line data in while lop
        String line;
        while ((line = file.readLine()) != null) {
          //use split for sub string and add it to array variable
          String data[] = line.split(",");
          //afer check id form data[0] != id form id parameter
          if(Integer.parseInt(data[0]) != id){
            // true : add to string buffer
            inputBuffer.append(line);
            inputBuffer.append('\n');
          }
          //else : don't use because delete it 
           
        }
        file.close();
                
        // write the new string with the replaced line OVER the same file
        FileOutputStream fileOut = new FileOutputStream(fileProduct);
        fileOut.write(inputBuffer.toString().getBytes());
        fileOut.close();
        return true;
      } catch (Exception e) {
          System.out.println("Problem reading file.");
          return false;
      }
    }

}
