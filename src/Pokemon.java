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
	//戦闘状態（0:戦闘中 1:戦闘可能 2:戦闘不可/ひんし状態）
	final static String[][] ARRAY_STATUS = { { "In Battle", "○" }, { "Can Battle", "○" }, { "Can't Battle", "●" } };
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
	private String status; //ステータス
	private int dexNo; //ずかん番号
	private int level; //レベル
	int hp; //体力
	private int hp_max; //最大HP
	private int exp; //経験値
	private int exp_max; //レベルアップに必要な経験値
	private Random rand; //乱数用
	private Move[] moves; //技
	boolean fainted = false;

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
		this.setHP_max(0);
		this.setHP(this.getHP_max());
		this.setExp(0);
		this.setExp_max(10);
		this.setRand(new Random());
		this.moves = new Move[4];
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
		return this.types;
	}

	//オーバーロード
	public String getType(int num) {
		return getType()[num];
	}
	
	public void setType(String[] types) {
		this.types = types;
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
		String imputBall = "";
		for (int i = 0; i < ARRAY_BALL.length; i++) {
			if (ball.equals(ARRAY_BALL[i][0])) {
				//ボールの名前が正しければ代入する
				imputBall = ARRAY_BALL[i][1];
			}
		}
		if(imputBall.isEmpty()) {
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
		System.out.println("Level up!");
	}

	public int getHP() {
		return this.hp;
	}
	
	public void setHP(int hp) {
		this.hp = hp;
		if(this.hp < 0){
			this.hp = 0;
			this.fainted = true;
			System.out.println(this.getNickname() + " fainted.");
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
		this.exp = exp;
		if(this.exp_max - this.exp <= 0){
			this.setLevel();
			this.exp = Math.abs(this.exp_max - this.exp);
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

	public Move[] getMove() {
		return this.moves;
	}

	//オーバーロード
	public Move getMove(int num) {
		return getMove()[num];
	}
	
	public void setMove(Move[] moves) {
		this.moves = moves;
	}

	//オーバーロード
	public void setMove(int num, Move move) {
		if(num < 0 || num > 3){
			System.out.println("ERROR >> "+ move + " cannot set.");
			return;
		}
		this.getMove()[num] = move;
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

	//バトル画面にて表示するポケモン情報
	public void checkHP(){
		String str = this.nickname + " Lv." + this.level + " " + this.gender 
				+ " HP:" + this.hp + "/" + this.hp_max;
		System.out.println(str);
	}

	//技を確認する
	public void checkMoves(){
		System.out.println("Current Moves");
		for(int i = 0; i < this.getMove().length; i++){
			System.out.println("[" + (i + 1) + "] " + getMove(i));
		}
		// for (Move m : this.getMove()) {
		// 	if (m != null) {
		// 		System.out.println(m);
		// 	}
		// }
	}
	
	//技が使えるかの確認
	public boolean booleanMove(int num){
		//技が選択できない場合
		if((num - 1 < 0 && num - 1 > 3)||this.getMove(num - 1) == null){
			return false;
		}
		//MPが0の場合
		if(this.getMove(num - 1).getMP() == 0){
			System.out.println("MISS! " + this.getNickname() +" cannot use " + this.getMove(num - 1).name + ".");
			return false;
		}
		return true;
	}

	//自分自身に対する技
	public void useMove(int num){
		if(this.booleanMove(num) == false){
			return;
		}
		this.getMove(num - 1).setMP(this.getMove(num - 1).getMP() - 1);
		System.out.println(this.getNickname() + " used " + this.getMove(num - 1).name + "!");
		/*
		技の内容
		*/
	}

	//相手に対する技
	public void useMove(int num, Pokemon enemy){
		if(this.booleanMove(num) == false){
			return;
		}
		int damage = 0;
		this.getMove(num - 1).setMP(this.getMove(num - 1).getMP() - 1);
		System.out.println(this.getNickname() + " used " + this.getMove(num - 1).name + "!");
		damage = this.getMove(num - 1).power;
		enemy.setHP(enemy.getHP() - damage);
	}
	
	//逃げる
	public void run() {
		System.out.println(this.nickname + " run away.");
	}

	//回復する
	public void recover() {
		this.hp = this.hp_max;
		// this.moves.mp = this.mp_max;
		for(int i = 0; i<this.getMove().length;i++){
			if(this.getMove(i) != null){
				Move m = this.getMove(i);
				m.setMP(m.getMP_max());
			}
		}
		this.fainted = false;
	}

	//抽象メゾット
	//戦う
	abstract void attack(Pokemon p);

	//進化
	abstract void evolve(int num);

}