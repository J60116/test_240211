public class Leafblade extends Move {

    public Leafblade(){
        super();
        this.setName("Leafblade");
        this.setType(Pokemon.ARRAY_TYPE[4]);//Grass
        this.setMP_max(35);
        this.setMP(this.getMP_max());
        this.setPower(10);
        this.setAccuracy(100);
    }
    
}
