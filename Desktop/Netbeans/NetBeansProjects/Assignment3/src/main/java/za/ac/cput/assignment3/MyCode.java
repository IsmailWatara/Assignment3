/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package za.ac.cput.assignment3;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.Period;
import java.util.Arrays;
import java.io.EOFException;
import java.io.BufferedWriter;
/**
 *
 * @author Ismail Watara(219018790)
 */
public class MyCode {
ObjectInputStream display;
FileWriter writer;
BufferedWriter buffWriter;
ArrayList<Customer> customers= new ArrayList<Customer>();
ArrayList<Supplier> suppliers= new ArrayList<Supplier>();


//2a
    public void openFile(){
        try{
            display = new ObjectInputStream( new FileInputStream( "stakeholder.ser" ) ); 
            System.out.println("*** ser file created and opened for reading  ***");               
        }
        catch (IOException ioe){
            System.out.println("error opening ser file: " + ioe.getMessage());
            System.exit(1);
        }
    }
    public void readFile(){
        try{
           while(true){
               Object line = display.readObject();
               String i ="Customer";
               String w = "Supplier";
               String name = line.getClass().getSimpleName();
               if ( name.equals(i)){
                   customers.add((Customer)line);
               } else if ( name.equals(w)){
                   suppliers.add((Supplier)line);
               } else {
                   System.out.println("It didn't work");
               }
           } 
        }
        catch (EOFException eofe) {
            System.out.println("End of file reached");
        }
        catch (ClassNotFoundException ioe) {
            System.out.println("Class error reading ser file: "+ ioe);
        }
        catch (IOException ioe) {
            System.out.println("Error reading ser file: "+ ioe);
        }
        
        sortCustomerDetails();
        sortSupplierDetails();
    }
    public void CloseFile(){
        try{
            display.close( ); 
        }
        catch (IOException ioe){            
            System.out.println("error closing ser file: " + ioe.getMessage());
            System.exit(1);
        }
    }
    //2b
    public void sortCustomerDetails(){
        String[] sortID = new String[customers.size()];
        ArrayList<Customer> sortJ= new ArrayList<Customer>();
        int count = customers.size();
        for (int a = 0; a < count; a++) {
            sortID[a] = customers.get(a).getStHolderId();
        }
        Arrays.sort(sortID);
        
        for (int b = 0; b < count; b++) {
            for (int c = 0; c < count; c++) {
                if (sortID[b].equals(customers.get(c).getStHolderId())){
                    sortJ.add(customers.get(c));
                }
            }
        }
        customers.clear();
        customers= sortJ;
    }
    
    //2c
    public int getAge(String dob){
        String[] seperation = dob.split("-");
        
        LocalDate birth = LocalDate.of(Integer.parseInt(seperation[0]), Integer.parseInt(seperation[1]), Integer.parseInt(seperation[2]));
        LocalDate current = LocalDate.now();
        Period difference = Period.between(birth, current);
        int age = difference.getYears();
        return age;
    }
    
    //2d
    public String formatDob(Customer dob){
        LocalDate dateOfBirthToFormat = LocalDate.parse(dob.getDateOfBirth());
        DateTimeFormatter changeFormat = DateTimeFormatter.ofPattern("dd MMM yyyy");
        return dateOfBirthToFormat.format(changeFormat);
    }
    
    
    public void displayCustomersOutput(){
        try{
            writer = new FileWriter("customerOutFile.txt");
            buffWriter = new BufferedWriter(writer);
            buffWriter.write(String.format("%s\n","===========================CUSTOMERS========================================"));
            
            buffWriter.write(String.format("%-15s %-15s %-15s %-15s %-15s\n", "ID","Name","Surname","Date of Birth","Age"));
             buffWriter.write(String.format("%s\n","================================================================================"));
            for (int i = 0; i < customers.size(); i++) {
                buffWriter.write(String.format("%-15s %-15s %-15s %-15s %-15s \n", customers.get(i).getStHolderId(), customers.get(i).getFirstName(), customers.get(i).getSurName(), formatDob(customers.get(i)),getAge(customers.get(i).getDateOfBirth())));
            }
            buffWriter.write(String.format("%s\n"," "));
            buffWriter.write(String.format("%s\n"," "));
            buffWriter.write(String.format("%s\n",rent()));
        }
        catch(IOException fnfe )
        {
            System.out.println(fnfe);
            System.exit(1);
        }
        try{
            buffWriter.close( ); 
        }
        catch (IOException ioe){            
            System.out.println("error closing text file: " + ioe.getMessage());
            System.exit(1);
        }
    }
    //2f
    public String rent(){
        int count = customers.size();
        int canRent = 0;
        int notRent = 0;
        for (int i = 0; i < count; i++) {
            if (customers.get(i).getCanRent()){
                canRent++;
            }else {
                notRent++;
            }
        }
        String line = "Number of customers who can rent : "+ '\t' + canRent + '\n' + "Number of customers who cannot rent : "+ '\t' + notRent;
        return line;
    }
    
    
    
    
    //3a
    public void sortSupplierDetails(){
        String[] sortID = new String[suppliers.size()];
        ArrayList<Supplier> sortJ= new ArrayList<Supplier>();
        int count = suppliers.size();
        for (int a = 0; a < count; a++) {
            sortID[a] = suppliers.get(a).getName();
        }
        Arrays.sort(sortID);
        
        for (int b = 0; b < count; b++) {
            for (int j = 0; j < count; j++) {
                if (sortID[b].equals(suppliers.get(j).getName())){
                    sortJ.add(suppliers.get(j));
                }
            }
        }
        suppliers.clear();
        suppliers = sortJ;
    }
   
   public void displaySupplierOutput(){
        try{
            writer = new FileWriter("supplierOutFile.txt");
            buffWriter = new BufferedWriter(writer);
            buffWriter.write(String.format("%s\n","===========================SUPPLIERS=========================================="));
           
            buffWriter.write(String.format("%-15s %-15s \t %-15s %-15s \n", "ID","Name","Prod Type","Description"));
            buffWriter.write("==============================================================================\n");
            for (int i = 0; i < suppliers.size(); i++) {
                buffWriter.write(String.format("%-15s %-15s \t %-15s %-15s \n", suppliers.get(i).getStHolderId(), suppliers.get(i).getName(), suppliers.get(i).getProductType(),suppliers.get(i).getProductDescription()));
            }
            
            
        }
        catch(IOException fnfe )
        {
            System.out.println(fnfe);
            System.exit(1);
        }
        try{
            buffWriter.close( ); 
        }
        catch (IOException ioe){            
            System.out.println("error closing text file: " + ioe.getMessage());
            System.exit(1);
        }
    }  
    
      
    
public static void main(String args[])  {
MyCode obj=new MyCode(); 
obj.openFile();
obj.readFile();
obj.CloseFile();
obj.displayCustomersOutput();
obj.displaySupplierOutput();

     }    
  
}

