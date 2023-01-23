package billingSystem.subFrames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import billingSystem.jdbc.DbConnection;

import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.Document;
import java.io.FileOutputStream;
import java.util.Date;

public class AddBills extends JFrame implements ActionListener {

    JLabel lb,PatientName,BillAmount,PayMode,service;
    JTextField bam;
    JComboBox paymode,pnm;
    JCheckBox ser[]=null;
    JButton addB;
    String services[]=null;
    int servicesPrice[]=null;


    public AddBills(){
        setTitle("Generate Bill");
        getContentPane().setBackground(new Color(131,193,233));
        lb=new JLabel("GENERATE BILL");
        lb.setBounds(280,0,250,40);
        lb.setFont(new Font("Tahoma",Font.BOLD,17));
        add(lb);

        PatientName=new JLabel("Select Patient:");
        PatientName.setBounds(20,80,120,30);
        add(PatientName);
        DbConnection obj=new DbConnection();
        pnm=new JComboBox(obj.getPatients());
        pnm.setBounds(150,80,250,30);
        add(pnm);

        service=new JLabel("Service:");
        service.setBounds(20,140,250,30);
        add(service);

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
        add(serPanel);

        BillAmount=new JLabel("Bill Amount:");
        BillAmount.setBounds(20,220,80,30);
        add(BillAmount);
        bam=new JTextField("0");

        bam.setBounds(150,220,100,30);
        add(bam);

        //add checkbox for services
        for(int i=0;i<services.length;i++){
            ser[i]=new JCheckBox(services[i]);
            ser[i].setBackground(new Color(131,193,233));
            serPanel.add(ser[i]);
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
        add(PayMode);

        paymode=new JComboBox(new String[]{"Online transfer","Credit card","Debit card","Cash"});
        paymode.setBounds(150,290,250,30);
        add(paymode);

        addB=new JButton("Generate Bill");
        addB.setBackground(Color.BLACK);
        addB.setForeground(Color.white);
        addB.setBounds(280,400,150,40);
        addB.addActionListener(this);
        add(addB);


        setLayout(null);
        setBounds(300,120,700,500);
        setVisible(true);

    }
    @Override
    public void actionPerformed(ActionEvent e) {

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

        generateBill(ptname);

    }
    public void generateBill(String patientName){


        DbConnection jdb=new DbConnection();
        String ptname=pnm.getSelectedItem().toString();
        try {
            PreparedStatement pst=jdb.con.prepareStatement("select * from bills where patientName=? ");
            pst.setString(1,patientName);
            ResultSet r=pst.executeQuery();

            int billNo=0;
            String pAddree="";
            String date="";
            String payMode="";
            String consult="";
            String pphone="";
            String ser []=new String[services.length];
            int billAmt=0;

            while(r.next()){
                billNo=r.getInt(1);
                pphone=r.getString(3);
                pAddree=r.getString(4);
                date=r.getString(5);
                payMode=r.getString(9);
                consult=r.getString(6);
                ser=r.getString(7).split(",");
                billAmt=r.getInt(8);

            }
            try {


                String path = "C:\\Users\\Vaibhavi\\Downloads\\Bills"+new Date().getTime()+".pdf";

                Document doc = new Document();
                PdfWriter.getInstance(doc, new FileOutputStream(path));
                doc.open();

                PdfPTable table = new PdfPTable(1);
                PdfPCell c1 = new PdfPCell(new Paragraph("HappyLife Clinic\nVasud Road,Ajinkya Plaza,Sangola 413307"));
                c1.setHorizontalAlignment(Element.ALIGN_CENTER);
                c1.setPadding(10f);
                table.addCell(c1);


                PdfPTable table2 = new PdfPTable(2); // 2 columns.

                PdfPCell cell1 = new PdfPCell(new Paragraph("Bill No:"+String.valueOf(billNo)));
                cell1.setBorder(PdfPCell.NO_BORDER);
                PdfPCell cell2 = new PdfPCell(new Paragraph("Date:"+date));
                cell2.setBorder(PdfPCell.NO_BORDER);
                table2.addCell(cell1);
                table2.addCell(cell2);
                table2.setSpacingBefore(20f);

                PdfPTable table3 = new PdfPTable(1); // 2 columns.

                PdfPCell name = new PdfPCell(new Paragraph("Patient Name:  "+ptname));
                name.setBorder(PdfPCell.NO_BORDER);
                name.setPadding(5f);
                PdfPCell address = new PdfPCell(new Paragraph("Address:  "+pAddree));
                address.setBorder(PdfPCell.NO_BORDER);
                address.setPadding(5f);
                PdfPCell phone = new PdfPCell(new Paragraph("Phone:  "+pphone));
                phone.setBorder(PdfPCell.NO_BORDER);
                phone.setPadding(5f);
                PdfPCell cons = new PdfPCell(new Paragraph("Consult:  "+consult));
                cons.setBorder(PdfPCell.NO_BORDER);
                cons.setPadding(5f);
                PdfPCell paymode = new PdfPCell(new Paragraph("Payment Mode:  "+payMode));
                paymode.setPadding(5f);

                paymode.setBorder(PdfPCell.NO_BORDER);
                table3.addCell(name);
                table3.addCell(address);
                table3.addCell(phone);
                table3.addCell(cons);
                table3.addCell(paymode);
                table3.setSpacingBefore(10f);

                PdfPTable table4 = new PdfPTable(3); // 3 columns.

                PdfPCell sr = new PdfPCell(new Paragraph("Sr. No:"));
                sr.setPadding(7f);
                PdfPCell serv = new PdfPCell(new Paragraph("Services:"));
                serv.setPadding(7f);
                PdfPCell amount = new PdfPCell(new Paragraph("Amount:"));
                amount.setPadding(7f);
                table4.addCell(sr);
                table4.addCell(serv);
                table4.addCell(amount);
                table4.setSpacingBefore(20f);

                PdfPTable table5 = new PdfPTable(3);
                for (int i = 0; i < ser.length; i++) {
                    PdfPCell num = new PdfPCell(new Paragraph(Integer.toString(i + 1)));
                    num.setPadding(7f);
                    PdfPCell s = new PdfPCell(new Paragraph(ser[i]));
                    s.setPadding(7f);
                    PdfPCell amt = new PdfPCell(new Paragraph(Integer.toString(new DbConnection().getServicePrice(ser[i]) )) );
                    amt.setPadding(7f);
                    table5.addCell(num);
                    table5.addCell(s);
                    table5.addCell(amt);
                }


                PdfPTable table6 = new PdfPTable(3);
                PdfPCell b1 = new PdfPCell(new Paragraph(" "));
                b1.setBorder(PdfPCell.NO_BORDER);
                PdfPCell b2 = new PdfPCell(new Paragraph("Total: "));
                b2.setPadding(7f);
                b2.setBorder(PdfPCell.NO_BORDER);
                PdfPCell b3 = new PdfPCell(new Paragraph(Integer.toString(billAmt)));
                b3.setPadding(7f);
                b3.setBorder(PdfPCell.NO_BORDER);
                table6.addCell(b1);
                table6.addCell(b2);
                table6.addCell(b3);
                table6.setSpacingBefore(25f);


                doc.add(table);
                doc.add(table2);
                doc.add(table3);
                doc.add(table4);
                doc.add(table5);
                doc.add(table6);

                doc.close();
                JOptionPane.showMessageDialog(getContentPane(),"Bill is downloaded successfully");
            }
            catch (Exception e){
                e.printStackTrace();
            }






        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        new AddBills();
    }

}