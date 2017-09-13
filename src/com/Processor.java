package com;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class Processor {


    Map<Status, Set<WorkOrder>> workOrderMap = new HashMap<>();

    public static void main(String args[]) {
        Processor processor = new Processor();
        processor.workOrderMapInitial();
        processor.processWorkOrders();
    }

    public void processWorkOrders() {
        try {
            readIt();
            moveIt();
            Thread.sleep(5000L);
            processWorkOrders();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void workOrderMapInitial(){
        workOrderMap.put(Status.INITIAL, new HashSet<>());
        workOrderMap.put(Status.ASSIGNED, new HashSet<>());
        workOrderMap.put(Status.IN_PROGRESS, new HashSet<>());
        workOrderMap.put(Status.DONE, new HashSet<>());
    }

    private void moveIt() {
        File currentFile = new File(".");
        File files[] = currentFile.listFiles();

        Set<WorkOrder> workOrderSetInitial = workOrderMap.get(Status.INITIAL);
        Set<WorkOrder> workOrderSetAssigned = workOrderMap.get(Status.ASSIGNED);
        Set<WorkOrder> workOrderSetInProgress = workOrderMap.get(Status.IN_PROGRESS);
        Set<WorkOrder> workOrderSetSetDone = workOrderMap.get(Status.DONE);

        workOrderMapInitial();

        workOrderMap.put(Status.ASSIGNED, workOrderSetInitial);
        workOrderMap.put(Status.IN_PROGRESS, workOrderSetAssigned);
        workOrderMap.put(Status.DONE, workOrderSetInProgress);

        System.out.println(workOrderMap);
        System.out.println( "Initial: " + workOrderSetInitial.size());
        System.out.println( "Assigned: " + workOrderSetAssigned.size());
        System.out.println( "In progress: " + workOrderSetInProgress.size());
    }


    private void workOrderInMapAdd(Status status, WorkOrder workOrder) {
        Set<WorkOrder> workOrderSet = workOrderMap.get(status);
        workOrderSet.add(workOrder);
        workOrderMap.put(status, workOrderSet);
    }


    private void readIt() {
        File currentFile = new File(".");
        File files[] = currentFile.listFiles();

        for (File f : files) {
            if (f.getName().endsWith(".json")) {
                ObjectMapper mapper = new ObjectMapper();
                try {
                    WorkOrder order = mapper.readValue(new File(f.getName()), WorkOrder.class);
                    Status orderStatus = order.getStatus();

                    workOrderInMapAdd(orderStatus, order);

                    f.delete();
                    System.out.println(workOrderMap);

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }

    }



}
