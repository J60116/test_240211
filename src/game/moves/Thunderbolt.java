package game.moves;
import game.pokemon.Pokemon;

public class Thunderbolt extends Move {

    public Thunderbolt(){
        super();
        this.setName("Thunderbolt");
        this.setType(Pokemon.getArrayType()[3]);//ELECTRIC
        this.setMP_max(15);
        this.setMP(this.getMP_max());
        this.setMoveType(ARRAY_MOVE_TYPE[0]);
        this.setPower(90);
        this.setAccuracy(100);
    }

}
