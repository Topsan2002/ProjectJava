import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileCustomer {
    private String fileCustomer;
    private FileReader fileReader;
    private BufferedReader buffReader;
    FileCustomer(){
 
        fileCustomer = "customer.text";
        try {
        
          File myObj = new File(fileCustomer);
          if (myObj.createNewFile()) {
           
          } 
        
        } catch (IOException e) {
           
          System.out.println("An error occurred.");
          e.printStackTrace();
        }
    
    }

   
    public int countCustomer(){
    
      int lines = 0;
      try{
        // instantiate variable
        fileReader = new FileReader(fileCustomer);
        buffReader = new BufferedReader(fileReader);
    
        while (buffReader.readLine() != null) lines++;
      }catch (IOException e) {
      }
      
      return lines;
    }
    
    public int getLastId(){
     
      int lines = 0;
      try{
        
        fileReader = new FileReader(fileCustomer);
        buffReader = new BufferedReader(fileReader);
       
        while (buffReader.readLine() != null) lines++;
      }catch (IOException e) {
      }
      
      return lines;
     
    }




    public String[][] getCustomer(){
        String data[][];
  
        try {
          
          int lines = countCustomer();
         
          fileReader = new FileReader(fileCustomer);
          buffReader = new BufferedReader(fileReader);
         
            if(lines == 0){
              //set data for return 
              data = new String[1][];
              data[0] = new String[4];
              data[0][0] = "0";
              data[0][1] = "None";
              data[0][2] = "None";
              data[0][3] = "None";

            }else{
             
              data = new String[lines][];
              for(int i=0; i< lines; i++){
                data[i] = new String[4];
              }
              
              String s1;
              int i = 0;
              
              while ((s1 = buffReader.readLine()) != null){
                
                String customer[] = s1.split(",");
                
                    for(int j = 0; j < 4; j++){
                      data[i][j] = customer[j];
                    }
                i++;
              } 
            }
          } catch (IOException e) {
            
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

    
    public boolean addCustomer(String product){     
      try{
        
        FileWriter fileWriter = new FileWriter(fileCustomer,true);
        fileWriter.write(product);
        fileWriter.close();
        return true;
      }
      catch(IOException e){
        System.out.println("An error occurred.");
            e.printStackTrace();
        return false;

      }
    }

    
    public boolean editCustomer(int id, String customer){
      try {

        BufferedReader file = new BufferedReader(new FileReader(fileCustomer));
        StringBuffer inputBuffer = new StringBuffer();
       
        String line;
        while ((line = file.readLine()) != null) {
         
          String data[] = line.split(",");
         
          if(Integer.parseInt(data[0]) == id){
              line = customer;  
          }
          
          inputBuffer.append(line);
          inputBuffer.append('\n');
        }
        file.close();
                
        FileOutputStream fileOut = new FileOutputStream(fileCustomer);
        fileOut.write(inputBuffer.toString().getBytes());
        fileOut.close();
        return true;
      } catch (Exception e) {
          System.out.println("Problem reading file.");
          return false;
      }
    }


    public boolean deleteCustomer(int id){
      try {
      
        BufferedReader file = new BufferedReader(new FileReader(fileCustomer));
        StringBuffer inputBuffer = new StringBuffer();        
        String line;
        while ((line = file.readLine()) != null) {
          String data[] = line.split(",");
          if(Integer.parseInt(data[0]) != id){
            inputBuffer.append(line);
            inputBuffer.append('\n');
          } 
        }
        file.close();
        FileOutputStream fileOut = new FileOutputStream(fileCustomer);
        fileOut.write(inputBuffer.toString().getBytes());
        fileOut.close();
        return true;
      } catch (Exception e) {
          System.out.println("Problem reading file.");
          return false;
      }
    }

}
