package game.pokemon;
import game.moves.*;
import game.player.User;

public final class Leafeon extends Eevee{ 
	//リーフィア
	static final int NUM_EVOLVED = 3;
	static final String NAME = ARRAY_EVOLVED_NAME[NUM_EVOLVED];
    
	public Leafeon(){
		this(null, User.getArrayBall()[0][0]);
	}

	public Leafeon(String owner, String ball){ 
		super(owner, ball);
		super.setEvolvedConstructor(3);
		this.setHP(this.getHP_max());
		this.setMoves(1, new Leafblade());
	}

	@Override
	public String[] getTypes(){
		//アクセスできるようにsuperを使用
		return super.getTypes();
	}

	@Override
	public void setTypes(String[] type){
		//アクセスできるようにsuperを使用
		super.setTypes(type);
	}

	@Override
	public void setItem(String item) {
		//進化の石を持たせてもevolve(int)を呼び出さない
		super.setItem(item);
	}
	
	@Override
	public void evolve(int i) {
		//何もしない
	}

}