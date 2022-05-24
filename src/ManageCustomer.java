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

public class ManageCustomer extends JPanel implements ActionListener, DocumentListener, ListSelectionListener {

    private JTable  tableCustomer;
    private JScrollPane scrollPro;
    private JTextField textField, idTf, nameTf, phoneTf, emailTf;
    private JTextField  idETf, nameETf, phoneETf, emailETf;
    private TableRowSorter<TableModel> sort;
    private TableColumnModel columnModel;
    private JLabel labelSearch,idLb, nameLb, emailLb, phoneLb, labelTileEdit, labelTitleAdd;
    private JLabel idELb, labelEName, emailELb, phoneELb;
    private JPanel topBar, right;
    private JButton insetBtn, delBtn, editBtn, cancelBtn;
    private DefaultTableModel modelCustomer;
    private FileCustomer fileCustomer;
    private Customer customer;

    ManageCustomer(){
        super(new BorderLayout(),true);
        setPreferredSize( new Dimension(1000, 600));
        customer = new Customer();
        fileCustomer = new FileCustomer();
        setTopBar();
        setRight();
        settableCustomer();
    }

    public void setTopBar(){
        
        topBar = new JPanel();
        labelSearch = new JLabel("Search Customer : "); 
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
        
        columnModel = tableCustomer.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(150);
        columnModel.getColumn(1).setPreferredWidth(150);
        columnModel.getColumn(2).setPreferredWidth(150);
        columnModel.getColumn(3).setPreferredWidth(150);


        
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
        String productData[][] = fileCustomer.getCustomer();
        
        for (int i = 0; i < productData.length; i++){
            modelCustomer.addRow(new Object[]{productData[i][0],productData[i][1],productData[i][2],productData[i][3]});
        } 
        int sId = fileCustomer.getLastId()+1;
        idTf.setText(String.valueOf(sId));
       
        resetDataAdd();
    }

    public void setRight(){
       
        right = new JPanel();
        
        setUiAddCustomer();
        setUiEditCustomer();
       
        add(right, BorderLayout.CENTER);
    }


    public void setUiAddCustomer(){
      
        labelTitleAdd = new JLabel(":                                                                                        Add Customer                                                                                       :");
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

    public void setUiEditCustomer(){
         
         labelTileEdit = new JLabel(":                                                                                        Edit Customer                                                                                       :");
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
        // TODO Auto-generated method stub

        if(e.getSource() == insetBtn){
            
            if(!nameTf.getText().equals("") && !emailTf.getText().equals("") && !phoneTf.getText().equals("")) {
               
                int id = Integer.parseInt(idTf.getText());
                customer.setId(id);
                customer.setName(nameTf.getText());
                customer.setEmail(emailTf.getText());
                customer.setPhone(phoneTf.getText());
               
                if(fileCustomer.addCustomer(customer.toString()+"\n")){
                    
                    String str = "Insert Customer:" + "Success\nID : " + id +"\nName : " + nameTf.getText() + "\n" + "Email : " + emailTf.getText() + "\n" + "Phoen : " + phoneTf.getText() + "\n";
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
                customer.setId(id);
                customer.setName(nameETf.getText());
                customer.setEmail(emailETf.getText());
                customer.setPhone(phoneETf.getText());
               
                if(fileCustomer.deleteCustomer(customer.getId())){
                    
                    String str = "Delete Customer:" + "Success\nID : " + id +"\nName : " + nameETf.getText() + "\n" + "Email : " + emailETf.getText() + "\n" + "Phoen : " + phoneETf.getText() + "\n";
                    JOptionPane.showMessageDialog(null, str, "Message", JOptionPane.INFORMATION_MESSAGE);;
                    
                    resetDataEdit();
                    refresh();
                }


            }catch(Exception ex){
             
                JOptionPane.showMessageDialog(null, "Please Input All Data !!!", "Message", JOptionPane.ERROR_MESSAGE);

            }
       
        }else if(e.getSource()  == editBtn){
            if(!nameETf.getText().equals("") && !emailETf.getText().equals("") && !phoneETf.getText().equals("")) {
               
                int id = Integer.parseInt(idETf.getText());
                customer.setId(id);
                customer.setName(nameETf.getText());
                customer.setEmail(emailETf.getText());
                customer.setPhone(phoneETf.getText());
              
                if(fileCustomer.editCustomer(customer.getId(),customer.toString())){
                   
                    String str = "Edit Customer:" + "Success\nID : " + id +"\nName : " + nameETf.getText() + "\n" + "Email : " + emailETf.getText() + "\n" + "Phoen : " + phoneETf.getText() + "\n";
                    JOptionPane.showMessageDialog(null, str, "Message", JOptionPane.INFORMATION_MESSAGE);
                    
                    resetDataEdit();
                    refresh();
                }


            }else{
               
                JOptionPane.showMessageDialog(null, "Please Input All Data !!!", "Message", JOptionPane.ERROR_MESSAGE);

            }
      
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
