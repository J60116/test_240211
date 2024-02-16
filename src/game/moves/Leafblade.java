package game.moves;
import game.pokemon.Pokemon;

public class Leafblade extends Move {

    public Leafblade(){
        super();
        this.setName("Leafblade");
        this.setType(Pokemon.getArrayType()[4]);//Grass
        this.setMP_max(15);
        this.setMP(this.getMP_max());
        this.setMoveType(ARRAY_MOVE_TYPE[0]);
        this.setPower(90);
        this.setAccuracy(100);
    }
    
}
