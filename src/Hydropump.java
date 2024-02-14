public class Hydropump extends Move{

    public Hydropump(){
        super();
        this.setName("Hydro pump");
        this.setType(Pokemon.ARRAY_TYPE[2]);//Water
        this.setMP_max(5);
        this.setMP(this.getMP_max());
        this.setPower(110);
        this.setAccuracy(80);
    }

}