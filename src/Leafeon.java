final class Leafon extends Eevee{ 
	//リーフィア
	static final String NAME = "Leafia";

	public Leafon(){
		this(null, User.ARRAY_BALL[0][0]);
	}

	public Leafon(String owner, String ball){ 
		super(owner, ball);
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