package game.pokemon;
import java.util.Random;

import game.moves.Move;
import game.player.User;

public abstract class Pokemon {
	//タイプ
	final static String[] ARRAY_TYPE = { "NORMAL", "FIRE", "WATER", "ELECTRIC", "GRASS", "ICE", "FIGHTING",
			"POISON", "GROUND", "FLYING", "PSYCHIC", "BUG", "ROCK", "GHOST", "DRAGON", "DARK", "STEEL", "FAIRY" };
	//性別(0:Unknown 1:Male 2:Female)
	final static String[] ARRAY_GENDER = { "・", "♂", "♀" };
	//戦闘状態（0:戦闘不可/ひんし状態 1:戦闘中 2:戦闘可能）
	final static String[] ARRAY_STATUS = { "Can't Battle", "In Battle", "Can Battle" };
	//ボールの画像(0:ひんし状態 1:戦闘可能）
	final static String[] ARRAY_IMG_BALL = { "●", "○" };
	//名前の条件
	final static String FMT_NAME = "[A-Z][A-Za-z]{1,7}";
	//技の効果
	final static String[] ARRAY_EFFECTIVE = {  "〇 Effective", "× Has no effect", "△ Not very effective", "◎ Super effective" };
	//技の攻撃倍率
	final static double[] ARRAY_EFFECTIVE_RATE = { 1.0, 0.0, 0.5, 2.0 };
	//技の効果メッセージ
	final static String[] ARRAY_EFFECTIVE_MSG = { "", "It's not effective...", "It's not very effective.", "It's super effective!" };
	//タイプ相性表（normal~grass）
	final int[][] ARRAY_EFFECTIVE_NUM = new int[5][5];

	int num_type; 
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
	private int hp; //体力
	private int hp_max; //最大HP
	private int exp; //経験値
	private int exp_max; //レベルアップに必要な経験値
	private Random rand; //乱数用
	private Move[] moves; //技
	private boolean fainted = false; //ひんし状態
	private boolean stuck; //特殊技による拘束
	
	//コンストラクタ
	public Pokemon() {
		this(null, User.getArrayBall()[0][0]);
	}

	public Pokemon(String owner, String ball) {
		this.num_type = 0;
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
		this.setLevel(1);
		this.setHP_max(10);
		this.setHP(this.getHP_max());
		this.setExp(0);
		this.setExp_max(10);
		this.setRand(new Random());
		this.setMoves(new Move[4]);
		//タイプ相性表の作成
		this.makeTypeStrengthChart();
	}

	//アクセサ
	public static String[] getArrayType() {
		return ARRAY_TYPE;
	}

	public static String[] getArrayStatus() {
		return ARRAY_STATUS;
	}

