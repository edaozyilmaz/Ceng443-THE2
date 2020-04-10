import java.util.ArrayList;

public class User implements Runnable{
  private String name;
  private ArrayList<Seat> seats;
  private ArrayList<Seat> allSeats;


  public User(){}

  public User(String name, ArrayList<Seat> seat, ArrayList<Seat> allSeats){
    this.name=name;
    this.seats=seat;
    this.allSeats=allSeats;
  }
  public void setName(String name){
    this.name=name;
  }
  public String getName(){
    return this.name;
  }
  public ArrayList<Seat> getSeat(){
    return this.seats;
  }
  public void setSeat(ArrayList<Seat> seat){
    this.seats=seat;
  }
  public void run(){
      boolean retry = true;
      boolean empty=true;
      while(retry){
          synchronized (allSeats){
          double random = Math.random();
          for (int count = 0; count < allSeats.size(); count++) {
              for(int my=0;my<seats.size();my++){
                  if(seats.get(my).getSeatName().equals(allSeats.get(count).getSeatName())){
                      if(allSeats.get(count).getTaken()==true){
                        empty=false;
                        retry=false;
                     }

                 }
             }
          }
           //System.out.println("------------------------");
          StringBuffer sb = new StringBuffer();
          sb.append("[");
          for (Seat s : seats) {
              sb.append(s.getSeatName());
              if (s!=seats.get(seats.size() - 1))
                  sb.append(", ");
          }
          sb.append("]");
          String str = sb.toString();
          //All wanted seats are empty
          if(empty){
              //database failed
              if (random <= 0.1){
                Logger.LogDatabaseFailiure(this.name, str, System.nanoTime(),"Fail!!!");
                try{
                    Thread.sleep(100);
                }
                catch(InterruptedException e){
                    Thread.currentThread().interrupt();
                }
              }
              //database didn't fail
              else{
                  for (int count = 0; count < allSeats.size(); count++) {
                      for(int my=0;my<seats.size();my++){
                          if(seats.get(my).getSeatName().equals(allSeats.get(count).getSeatName())){

                          allSeats.get(count).setTaken(true);
                          allSeats.get(count).setUserName(name);
                      }
                     }
                 }

                  try{
                      Thread.sleep(50);
                  }
                  catch(InterruptedException e){
                      Thread.currentThread().interrupt();
                  }
                  Logger.LogSuccessfulReservation(this.name, str, System.nanoTime(),"I'm in!!!");
                  retry=false;
              }
          }
          //if seats are not empty
          else{
             Logger.LogFailedReservation(this.name, str, System.nanoTime(),"Seats are not empty!!");
         }}
      }
  }

}
