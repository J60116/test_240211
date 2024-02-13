import java.util.Random;

public abstract class Pokemon {

	//タイプ
	final static String[] ARRAY_TYPE = { "NORMAL", "FIRE", "WATER", "ELECTRIC", "GRASS", "ICE", "FIGHTING",
			"POISON", "GROUND", "FLYING", "PSYCHIC", "BUG", "ROCK", "GHOST", "DRAGON", "DARK", "STEEL", "FAIRY" };
	//ボールの種類(0:野生、1:モンスターボール、2:スーパーボール、3:マスターボール)
	final static String[][] ARRAY_BALL = { { "Wild", " W " }, { "PokeBall", "(p)" }, { "SuperBall", "(s)" },
			{ "MasterBall", "(m)" } };
	//	final static String[] ARRAY_IMG_BALL = { "W)", "○", "◎", "●", "E)" };
	//性別(0:Unknown 1:Male 2:Female)
	final static String[] ARRAY_GENDER = { "・", "♂", "♀" };
	//戦闘状態（0:戦闘不可/ひんし状態 1:戦闘中 2:戦闘可能）
	final static String[] ARRAY_STATUS = { "Can't Battle", "In Battle", "Can Battle" };
	//ボールの画像(0:ひんし状態 1:戦闘可能）
	final static String[] ARRAY_IMG_BALL = { "●", "○" };
	//名前の条件
	final static String FMT_NAME = "[A-Z][A-Za-z]{1,14}";

	private String name; //名前
	private String nickname; //ニックネーム
	private String owner; //トレーナー
	private String gender; //性別
	private String[] types; //タイプ
	private String ability; //特性
	private String ball; //ボール
	private String item; //もちもの
	private String status; //戦闘状態
	private int dexNo; //ずかん番号
	private int level; //レベル
	int hp; //体力
	private int hp_max; //最大HP
	private int exp; //経験値
	private int exp_max; //レベルアップに必要な経験値
	private Random rand; //乱数用
	private Move[] moves; //技
	private boolean fainted = false; //ひんし状態

	//コンストラクタ
	public Pokemon() {
		this(null, ARRAY_BALL[0][0]);
	}

