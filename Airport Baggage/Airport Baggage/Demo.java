package com.autobaggage;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@SuppressWarnings("unchecked")
public class Demo {
	public static void main(String[] args) {

		// create graph
		@SuppressWarnings("rawtypes")
		DiGraph graph = new DiGraph();
		
		// add a bunch of edges
		graph.add("Concourse_A_Ticketing", "A5", 5);
		graph.add("A5", "BaggageClaim", 5);
		graph.add("A5", "A10", 4);
		graph.add("A5", "A1", 6);
		graph.add("A1", "A2", 1); 
		graph.add("A2", "A3", 1);
		graph.add("A3", "A4", 1);
		graph.add("A10", "A9", 1);
		graph.add("A9", "A8", 1);
		graph.add("A8", "A7", 1);
		graph.add("A7", "A6", 1);

		System.out.println(graph.edgesToString());
		System.out.println();
		System.out.println("Flight_Id\tGate\tDestination\tFlight time");
		for (Departure departure : getDepartures()) {
			System.out.println(departure.toString());
		}
		
		List<BagInfo> baggageList = executeBaggage();
		
		System.out.println();
		System.out.println("Bag_id\tEntryPoint\t\tFlight");
		for (BagInfo baggage : baggageList) {
			System.out.println(baggage.toString());
		}
		
		System.out.println();
		System.out.println("OutPut:-");
		System.out.println("============");
		System.out.println("Bag_id\t|EntryPoint\t\t\t|TotalTime");
		for(BagInfo baggage1 : baggageList){
			for(Departure departure : getDepartures()){
				String flight_Arrival = "ARRIVAL";
				if(departure.getFlight_Id().equalsIgnoreCase(baggage1.getFlight_Id()) || flight_Arrival.equalsIgnoreCase(baggage1.getFlight_Id())){
					String flight_gate = flight_Arrival.equalsIgnoreCase(baggage1.getFlight_Id())?"BaggageClaim":departure.getFlight_gate().toUpperCase();
					List<String> path = graph.getPath(baggage1.getEntryPoint().toUpperCase(),flight_gate);
					String totalTime = path.get(path.size()-1);
					
					if(!totalTime.equals("No path found")){
						totalTime = totalTime.substring(totalTime.length()-2);
					}
					
					System.out.println(baggage1.getBagNumber()+"\t|"+path.toString()+"\t"+totalTime);
					break;
				}
			}
		}
		
	}
	
	private static List<Departure> getDepartures(){
		List<Departure> departures = new ArrayList<>();

		Departure d1 = new Departure("UA10","A1","MIA","08:00");
		Departure d2 = new Departure("UA11","A1","LAX","09:00");
		Departure d3 = new Departure("UA12","A1","JFK","09:45");
		Departure d4 = new Departure("UA13","A2","JFK","08:30");
		Departure d5 = new Departure("UA14","A2","JFK","09:45");
		Departure d6 = new Departure("UA15","A2","JFK","10:00");
		Departure d7 = new Departure("UA16","A3","JFK","09:00");
		Departure d8 = new Departure("UA17","A4","MHT","09:15");
		Departure d9 = new Departure("UA18","A5","LAX","10:15");
		
		departures.add(d1);
		departures.add(d2);
		departures.add(d3);
		departures.add(d4);
		departures.add(d5);
		departures.add(d6);
		departures.add(d7);
		departures.add(d8);
		departures.add(d9);
		
		return departures;
	}
	
	@SuppressWarnings("resource")
	private static List<BagInfo> executeBaggage(){
		List<BagInfo> bagInfos = new ArrayList<>();
		
		System.out.println("Enter Baggage details with flight id. ");
		for(int i=0; i<100; i++){
			
			System.out.print("\nEnter EntryPoint : ");
			
			Scanner bInfo = new Scanner(System.in);
			String entryPoint = bInfo.nextLine();
			System.out.print("\nEnter Flight Id : ");
			String flight_id = bInfo.nextLine();
			System.out.print("\nDo you want to continue (Y/N): ");
			String isContinue = bInfo.nextLine();
			
			BagInfo bagInfo = new BagInfo((i+1), entryPoint, flight_id);
			bagInfos.add(bagInfo);
			
			if("N".equalsIgnoreCase(isContinue)){
				break;
			}
			
		}
		
		return bagInfos;
	}
}