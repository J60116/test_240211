package game.moves;
import game.pokemon.Pokemon;

public class Thunderbolt extends Move {

    public Thunderbolt(){
        super();
        this.num_type = 3;
        this.setName("Thunderbolt");
        this.setType(Pokemon.getArrayType()[num_type]);//ELECTRIC
        this.setMP_max(15);
        this.setMP(this.getMP_max());
        this.setMoveType(ARRAY_MOVE_TYPE[0]);
        this.setPower(90);
        this.setAccuracy(100);
    }

}
