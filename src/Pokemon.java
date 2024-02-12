import java.util.Random;

public abstract class Pokemon {

	//タイプ
	final static String[] ARRAY_TYPE = { null, "NORMAL", "FIRE", "WATER", "ELECTRIC", "GRASS", "ICE", "FIGHTING",
			"POISON", "GROUND", "FLYING", "PSYCHIC", "BUG", "ROCK", "GHOST", "DRAGON", "DARK", "STEEL", "FAIRY" };
	//ボールの種類(0:野生、1:モンスターボール、2:スーパーボール、3:マスターボール)
	final static String[][] ARRAY_BALL = { { "Wild", " W " }, { "PokeBall", "(p)" }, { "SuperBall", "(s)" },
			{ "MasterBall", "(m)" } };
	//	final static String[] ARRAY_IMG_BALL = { "W)", "○", "◎", "●", "E)" };
	//ボールの画像(0:ボールなし（野生または敵）1:戦闘可能、2:瀕死状態）
	//	final static String[] ARRAY_IMG_BALL = { "・", "○", "●" };
	//性別(0:Unknown 1:Male 2:Female)
	final static String[] ARRAY_GENDER = { "・", "♂", "♀" };
	//技の効果
	final static String[] ARRAY_EFFECTIVE_MSG = { "×Has no effect", "△Not very effective", "〇Effective",
			"◎Super effective" };
	final static double[] ARRAY_EFFECTIVE_RATE = { 0.0, 0.5, 1.0, 2.0 };
	//戦闘状態（0:戦闘中 1:戦闘可能 2:戦闘不可/ひんし状態）
	final static String[][] ARRAY_STATUS = { { "In Battle", "○" }, { "Can Battle", "○" }, { "Can't Battle", "●" } };
	//名前の条件
	final static String FMT_NAME = "[A-Z][A-Za-z]{1,14}";

	private String name; //名前
	private String nickname; //ニックネーム
	private String owner; //トレーナー
	private String gender; //性別
	private String[] type; //タイプ
	private String ability; //特性
	private String ball; //ボール
	private String item; //もちもの
	private String status; //ステータス
	int dexNo; //ずかん番号
	int level; //レベル
	int hp; //体力
	int hp_max; //最大HP
	int exp; //経験値
	int exp_max; //レベルアップに必要な経験値
	Random rand; //乱数用

	//コンストラクタ
	public Pokemon() {
		this(null , ARRAY_BALL[0][0]);
	}

	public Pokemon(String owner, String ball) {
		this.setName("None");
		this.setNickname(this.name);
		this.setOwner(owner);
		this.setGender(ARRAY_GENDER[0]);
		this.setType(new String[2]);
		this.setAbility(null);
		this.setBall(ball);
		this.setItem("None");
		this.setStatus(null);
		this.setDexNo(0);
		this.setLevel(0);
		this.setHp_max(0);
		this.setHp(this.getHp_max());
		this.setExp(10);
		this.setExp_max(0);
		this.setRand(new Random());
	}
	
	//アクセサ
	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getNickname() {
		return this.nickname;
	}
	
	public void setNickname(String nickname) {
		if (!nickname.matches(FMT_NAME)) {
			System.out.println(nickname +" is not acceptable.");
			return;
		}
		this.nickname = nickname;
	}
	
	public String getOwner() {
		return this.owner;
	}
	
	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}
	
	public String[] getType() {
		return this.type;
	}
	
	public void setType(String[] type) {
		this.type = type;
	}

	//オーバーロード
	public void setType(int num, String type) {
		if(num != 0 && num != 1){
			System.out.println("ERROR >> "+ type + " cannot set.");
			return;
		}
		this.getType()[num] = type;
	}
	
	public String getAbility() {
		return this.ability;
	}
	
	public void setAbility(String ability) {
		this.ability = ability;
	}
	
	public String getBall() {
		return this.ball;
	}

	public void setBall(String ball) {
		String str = "";
		for (int i = 0; i < ARRAY_BALL.length; i++) {
			if (ball.equals(ARRAY_BALL[i][0])) {
				str = ARRAY_BALL[i][1];
			}
		}
		if(str.isEmpty()) {
			System.out.println(ball + " is not tool to catch Pokemon.");
			return;
		}
		this.ball = str;
	}

	public String getItem() {
		return this.item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public int getDexNo() {
		return this.dexNo;
	}
	
	public void setDexNo(int dexNo) {
		this.dexNo = dexNo;
	}

	public int getLevel() {
		return this.level;
	}
	
	public void setLevel(int level) {
		this.level = level;
	}

	public int getHp() {
		return this.hp;
	}
	
	public void setHp(int hp) {
		this.hp = hp;
	}

	public int getHp_max() {
		return this.hp_max;
	}

	public void setHp_max(int hp_max) {
		this.hp_max = hp_max;
	}
	
	public int getExp() {
		return this.exp;
	}
	
	public void setExp(int exp) {
		this.exp = exp;
	}
	
	public int getExp_max() {
		return this.exp_max;
	}
	
	public void setExp_max(int exp_max) {
		this.exp_max = exp_max;
	}
	
	public Random getRand() {
		return this.rand;
	}
	
	public void setRand(Random rand) {
		this.rand = rand;
	}

	//メゾット
	@Override
	public String toString() {
		String type = "";
		if (this.type[1] == null) {
			type = this.type[0];
		} else {
			type = this.type[0] + "・" + this.type[1];
		}
		String str = this.ball + this.nickname + "/" + this.name + " Lv." + this.level + " " + this.gender
				+ "\nType: " + type
				+ "\nHP: " + this.hp + "/" + this.hp_max
				+ "\nExp.Points: " + this.exp
				;
		return str;
	}

	//ステータスを確認する
	public void checkStatus() {
		System.out.println(this.toString());
	}
	
	//逃げる
	public void run() {
		System.out.println(this.nickname + " run away.");
	}

	//回復する
	public void recover() {
		this.hp = this.hp_max;
	}

	//抽象メゾット
	//戦う
	abstract void attack(Pokemon p);

	//進化
	abstract void evolve(int num);

}