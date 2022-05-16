import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ManageSaller extends JPanel implements ActionListener, DocumentListener, ListSelectionListener {

    JTable  tableCustomer;
    JScrollPane scrollPro, scrollOrder;
    JTextField textField, idTf, nameTf, phoneTf, emailTf, totalTf;
    JTextField  idETf, nameETf, phoneETf, emailETf;

    TableRowSorter<TableModel> sort;
    TableColumnModel columnModelPro, columnModelOrder;
    JLabel labelSearch,idLb, nameLb, emailLb, phoneLb, labelTotal, labelTileEdit, labelTitleAdd;
    JLabel idELb, labelEName, emailELb, phoneELb;

    JPanel topBar, right, bottom;
    JButton insetBtn, delBtn, editBtn, cancelBtn;
    DefaultTableModel modelCustomer;
    JComboBox<String> comCus;

    FileSaller fileSaller;
    Saller saller;
    ManageSaller(){
        super(new BorderLayout(),true);
        setPreferredSize( new Dimension(1000, 600));
        saller = new Saller();
        fileSaller = new FileSaller();
        setTopBar();
        setRight();
        settableCustomer();
    }

    public void setTopBar(){
    
        topBar = new JPanel();
        labelSearch = new JLabel("Search Saller : "); 
        topBar.add(labelSearch );
      
        textField = new JTextField(35);
        textField.getDocument().addDocumentListener(this);
        topBar.add(textField);
      
        add(topBar, BorderLayout.NORTH);
    }

    public void settableCustomer() {
        
        String header[] = { "ID", "Name", "Email" ,  "Phone" };
        tableCustomer = new JTable(new DefaultTableModel(header,0));
        modelCustomer = (DefaultTableModel) tableCustomer.getModel();
        refresh();
        
        columnModelPro = tableCustomer.getColumnModel();
        columnModelPro.getColumn(0).setPreferredWidth(150);
        columnModelPro.getColumn(1).setPreferredWidth(150);
        columnModelPro.getColumn(2).setPreferredWidth(150);
        columnModelPro.getColumn(3).setPreferredWidth(150);


        
        sort = new TableRowSorter<>(tableCustomer.getModel());
        ListSelectionModel cellSelectionModel = tableCustomer.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
     
        cellSelectionModel.addListSelectionListener(this);
        tableCustomer.setRowSorter(sort);
        
        scrollPro = new JScrollPane(tableCustomer);
        scrollPro.setBounds(10, 10, 50, 100);
        add(scrollPro,  BorderLayout.WEST);
    }

    public void refresh(){
       
        modelCustomer.setNumRows(0);
        String productData[][] = fileSaller.getSaller();
        for (int i = 0; i < productData.length; i++){
            modelCustomer.addRow(new Object[]{productData[i][0],productData[i][1],productData[i][2],productData[i][3]});
        } 
        int sId = fileSaller.getLastId()+1;
        idTf.setText(String.valueOf(sId));
        
        resetDataAdd();
    }

    public void setRight(){
        
        right = new JPanel();
        setUiAddProduct();
        setUiEditProduct();
        
        add(right, BorderLayout.CENTER);
    }


    public void setUiAddProduct(){
      
        labelTitleAdd = new JLabel(":                                                                                             Add Saller                                                                                            :");
        right.add(labelTitleAdd);

      
        idLb = new JLabel("ID : ");
        idTf = new JTextField(25);
        idTf.setEditable(false);
       
        right.add(idLb);
        right.add(idTf);

        nameLb = new JLabel("Name : ");
        nameTf = new JTextField(25);

        right.add(nameLb);
        right.add(nameTf);

        phoneLb = new JLabel("Phone : ");
        phoneTf = new JTextField(10);

        right.add(phoneLb);
        right.add(phoneTf);

        emailLb = new JLabel("Email : ");
        emailTf = new JTextField(15);
        
        right.add(emailLb);
        right.add(emailTf);

        insetBtn = new JButton("Add Customer");
        insetBtn.addActionListener(this);
        right.add(insetBtn);
        cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(this);
        right.add(cancelBtn);

    }

    public void setUiEditProduct(){
        
         labelTileEdit = new JLabel(":                                                                                            Edit Saller                                                                                            :");
         right.add(labelTileEdit);
    
         idELb = new JLabel("ID : ");
         idETf = new JTextField(25);
         idETf.setEditable(false);
    
         right.add(idELb);
         right.add(idETf);
    
         labelEName = new JLabel("Name : ");
         nameETf = new JTextField(25);
         
         right.add(labelEName);
         right.add(nameETf);
    
         phoneELb = new JLabel("Phone : ");
         phoneETf = new JTextField(10);
         
         right.add(phoneELb);
         right.add(phoneETf);
    
         emailELb = new JLabel("Email : ");
         emailETf = new JTextField(15);
         
         right.add(emailELb);
         right.add(emailETf);
    
         editBtn = new JButton("Edit Customer");
         editBtn.addActionListener(this);
         right.add(editBtn);
         
         delBtn = new JButton("Delete");
         delBtn.addActionListener(this);
         right.add(delBtn);
    }

        public void resetDataAdd(){
           
            nameTf.setText("");
            phoneTf.setText("");
            emailTf.setText("");
        }
        public void resetDataEdit(){
            idETf.setText("");
            nameETf.setText("");
            phoneETf.setText("");
            emailETf.setText("");
        }


    @Override
    public void actionPerformed(ActionEvent e) {
        
        if(e.getSource() == insetBtn){
            
            if(!nameTf.getText().equals("") && !emailTf.getText().equals("") && !phoneTf.getText().equals("")) {
               
                int id = Integer.parseInt(idTf.getText());
                saller.setId(id);
                saller.setName(nameTf.getText());
                saller.setEmail(emailTf.getText());
                saller.setPhone(phoneTf.getText());
                
                if(fileSaller.addSaller(saller.toString()+"\n")){
                    
                    String str = "Insert Saller:" + "Success\nID : " + id +"\nName : " + nameTf.getText() + "\n" + "Email : " + emailTf.getText() + "\n" + "Phoen : " + phoneTf.getText() + "\n";
                    JOptionPane.showMessageDialog(null, str, "Message", JOptionPane.INFORMATION_MESSAGE);
                    
                    resetDataAdd();
                    refresh();

                    }else{  
                       
                        JOptionPane.showMessageDialog(null, "Error", "Message", JOptionPane.ERROR_MESSAGE);

                    }
                
            }else{
                JOptionPane.showMessageDialog(null, "Please Input All Data !!!", "Message", JOptionPane.ERROR_MESSAGE);

            }

       
        }else if(e.getSource() == delBtn){
            try{
                
                int id = Integer.parseInt(idETf.getText());
                saller.setId(id);
                saller.setName(nameETf.getText());
                saller.setEmail(emailETf.getText());
                saller.setPhone(phoneETf.getText());
               
                if(fileSaller.deleteSaller(saller.getId())){
                   
                    String str = "Delete Saller:" + "Success\nID : " + id +"\nName : " + nameETf.getText() + "\n" + "Email : " + emailETf.getText() + "\n" + "Phoen : " + phoneETf.getText() + "\n";
                    JOptionPane.showMessageDialog(null, str, "Message", JOptionPane.INFORMATION_MESSAGE);;
                   
                    resetDataEdit();                   
                    refresh();
                }


            }catch(Exception ex){
             
                JOptionPane.showMessageDialog(null, "Id, amount is Integer Number\nPrice is Float Number", "Message", JOptionPane.ERROR_MESSAGE);

            }
       
        }else if(e.getSource()  == editBtn){
            try{
                
                int id = Integer.parseInt(idETf.getText());
                saller.setId(id);
                saller.setName(nameETf.getText());
                saller.setEmail(emailETf.getText());
                saller.setPhone(phoneETf.getText());
                
                if(fileSaller.editSaller(saller.getId(),saller.toString())){

                    String str = "Edit Saller:" + "Success\nID : " + id +"\nName : " + nameETf.getText() + "\n" + "Email : " + emailETf.getText() + "\n" + "Phoen : " + phoneETf.getText() + "\n";
                    JOptionPane.showMessageDialog(null, str, "Message", JOptionPane.INFORMATION_MESSAGE);
                   
                    resetDataEdit();
                    refresh();
                }


            }catch(Exception ex){
                
                JOptionPane.showMessageDialog(null, "Id, amount is Integer Number\nPrice is Float Number", "Message", JOptionPane.ERROR_MESSAGE);

            }
        //check button click cancel
        }else if(e.getSource() == cancelBtn){
            
            resetDataAdd();
        }
        
    }


    @Override
    public void insertUpdate(DocumentEvent e) {
        String str = textField.getText();
      
        if (str.trim().length() == 0) {
            sort.setRowFilter(null);
        } else {
            sort.setRowFilter(RowFilter.regexFilter("(?i)" + str));
        }
        
    }


    @Override
    public void removeUpdate(DocumentEvent e) {
        String str = textField.getText();
        if (str.trim().length() == 0) {
            sort.setRowFilter(null);
        } else {
            sort.setRowFilter(RowFilter.regexFilter("(?i)" + str));
        }
    }



    @Override
    public void changedUpdate(DocumentEvent e) {
       
        
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        int[] selectedRow = tableCustomer.getSelectedRows();
        
        for (int i = 0; i < selectedRow.length; i++) {
          
            idETf.setText((String) tableCustomer.getValueAt(selectedRow[i], 0));
            nameETf.setText((String) tableCustomer.getValueAt(selectedRow[i], 1));
            emailETf.setText((String) tableCustomer.getValueAt(selectedRow[i], 2));
            phoneETf.setText((String) tableCustomer.getValueAt(selectedRow[i], 3));
        }
     
    } 
    


}
