public class Hydropump extends Move{

    public Hydropump(){
        super();
        this.name = "Hydro pump";
        this.type = ARRAY_TYPE[3];
        this.setMP_max(5);
        this.setMP(this.getMP_max());
        this.power = 110;
        this.accuracy = 80;
    }

}
