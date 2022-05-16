import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.time.LocalDate;

public class Report extends JPanel implements ActionListener, DocumentListener, ListSelectionListener {

    JTextField textField, dateTf, totalOrderTf, sallerTf, customerTf, totalItemTf, orderDataTf;
    JLabel searchLb, dateLabel, totalOrderLb, orderItem, sallerlb, customerLb, totalItemLb, orderDateLb;
    JTable  tableOrder, tableItem;
    TableRowSorter<TableModel> sort;
    TableColumnModel columnModelPro, columnModelOrder;
    DefaultTableModel modelOrder, modelItem;
    JScrollPane scrollItem, scrollOrder;
    FileOrder fileOrder;
    DecimalFormat fm2;
    JPanel topBar, right;
    JComboBox<String> comDay;
    JButton selectDateBtn, exBtn;
    ExReprort exReprort;
    Report(){
        super(new BorderLayout(),true);
        fm2 = new DecimalFormat("#,##0.00");
        fileOrder = new FileOrder();
        exReprort = new ExReprort();
        setTopBar();
        setRight();
        setTableOrder();
        
    }

    public void setTopBar(){
        topBar = new JPanel();

        searchLb = new JLabel("Search Order :");
        topBar.add(searchLb);

        textField = new JTextField(20);
        textField.getDocument().addDocumentListener(this);
        topBar.add(textField);

        totalOrderLb = new JLabel("Total Order :");
        topBar.add(totalOrderLb);

        totalOrderTf = new JTextField(10);
        totalOrderTf.setEditable(false);
        topBar.add(totalOrderTf);

        dateLabel = new JLabel("Select Date : ");
        topBar.add(dateLabel);
        
        dateTf = new JTextField(10);
        dateTf.setEditable(false);
        topBar.add(dateTf);

        comDay = new JComboBox<String>();
        topBar.add(comDay);

        selectDateBtn = new JButton("Select");
        selectDateBtn.addActionListener(this);
        topBar.add(selectDateBtn);
        
        exBtn = new JButton("Export");
        exBtn.addActionListener(this);
        topBar.add(exBtn);

        add(topBar, BorderLayout.NORTH);
    
    }


    public void setTableOrder(){
        String header[] = { "ID", "Customer", "Saller","Total", "Date" };

        tableOrder = new JTable(new DefaultTableModel(header,0));
        modelOrder = (DefaultTableModel) tableOrder.getModel();
        refresh();

        columnModelPro = tableOrder.getColumnModel();        
        columnModelPro.getColumn(0).setPreferredWidth(100);
        columnModelPro.getColumn(1).setPreferredWidth(400);
        columnModelPro.getColumn(2).setPreferredWidth(400);
        columnModelPro.getColumn(3).setPreferredWidth(400);
        columnModelPro.getColumn(4).setPreferredWidth(400);
    
        sort = new TableRowSorter<>(tableOrder.getModel());
        ListSelectionModel cellSelectionModel = tableOrder.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cellSelectionModel.addListSelectionListener(this);
        tableOrder.setRowSorter(sort); 

        scrollOrder = new JScrollPane(tableOrder);
        add(scrollOrder, BorderLayout.WEST);
        
    }

    public void setTableOrderItem(){
        String header[] = { "ID", "Name", "Amount","Price"};

        tableItem = new JTable(new DefaultTableModel(header,0));
        modelItem = (DefaultTableModel) tableItem.getModel();

        scrollItem = new JScrollPane(tableItem);
        right.add(scrollItem);
    }

    public void refresh(){
       String orderData[][] = fileOrder.getOrder(String.valueOf(LocalDate.now()));
       modelOrder.setRowCount(0);
       float total = 0;
       for (int i = 0; i < orderData.length; i++){
            total += Float.valueOf(orderData[i][5]);
            modelOrder.addRow(new Object[]{orderData[i][0],orderData[i][2], orderData[i][4],String.valueOf(fm2.format(Float.parseFloat(orderData[i][5]))), (orderData[i][6])});     
       }

       totalOrderTf.setText(String.valueOf(fm2.format(total)));
       dateTf.setText(String.valueOf(LocalDate.now()));

       DefaultComboBoxModel<String> modelDay = new DefaultComboBoxModel<>(  fileOrder.getOrderDay() );
       comDay.setModel( modelDay);

       resetItem();
       
    }
    
    public void upDateTabelOrder(){
       modelOrder.setRowCount(0);
        String orderData[][] = fileOrder.getOrder(dateTf.getText());
        float total = 0;
        for (int i = 0; i < orderData.length; i++){
            total += Float.valueOf(orderData[i][5]);
            modelOrder.addRow(new Object[]{orderData[i][0],orderData[i][2], orderData[i][4],String.valueOf(fm2.format(Float.parseFloat(orderData[i][5]))), (orderData[i][6])});     
       }
       totalOrderTf.setText(String.valueOf(fm2.format(total)));

    }

    
    public void setRight(){
        right = new JPanel();
        orderItem = new JLabel(":                                                                                        Order Detail                                                                                       :");
        right.add(orderItem);

        sallerlb = new JLabel("Saler Name : ");
        right.add(sallerlb);

        sallerTf = new JTextField(19);
        sallerTf.setEditable(false);
        right.add(sallerTf);

        customerLb = new JLabel("Customer Name : ");
        right.add(customerLb);

        customerTf = new JTextField(19);
        customerTf.setEditable(false);
        right.add(customerTf);

        totalItemLb = new JLabel("Order Total : ");
        right.add(totalItemLb);

        totalItemTf = new JTextField(20);
        totalItemTf.setEditable(false);
        right.add(totalItemTf);

        orderDateLb = new JLabel("Order Date");
        right.add(orderDateLb);

        orderDataTf = new JTextField(20);
        orderDataTf.setEditable(false);
        right.add(orderDataTf);

        setTableOrderItem();
        add(right, BorderLayout.CENTER);

    }


    public void upDateTabelOrderItem(int id) {
        String item[][] = fileOrder.getOrderItem(id);
        modelItem.setRowCount(0);
        for (int i = 0; i < item.length; i++){
            modelItem.addRow(new Object[]{String.valueOf(i+1),item[i][0],item[i][1], item[i][2]});     

        }
    }

    public void resetItem(){
       modelItem.setRowCount(0);
       customerTf.setText("");
       sallerTf.setText("");
       totalItemTf.setText("");
       orderDataTf.setText("");
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource() == selectDateBtn){
            dateTf.setText((String)comDay.getSelectedItem());
            upDateTabelOrder();
        }else if(e.getSource() == exBtn){
            exReprort.setDate(dateTf.getText());
            exReprort.setFile();
            exReprort.writeReportOrder();
            JOptionPane.showMessageDialog(null, "Export Success", "Message", JOptionPane.INFORMATION_MESSAGE);

        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        // TODO Auto-generated method stub
        int[] selectedRow = tableOrder.getSelectedRows();
        int idOrder =0;
        for (int i = 0; i < selectedRow.length; i++) {
          
            customerTf.setText((String) tableOrder.getValueAt(selectedRow[i], 1));
            sallerTf.setText((String) tableOrder.getValueAt(selectedRow[i], 2));
            totalItemTf.setText((String) tableOrder.getValueAt(selectedRow[i], 3));
            orderDataTf.setText((String) tableOrder.getValueAt(selectedRow[i], 4));
            idOrder = Integer.valueOf((String) tableOrder.getValueAt(selectedRow[i], 0));
        }
        upDateTabelOrderItem(idOrder);
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
    
}
