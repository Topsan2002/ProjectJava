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
import java.text.DecimalFormat;

public class Home extends JPanel implements ActionListener, DocumentListener, ListSelectionListener {

    private  JTable  tableProduct, tableOrder;
    private  JScrollPane scrollPro, scrollOrder;
    private  JTextField textField, idTf, nameTf, amountTf, priceTf, totalTf, customerNameTf, sallerNameTf;
    private  TableRowSorter<TableModel> sort;
    private  TableColumnModel columnModelPro, columnModelOrder;
    private  JLabel labelSearch,labelId, labelName, labelPrice, labelAmount, labelTotal, labelTile, customerLabel, cusNameLabel;
    private  JPanel top, right;
    private  JButton insetBtn, delBtn, conCusBtn, editCusBtn, submitBtn, cancelBtn;
    private  DefaultTableModel modelOrder, modelProduct;
    private  JComboBox<String> comCus;
    private  DecimalFormat fm2;
    private  JLabel sallerLb, sallerNameLb; 
    private  JComboBox<String> comSaller;
    private  Order order;
    private  OrderItem orderItem[];
    private  FileOrder fileOrder;
    private  FileSaller fileSaller;
    private  FileCustomer fileCustomer;
    private  FileProduct file;

    Home(){
        super(new BorderLayout(),true);
        setPreferredSize( new Dimension(1000, 600));
        fm2 = new DecimalFormat("#,##0.00");
        file = new FileProduct();
        order = new Order();
        fileOrder = new FileOrder();
        fileSaller = new FileSaller();
        fileCustomer = new FileCustomer();
        setTopBar();
        setRight();
        setTableProduct();

    }

    public void setTopBar(){
        top = new JPanel();
        labelSearch = new JLabel("Search Product : "); 
        top.add(labelSearch );

        textField = new JTextField(15);
        textField.getDocument().addDocumentListener(this);
        top.add(textField);

        sallerLb  = new JLabel("Saller : ");
        top.add(sallerLb);
        comSaller = new JComboBox<String>( );
        top.add(comSaller);
       
        
        customerLabel = new JLabel("Customer : ");
        top.add(customerLabel);
        comCus = new JComboBox<String>( );
        top.add(comCus);

        conCusBtn = new JButton("Confirm");
        conCusBtn.addActionListener(this);
        top.add(conCusBtn);

        editCusBtn = new JButton("Edit");
        editCusBtn.addActionListener(this);
        editCusBtn.setEnabled(false);
        top.add(editCusBtn);
        
        add(top, BorderLayout.NORTH);
    }

    public void setTableProduct() {
      
        String header[] = { "ID", "Name", "Price" };

        tableProduct = new JTable(new DefaultTableModel(header,0));
        modelProduct = (DefaultTableModel) tableProduct.getModel();

        reFreshPage();
        
        columnModelPro = tableProduct.getColumnModel();        
        columnModelPro.getColumn(0).setPreferredWidth(400);
        columnModelPro.getColumn(1).setPreferredWidth(400);
        columnModelPro.getColumn(2).setPreferredWidth(400);
       
        sort = new TableRowSorter<>(tableProduct.getModel());
        ListSelectionModel cellSelectionModel = tableProduct.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cellSelectionModel.addListSelectionListener(this);
        tableProduct.setRowSorter(sort); 

        scrollPro = new JScrollPane(tableProduct);

        scrollPro.setBounds(10, 10, 50, 100);
        add(scrollPro,  BorderLayout.WEST);
    }

    public void reFreshPage(){
      
        modelProduct.setNumRows(0);
        String productData[][] = file.getProduct();
        for (int i = 0; i < productData.length; i++){
             modelProduct.addRow(new Object[]{productData[i][0],productData[i][1],String.valueOf(fm2.format(Float.parseFloat(productData[i][3])))});     
        }

        setCombobox();
        resetCustomer();
        resetOrder();
        
    }

    public void setCombobox(){
        String userData[][] = fileCustomer.getCustomer();
        String user[] = new String[userData.length];
        for (int i = 0; i < userData.length; i++){
            user[i] = userData[i][0] + "," + userData[i][1];
        }
        
        DefaultComboBoxModel<String> modelUset = new DefaultComboBoxModel<>( user );
        comCus.setModel( modelUset );

        String sallerData[][] = fileSaller.getSaller();
        String saller[]  = new String[sallerData.length];
        for(int i = 0; i < sallerData.length; i++){
            saller[i] = sallerData[i][0] + "," + sallerData[i][1];
        }
        DefaultComboBoxModel<String> modelSaller = new DefaultComboBoxModel<>( saller );
        comSaller.setModel( modelSaller );
    }   

    public void setRight(){
        
        right = new JPanel();

        setUiCustomer();
        setUiProductOrder();
        setTableOrder();
        setUiTotal();      
        add(right, BorderLayout.CENTER);
    }

