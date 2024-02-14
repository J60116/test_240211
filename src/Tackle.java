public class Tackle extends Move {
    
    public Tackle(){
        super();
        this.setName("Tackle");
        this.setType(Pokemon.ARRAY_TYPE[0]);//Normal
        this.setMP_max(35);
        this.setMP(this.getMP_max());
        this.setPower(10);
        this.setAccuracy(100);
    }
    
}
