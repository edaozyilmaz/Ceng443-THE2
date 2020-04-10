import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Main{
    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<Seat> seats = new ArrayList<>();
  public static void main(String[] args) throws IOException{
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    // Reading data using readLine
    String[] gridSize = reader.readLine().split(" ");
    int count = Integer.parseInt(reader.readLine());
    int i = 0;
    ArrayList<ArrayList<Seat>> allSeats = new ArrayList<ArrayList<Seat>>();

    for(int m=65;m<65+Integer.valueOf(gridSize[0]);m++){
        for(int j=0;j<Integer.valueOf(gridSize[1]);j++){
            Seat newSeat = new Seat((char)m+Integer.toString(j));
            seats.add(newSeat);
        }
    }
    int temp=0;
    for(int j=0;j<Integer.valueOf(gridSize[0]);j++){
        ArrayList<Seat> row = new ArrayList<>();
        for(int k=0;k<Integer.valueOf(gridSize[1]);k++){
            Seat dummy = new Seat();
            row.add(k, seats.get(temp));
            temp++;
        }
        allSeats.add(j,row);
    }

    while(i<count){
      ArrayList<String> line = new ArrayList<String>(Arrays.asList(reader.readLine().split(" ")));
      String name = line.get(0);
      line.remove(0);
      ArrayList<Seat> mySeats = new ArrayList<>();
    	for(String oneLine: line){
    			Seat seat = new Seat(oneLine);
                mySeats.add(seat);
    	}
        User user = new User(name, mySeats,seats);
    	users.add(user);
    	i++;
    }
    Logger.InitLogger();
    ArrayList<Thread> threads = new ArrayList<>();
	for(User user:users){
        Thread t1 = new Thread(user);
        t1.start();
        threads.add(t1);
    }
    for(int a=0; a<threads.size();a++){
        try{
            threads.get(a).join();
        }
        catch(Exception e){
            System.out.println("exception");
        }
    }
    for(int a=0;a<Integer.parseInt(gridSize[0]);a++){
        for(int size=0;size<Integer.parseInt(gridSize[1]);size++){
            if(allSeats.get(a).get(size).getTaken() == true)
                System.out.print("T:"+allSeats.get(a).get(size).getUserName()+" ");
            else{
                System.out.print("E:      ");
            }
        }
        System.out.println();
    }
  }
}
