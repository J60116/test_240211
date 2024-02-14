final class Vaporeon extends Eevee{ 
	//シャワーズ
	static final String NAME = "Showers";

	public Vaporeon(){
		this(null, User.ARRAY_BALL[0][0]);
	}

	public Vaporeon(String owner, String ball){
		super(owner, ball);
//		this.dexNo = super.ARRAY_EVOLVUTION[0][0];
//		this.name = super.ARRAY_EVOLVUTION[0][1];
//		this.type = super.ARRAY_EVOLVUTION[0][2];
//		this.ability = super.ARRAY_EVOLVUTION[0][3];
//		this.hp_max = super.ARRAY_EVOLVUTION[0][4];
//		this.height = super.ARRAY_EVOLVUTION[0][5];
//		this.weight = super.ARRAY_EVOLVUTION[0][6];
		this.setDexNo(ARRAY_EVOLVED_DEXNO[0]);
		this.setName(ARRAY_EVOLVED_NAME[0]);
		this.setNickname(ARRAY_EVOLVED_NAME[0]);
		this.setTypes(0, ARRAY_EVOLVED_TYPE[0]);
		this.setAbility(ARRAY_EVOLVED_ABILITY[0]);
		this.setHP_max(ARRAY_EVOLVED_MAXHP[0]);
		this.hp = this.getHP_max();
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