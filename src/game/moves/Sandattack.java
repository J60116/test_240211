package game.moves;
import game.pokemon.Pokemon;

public class Sandattack extends Move {

    public Sandattack(){
        super();
        this.setNum_type(8);//GRAND
        this.setName("Sand attack");
        this.setType(Pokemon.getArrayType()[this.getNum_type()]);//Normal
        this.setMP_max(15);
        this.setMP(this.getMP_max());
        this.setMoveType(ARRAY_MOVE_TYPE[2]);
        this.setPower(0);
        this.setAccuracy(100);
    }
    
}