	public static String getFmtName() {
		return FMT_NAME;
	}

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
		//先頭文字を大文字に変換
		String str = nickname.substring(0,1).toUpperCase() + nickname.substring(1);
		if (!str.matches(FMT_NAME)) {
			System.out.println("MISS! " + str + " is not acceptable.");
			return;
		}
		this.nickname = str;
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
		String imgBall = "";
		for (int i = 0; i < User.getArrayBall().length; i++) {
			if (ball.equals(User.getArrayBall()[i][0])) {
				//ボールの名前が正しければ代入する
				imgBall = User.getArrayBall()[i][1];
			}
		}
		if (imgBall.isEmpty()) {
			System.out.println("MISS! " + ball + " is not tool to catch Pokemon.");
			return;
		}
		this.ball = imgBall;
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
		//Can Battleの場合
		if(this.getMoves() != null && this.status.equals(ARRAY_STATUS[2])){
			for(int i = 0; i < this.getMoves().length; i++){
				if(this.getMoves(i) != null){
					//命中率を元に戻す
					this.getMoves(i).setAccuracy(this.getMoves(i).getAccuracy_max());
				}
			}
		}
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
		System.out.println("\n " + this.getNickname());
		System.out.println(" ---^--------");
		System.out.println("| LEVEL UP ♪ |");
		System.out.println(" ------------");
	}

	public int getHP() {
		return this.hp;
	}

	public void setHP(int hp) {
		this.hp = hp;
		if (this.hp <= 0) {
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
		this.exp = exp;
	}
	//オーバーロード
	public void setExp(int exp_inBattle, int exp_canBattle){
		if(this.getStatus().equals(ARRAY_STATUS[1])){
			//In Battleの場合
			this.exp += exp_inBattle;
		} else if (this.getStatus().equals(ARRAY_STATUS[2])){
			//Can Battleの場合
			this.exp += exp_canBattle;
		}
		//Can't Battle以外かつ経験値がたまった場合
		if (!this.getStatus().equals(ARRAY_STATUS[0]) && this.getExp_max() - this.exp <= 0) {
			this.exp = Math.abs(this.getExp_max() - this.exp);
			this.setLevel();
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
		//拘束の効果を無くす
		this.falseStuck();
		System.out.println(this.getNickname() + " fainted.");
	}

	public void falseFainted() {
		this.fainted = false;
		this.setStatus(ARRAY_STATUS[2]);
	}

	public boolean getStuck() {
		return this.stuck;
	}

	public void trueStuck() {
		this.stuck = true;
	}

	public void falseStuck() {
		this.stuck = false;
	}

	//メゾット
	@Override
	public String toString() {
		String type = "";
		if (this.types[1] == null) {
			type = this.types[0];
		} else {
			type = this.types[0] + "][" + this.types[1];
		}
		String hp = String.format("%3d/%3d",this.hp,this.hp_max);
		if(this.getFainted()){
			hp = hp + " (FAINTED)";
		}
		String str = String.format
			("%s%s /%s Lv.%2d %s\n   Type: [%s]\n   HP:  %s\n   Ability: %s\n   Exp. Points: %3d\n   To Next Lv.: %3d",
				this.ball, this.nickname, this.name, + this.level, this.gender
					, type , hp , this.ability, this.exp , (this.exp_max - this.exp));
		return str;
	}

	//引数の生息地に住むポケモンの名前が一致するか判定する
	public boolean liveIn(String habitat){
		if(Environment.getPokemon(habitat).contains(this.getName())){
			return true;
		}
		return false;
	}

	//ステータスを確認する
	public void checkStatus() {
		System.out.println(this.toString());
	}

	//ステータスを確認する(バトル画面用)
	public String getBattleStatus() {
		String str = String.format(" %-8s/ %-8s Lv.%2d %s\n   HP:%3d/%3d", 
			this.nickname, this.name, this.level, this.gender, this.hp, this.hp_max);
		return str;
	}

	//技を確認する
	public void checkMoves(Pokemon opponent) {
		System.out.println("Current Moves:");
		for (int i = 0; i < this.getMoves().length; i++) {
			//effect: タイプ相性
			String effect = "";
			if(this.getMoves(i) != null && !this.getMoves(i).getMoveType().equals(Move.getArrayMoveType()[2])){
				//Normal~Grassの範囲
				for(int t=0; t<5; t++){
					for(int o=0; o<5; o++){
						if(t == this.getMoves(i).getNum_type() && o == opponent.num_type){
							int num = this.ARRAY_EFFECTIVE_NUM[t][o];
							effect = ARRAY_EFFECTIVE[num];
						}
					}
				}
			}
			System.out.println("[" + (i + 1) + "] " + getMoves(i) + "  " + effect);
		}
	}

	//技が使えるかの確認
	public boolean booleanMove(int num) {
		//ひんし状態の場合
		if (this.getFainted()) {
			return false;
		}
		//nullの場合
		if (this.getMoves(num) == null) {
			System.out.println("MISS! " + "Moves[" + (num + 1) + "] is null.");
			return false;
		}
		//MPが0の場合
		if (this.getMoves(num).getMP() == 0) {
			System.out.println("MISS! " + "Moves[" + (num + 1) + "] 's MP is zero.");
			return false;
		}
		return true;
	}

	//自分自身に対する技
	public void useMove(int num) {

	}

	//相手に対する技
	public void useMove(int num, Pokemon opponent) {
		if (!this.booleanMove(num)) {
			return;
		}
		if(opponent.getFainted()) {
			return;
		}
		//技のMPを1減らす
		this.getMoves(num).setMP();
		if(this.isWild()){
			System.out.print("A wild ");
		}
		System.out.println(this.getNickname() + " used " + this.getMoves(num).getName() + "!");
		//0~100までの乱数を生成
		int per = this.getRand().nextInt(101);
		//技の命中率よりも小さい値であれば攻撃が当たる
		if (per <= this.getMoves(num).getAccuracy()) {
			switch(getMoves(num).getMoveType()){
				case "Special" :
				//特殊技（作成中）
				// Fire spin:攻撃を与え続ける技
					if(opponent.getStuck()){
						//相手が既に特殊技を受けている場合
						System.out.println("But it's failed because " + opponent.getNickname() + " is already stuck.");
						break;
					} else {
						opponent.trueStuck();
					}
					//物理技に続けるためbreak;は省略
				case "Physical" :
				//物理技
					//damage: 技の威力
					int damage = this.getMoves(num).getPower();
					//effect: 技の効果
					String effect = "";
					//t: 技のタイプ
					int t = this.getMoves(num).getNum_type();
					for(int o = 0; o < 5; o++){
						//o: 技を受けるポケモンのタイプ
						if(o == opponent.num_type){
							//タイプ相性表
							int n = this.ARRAY_EFFECTIVE_NUM[t][o];
							damage *= ARRAY_EFFECTIVE_RATE[n];
							effect = ARRAY_EFFECTIVE_MSG[n];
							break;
						}
					}
					if(!effect.isEmpty()){
						System.out.println(effect);
					}
					opponent.getDamage(damage);
					break;
				case "Status" :
				//変化技（作成中）
				// Sand attack:命中率を下げる技
					for(int i = 0; i < opponent.getMoves().length; i++){
						if(opponent.getMoves(i)!=null){
							//命中率を2割下げる
							opponent.getMoves(i).setAccuracy(opponent.getMoves(i).getAccuracy() * 80 / 100);
						}
					}
					System.out.println(opponent.getName() + "\'s accuracy was low.");
					break;
			}
		} else {
			System.out.println("But it's failed!");
		}
	}

	//野生ポケモンかどうか
	public boolean isWild(){
		if(this.getBall().equals(" W ")){
			return true;
		}
		return false;
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
		if(this.isWild()){
			System.out.print("\nA wild ");
		}
		System.out.println(this.nickname + " run away.");
	}

	//回復する
	public void recover() {
		this.setHP(this.getHP_max());
		this.falseFainted();
		// this.moves.mp = this.mp_max;
		for (int i = 0; i < this.getMoves().length; i++) {
			if (this.getMoves(i) != null) {
				Move m = this.getMoves(i);
				m.setMP(m.getMP_max());
			}
		}
		this.falseFainted();
	}
	
	//タイプ相性表の作成
	public void makeTypeStrengthChart(){
		this.ARRAY_EFFECTIVE_NUM[1][1] = 2;
		this.ARRAY_EFFECTIVE_NUM[1][2] = 2;
		this.ARRAY_EFFECTIVE_NUM[1][4] = 3;
		this.ARRAY_EFFECTIVE_NUM[2][1] = 3;
		this.ARRAY_EFFECTIVE_NUM[2][2] = 2;
		this.ARRAY_EFFECTIVE_NUM[2][4] = 2;
		this.ARRAY_EFFECTIVE_NUM[3][2] = 3;
		this.ARRAY_EFFECTIVE_NUM[3][3] = 2;
		this.ARRAY_EFFECTIVE_NUM[3][4] = 2;
		this.ARRAY_EFFECTIVE_NUM[4][1] = 2;
		this.ARRAY_EFFECTIVE_NUM[4][2] = 3;
		this.ARRAY_EFFECTIVE_NUM[4][4] = 2;
	} 

	//抽象メゾット
	//進化
	abstract void evolve(int num);

}