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

    private JTextField textField, dateTf, totalOrderTf;
    private JLabel searchLb, dateLabel, totalOrderLb;
    private JTable  tableProduct;
    private TableRowSorter<TableModel> sort;
    private TableColumnModel columnModel;
    private DefaultTableModel modelProduct ;
    private JScrollPane  scrollProduct;
    private DecimalFormat fm2;
    private JPanel topBar;
    private JComboBox<String> comDay;
    private JButton selectDateBtn, exBtn;
    private FileOrder fileOrder;
    private ExReport exReport;
    ReportPorduct(){
        super(new BorderLayout(),true);
        fm2 = new DecimalFormat("#,##0.00");
        fileOrder = new FileOrder();
        exReport = new ExReport();
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

        columnModel = tableProduct.getColumnModel();        
        columnModel.getColumn(0).setPreferredWidth(100);
        columnModel.getColumn(1).setPreferredWidth(200);
        columnModel.getColumn(2).setPreferredWidth(200);
        columnModel.getColumn(3).setPreferredWidth(200);
        columnModel.getColumn(4).setPreferredWidth(200);
        columnModel.getColumn(5).setPreferredWidth(200);

    
        sort = new TableRowSorter<>(tableProduct.getModel());
        ListSelectionModel cellSelectionModel = tableProduct.getSelectionModel();
        cellSelectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        cellSelectionModel.addListSelectionListener(this);
        tableProduct.setRowSorter(sort); 

        scrollProduct = new JScrollPane(tableProduct);
        add(scrollProduct, BorderLayout.CENTER);
        
    }



    public void refresh(){
       String productData[][] = fileOrder.getProductSale(String.valueOf(LocalDate.now()));
       modelProduct.setRowCount(0);
       float total = 0;
       for (int i = 0; i < productData.length; i++){
            total += Float.valueOf(productData[i][4]);
            modelProduct.addRow(new Object[]{productData[i][0],productData[i][1], productData[i][2],String.valueOf(fm2.format(Float.parseFloat(productData[i][3]))), String.valueOf(fm2.format(Float.parseFloat(productData[i][4]))), (productData[i][5])});     
       }

       totalOrderTf.setText(String.valueOf(fm2.format(total)));
       dateTf.setText(String.valueOf(LocalDate.now()));

       DefaultComboBoxModel<String> modelDay = new DefaultComboBoxModel<>(  fileOrder.getOrderDay() );
       comDay.setModel( modelDay);
       
    }
    
    public void updateTabelProduct(){
       modelProduct.setRowCount(0);
        String productData[][] = fileOrder.getProductSale(dateTf.getText());
        float total = 0;
        for (int i = 0; i < productData.length; i++){
            total += Float.valueOf(productData[i][4]);
            modelProduct.addRow(new Object[]{productData[i][0],productData[i][1], productData[i][2],String.valueOf(fm2.format(Float.parseFloat(productData[i][3]))), String.valueOf(fm2.format(Float.parseFloat(productData[i][4]))), (productData[i][5])});          
 
       }
       totalOrderTf.setText(String.valueOf(fm2.format(total)));

    }

  


    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        if(e.getSource() == selectDateBtn){
            dateTf.setText((String)comDay.getSelectedItem());
            updateTabelProduct();
        }else if(e.getSource() == exBtn){
            exReport.setDate(dateTf.getText());
            exReport.setFile();
            exReport.writeReportProduct();
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
