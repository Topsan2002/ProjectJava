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

public class ReportPorduct extends JPanel implements ActionListener, DocumentListener, ListSelectionListener {

    JTextField textField, dateTf, totalOrderTf;
    JLabel searchLb, dateLabel, totalOrderLb;
    JTable  tableProduct;
    TableRowSorter<TableModel> sort;
    TableColumnModel columnModelPro;
    DefaultTableModel modelProduct ;
    JScrollPane scrollItem, scrollOrder;
    FileOrder fileOrder;
    DecimalFormat fm2;
    JPanel topBar;
    JComboBox<String> comDay;
    JButton selectDateBtn, exBtn;
    ExReprort exReprort;
    ReportPorduct(){
        super(new BorderLayout(),true);
        fm2 = new DecimalFormat("#,##0.00");
        fileOrder = new FileOrder();
        exReprort = new ExReprort();
        setTopBar();
        settableProduct();
        
    }

    public void setTopBar(){
        topBar = new JPanel();

        searchLb = new JLabel("Search Product :");
        topBar.add(searchLb);

        textField = new JTextField(20);
        textField.getDocument().addDocumentListener(this);
        topBar.add(textField);

        totalOrderLb = new JLabel("Total :");
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


    public void settableProduct(){
        String header[] = { "ID", "Name", "Amount", "Price" ,"Total", "Date" };

        tableProduct = new JTable(new DefaultTableModel(header,0));
        modelProduct = (DefaultTableModel) tableProduct.getModel();
        refresh();

        columnModelPro = tableProduct.getColumnModel();        
        columnModelPro.getColumn(0).setPreferredWidth(100);
        columnModelPro.getColumn(1).setPreferredWidth(200);
        columnModelPro.getColumn(2).setPreferredWidth(200);
        columnModelPro.getColumn(3).setPreferredWidth(200);
        columnModelPro.getColumn(4).setPreferredWidth(200);
        columnModelPro.getColumn(5).setPreferredWidth(200);

    
        sort = new TableRowSorter<>(tableProduct.getModel());
        ListSelectionModel cellSelectionModel = tableProduct.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cellSelectionModel.addListSelectionListener(this);
        tableProduct.setRowSorter(sort); 

        scrollOrder = new JScrollPane(tableProduct);
        add(scrollOrder, BorderLayout.CENTER);
        
    }



    public void refresh(){
       String orderData[][] = fileOrder.getProductSale(String.valueOf(LocalDate.now()));
       modelProduct.setRowCount(0);
       float total = 0;
       for (int i = 0; i < orderData.length; i++){
            total += Float.valueOf(orderData[i][4]);
            modelProduct.addRow(new Object[]{orderData[i][0],orderData[i][1], orderData[i][2],String.valueOf(fm2.format(Float.parseFloat(orderData[i][3]))), String.valueOf(fm2.format(Float.parseFloat(orderData[i][4]))), (orderData[i][5])});     
       }

       totalOrderTf.setText(String.valueOf(fm2.format(total)));
       dateTf.setText(String.valueOf(LocalDate.now()));

       DefaultComboBoxModel<String> modelDay = new DefaultComboBoxModel<>(  fileOrder.getOrderDay() );
       comDay.setModel( modelDay);
       
    }
    
    public void upDateTabelOrder(){
       modelProduct.setRowCount(0);
        String orderData[][] = fileOrder.getProductSale(dateTf.getText());
        float total = 0;
        for (int i = 0; i < orderData.length; i++){
            total += Float.valueOf(orderData[i][4]);
            modelProduct.addRow(new Object[]{orderData[i][0],orderData[i][1], orderData[i][2],String.valueOf(fm2.format(Float.parseFloat(orderData[i][3]))), String.valueOf(fm2.format(Float.parseFloat(orderData[i][4]))), (orderData[i][5])});          
 
       }
       totalOrderTf.setText(String.valueOf(fm2.format(total)));

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
            exReprort.writeReportProduct();
            JOptionPane.showMessageDialog(null, "Export Success", "Message", JOptionPane.INFORMATION_MESSAGE);

        }
    }

    @Override
    public void valueChanged(ListSelectionEvent e) {
        // TODO Auto-generated method stub
        
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
