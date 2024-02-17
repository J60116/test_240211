package game.moves;
import game.pokemon.Pokemon;

public class Hydropump extends Move{

    public Hydropump(){
        super();
        this.num_type = 2;
        this.setName("Hydro pump");
        this.setType(Pokemon.getArrayType()[num_type]);//Water
        this.setMP_max(5);
        this.setMP(this.getMP_max());
        this.setMoveType(ARRAY_MOVE_TYPE[0]);
        this.setPower(110);
        this.setAccuracy(80);
    }

}