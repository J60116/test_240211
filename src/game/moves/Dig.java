package game.moves;
import game.pokemon.Pokemon;

public class Dig extends Move {

    public Dig(){
        super();
        this.setName("Dig");
        this.setType(Pokemon.getArrayType()[8]);//GROUND
        this.setMP_max(10);
        this.setMP(this.getMP_max());
        this.setMoveType(ARRAY_MOVE_TYPE[0]);
        this.setPower(80);
        this.setAccuracy(100);
    }

}
