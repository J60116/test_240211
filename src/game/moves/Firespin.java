package game.moves;
import game.pokemon.Pokemon;

public class Firespin extends Move {

    public Firespin(){
        super();
        this.setNum_type(1);
        this.setName("Fire spin");
        this.setType(Pokemon.getArrayType()[this.getNum_type()]);//Fire
        this.setMP_max(15);
        this.setMP(this.getMP_max());
        this.setMoveType(ARRAY_MOVE_TYPE[1]);
        this.setPower(35);
        this.setAccuracy(85);
    }

}