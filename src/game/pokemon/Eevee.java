package game.pokemon;
import game.moves.*;
import game.player.User;

public class Eevee extends Pokemon {
	//イーブイの情報
	static final String NAME = "Eievui";
	static final String[] ARRAY_ABILITY = { "Nigeashi", "Tekiouryoku" };
	//Object[] EEVEE = { 133, "Eievui", ARRAY_TYPE[0], "Nigeashi", 0.3, 6.5, 55 };
	//進化の石
	static final String[] ARRAY_EVOLUTIONARY_STONE = { "Water Stone", "Thunder Stone", "Fire Stone" , "Leaf Stone"};
	//進化ポケモンの情報
	static final int[] ARRAY_EVOLVED_DEXNO = { 134, 135, 136, 470};
	static final String[] ARRAY_EVOLVED_NAME = { "Showers", "Thunders", "Booster", "Leafia"};
	static final String[] ARRAY_EVOLVED_TYPE = { ARRAY_TYPE[2], ARRAY_TYPE[3], ARRAY_TYPE[1], ARRAY_TYPE[4]};
	//特性(ちょすい、ちくでん、もらいび、リーフガード)
	static final String[] ARRAY_EVOLVED_ABILITY = { "Water Absorb", "Volt Absorb", "Flash Fire", "Leaf Guard" };
	static final int[] ARRAY_EVOLVED_MAXHP = { 130, 65, 65, 65 };
	// static final double[][] ARRAY_EVOLVED_HW = { { 1.0, 29.0 }, { 0.8, 24.5 }, { 0.9, 25.0 } };

	public Eevee() {
		this(null, User.getArrayBall()[0][0]);
	}

	public Eevee(String owner, String ball) {
		super(owner, ball);
		this.setName(NAME);
		this.setNickname(NAME);
		this.setGender();
		this.setTypes(0, ARRAY_TYPE[0]); //Normal
		this.setAbility();
		this.setDexNo(133);
		this.setLevel(1);
		this.setHP_max(55);
		this.setHP(this.getHP_max());
		this.setMoves(0, new Tackle());
		this.setMoves(1, new Sandattack());
	}

	//進化ポケモンのコンストラクタ設定用
	public void setEvolvedConstructor(int num){
		this.setDexNo(ARRAY_EVOLVED_DEXNO[num]);
		this.setName(ARRAY_EVOLVED_NAME[num]);
		this.setNickname(ARRAY_EVOLVED_NAME[num]);
		this.setTypes(0, ARRAY_EVOLVED_TYPE[num]);
		this.setAbility(ARRAY_EVOLVED_ABILITY[num]);
		this.setHP_max(ARRAY_EVOLVED_MAXHP[num]);
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
	public String[] getTypes(){
		//アクセスできるようにsuperを使用
		return super.getTypes();
	}

	@Override
	public void setTypes(String[] type){
		//アクセスできるようにsuperを使用
		super.setTypes(type);
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

	//進化
	@Override
	public void evolve(int i) {
		System.out.println("What? "+ this.getName() + " is evolving...");
		System.out.println("Congratulations! Your " + this.getNickname() + " evolved into " + ARRAY_EVOLVED_NAME[i] + "!");
		this.setDexNo(ARRAY_EVOLVED_DEXNO[i]);
		this.setName(ARRAY_EVOLVED_NAME[i]);
		if(this.getNickname().equals(NAME)) {
			this.setNickname(this.getName());
		}
		this.setTypes(0, ARRAY_EVOLVED_TYPE[i]);
		this.setAbility(ARRAY_EVOLVED_ABILITY[i]);
		this.setHP_max(ARRAY_EVOLVED_MAXHP[i]);
    }
	
}