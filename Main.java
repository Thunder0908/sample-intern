package org.example;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("1 Add Items");
            System.out.println("2 Update Item Quantity");
            System.out.println("3 Remove Items");
            System.out.println("4 View Items");
            System.out.println("5 Exit");
            System.out.println("Enter your choice");
            int c = sc.nextInt();
            sc.nextLine();
            switch(c){
                case 1:
                    System.out.println("Enter Item Name");
                    String addname = sc.nextLine();
                    System.out.println("Enter Item Quantity");
                    int addquantity = sc.nextInt();
                    inventory.additem(addname,addquantity);
                    break;
                case 2:
                    System.out.println("Enter Item Name");
                    String updatename = sc.nextLine();
                    System.out.println("Enter Item Quantity");
                    int updatequantity = sc.nextInt();
                    inventory.updateitem(updatename,updatequantity);
                    break;
                case 3:
                    System.out.println("Enter Item Name");
                    String removeename = sc.nextLine();
                    inventory.removeitem(removeename);
                    break;
                case 4:
                    inventory.viewitem();
                    break;
                case 5:
                    System.out.println("Exiting");
                    sc.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please try again.");

            }
        }

        }
}
class inventory{
    public static MongoCollection<Document> Items;
    static {
        String connectionString="mongodb://localhost:27017";
        MongoClient mongoClient = MongoClients.create(connectionString);
        MongoDatabase database = mongoClient.getDatabase("Inventoryy");
        Items = database.getCollection("Items");
    }
    public static void additem(String i,int Q ){
          Document doc=new Document("Name",i).append("Quantity",Q);
          Items.insertOne(doc);
          System.out.println("Items Added"+doc.toJson());
    }
    public static void updateitem(String i,int Q ){
        Items.updateOne(Filters.eq("Name",i), Updates.set("Quantity",Q));
        System.out.println(" Updated "+ i +" Quantity " + Q);
    }
    public static void removeitem(String i){
        Items.deleteOne(Filters.eq("Name",i));
        System.out.println("Deleted Item "+ i);
    }
    public static void viewitem(){
        Items.find().forEach(document -> {
            System.out.println(document.toJson());
        });
    }
}