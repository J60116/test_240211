public class Tackle extends Move {
    
    public Tackle(){
        super();
        this.name = "Tackle";
        this.type = ARRAY_TYPE[1];
        this.setMP_max(35);
        this.setMP(this.getMP_max());
        this.power = 10;
        this.accuracy = 100;
    }
    
}
