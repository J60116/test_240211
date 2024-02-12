final class Vaporeon extends Eevee{ 
	//シャワーズ
	static final String NAME = "Showers";

	public Vaporeon(){
		this(null, ARRAY_BALL[0][1]);
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
		this.dexNo = ARRAY_EVOLVED_DEXNO[0];
		this.setName(ARRAY_EVOLVED_NAME[0]);
		this.setNickname(this.getName());
		this.setType(0, ARRAY_EVOLVED_TYPE[0]);
		this.setAbility(ARRAY_EVOLVED_ABILITY[0]);
		this.hp_max = ARRAY_EVOLVED_MAXHP[0];
		this.hp = this.hp_max;
	}

	@Override
	public String[] getType(){
		//アクセスできるようにsuperを使用
		return super.getType();
	}

	@Override
	public void setType(String[] type){
		//アクセスできるようにsuperを使用
		super.setType(type);
	}

	@Override
	public void setItem(String item) {
		//進化の石を持たせてもevolve(int)を呼び出さない
		this.item = item;
	}

	@Override
	public void attack(Pokemon p) {
		System.out.println(this.getName() + " attacked" + p.getName() + "!");
		p.hp -= 10;
	}

	public void HydroPump(Pokemon p){
		System.out.println(this.getName() + " used Hydro Pump!");
		p.hp -= 20;
	}
	
	@Override
	public void evolve(int i) {
		//何もしない
	}

}