final class Vaporeon extends Eevee{ 
	//シャワーズ
	static final String NAME = "Showers";

	public Vaporeon(){
		this(null, User.ARRAY_BALL[0][0]);
	}

	public Vaporeon(String owner, String ball){
		super(owner, ball);
		super.setEvolvedConstructor(0);
		this.setHP(this.getHP_max());
		this.setMoves(1, new Hydropump());
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