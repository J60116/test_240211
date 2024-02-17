package game.moves;
import game.pokemon.Pokemon;

public class Tackle extends Move {
    
    public Tackle(){
        super();
        this.setName("Tackle");
        this.setMP_max(35);
        this.setMP(this.getMP_max());
        this.setType(Pokemon.getArrayType()[num_type]);//Normal
        this.setMoveType(ARRAY_MOVE_TYPE[0]);
        this.setPower(10);
        this.setAccuracy(100);
    }
    
}
