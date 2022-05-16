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

public class ManageProduct extends JPanel implements ActionListener, DocumentListener, ListSelectionListener {

    JTable  tableProduct, tableOrder;
    JScrollPane scrollPro, scrollOrder;
    JTextField textField, idTf, nameTf, amountTf, priceTf, totalTf;
    JTextField  idETf, nameETf, amountETf, priceETf;

    TableRowSorter<TableModel> sort;
    TableColumnModel columnModelPro, columnModelOrder;
    JLabel labelSearch,labelId, labelName, labelPrice, labelAmount, labelTotal, labelTileEdit, labelTitleAdd;
    JLabel labelEId, labelEName, labelEPrice, labelEAmount;

    JPanel topBar, right, bottom;
    JButton insetBtn, delBtn, editBtn, cancelBtn;
    DefaultTableModel modelProduct;
    JComboBox<String> comCus;
    Product product;
    FileProduct file;

    ManageProduct(){
        super(new BorderLayout(),true);
        setPreferredSize( new Dimension(1000, 600));
        product = new Product();
        file = new FileProduct();
        setTopBar();
        setRight();
        setTableProduct();
    }

    public void setTopBar(){
        //Top bar Search Box
        //Add Label
        topBar = new JPanel();
        labelSearch = new JLabel("Search Product : "); 
        topBar.add(labelSearch );

        //Add textField for search in table
        textField = new JTextField(35);
        textField.getDocument().addDocumentListener(this);
        topBar.add(textField);
        //Add topBar To JPanel
        add(topBar, BorderLayout.NORTH);
    }

    public void setTableProduct() {
        //set Table Product 
        String header[] = { "ID", "Name", "Amount" ,  "Price" };
        tableProduct = new JTable(new DefaultTableModel(header,0));
        modelProduct = (DefaultTableModel) tableProduct.getModel();
        refresh();
        //set Width table
        columnModelPro = tableProduct.getColumnModel();
        columnModelPro.getColumn(0).setPreferredWidth(150);
        columnModelPro.getColumn(1).setPreferredWidth(150);
        columnModelPro.getColumn(2).setPreferredWidth(150);
        columnModelPro.getColumn(3).setPreferredWidth(150);


        //set for sort data in table
        sort = new TableRowSorter<>(tableProduct.getModel());
        ListSelectionModel cellSelectionModel = tableProduct.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // action for select row and insert in TextField Edit Product
        cellSelectionModel.addListSelectionListener(this);
        tableProduct.setRowSorter(sort);
        //Add scroll for scroll
        scrollPro = new JScrollPane(tableProduct);
        scrollPro.setBounds(10, 10, 50, 100);
        add(scrollPro,  BorderLayout.WEST);
    }

    public void refresh(){
        //set Data in Table and get data form file
        modelProduct.setNumRows(0);
        String productData[][] = file.getProduct();
        //loop for add data to table
        for (int i = 0; i < productData.length; i++){
            modelProduct.addRow(new Object[]{productData[i][0],productData[i][1],productData[i][2],productData[i][3]});
        } 
         //set textField is null data
         resetDataAddProduct();
         resetDataEditProduct();

    }

    public void setRight(){
        //set right Edit and Add product data
        right = new JPanel();
        //set ui add produt
        setUiAddProduct();
        //set ui edit product
        setUiEditProduct();
        //add to main JPanel And cet layout center
        add(right, BorderLayout.CENTER);
    }


    public void setUiAddProduct(){
        //addtitle Add Product
        labelTitleAdd = new JLabel(":                                                                                             Add Product                                                                                            :");
        right.add(labelTitleAdd);

        //add label,TextFiled Id for add
        labelId = new JLabel("ID : ");
        idTf = new JTextField(25);

        //add in right Panel
        right.add(labelId);
        right.add(idTf);

        //add label,TextFiled name for add
        labelName = new JLabel("Name : ");
        nameTf = new JTextField(25);

        //add in right Panel
        right.add(labelName);
        right.add(nameTf);

        //add label,TextFiled Amount for add
        labelAmount = new JLabel("Amount : ");
        amountTf = new JTextField(10);

        //add in right Panel
        right.add(labelAmount);
        right.add(amountTf);

        //add label,TextFiled Price for add
        labelPrice = new JLabel("Price : ");
        priceTf = new JTextField(15);
        
        //add in right Panel
        right.add(labelPrice);
        right.add(priceTf);

        //add Button Add Product, Cancel
        insetBtn = new JButton("Add Product");
        insetBtn.addActionListener(this);
        right.add(insetBtn);
        cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(this);
        right.add(cancelBtn);

    }