	public Pokemon(String owner, String ball) {
		this.setName("None");
		this.setNickname(this.getName());
		this.setOwner(owner);
		this.setGender(ARRAY_GENDER[0]);
		this.setTypes(new String[2]);
		this.setAbility(null);
		this.setBall(ball);
		this.setItem("None");
		this.setStatus(ARRAY_STATUS[2]);
		this.setDexNo(0);
		this.setLevel(0);
		this.setHP_max(0);
		this.setHP(this.getHP_max());
		this.setExp(0);
		this.setExp_max(10);
		this.setRand(new Random());
		this.setMoves(new Move[4]);
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
			System.out.println(nickname + " is not acceptable.");
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

	public String[] getTypes() {
		return this.types;
	}

	//オーバーロード
	public String getTypes(int num) {
		return getTypes()[num];
	}

	public void setTypes(String[] types) {
		this.types = types;
	}

	//オーバーロード
	public void setTypes(int num, String type) {
		if (num != 0 && num != 1) {
			System.out.println("ERROR >> " + type + " cannot set.");
			return;
		}
		this.getTypes()[num] = type;
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
		String imputBall = "";
		for (int i = 0; i < ARRAY_BALL.length; i++) {
			if (ball.equals(ARRAY_BALL[i][0])) {
				//ボールの名前が正しければ代入する
				imputBall = ARRAY_BALL[i][1];
			}
		}
		if (imputBall.isEmpty()) {
			System.out.println("MISS! " + ball + " is not tool to catch Pokemon.");
			return;
		}
		this.ball = imputBall;
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

	//オーバーロード
	public void setLevel() {
		this.level++;
		System.out.println(this.getNickname() + " < Level UP♪");
	}

	public int getHP() {
		return this.hp;
	}

	public void setHP(int hp) {
		this.hp = hp;
		if (this.hp < 0) {
			this.hp = 0;
			this.trueFainted();
		}
	}

	public int getHP_max() {
		return this.hp_max;
	}

	public void setHP_max(int hp_max) {
		this.hp_max = hp_max;
	}

	public int getExp() {
		return this.exp;
	}

	public void setExp(int exp) {
		//ひんし状態でない場合
		if (!this.getStatus().equals(ARRAY_STATUS[0])) {
			this.exp = exp;
			//経験値がたまった場合
			if (this.getExp_max() - this.exp <= 0) {
				this.exp = Math.abs(this.getExp_max() - this.exp);
				this.setLevel();
			}
		}
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

	public Move[] getMoves() {
		return this.moves;
	}

	//オーバーロード
	public Move getMoves(int num) {
		return getMoves()[num];
	}

	public void setMoves(Move[] moves) {
		this.moves = moves;
	}

	//オーバーロード
	public void setMoves(int num, Move move) {
		if (num < 0 || num > 3) {
			System.out.println("ERROR >> " + move + " cannot set.");
			return;
		}
		this.getMoves()[num] = move;
	}

	public boolean getFainted() {
		return this.fainted;
	}

	public void trueFainted() {
		this.fainted = true;
		this.setStatus(ARRAY_STATUS[0]);
		System.out.println(this.getNickname() + " fainted.");
	}

	public void falseFainted() {
		this.fainted = false;
	}

	//メゾット
	@Override
	public String toString() {
		String type = "";
		if (this.types[1] == null) {
			type = this.types[0];
		} else {
			type = this.types[0] + "・" + this.types[1];
		}
		String str = this.ball + this.nickname + "/" + this.name + " Lv." + this.level + " " + this.gender
				+ "\nType: " + type
				+ "\nHP: " + this.hp + "/" + this.hp_max
				+ "\nExp.Points: " + this.exp
				+ "\nTo Next Lv.: " + (this.exp_max - this.exp);
		return str;
	}

	//ステータスを確認する
	public void checkStatus() {
		System.out.println(this.toString());
	}

	//ステータスを確認する(バトル画面用)
	public void checkBattleStatus() {
		String str = this.nickname + " Lv." + this.level + " " + this.gender
				+ " HP:" + this.hp + "/" + this.hp_max;
		System.out.println(str);
	}

	//技を確認する
	public void checkMoves() {
		System.out.println("Current Moves:");
		for (int i = 0; i < this.getMoves().length; i++) {
			System.out.println("[" + (i + 1) + "] " + getMoves(i));
		}
		// for (Moves m : this.getMoves()) {
		// 	if (m != null) {
		// 		System.out.println(m);
		// 	}
		// }
	}

	//技が使えるかの確認
	public boolean booleanMove(int num) {
		//ひんし状態の場合
		if (this.getFainted()) {
			return false;
		}
		//技が選択できない場合
		if (!(num - 1 >= 0 && num - 1 < 4) || this.getMoves(num - 1) == null) {
			System.out.println("MISS! " + "Moves[" + num + "] is null.");
			return false;
		}
		//MPが0の場合
		if (this.getMoves(num - 1).getMP() == 0) {
			System.out.println("MISS! " + "Moves[" + num + "] 's MP is zero.");
			return false;
		}
		return true;
	}

	//自分自身に対する技
	public void useMove(int num) {
		if (!this.booleanMove(num)) {
			return;
		}
		this.getMoves(num - 1).setMP();
		System.out.println(this.getNickname() + " used " + this.getMoves(num - 1).getName() + "!");
		/*
		 * 技の内容
		 * 
		 */
	}

	//相手に対する技
	public void useMove(int num, Pokemon enemy) {
		if (!this.booleanMove(num)) {
			return;
		}
		if(enemy.getFainted()) {
			return;
		}
		//技のMPを1減らす
		this.getMoves(num - 1).setMP();
		System.out.println(this.getNickname() + " used " + this.getMoves(num - 1).getName() + "!");
		//技の命中率
		int per = this.getRand().nextInt(101);
		if (per <= this.getMoves(num - 1).getAccuracy()) {
			//技の威力
			int damage = this.getMoves(num - 1).getPower();
			enemy.getDamage(damage);
			/*
			 * 技のタイプ
			 * 
			 */
		} else {
			System.out.println("But it's failed!");
		}
	}

	//HPを下げる
	public void getDamage(int num) {
		this.setHP(this.getHP() - num);
	}
	
	//HPを上げる
	public void getRecovery(int num) {
		this.setHP(this.getHP() + num);
		this.falseFainted();
	}

	//逃げる
	public void run() {
		System.out.println(this.nickname + " run away.");
	}

	//回復する
	public void recover() {
		this.hp = this.hp_max;
		// this.moves.mp = this.mp_max;
		for (int i = 0; i < this.getMoves().length; i++) {
			if (this.getMoves(i) != null) {
				Move m = this.getMoves(i);
				m.setMP(m.getMP_max());
			}
		}
		this.falseFainted();
	}

	//抽象メゾット
	//進化
	abstract void evolve(int num);

}