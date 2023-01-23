package billingSystem.jdbc;

import java.sql.*;

public class DbConnection {
    public static Connection con=null;
    public static Statement stmt=null;
    public static PreparedStatement pstmt=null;

    //connecting to database
    public DbConnection(){
        try{
            //load mysql driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            //jdbc connection
            con= DriverManager.getConnection("jdbc:mysql://localhost:3306/happylife?autoReconnect=true&useSSL=false","root","vaibhu");
            stmt=con.createStatement();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    //add patients data into patients table
    public String addIntoPatients(String ptname, String ptdob, String ptage, String ptgender, String ptmobile, String ptaddress, String ptreason){
        String msg="";
        try {
            pstmt=con.prepareStatement("insert into patients (ptname,ptdob,ptage,ptgender,ptmobile,ptaddress,reason) values(?,?,?,?,?,?,?)");
            pstmt.setString(1,ptname);
            pstmt.setString(2,ptdob);
            pstmt.setString(3,ptage);
            pstmt.setString(4,ptgender);
            pstmt.setString(5,ptmobile);
            pstmt.setString(6,ptaddress);
            pstmt.setString(7,ptreason);

            pstmt.executeUpdate();
            msg="Patient registered successfully...";

        } catch (SQLException e) {
            msg=e.getMessage();
            e.printStackTrace();
        }
        return msg;

    }


    //update patients details with respect to id as a primary key.
    public String updatePatient(int reg,String ptname, String ptdob, String ptage, String ptgender, String ptmobile, String ptaddress, String ptreason){
        String msg="";
        try {
            pstmt=con.prepareStatement("update patients set ptname=?,ptdob=?,ptage=?,ptgender=?,ptmobile=?,ptaddress=?,reason=? where reg_no=?");
            pstmt.setString(1,ptname);
            pstmt.setString(2,ptdob);
            pstmt.setString(3,ptage);
            pstmt.setString(4,ptgender);
            pstmt.setString(5,ptmobile);
            pstmt.setString(6,ptaddress);
            pstmt.setString(7,ptreason);
            pstmt.setInt(8,reg);

            pstmt.executeUpdate();
            msg="Patient details edited...";

        } catch (SQLException e) {
            msg=e.getMessage();
            e.printStackTrace();
        }
        return msg;

    }


    //add doctors data into doctors table
    public String addIntoDoctor(String dname, String dmobile, String demail, String dquali, String dspeciality) {

        String msg="";
        try {
            pstmt=con.prepareStatement("insert into doctors (dname,dmobile,demail,dquali,dspeciality) values(?,?,?,?,?)");
            pstmt.setString(1,dname);
            pstmt.setString(2,dmobile);
            pstmt.setString(3,demail);
            pstmt.setString(4,dquali);
            pstmt.setString(5,dspeciality);

            pstmt.executeUpdate();
            msg="Doctor registered successfully...";

        } catch (SQLException e) {
            msg=e.getMessage();
            e.printStackTrace();
        }
        return msg;

    }


    //add appointment details in appointment table
    public String addIntoAppointment(String ptname, String apdate, String session, String apDoctor) {

        String msg="";
        try {
            pstmt=con.prepareStatement("insert into appointments(patientName,apDate,apSession,apDoctor) values(?,?,?,?)");
            pstmt.setString(1,ptname);
            pstmt.setString(2,apdate);
            pstmt.setString(3,session);
            pstmt.setString(4,apDoctor);

            pstmt.executeUpdate();
            msg="Appointment marked successfully...";



        } catch (SQLException e) {
            msg=e.getMessage();
            e.printStackTrace();
        }
        return msg;
    }

    public String updateDoctor(int id, String text, String text1, String text2, String text3, String text4) {

        String msg="";
        try {
            pstmt=con.prepareStatement("update doctors set dname=?,dmobile=?,demail=?,dquali=?,dspeciality=? where id=?");
            pstmt.setString(1,text);
            pstmt.setString(2,text1);
            pstmt.setString(3,text2);
            pstmt.setString(4,text3);
            pstmt.setString(5,text4);
            pstmt.setInt(6,id);

            pstmt.executeUpdate();
            msg="Doctor details edited...";

        } catch (SQLException e) {
            msg=e.getMessage();
            e.printStackTrace();
        }
        return msg;

    }

    //this method update appointments details in appointment table
    public String updateAppointment(int id, String text, String text1, String text2, String text3) {

        String msg="";
        try {
            pstmt=con.prepareStatement("update appointments set patientName=?,apDate=?,apSession=?,apDoctor=? where id=?");
            pstmt.setString(1,text);
            pstmt.setString(2,text1);
            pstmt.setString(3,text2);
            pstmt.setString(4,text3);
            pstmt.setInt(5,id);

            pstmt.executeUpdate();
            msg="Appointment details edited...";

        } catch (SQLException e) {
            msg=e.getMessage();
            e.printStackTrace();
        }
        return msg;

    }

    //get all patients from patients table
    public String[] getPatients(){
        String[] patients=new String[100];
        try {
            ResultSet resultSet=stmt.executeQuery("select * from patients");
            int j=0;
            while (resultSet.next()){
                patients[j]=resultSet.getString(2)+" Reg No :-"+resultSet.getInt(1);
                j++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }

    //get all doctors names from doctors table
    public String[] getDoctors(){

        String[] patients=new String[100];
        try {
            ResultSet resultSet=stmt.executeQuery("select * from doctors");
            int i=0;
            while (resultSet.next()){
                patients[i]=resultSet.getString(2);
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }


    //add bill details in bills table
    public String addIntoBills(String ptname,String bamount, String pmode,String services) {
        String[] regEx=ptname.split(":-");
        int id=Integer.parseInt(regEx[1].trim());
        String msg="";
        String ptphone="";
        String ptaddress="";
        String consult="";
        try {
            pstmt=con.prepareStatement("select * from patients where reg_no=?");
            pstmt.setInt(1,id);
            ResultSet rset= pstmt.executeQuery();
            while (rset.next()){
                ptphone=rset.getString(6);
                ptaddress=rset.getString(7);
            }
            pstmt=con.prepareStatement("select * from appointments where patientName=?");
            pstmt.setString(1,ptname);
            ResultSet r= pstmt.executeQuery();
            while (r.next()){
                consult=r.getString(5);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            pstmt=con.prepareStatement("insert into bills (patientName,patientPhone,patientAdd,billDate,consult,services,billAmount,payMode) values(?,?,?,?,?,?,?,?)");
            pstmt.setString(1,ptname);
            pstmt.setString(2,ptphone);
            pstmt.setString(3,ptaddress);
            pstmt.setDate(4,new java.sql.Date(new java.util.Date().getTime()));
            pstmt.setString(5,consult);
            pstmt.setString(6,services);
            pstmt.setString(7,bamount);
            pstmt.setString(8,pmode);

            pstmt.executeUpdate();
            msg="Bill generated successfully...";
            //delete that patient from appointment list after generating bill
            pstmt=con.prepareStatement("delete from appointments where patientName=?");
            pstmt.setString(1,ptname);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            msg=e.getMessage();
            e.printStackTrace();
        }
        return msg;
    }

//update bill details in bills table
    public String updateBill(int id, String toString, String text, String toString1) {
        String msg = "";
        int ID= Integer.parseInt(String.valueOf(toString.charAt(1)));
        String ptphone = "";
        String ptaddress = "";
        try {
            pstmt = con.prepareStatement("select * from patients where reg_no=?");
            pstmt.setInt(1, ID);
            ResultSet rset = pstmt.executeQuery();
            while (rset.next()) {
                ptphone = rset.getString(6);
                ptaddress = rset.getString(7);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            pstmt = con.prepareStatement("update bills set patientName=?,patientPhone=?,patientAdd=?,billDate=?,billAmount=?,payMode=? where id=?");
            pstmt.setString(1, toString);
            pstmt.setString(2, ptphone);
            pstmt.setString(3, ptaddress);
            pstmt.setDate(4, new java.sql.Date(new java.util.Date().getTime()));
            pstmt.setString(5, text);
            pstmt.setString(6,toString1);

            pstmt.executeUpdate();
            msg = "Bill updated successfully...";

        } catch (SQLException e) {
            msg = e.getMessage();
            e.printStackTrace();
        }
        return msg;
    }

    //return total number of patients
    public int getTotalPatients(){
        int num=0;
        try {
            ResultSet rs=con.createStatement().executeQuery("select * from patients");
            while (rs.next()){
                num++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return num;
    }
    //return total number of bills
    public int getTotalBills(){
        int num=0;
        try {
            ResultSet rs=con.createStatement().executeQuery("select * from bills");
            while (rs.next()){
                num++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return num;
    }

    //return total number of appointments
    public int getTotalAppointments(){
        int num=0;
        try {
            ResultSet rs=con.createStatement().executeQuery("select * from appointments");
            while (rs.next()){
                num++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return num;
    }

    //this method return array of services name in services table
    public String[] getServices(){
        String services[]=null;
        try {
            ResultSet rs=con.createStatement().executeQuery("select * from services");
            int num=0;
            while (rs.next()){
                num++;
            }
            services=new String[num];
            ResultSet r=con.createStatement().executeQuery("select * from services");
            int i=0;
            while (r.next()){
                services[i]=r.getString(2);
                i++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return services;
    }


    //add service name and price into services table
    public String addIntoService(String serName, int serPrice) {

        String msg="";
        try
        {
            pstmt=con.prepareStatement("insert into services(serName,serPrice) values(?,?)");
            pstmt.setString(1,serName);
            pstmt.setInt(2,serPrice);

            pstmt.executeUpdate();
            msg="Service Added successfully...";

        } catch (SQLException e) {
            msg=e.getMessage();
            e.printStackTrace();
        }
        return msg;
    }

    //this method return array of  price  in services table
    public int[] getSerPrice(){
        int serPrice[]=null;
        try {
            ResultSet rs=con.createStatement().executeQuery("select * from services");
            int num=0;
            while (rs.next()){
                num++;
            }
            serPrice=new int[num];
            ResultSet r=con.createStatement().executeQuery("select * from services");
            int i=0;
            while (r.next()){
                serPrice[i]=r.getInt(3);
                i++;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return serPrice;
    }

    //method to get service name and return price
    public int getServicePrice(String service){
        int i=0;

        try {
            pstmt=con.prepareStatement("select * from services where serName=?");
            pstmt.setString(1,service);
            ResultSet rs=pstmt.executeQuery();
            while (rs.next()){
                i=rs.getInt(3);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return i;
    }



}//class ends






