import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileSaller {
    private String fileSaller;
    private FileReader fileReader;
    private BufferedReader buffReader;
    FileSaller(){
      
        fileSaller = "saller.text";
        try {
       
          File myObj = new File(fileSaller);
          if (myObj.createNewFile()) {
            System.out.println("File created: " + myObj.getName());
          } 
        
        } catch (IOException e) {
           
          System.out.println("An error occurred.");
          e.printStackTrace();
        }
    
    }

  
    public int countSaller(){
    
      int lines = 0;
      try{
       
        fileReader = new FileReader(fileSaller);
        buffReader = new BufferedReader(fileReader);
        
        while (buffReader.readLine() != null) lines++;
      }catch (IOException e) {
      }
      
      return lines;
    }
    
    public int getLastId(){
      
      int lines = 0;
      try{
        
        fileReader = new FileReader(fileSaller);
        buffReader = new BufferedReader(fileReader);
        
        while (buffReader.readLine() != null) lines++;
      }catch (IOException e) {
      }
      
      return lines;
     
    }

    public String[][] getSaller(){
        String data[][];
  
        try {
        
          int lines = countSaller();
         
          fileReader = new FileReader(fileSaller);
          buffReader = new BufferedReader(fileReader);
         
            if(lines == 0){
              
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
               
                String saller[] = s1.split(",");
                
                    for(int j = 0; j < 4; j++){
                      data[i][j] = saller[j];
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

   
    public boolean addSaller(String saller){     
      try{
       
        FileWriter fileWriter = new FileWriter(fileSaller,true);
        fileWriter.write(saller);
        fileWriter.close();
        return true;
      }
      catch(IOException e){
        System.out.println("An error occurred.");
            e.printStackTrace();
        return false;

      }
    }

    
    public boolean editSaller(int id, String saller){
      try {
        BufferedReader file = new BufferedReader(new FileReader(fileSaller));
        StringBuffer inputBuffer = new StringBuffer();
        String line;

        while ((line = file.readLine()) != null) {
          String data[] = line.split(",");
          
          if(Integer.parseInt(data[0]) == id){
              line = saller;  
          }
          
          inputBuffer.append(line);
          inputBuffer.append('\n');
        }
        file.close();
                
        FileOutputStream fileOut = new FileOutputStream(fileSaller);
        fileOut.write(inputBuffer.toString().getBytes());
        fileOut.close();
        return true;
      } catch (Exception e) {
          System.out.println("Problem reading file.");
          return false;
      }
    }


    public boolean deleteSaller(int id){
      try {
       
        BufferedReader file = new BufferedReader(new FileReader(fileSaller));
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
     
        FileOutputStream fileOut = new FileOutputStream(fileSaller);
        fileOut.write(inputBuffer.toString().getBytes());
        fileOut.close();
        return true;
      } catch (Exception e) {
          System.out.println("Problem reading file.");
          return false;
      }
    }

}