    public void setUiEditProduct(){
         //addtitle Edit Product
         labelTileEdit = new JLabel(":                                                                                             Edit Product                                                                                            :");
         right.add(labelTileEdit);
    
         //edit label,TextFiled ID for edit
         labelEId = new JLabel("ID : ");
         idETf = new JTextField(25);
         idETf.setEditable(false);
    
         //add in right Panel
         right.add(labelEId);
         right.add(idETf);
    
         //edit label,TextFiled Name for edit
         labelEName = new JLabel("Name : ");
         nameETf = new JTextField(25);
         
         //add in right Panel
         right.add(labelEName);
         right.add(nameETf);
    
         //edit label,TextFiled Amount for edit
         labelEAmount = new JLabel("Amount : ");
         amountETf = new JTextField(10);
         
         //add in right Panel
         right.add(labelEAmount);
         right.add(amountETf);
    
         //edit label,TextFiled Price for edit
         labelEPrice = new JLabel("Price : ");
         priceETf = new JTextField(15);
         
         //add in right Panel
         right.add(labelEPrice);
         right.add(priceETf);
    
         //add Button Add Edit Product
         editBtn = new JButton("Edit Product");
         editBtn.addActionListener(this);
         right.add(editBtn);
         
         //add Button Add Edit Delete
         delBtn = new JButton("Delete");
         delBtn.addActionListener(this);
         right.add(delBtn);
    }

    public void resetDataAddProduct() {
        idTf.setText("");
        nameTf.setText("");
        amountTf.setText("");
        priceTf.setText("");
    }

