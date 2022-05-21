import javax.swing.JFrame;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Index extends JFrame implements ActionListener   {
    
   
    private JButton productBtn, sallerBtn, customerBtn, homeBtn, reportBtn, reportProBtn;
    private JPanel sidebar;
    private Home home; 
    private Container c;
    private ReportOrderDay report;
    private ReportPorduct reportPro;
    private ManageCustomer manageCustomer;
    private ManageProduct manageProduct;
    private ManageSaller manageSaller;

    Index(){
        super("Program Project : ");
        setSize(1200,700);
        
        c = getContentPane();
        c.setLayout(new BorderLayout());


        setSideBar();
        c.add(sidebar, BorderLayout.WEST);

        manageProduct = new ManageProduct();
        manageCustomer = new ManageCustomer();
        manageSaller = new ManageSaller();
        report = new ReportOrderDay();
        reportPro = new ReportPorduct();
        home = new Home();

        c.add(home, BorderLayout.CENTER);
        home.setVisible(true);
      
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
       
    }

    public void setSideBar(){
        sidebar = new JPanel(new GridLayout(10,1,3,3), true);
        homeBtn = new JButton("Home");
        homeBtn.addActionListener(this);

        productBtn = new JButton("Product");
        productBtn.addActionListener(this);

        sallerBtn = new JButton("Seller");
        sallerBtn.addActionListener(this);

        customerBtn = new JButton("Customer");
        customerBtn.addActionListener(this);

        reportBtn = new JButton("Report Sale");
        reportBtn.addActionListener(this);

        reportProBtn = new JButton("Product Sale");
        reportProBtn.addActionListener(this);

        sidebar.add(homeBtn);
        sidebar.add(productBtn);
        sidebar.add(sallerBtn);
        sidebar.add(customerBtn);
        sidebar.add(reportBtn);
        sidebar.add(reportProBtn);
    }

   
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == productBtn){
            home.setVisible(false);
            report.setVisible(false);
            manageCustomer.setVisible(false);
            manageSaller.setVisible(false);
            reportPro.setVisible(false);

            manageProduct.refresh();
            manageProduct.setVisible(true);
            c.add(manageProduct, BorderLayout.CENTER);
        
        }else if(e.getSource() == sallerBtn){
            home.setVisible(false);
            report.setVisible(false);
            manageProduct.setVisible(false);
            manageCustomer.setVisible(false);
            reportPro.setVisible(false);



            manageSaller.setVisible(true);
            manageSaller.refresh();
            c.add(manageSaller);

        }else if(e.getSource() == customerBtn){
            
            home.setVisible(false);
            report.setVisible(false);
            manageProduct.setVisible(false);
            manageSaller.setVisible(false);
            reportPro.setVisible(false);



            manageCustomer.setVisible(true);
            manageCustomer.refresh();
            c.add(manageCustomer);

        }else if(e.getSource() == homeBtn){
           
            home.setVisible(true);
            home.reFreshPage();
            report.setVisible(false);
            manageProduct.setVisible(false);
            manageSaller.setVisible(false);
            manageCustomer.setVisible(false);
            reportPro.setVisible(false);


            c.remove(manageProduct);
            c.remove(manageCustomer);
            c.remove(report);
            c.remove(manageSaller);
            c.remove(reportPro);

        }else if(e.getSource() == reportBtn){

            home.setVisible(false);
            manageProduct.setVisible(false);
            manageCustomer.setVisible(false);
            manageSaller.setVisible(false);
            reportPro.setVisible(false);


            report.setVisible(true);
            report.refresh();
            c.add(report);

        }else if(e.getSource() == reportProBtn){
            home.setVisible(false);
            manageProduct.setVisible(false);
            manageCustomer.setVisible(false);
            manageSaller.setVisible(false);
            report.setVisible(false);

            reportPro.setVisible(true);
            reportPro.refresh();
            c.add(reportPro);

        }
    }

    public static void main(String[] args){
        new Index();

    }
    

}
