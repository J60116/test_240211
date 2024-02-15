public class Sandattack extends Move {

    public Sandattack(){
        super();
        this.setName("Growl");
        this.setType(Pokemon.ARRAY_TYPE[0]);//NOrmal
        this.setMP_max(40);
        this.setMP(this.getMP_max());
        this.setMoveType(ARRAY_MOVE_TYPE[2]);
        this.setPower(0);
        this.setAccuracy(100);
    }
    
}