    public void resetDataEditProduct() {
        idETf.setText("");
        nameETf.setText("");
        amountETf.setText("");
        priceETf.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

        //check button click Add Product 
        if(e.getSource() == insetBtn){
            
            try {
                //get Data form textField Add and  add to variables
                int id = Integer.parseInt(idTf.getText());
                int amount = Integer.parseInt(amountTf.getText());
                float price = Float.parseFloat(priceTf.getText());
                //set Data product to Class Product
                product.setId(id);
                product.setAmount(amount);
                product.setPrice(price);
                product.setName(nameTf.getText());
                product.setCreated();
                //check id product before add
                if(file.checkId(id)){
                    //check status of add product
                    if(file.addProduct(product.toString()+"\n")){
                    //show dialog success
                    String str = "Insert Product :" + "Success\nName : " + nameTf.getText() +"\n" + "ID : " + id + "\n" + "Amount : " + amount + "\n" + "Price : " + price + "\n";
                    JOptionPane.showMessageDialog(null, str, "Message", JOptionPane.INFORMATION_MESSAGE);
                    //set textField is null data
                    resetDataAddProduct();
                    //call method for reset data table
                    refresh();
                    }else{  
                        //show dialog error if have error add product
                        JOptionPane.showMessageDialog(null, "Error", "Message", JOptionPane.ERROR_MESSAGE);

                    }
                }else{
                    //show dialog Your id Duplicate : id is have in file 
                    JOptionPane.showMessageDialog(null, "Your id Duplicate !!!", "Message", JOptionPane.ERROR_MESSAGE);

                }
            
                

            }catch (Exception ec){
                //show dialog error if have Textfield null or id,amount is not integer and price is no float 
                JOptionPane.showMessageDialog(null, "Id, amount is Integer Number\nPrice is Float Number\nOr You Input Data All !!!", "Message", JOptionPane.ERROR_MESSAGE);
               
            }

        //check button click delete
        }else if(e.getSource() == delBtn){
            try{
                //get Data form textField Edit and  add to variables
                int id = Integer.parseInt(idETf.getText());
                int amount = Integer.parseInt(amountETf.getText());
                float price = Float.parseFloat(priceETf.getText());
                //set Data product to Class Product
                product.setId(id);
                product.setAmount(amount);
                product.setPrice(price);
                product.setName(nameETf.getText());
                product.setCreated();
                //check status of delete product
                if(file.deleteProduct(product.getId())){
                    //show dialog success 
                    String str = "Delete Product :" + "Success\nName : " + nameETf.getText() +"\n" + "ID : " + id + "\n" + "Amount : " + amount + "\n" + "Price : " + price + "\n";
                    JOptionPane.showMessageDialog(null, str, "Message", JOptionPane.INFORMATION_MESSAGE);
                    //set textField id null
                   resetDataEditProduct();
                    //call method for reset data table
                    refresh();
                }


            }catch(Exception ex){
                //show dialog error id delete product have error
                JOptionPane.showMessageDialog(null, "Id, amount is Integer Number\nPrice is Float Number", "Message", JOptionPane.ERROR_MESSAGE);

            }
        //check button click edit product
        }else if(e.getSource()  == editBtn){
            try{
                //get Data form textField Edit and  add to variables
                int id = Integer.parseInt(idETf.getText());
                int amount = Integer.parseInt(amountETf.getText());
                float price = Float.parseFloat(priceETf.getText());
                //set Data product to Class Product
                product.setId(id);
                product.setAmount(amount);
                product.setPrice(price);
                product.setName(nameETf.getText());
                product.setCreated();
                //check status of edit product
                if(file.editProduct(product.getId(),product.toString())){
                    //show dialog success 
                    String str = "Edit Product :" + "Success\nName : " + nameETf.getText() +"\n" + "ID : " + id + "\n" + "Amount : " + amount + "\n" + "Price : " + price + "\n";
                    JOptionPane.showMessageDialog(null, str, "Message", JOptionPane.INFORMATION_MESSAGE);
                    //set textField Edit is null
                   resetDataEditProduct();
                    //call method for reset data table
                    refresh();
                }


            }catch(Exception ex){
                //show dialog error id edit product have error
                JOptionPane.showMessageDialog(null, "Id, amount is Integer Number\nPrice is Float Number", "Message", JOptionPane.ERROR_MESSAGE);

            }
        //check button click cancel
        }else if(e.getSource() == cancelBtn){
            //set textField Add is null
            resetDataAddProduct();
        }
        
    }


    @Override
    public void insertUpdate(DocumentEvent e) {
        // TODO Auto-generated method stub

        //user for search in table
        //get text form textfield
        String str = textField.getText();
        //check string is null or empty
        if (str.trim().length() == 0) {
            //sort not have
            sort.setRowFilter(null);
        } else {
            //(?i) means case insensitive search
            //table filter for search
            sort.setRowFilter(RowFilter.regexFilter("(?i)" + str));
        }
        
    }


    @Override
    public void removeUpdate(DocumentEvent e) {
        // TODO Auto-generated method stub
        //user for search in table
        String str = textField.getText();
        if (str.trim().length() == 0) {
            //sort not have
            sort.setRowFilter(null);
        } else {
            //table filter for search
            sort.setRowFilter(RowFilter.regexFilter("(?i)" + str));
        }
    }



    @Override
    public void changedUpdate(DocumentEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        // TODO Auto-generated method stub
        //use for select row in table and insert to textfield edit
        //add number row select to variable
        int[] selectedRow = tableProduct.getSelectedRows();
        
        //user for loop for set textField
        for (int i = 0; i < selectedRow.length; i++) {
            //set textField data form row select
            idETf.setText((String) tableProduct.getValueAt(selectedRow[i], 0));
            nameETf.setText((String) tableProduct.getValueAt(selectedRow[i], 1));
            amountETf.setText((String) tableProduct.getValueAt(selectedRow[i], 2));
            priceETf.setText((String) tableProduct.getValueAt(selectedRow[i], 3));
        }
     
    } 
    


}
