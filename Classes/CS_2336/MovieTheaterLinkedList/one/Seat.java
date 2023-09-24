//Pratyush Niraula
//Pxn210033
public class Seat{
    private int row;
    private char column;
    private char ticketType;

    //seat constructor
    public Seat(int row, char column, char ticketType){
        this.row = row;
        this.column = column;
        this.ticketType = ticketType;
    }
    public Seat(){
        this.row = 0;
        this.column = '/';
        this.ticketType = 0;
    }

    public int getRow(){
        return row;
    }
    public char getColumn(){
        return column;
    }
    public char getTicketType(){
        return ticketType;
    }
    public void setRow(int row){
        this.row = row;
    }
    public void setColumn(char column){
        this.column = column;
    }
    public void setTicketType(char ticket){
        this.ticketType = ticket;
    }
    //toString method
    public String toString(){
        String s = "" + this.ticketType;
        return s;

    }



    public String seatString(){
        return Integer.toString(row) + column;

    }
}