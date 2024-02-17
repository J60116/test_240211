package game.pokemon;
import game.moves.Hydropump;
import game.player.User;

public final class Vaporeon extends Eevee{ 
	//シャワーズ
	static final int NUM_EVOLVED = 0;
	static final String NAME = ARRAY_EVOLVED_NAME[NUM_EVOLVED];

	public Vaporeon(){
		this(null, User.getArrayBall()[0][0]);
	}

	public Vaporeon(String owner, String ball){
		super(owner, ball);
		this.num_type = 2;
		super.setEvolvedConstructor(NUM_EVOLVED);
		this.setHP(this.getHP_max());
		this.setMoves(2, new Hydropump());
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