public class Ticket {
    private int row;
    private int seat;
    private double price;
    private Person person;

    public Ticket(int row, int seat, double price, Person person){
        this.row = row;
        this.seat = seat;
        this.price = price;
        this.person = person;
    }

    public Ticket(){

    }

    public double getPrice(){
        return this.price;
    }

    public int getRow() {
        return row;
    }

    public int getSeat(){
        return seat;
    }

    public void print(){
        System.out.println("-".repeat(49));
        System.out.println("Name: "+person.getName());
        System.out.println("Surname: "+person.getSurname());
        System.out.println("Email: "+person.getEmail());
        System.out.println("Row number: "+this.row);
        System.out.println("Seat number: "+this.seat);
        System.out.println("Price: $ "+this.price);

    }
}
