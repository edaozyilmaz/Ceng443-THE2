public class Seat{
  private String userName;
  private boolean taken=false;
  private String seatName;

  public Seat(){}

  public Seat( String seatName){
    this.seatName=seatName;
  }
  public String getSeatName(){
    return this.seatName;
  }
  public void setSeatName(String name){
    this.seatName=name;
  }
  public String getUserName(){
    return this.userName;
  }
  public void setUserName(String name){
    this.userName=name;
  }
  public synchronized boolean getTaken(){
    return this.taken;
  }
  public synchronized void setTaken(boolean taken){
    this.taken=taken;
  }
}