    public void setUiCustomer(){
        
        labelTile = new JLabel(":                                                                                   Customer Order                                                                                  :");
        right.add(labelTile);
        
        sallerNameLb = new JLabel("Saller Name : ");
        right.add(sallerNameLb);
        sallerNameTf = new JTextField(20);
        sallerNameTf.setEditable(false);
        right.add(sallerNameTf);

        cusNameLabel = new JLabel("Customer Name : ");
        right.add(cusNameLabel);

        customerNameTf = new JTextField(22);
        customerNameTf.setEditable(false);
        right.add(customerNameTf);
    }

    public void setUiProductOrder(){
        labelId = new JLabel("ID : ");
        idTf = new JTextField(25);
        idTf.setEditable(false);
        right.add(labelId);
        right.add(idTf);

        labelName = new JLabel("Name : ");
        nameTf = new JTextField(25);
        nameTf.setEditable(false);
        right.add(labelName);
        right.add(nameTf);

        labelAmount = new JLabel("Amount : ");
        amountTf = new JTextField(10);
        amountTf.setEditable(true);
        right.add(labelAmount);
        right.add(amountTf);

        labelPrice = new JLabel("Price : ");
        priceTf = new JTextField(15);
        priceTf.setEditable(false);
        right.add(labelPrice);
        right.add(priceTf);

        insetBtn = new JButton("Add Product");
        insetBtn.addActionListener(this);
        right.add(insetBtn);
    }

    public void setTableOrder(){
        String header[] = { "ID", "Name", "Amount" ,  "Price",  };
        tableOrder = new JTable(new DefaultTableModel(header,0));
        modelOrder = (DefaultTableModel) tableOrder.getModel();

        columnModelOrder = tableOrder.getColumnModel();
        columnModelOrder.getColumn(0).setPreferredWidth(400);
        columnModelOrder.getColumn(1).setPreferredWidth(600);
        columnModelOrder.getColumn(2).setPreferredWidth(600);
        columnModelOrder.getColumn(3).setPreferredWidth(600);

        scrollOrder = new JScrollPane(tableOrder);
      
        right.add(scrollOrder);

        delBtn = new JButton("Delete Product");
        delBtn.addActionListener(this);
        right.add(delBtn);
    }

    public void setUiTotal(){

        labelTotal = new JLabel("     Total  : ");
        right.add(labelTotal);
        totalTf = new JTextField(20);
        totalTf.setEditable(false);
        totalTf.setText("0.00");
        totalTf.setHorizontalAlignment(JTextField.RIGHT);
        right.add(totalTf);
        
        submitBtn = new JButton("Submit");
        submitBtn.addActionListener(this);
        right.add(submitBtn);

        cancelBtn = new JButton("Cancel");
        cancelBtn.addActionListener(this);
        right.add(cancelBtn);

    }

    
    public void resetOrder() {
        modelOrder.setRowCount(0);
        conCusBtn.setEnabled(true);
        editCusBtn.setEnabled(false);
        comCus.setEnabled(true);
        comSaller.setEnabled(true);
        customerNameTf.setText("");
        sallerNameTf.setText("");
        totalTf.setText("0.00");
    }
    
