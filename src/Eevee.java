class Eevee extends Pokemon {
	//イーブイの情報
	static final String NAME = "Eievui";
	static final String[] ARRAY_ABILITY = { "Nigeashi", "Tekiouryoku" };
	//Object[] EEVEE = { 133, "Eievui", ARRAY_TYPE[0], "Nigeashi", 0.3, 6.5, 55 };
	//進化の石
	static final String[] ARRAY_EVOLUTIONARY_STONE = { "WaterStone", "ThunderStone", "FireStone" };
	//進化ポケモンの情報
	//0:DexNo 1:Name 2:Type 3:Ability 4:Height 5:Weight 6:HP_MAX
	//static final Object[] VAPOREON = {134, "Showers", ARRAY_TYPE[2], "Chosui", 1.0, 29.0, 130};
	//static final Object[] JOLTEON = {135, "Thunders", ARRAY_TYPE[3], "Chikuden", 0.8, 24.5, 65};
	//static final Object[] FLAREON = {136, "Booster", ARRAY_TYPE[1], "Moraibi", 0.9, 25.0, 65};
	//static final Object[][] ARRAY_EVOLUTION = {VAPOREON, JOLTEON, FLAREON};
	static final int[] ARRAY_EVOLVED_DEXNO = { 134, 135, 136 };
	static final String[] ARRAY_EVOLVED_NAME = { "Showers", "Thunders", "Booster" };
	static final String[] ARRAY_EVOLVED_TYPE = { ARRAY_TYPE[3], ARRAY_TYPE[4], ARRAY_TYPE[2] };
	static final String[] ARRAY_EVOLVED_ABILITY = { "Chosui", "Chikuden", "Moraibi" };
	// static final double[][] ARRAY_EVOLVED_HW = { { 1.0, 29.0 }, { 0.8, 24.5 }, { 0.9, 25.0 } };
	static final int[] ARRAY_EVOLVED_MAXHP = { 130, 65, 65 };

	public Eevee() {
		this(null, ARRAY_BALL[0][0]);
	}

	public Eevee(String owner, String ball) {
		super(owner, ball);
		this.setName(NAME);
		this.setNickname(NAME);
		this.setGender();
		this.setType(0, ARRAY_TYPE[1]); //Normal
		this.setAbility();
		this.setDexNo(133);
		this.setLevel(1);
		this.setHP_max(55);
		this.hp = this.getHP_max();
		this.setMove(0, new Tackle());
	}

	private void setGender() {
		//87.5%♂・12.5%♀
		int num = this.getRand().nextInt(8); //性別設定用
		if (num != 0) {
			this.setGender(ARRAY_GENDER[1]);
		} else {
			this.setGender(ARRAY_GENDER[2]);
		}
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
	
	private void setAbility() {
		int num = this.getRand().nextInt(ARRAY_ABILITY.length); //特性設定用
		this.setAbility(ARRAY_ABILITY[num]);
	}
	
	@Override
	public void setItem(String item) {
		//持たせたアイテムが進化の石の場合
		for (int i = 0; i < ARRAY_EVOLUTIONARY_STONE.length; i++) {
			if (item.equals(ARRAY_EVOLUTIONARY_STONE[i])) {
				this.evolve(i);
				return;
			}
		}
		super.setItem(item);
	}

	@Override
	public void attack(Pokemon p) {
		System.out.println(this.getName() + "attacked" + p.getName() + "!");
		p.hp -= 5;
	}

	//進化
	@Override
	public void evolve(int i) {
		System.out.println("Congratulations! Your " + this.getNickname() + " evolved into " + ARRAY_EVOLVED_NAME[i] + "!");
		this.setDexNo(ARRAY_EVOLVED_DEXNO[i]);
		this.setName(ARRAY_EVOLVED_NAME[i]);
		if(this.getNickname().equals(NAME)) {
			this.setNickname(this.getName());
		}
		this.setType(0, ARRAY_EVOLVED_TYPE[i]);
		this.setAbility(ARRAY_EVOLVED_ABILITY[i]);
		this.setHP_max(ARRAY_EVOLVED_MAXHP[i]);
    }
	
}