package billingSystem.subFrames;

import billingSystem.jdbc.DbConnection;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ListBills extends JFrame {
    JTable table = new JTable();
    DefaultTableModel model=null;
    Object[] row=new Object[8];
    JButton update,delete;

    //variables initialized to update bill
    JLabel lb,PatientName,BillAmount,PayMode,service;
    JTextField bam;
    JComboBox paymode,pnm;
    JCheckBox ser[]=null;
    JButton addB;
    String services[]=null;
    int servicesPrice[]=null;

    public ListBills(){
        setTitle("Generated Bills");
        getContentPane().setBackground(new Color(131,193,233));
        JLabel title=new JLabel("Generated Bills");
        title.setBounds(280,10,200,30);
        title.setFont(new Font("Tahoma",Font.BOLD,16));
        add(title);

        // create a table model and set a column.
        Object[] columns = {"Bill No.","Patient_Name","Patient_Phone","Patient_Address","Bill_Date","Consult","Amount","Paymode"};
        model = new DefaultTableModel();
        model.setColumnIdentifiers(columns);


        // set the model to the table
        table.setModel(model);

        // Change A JTable Background Color, Font Size, Font Color, Row Height
        table.setBackground(Color.white);
        table.setForeground(Color.black);
        Font font = new Font("Tahoma", Font.PLAIN, 15);
        table.setFont(font);
        table.setRowHeight(30);

        try {

            ResultSet resultSet=new DbConnection().stmt.executeQuery("select * from bills");
            while (resultSet.next()){

                row[0]=resultSet.getInt(1);
                row[1]=resultSet.getString(2);
                row[2]=resultSet.getString(3);
                row[3]=resultSet.getString(4);
                row[4]=resultSet.getString(5);
                row[5]=resultSet.getString(6);
                row[6]=resultSet.getString(8);
                row[7]=resultSet.getString(9);
                // add row to the model
                model.addRow(row);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        JScrollPane pane = new JScrollPane(table);
        pane.setBounds(0, 50, 690, 350);
        add(pane);

        delete=new JButton("Delete");
        delete.setBounds(30,400,100,30);
        delete.setForeground(Color.WHITE);
        delete.setBackground(Color.BLACK);
        add(delete);

        // button remove row - Clicked on Delete Button
        delete.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {

                // i = index of selected number.
                int i = table.getSelectedRow();
                if (i >= 0) {
                    // remove a row from jtable

                    DbConnection ob=new DbConnection();
                    try {
                        ob.pstmt=ob.con.prepareStatement("delete from bills where id=?");
                        ob.pstmt.setInt(1, (Integer) model.getValueAt(i,0));
                        ob.pstmt.executeUpdate();
                        JOptionPane.showMessageDialog(getContentPane(),"Bill deleted successfully");
                        model.removeRow(i);
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }


                } else {
                    JOptionPane.showMessageDialog(getContentPane(),"Unable to delete patient");
                }
            }
        });
        update=new JButton("Edit");
        update.setBounds(160,400,100,30);
        update.setForeground(Color.WHITE);
        update.setBackground(Color.BLACK);
        add(update);

        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                edit();

            }
        });

        setLayout(null);
        setBounds(300,120,700,500);
        setVisible(true);

    }
    public void edit(){
        int i = table.getSelectedRow();
        String s1,s2,s3,s4,s5,s6;
        int id;
        if(i>=0) {
            id = (int) model.getValueAt(i, 0);
            s1 = model.getValueAt(i, 1).toString();
            s2 = model.getValueAt(i, 2).toString();
            s3 = model.getValueAt(i, 3).toString();
            s4 = model.getValueAt(i, 4).toString();
            s5 = model.getValueAt(i, 5).toString();
            s6 = model.getValueAt(i, 6).toString();


            JFrame update = new JFrame("Edit Bill");
            update.setBackground(new Color(131,193,233));
            JLabel lb = new JLabel("Edit Bill");
            lb.setBounds(280, 0, 200, 40);
            lb.setFont(new Font("Tahoma", Font.BOLD, 17));
            update.add(lb);


            JLabel regNo=new JLabel("Bill No: "+id);
            regNo.setBounds(20,45,100,30);
            update.add(regNo);


            PatientName=new JLabel("Select Patient:");
            PatientName.setBounds(20,80,120,30);
            update.add(PatientName);
            DbConnection obj=new DbConnection();
            pnm=new JComboBox(obj.getPatients());
            pnm.setBounds(150,80,250,30);
            update.add(pnm);

            service=new JLabel("Service:");
            service.setBounds(20,140,250,30);
            update.add(service);

            JPanel serPanel =new JPanel();
            serPanel.setBackground(new Color(131,193,233));
            serPanel.setLayout(new FlowLayout());
            //get all the services from database
            DbConnection getSer=new DbConnection();
            services=new String[ getSer.getServices().length];
            servicesPrice=new int[getSer.getSerPrice().length];

            servicesPrice=getSer.getSerPrice();//get prices of services
            services=getSer.getServices();//get services

            ser=new JCheckBox[ getSer.getServices().length];


            serPanel.setBounds(150,140,500,50);
            update.add(serPanel);

            BillAmount=new JLabel("Bill Amount:");
            BillAmount.setBounds(20,220,80,30);
            update.add(BillAmount);
            bam=new JTextField("0");

            bam.setBounds(150,220,100,30);
            update.add(bam);

            //add checkbox for services
            for(int g=0;g<services.length;g++){
                ser[g]=new JCheckBox(services[g]);
                ser[g].setBackground(new Color(131,193,233));
                serPanel.add(ser[g]);
            }


            for (JCheckBox c:ser) {
                c.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String val="0";

                        for (int i=0;i<services.length;i++){
                            if(ser[i].isSelected()) {
                                val=String.valueOf(Integer.parseInt(bam.getText()) + servicesPrice[i]);
                            }
                        }
                        bam.setText(val);


                    }
                });
            }



            PayMode=new JLabel(" Pay Mode:");
            PayMode.setBounds(20,290,250,30);
            update.add(PayMode);

            paymode=new JComboBox(new String[]{"Online transfer","Credit card","Debit card","Cash"});
            paymode.setBounds(150,290,250,30);
            update.add(paymode);



            JButton addP = new JButton("Edit Bill");
            addP.setBackground(Color.BLACK);
            addP.setForeground(Color.white);
            addP.setBounds(280, 400, 130, 40);
            addP.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //update into database
                    String ptname,bamount,pmode,servi="";

                    ptname=pnm.getSelectedItem().toString();
                    bamount=bam.getText();
                    pmode=paymode.getSelectedItem().toString();

                    String val="";

                    for (int i=0;i<services.length;i++){
                        if(ser[i].isSelected()) {
                            val+=services[i]+",";
                        }
                    }
                    servi=val;


                    String msg=new DbConnection().addIntoBills(ptname,bamount,pmode,servi);
                    JOptionPane.showMessageDialog(getContentPane(),msg);

                    //call function that generate and downloads pdf of bill

                    new AddBills().generateBill(ptname);
                }
            });
            update.add(addP);
            update.setLayout(null);
            update.setBounds(300, 120, 700, 500);
            update.setVisible(true);

        }
    }

    public static void main(String[] args) {
        new ListBills().setVisible(true);
    }
}