    public void resetCustomer() {
        conCusBtn.setEnabled(true);
            editCusBtn.setEnabled(false);
            comCus.setEnabled(true);
            comSaller.setEnabled(true);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource() == insetBtn){
            
            try{
                int amTf = Integer.parseInt(amountTf.getText());
                float prTf = Float.parseFloat(priceTf.getText());
                float total = amTf * prTf;
                modelOrder.addRow(new Object[]{idTf.getText(),nameTf.getText(),amountTf.getText(),String.valueOf(fm2.format(total))});
                setTotal();        
            }catch(Exception ex){
                JOptionPane.showMessageDialog(null, "Amount is Integer Number !!!","Message",JOptionPane.ERROR_MESSAGE);
            }
           

        }else if(e.getSource() == delBtn){
            int[] selectedRow = tableOrder.getSelectedRows();
        
            for (int i = 0; i < selectedRow.length; i++) {
                modelOrder.removeRow(selectedRow[i]);
            }
                setTotal();        
        }else if(e.getSource() == conCusBtn){
            conCusBtn.setEnabled(false);
            editCusBtn.setEnabled(true);
            customerNameTf.setText((String) comCus.getSelectedItem());
            sallerNameTf.setText((String) comSaller.getSelectedItem());
            comCus.setEnabled(false);
            comSaller.setEnabled(false);

        }else if(e.getSource() == editCusBtn){
            resetCustomer();
        }else if(e.getSource() == submitBtn){
            
            sendOrder();



        }else if(e.getSource() == cancelBtn){
           
            resetOrder();
        }
        
    }




    public void sendOrder(){
        
        try {

            String saller[] = sallerNameTf.getText().split(",");
            int sallerId = Integer.parseInt(saller[0]);
            String sallerName = saller[1];
            String customer[] = customerNameTf.getText().split(",");
            int customerId = Integer.parseInt(customer[0]);
            String customerName = customer[1];
            int orderId = fileOrder.getOrderLastId() + 1;
            Float totalPrice = Float.parseFloat(totalTf.getText());
            if(tableOrder.getRowCount() == 0){
                JOptionPane.showMessageDialog(null, "You Don't have any orders", "Message", JOptionPane.ERROR_MESSAGE);

            }else{
                order.setOrderId(orderId);
                order.setCustomerId(customerId);
                order.setCustomerName(customerName);
                order.setSallerId(sallerId);
                order.setSallerName(sallerName);
                order.setTotalPrice(totalPrice);
                order.setOrderDate();

                sendOrderItem();
                
                String mess = "You Order : Success" +"\n";

                 mess += "You Total Order : " + fm2.format(totalPrice) +"\n";
                for(int i = 0; i < orderItem.length;i++){
                    mess +=  "Product  : " + orderItem[i].getProductName() + " | Amount : " + orderItem[i].getAmount() + " | Price : " + fm2.format(orderItem[i].getPrice()) + "\n";
                }
                JOptionPane.showMessageDialog(null, mess, "Message", JOptionPane.INFORMATION_MESSAGE);
                reFreshPage();

            }
            

        }catch(Exception ex){
            JOptionPane.showMessageDialog(null, "Select Seller Or Select Customer !!!", "Message", JOptionPane.ERROR_MESSAGE);
            
        }
    }

    public void sendOrderItem(){
     
        orderItem = new OrderItem[tableOrder.getRowCount()];
        float nTotal = 0;
        for(int i = 0; i < tableOrder.getRowCount(); i++){
            int itemId = fileOrder.getOrderItemLastId() +1;
            int proId = Integer.parseInt((String)tableOrder.getValueAt(i,0));
            String itemName = (String) tableOrder.getValueAt(i, 1);
            int amountItem = Integer.parseInt((String) tableOrder.getValueAt(i,2));
            float priceItem = Float.parseFloat((String)tableOrder.getValueAt(i,3));
            orderItem[i] = new OrderItem(itemId, order.getOrderId(),proId,itemName,amountItem,priceItem);

            String productData[][] = file.getProduct();
            for (int j = 0; j < productData.length; j++){
                if(Integer.parseInt(productData[j][0]) == proId){
                    if(Integer.parseInt(productData[j][2]) < amountItem){
                        orderItem[i].setAmount(Integer.parseInt(productData[j][2]));
                        float nPrice = orderItem[i].getAmount() * Float.parseFloat(productData[j][3]);
                        orderItem[i].setPrice(nPrice);
                        productData[j][2] = "0";
                        String mess = "Sorry!!!\nProduct : " + itemName + "\n" +"has Max Amount " + orderItem[i].getAmount() + "\nProgram Change You amount!!!";
                        JOptionPane.showMessageDialog(null, mess, "Message", JOptionPane.INFORMATION_MESSAGE);

                    }else{
                        productData[j][2] = String.valueOf(Integer.parseInt(productData[j][2]) - amountItem);
                    }
                    Product product = new Product();
                    product.setId(Integer.parseInt(productData[j][0]));
                    product.setName(productData[j][1]);
                    product.setAmount(Integer.parseInt(productData[j][2]));
                    product.setPrice(Float.parseFloat(productData[j][3]));
                    product.setCreated();
                    file.editProduct(product.getId(), product.toString());
                    nTotal += orderItem[i].getPrice();
                }
            }
            order.setTotalPrice(nTotal);
            fileOrder.addOrderItem(orderItem[i].toString()+"\n");
        }
        fileOrder.addOrder(order.toString()+"\n");


    

    }


    public void setTotal(){
        float total = 0;
        for(int i = 0; i < tableOrder.getRowCount(); i++){
            float price = Float.valueOf((String)tableOrder.getValueAt(i, 3));
            total +=   price;
        }
        totalTf.setText(String.valueOf(fm2.format(total)));
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        // TODO Auto-generated method stub
        String str = textField.getText();
        if (str.trim().length() == 0) {
            sort.setRowFilter(null);
        } else {
            //(?i) means case insensitive search
            sort.setRowFilter(RowFilter.regexFilter("(?i)" + str));
        }
        
    }


    @Override
    public void removeUpdate(DocumentEvent e) {
        // TODO Auto-generated method stub
        String str = textField.getText();
        if (str.trim().length() == 0) {
            sort.setRowFilter(null);
        } else {
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

       
        int[] selectedRow = tableProduct.getSelectedRows();
      
        for (int i = 0; i < selectedRow.length; i++) {
          for (int j = 0; j < tableProduct.getColumnCount(); j++) {
           
            if(j == 0){
                idTf.setText((String) tableProduct.getValueAt(selectedRow[i], j));
            }else if(j == 1){
                nameTf.setText((String) tableProduct.getValueAt(selectedRow[i], j));
            }else if(j == 2){
                priceTf.setText((String) tableProduct.getValueAt(selectedRow[i], j));
            }
            amountTf.setText("1");

          }
        }
       
    } 
    


}
