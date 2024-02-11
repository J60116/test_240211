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
		this.name = ARRAY_EVOLVED_NAME[0];
		this.setNickname(this.name);
		this.setGender();
		this.type[0] = ARRAY_EVOLVED_TYPE[0];
		this.ability = ARRAY_EVOLVED_ABILITY[0];
		this.hp_max = ARRAY_EVOLVED_MAXHP[0];
		this.hp = this.hp_max;
	}
	
	private void setGender() {
		//87.5%♂・12.5%♀
		int num = this.rand.nextInt(8); //性別設定用
		if (num != 0) {
			this.gender = ARRAY_GENDER[1];
		} else {
			this.gender = ARRAY_GENDER[2];
		}
	}

	@Override
	public void setItem(String item) {
		super.setItem(item);
	}

	@Override
	public void attack(Pokemon p) {
		System.out.println(this.name + " attacked" + p.name + "!");
		p.hp -= 10;
	}

	public void HydroPump(Pokemon p){
		System.out.println(this.name + " used Hydro Pump!");
		p.hp -= 20;
	}
	
	@Override
	public void evolve(int i) {
		//何もしない
	}

}