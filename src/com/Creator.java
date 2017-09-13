package com;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;


public class Creator {

    public void createWorkOrders() {
        // read input, create work orders and write as json files

        WorkOrder newWorkOrder = new WorkOrder();
        Scanner input = new Scanner(System.in);

        newWorkOrder.setStatus(Status.INITIAL);

        System.out.println("\nEnter Name");
        newWorkOrder.setSenderName(input.nextLine());

        System.out.println("Enter task");
        newWorkOrder.setSenderName(input.nextLine());

        ObjectMapper mapper = new ObjectMapper();

        int workOrderIDNo = newWorkOrder.getId();
        File jsonFile = new File( "" + workOrderIDNo + ".json" );

        try {
            mapper.writeValue( jsonFile,  newWorkOrder);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.print("Workorder created.");
        try {
           Thread.sleep(5000L);
            createWorkOrders();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public static void main(String args[]) {
        Creator creator = new Creator();
        creator.createWorkOrders();
    }
}



