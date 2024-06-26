package game.player;

import java.util.*;

import game.pokemon.*;

public class User {
	// 名前のルール
	final static String FMT_USER_NAME = "[A-Z][A-Za-z]{1,9}";
	// ボールの種類(0:野生、1:モンスターボール、2:スーパーボール、3:ハイパーボール、4:マスターボール)
	final static String[][] ARRAY_BALL = { { "Wild", " W " }, { "Poke Ball", "(p)" }, { "Super Ball", "(s)" },
			{ "Hyper Ball", "(h)" }, { "Master Ball", "(m)" } };
	// ボールの捕獲補正率
	// final static double[] ARRAY_BALL_RATE = { 0.0, 1.0, 1.5, 2.0, 100.0};
	private String name; // 名前
	private String location; // 今いる場所
	private Pokemon[] pocket; // ポケモンを格納するポケット
	private Pokemon[] box; // ポケモンを格納するボックス
	private int[] balls; // ボールの所持数
	Scanner sc; // 文字入力用
	private boolean battle; // バトル中かどうか

	// コンストラクタ
	public User() {
		this("Satoshi", new Eevee("Satoshi", ARRAY_BALL[1][0]));
	}

	public User(String name) {
		this(name, new Eevee(name, ARRAY_BALL[1][0]));
	}

	public User(String name, Pokemon pokemon) {
		this.setName(name);
		this.setLocation("Masara Town");
		this.pocket = new Pokemon[6];
		this.setPocket(0, pokemon);
		this.box = new Pokemon[30];
		this.balls = new int[4];
		this.setPokeball(10);
		this.setSuperball(3);
		this.setHyperball(1);
		this.sc = new Scanner(System.in);
		this.battle = false;
		this.dispGreet(this.getName(), pokemon);
	}

	// アクセサ
	public static String[][] getArrayBall() {
		return ARRAY_BALL;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		// 先頭文字を大文字に変換
		String str = name.substring(0, 1).toUpperCase() + name.substring(1);
		if (!str.matches(FMT_USER_NAME)) {
			System.out.println("MISS! " + str + " is not acceptable.");
			return;
		}
		this.name = str;
	}

	public String getLocation() {
		return this.location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Pokemon[] getPocket() {
		return this.pocket;
	}

	private void setPocket(int num, Pokemon pokemon) {
		// ボールの情報がないまま呼び出した場合
		if (pokemon.getBall() == null) {
			pokemon.setBall(ARRAY_BALL[0][0]);
			return;
		}
		// ポケモンがボールに入っていない場合
		if (pokemon.isWild()) {
			System.out.println("MISS! " + pokemon.getName() + " is wild Pokemon.");
			return;
		}
		this.pocket[num] = pokemon;
	}

	public int[] getBalls() {
		return this.balls;
	}

	public void setPokeball(int quantity) {
		this.balls[0] = quantity;
	}

	public void setSuperball(int quantity) {
		this.balls[1] = quantity;
	}

	public void setHyperball(int quantity) {
		this.balls[2] = quantity;
	}

	private void setMasterball() {
		this.balls[3] += 1;
	}

	private void useBalls(int num) {
		this.balls[num] -= 1;
	}

	public boolean getBattle() {
		return this.battle;
	}

	public void trueBattle() {
		this.battle = true;
		// 先頭側にいるCan BattleのポケモンをIn Battleにする
		this.getCanBattlePokemon().setStatus(Pokemon.getArrayStatus()[1]);
	}

	public void falseBattle() {
		this.battle = false;
		for (Pokemon p : this.getPocket()) {
			// In battleのポケモンを探す
			if (p != null && p.getStatus().equals(Pokemon.getArrayStatus()[1])) {
				// Can battleに変更
				p.setStatus(Pokemon.getArrayStatus()[2]);
				// 拘束の効果を消す
				p.falseStuck();
				break;
			}
		}
	}

	// メゾット
	// インスタンス生成時のあいさつ（コンストラクタ用）
	private void dispGreet(String name, Pokemon pokemon) {
		System.out.println("\n／");
		System.out.println(
				" " + name + ", welcome to new world!\n Let's go on an adventure with " + pokemon.getName() + ".");
		System.out.println("＼");
	}

	// 冒険に出かける
	public void goTo(String habitat, Pokemon pokemon) {
		this.setLocation(habitat);
		System.out.println("\n" + this.getName() + " went to the " + habitat + ".");
		// 行き先がポケモンの生息地かどうか
		if (Environment.LIST_HABITATS.contains(habitat) && pokemon.liveIn(habitat)) {
			this.lookFor(pokemon, Environment.getView(habitat));
		} else {
			System.out.println(this.getName() + " had a good time there.");
		}
	}

	// ポケモンを探す
	private void lookFor(Pokemon pokemon, String[][] view) {
		//ポケモンに飼い主がいる場合
		if (pokemon.getOwner() != null) {
			if (pokemon.getOwner().equals(this.getName())) {
				System.out.println("MISS! " + this.getName() + " already get " + pokemon.getName());
				return;
			}
		}
		// ポケモンが実際に隠れている場所を保存
		int hide = pokemon.getRand().nextInt(6) + 1;
		// マスターボールが隠れている場所を保存
		int masterBall = new java.util.Random().nextInt(6) + 1;
		// マスターボールを見つけたかどうか
		boolean lucky = false;
		int input = -1;
		while (input != hide) {
			// 風景の表示
			System.out.println();
			Environment.dispView(view);
			// 探す場所を数字にて選択
			System.out.print("\nSelect number: ");
			try {
				input = sc.nextInt();
			} catch (InputMismatchException e) {
				// 数値以外が入力された場合
				System.out.println("ERROR >> Please input number.");
				sc.nextLine();
				continue;
			}
			if (input == hide) {
				// 選択した場所にポケモンが隠れていたらループ抜ける
				break;
			} else if (input < 1 || input > 6) {
				// 1～6以外が入力された場合はメゾットを中断する
				System.out.println("(" + input + ") is not there.");
				System.out.println(this.getName() + " gave up looking for Pokemon...");
				sc.nextLine();
				return;
			} else {
				// 選択した場所にポケモンが隠れていなかった場合
				boolean check = false;
				for (int i = 1; i < view.length - 1; i++) {
					for (int j = 0; j < view[i].length; j++) {
						if (view[i][j].equals("(" + input + ")")) {
							// 選択した場所にたどりついた時
							check = true;
							// 数値を非表示にする
							view[i][j] = view[4][7];
							break;
						}
					}
					if (check) {
						break;
					}
				}
				if (!lucky && hide != masterBall && input == masterBall) {
					//マスターボールを見つけた場合
					System.out.println("\n／");
					System.out.println("  You did it!\n  " + this.getName() + " found Master Ball!");
					System.out.println("＼");
					this.setMasterball();
					lucky = true;
				} else {
					System.out.println(this.getName() + " could not find Pokemon there.");
				}
			}
		}
		// ポケモン遭遇イベント
		try {
			String[] event = new String[] { "        ", "        ", "        ", "        " };
			for (int i = 0; i < 2; i++) {
				for (String s : event) {
					System.out.print(s);
				}
				System.out.println();
				Thread.sleep(200);
				for (int j = 0; j < event.length; j++) {
					if (i % 2 == 0) {
						event[j] = ">>>>>>>>";
					} else {
						event[j] = "        ";
					}
					for (String s : event) {
						System.out.print(s);
					}
					System.out.println();
					Thread.sleep(200);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		// ポケモンが出現
		System.out.println("A wild " + pokemon.getName() + " has appeared!");
		// バトルを開始する
		startBattle(pokemon);
	}

	// 相手から声を掛けられる
	public void isCalled(User opponent) {
		// System.out.println("You are challenged by " + opponent.getName());
		// Pokemon pokemon = opponent.getPocket()[0];
		// System.out.println(opponent.getName() + " sent out " + pokemon.getName() +
		// "!");
		// startBattle(pokemon);
	}

	// バトル画面の表示
	private void dispBattleScreen(Pokemon friend, Pokemon enemy) {
		System.out.println("\n<<<<<<<<<<<<<<<<<<<<<< Enemy");
		System.out.println(enemy.getBattleStatus());
		System.out.println("============================");
		System.out.println(friend.getBattleStatus());
		System.out.println("Friend >>>>>>>>>>>>>>>>>>>>>\n");
	}

	// 数値入力画面の表示
	public int inputInt(int min, int max, String message) {
		// 引数のチェック
		if (min >= max) {
			throw new IllegalArgumentException("第2引数は第1引数よりも大きい数値を指定してください");
		}
		if (min < 0) {
			throw new IllegalArgumentException("第1引数は0以上の数値を指定してください");
		}
		// 入力範囲の文字列を作成
		String str = "between " + min + " and " + max;
		if (max - min == 1) {
			str = min + " or " + max;
		}
		// 戻り値の初期値は-1とする
		int num = -1;
		while (true) {
			try {
				// 第３引数の文字列を表示
				System.out.print(message);
				// 入力値を受け取る
				num = sc.nextInt();
				// min =< num <= max の場合、ループを抜ける
				if (num >= min && num <= max) {
					break;
				}
				System.out.println("ERROR >> Please select number " + str);
			} catch (InputMismatchException e) {
				// 数値以外が入力された場合
				System.out.println("ERROR >> Please input number");
				sc.nextLine();
			}
		}
		// 1行読み飛ばし
		sc.nextLine();
		return num;
	}

	// ポケモンバトル
	private void startBattle(Pokemon enemy) {
		// ポケモンセンターにいる場合
		if (this.getLocation().equals("Pokemon Center")) {
			System.out.println("MISS! " + this.getName() + " cannot start battle at " + this.getLocation());
			return;
		}
		// 対戦相手がひんし状態の場合
		if (enemy.getFainted()) {
			System.out.println("MISS! " + this.getName() + " cannot start battle because Enemy fainted.");
			return;
		}
		// ポケットの中でCanBattleのポケモンを指定する
		Pokemon friend = this.getCanBattlePokemon();
		if (friend == null) {
			System.out.println(
					"MISS! " + this.getName() + " cannot start battle because don't have Pokemon that can battle.");
			return;
		}
		// バトルを開始する
		this.trueBattle();
		System.out.println("Go! " + friend.getNickname() + "!");
		// バトル画面の表示
		this.dispBattleScreen(friend, enemy);
		// バトルが終わるまで繰り返す
		while (this.getBattle()) {
			if (friend.getFainted()) {
				// ポケモンを入れ替えるか確認
				System.out.println("Will you switch your Pokemon?");
				int num = this.inputInt(1, 2, "[1]Switch Pokemon [2]Run : ");
				if (num == 1) {
					// 入れ替え予定のポケモンを宣言
					Pokemon substitute = this.selectPokemon();
					if (!this.booleanSwitch(friend, substitute)) {
						// 入れ替えに失敗した場合は敵が逃げる
						enemy.run();
						// 対戦をやめる
						this.falseBattle();
						break;
					}
					System.out.println("\nCome back, " + friend.getNickname() + "!");
					friend = substitute;
					friend.setStatus(Pokemon.getArrayStatus()[1]);
					System.out.println("Go! " + friend.getNickname() + "!");
					// バトル画面の表示
					this.dispBattleScreen(friend, enemy);
				} else {
					this.run();
					break;
				}
			}
			// メニュー選択
			int menu = this.inputInt(1, 4, "Menu:\n[1]Battle [2]Pokemon [3]Throw PokeBall [4]Run : ");
			switch (menu) {
				case 1:
					// Battle
					// 味方の技の選択
					friend.checkMoves(enemy);
					int num_f = this.inputInt(1, 4, "What number of Move do you use?: ");
					// 味方の攻撃
					System.out.println("\nFriend -> Enemy");
					this.giveInstructions(friend, num_f, enemy);
					// 敵の攻撃
					if (!enemy.getFainted()) {
						System.out.println("\nEnemy -> Friend");
						int num_e1 = enemy.getRand().nextInt(4);
						enemy.useMove(num_e1, friend);
					}
					break;
				case 2:
					// Pokemon
					// メニュー選択が可能か
					if (!this.canPokemon(friend)) {
						break;
					}
					// 入れ替え予定のポケモンを宣言
					Pokemon substitute = this.selectPokemon();
					// 入れ替えが可能か確認する
					if (!this.booleanSwitch(friend, substitute)) {
						break;
					}
					// 入れ替え前にCan Battleに戻しておく
					friend.setStatus(Pokemon.getArrayStatus()[2]);
					System.out.println("Come back, " + friend.getNickname() + "!");
					// 入れ替え
					friend = substitute;
					// 入れ替えたポケモンをIn Battleに変更
					friend.setStatus(Pokemon.getArrayStatus()[1]);
					System.out.println("Go! " + friend.getNickname() + "!");
					// 敵の攻撃を受ける
					System.out.println("\nEnemy -> Friend");
					int num_e2 = enemy.getRand().nextInt(4);
					enemy.useMove(num_e2, friend);
					break;
				case 3:
					// Throw PokeBall
					// メニュー選択が可能か
					if (!this.canThrowPokeBall(enemy)) {
						break;
					}
					// モンスターボールの選択画面の表示
					this.dispBallQuantity();
					// 選択した情報の保存用
					int num_ball = -1;
					if(this.getBalls()[3]!=0){
						//マスターボールを所持している場合
						num_ball = inputInt(1, 4, "What type of Poke Balls do you use?: ");
					} else {
						//マスターボールを所持していない場合
						num_ball = inputInt(1, 3, "What type of Poke Balls do you use?: ");
					}
					this.throwPokeBall(num_ball - 1, enemy);
					if (!enemy.isWild()) {
						// ポケモンをゲットできた場合
						this.falseBattle();
					} else {
						// ポケモンをゲットできなかった場合
						// 敵の攻撃を受ける
						System.out.println("\nEnemy -> Friend");
						int num_e3 = enemy.getRand().nextInt(4);
						enemy.useMove(num_e3, friend);
					}
					break;
				case 4:
					// Run
					if (enemy.isWild() && friend.getAbility().equals("Run Away")) {
						// 特性「にげあし」の場合は必ず逃げられる
						this.run();
						break;
					}
					int num_e4 = enemy.getRand().nextInt(4);
					if (!friend.getStuck() && num_e4 != 1) {
						// 75%の確率で逃げる(stuckの場合は逃げられない)
						this.run();
					} else {
						// 25%の確率で攻撃を受ける(1番目の技)
						System.out.println(this.getName() + " could not run away!");
						System.out.println("\nEnemy -> Friend");
						enemy.useMove(num_e4, friend);
					}
					break;
			}
			System.out.println();
			// 特殊技を受けている場合、ダメージ10を受ける
			if (!enemy.getFainted() && enemy.getStuck()) {
				if (enemy.isWild()) {
					System.out.print("A wild ");
				}
				System.out.println(enemy.getNickname() + " was stuck in special move.");
				enemy.getDamage(10);
			}
			if (!friend.getFainted() && friend.getStuck()) {
				System.out.println(friend.getNickname() + " was stuck in special move.");
				friend.getDamage(10);
			}
			// バトルが継続している場合
			if (this.getBattle()) {
				// 判定（どちらかが瀕死状態になれはバトルを終わらせる）
				this.dispBattleScreen(friend, enemy);
				this.judgeBattle(friend, enemy);
			}
		}
		// 手持ちのポケモンが全滅した場合
		if (this.countCanBattlePokemon() == 0) {
			System.out.println("\n" + this.name + " is out of usable Pokemon.");
			System.out.println(this.name + " blacked out...\n");
			try {
				for (int i = 0; i < 3; i++) {
					Thread.sleep(1000);
					System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
				}
			} catch (Exception e) {
				System.out.println(e);
			}
			// ポケモンセンターで回復する
			this.visitPokemonCenter();
			try {
				Thread.sleep(3000);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}

	// ポケモンに技の指示を出す
	public void giveInstructions(Pokemon pokemon, int num_input, Pokemon enemy) {
		pokemon.useMove(num_input - 1, enemy);
	}

	// バトル結果の判定
	private void judgeBattle(Pokemon friend, Pokemon enemy) {
		if (enemy.getFainted()) {
			// 敵が気絶した場合
			System.out.println(this.getName() + " won the game!");
			// 得られる経験値(相手のレベルに比例して増加)
			int point = 5 * enemy.getLevel();
			System.out.println(this.getName() + "'s Party gained Exp. points.");
			for (int i = 0; i < this.getPocket().length; i++) {
				if (getPocket()[i] != null) {
					// In Battleのポケモンにはpoint分、Can Battleのポケモンにpointの半分を経験値を与える
					getPocket()[i].setExp(point, point * 1 / 2);
				}
			}
			this.falseBattle();
		} else if (friend.getFainted()) {
			// 味方が気絶した場合
			if (this.countCanBattlePokemon() == 0) {
				// 戦闘可能なポケモンを持っていない場合はバトルを終わらす
				this.falseBattle();
			}
		}
	}

	// ポケモンを選ぶ
	private Pokemon selectPokemon() {
		// 所持しているポケモンの確認
		System.out.println("Current Party: ");
		for (int i = 0; i < this.getPocket().length; i++) {
			if (this.getPocket()[i] != null) {
				// ポケモンがいる場合
				String name = this.getPocket()[i].getNickname() + " / " + this.getPocket()[i].getName();
				String str = String.format("[%d] %-18s( %s )",
						i + 1, name, this.getPocket()[i].getStatus());
				System.out.println(str);
			} else {
				// ポケモンがいない場合
				System.out.println("[" + (i + 1) + "] null");
			}
		}
		int num = this.inputInt(1, 6, "Which Pokemon do you select?: ");
		// 選択したポケモンを戻り値として返す
		return this.getPocket()[num - 1];
	}

	// 入れ替え可能かの判定
	private boolean booleanSwitch(Pokemon friend, Pokemon substitute) {
		// 入れ替えが可能か確認する
		if (substitute == null) {
			// nullを選択した場合
			System.out.println("MISS! Pokemon is null.");
			return false;
		}
		if (substitute.getFainted()) {
			// ひんし状態のポケモンを選択した場合
			System.out.println("MISS! " + substitute.getName() + " can't battle.");
			return false;
		}
		if (substitute.equals(friend)) {
			// 戦闘中のポケモンを選択した場合
			System.out.println("MISS! You cannot select Pokemon in battle.");
			return false;
		}
		return true;
	}

	// 各ポケモンボールの所持数
	public void dispBallQuantity() {
		System.out.println("Poke Balls:");
		String str = "";
		for (int i = 0; i < 4; i++) {
			if (i == 3 && this.getBalls()[i] == 0) {
				//マスターボールを所持していない場合はループを抜ける
				break;
			}
			str += "[" + (i + 1) + "]" + ARRAY_BALL[i + 1][0] + "(x" + this.getBalls()[i] + ") ";
		}
		System.out.println(str);
	}

	// 対戦時に送り出すポケモン
	public Pokemon getCanBattlePokemon() {
		Pokemon pokemon = null;
		for (Pokemon p : this.getPocket()) {
			if (p != null && p.getStatus().equals(Pokemon.getArrayStatus()[2])) {
				pokemon = p;
				break;
			}
		}
		return pokemon;
	}

	// 所持しているポケモンの数を数える
	public int countPokemon() {
		int count = 0;
		for (Pokemon p : this.getPocket()) {
			if (p != null) {
				count++;
			}
		}
		return count;
	}

	// CanBattleなポケモンの数を数える
	public int countCanBattlePokemon() {
		int count = 0;
		for (Pokemon p : this.getPocket()) {
			if (p != null && p.getStatus().equals(Pokemon.getArrayStatus()[2])) {
				count++;
			}
		}
		return count;
	}

	// 逃げる
	public void run() {
		this.falseBattle();
		System.out.println(this.getName() + " could run away smoothly!");
	}

	// ニックネームをつける
	public void giveNickname(Pokemon pokemon) {
		System.out.println("\nDo you give " + pokemon.getName() + " a nickname?");
		int num = this.inputInt(0, 1, "【1】YES 【0】NO : ");
		String input = pokemon.getNickname();
		if (num == 1) {
			while (true) {
				System.out.print("Nickname: ");
				input = sc.nextLine();
				if (input.isEmpty()){
					continue;
				}
				String str = input.substring(0, 1).toUpperCase() + input.substring(1);
				if (str.equals(pokemon.getName())) {
					System.out.println("ERROR >> Please input nickname except \"" + pokemon.getName() + "\"");
					continue;
				}
				if (str.matches(Pokemon.getFmtName())) {
					break;
				}
			}
		}
		pokemon.setNickname(input);
		System.out.println("Pleasure to meet you, " + pokemon.getNickname() + "!");
	}

	// ボールの名前が正しいか確認
	public boolean booleanBall(String ball) {
		for (int i = 0; i < ARRAY_BALL.length; i++) {
			if (ball.equals(ARRAY_BALL[i][0])) {
				return true;
			}
		}
		System.out.println("MISS! \"" + ball
				+ "\" is not tool to catch Pokemon.\nPlease input \"Poke Ball\", \"Super Ball\", \"Hyper Ball\" or \"Master Ball\"");
		return false;
	}

	// ポケモンにボールを投げる
	private void throwPokeBall(int num, Pokemon pokemon) {
		// ボールの数値情報を文字列に変換
		String ball = ARRAY_BALL[num + 1][0];
		// ボールの所持数が0の場合
		if (this.getBalls()[num] == 0) {
			System.out.println("MISS! " + this.getName() + " doesn't have " + ball + ".");
			return;
		}
		// ボールの所持数を１つ減らす
		this.useBalls(num);
		System.out.println("\n" + this.getName() + " threw " + ball + "!");
		// 捕獲率
		int capture = 0;
		if (ball.equals(ARRAY_BALL[1][0])) {
			// モンスターボール
			capture = pokemon.getRand().nextInt(256);
		} else if (ball.equals(ARRAY_BALL[2][0])) {
			// スーパーボール
			capture = pokemon.getRand().nextInt(201);
		} else if (ball.equals(ARRAY_BALL[3][0])) {
			// ハイパーボール
			capture = pokemon.getRand().nextInt(151);
		}
		// 相手のHPが最大HPの1/3以下の場合、捕獲率を上げる
		if (pokemon.getHP() <= pokemon.getHP_max() / 3) {
			capture -= 30;
		}
		// 相手が特殊技を受けている場合、捕獲率を上げる
		if (pokemon.getStuck()) {
			capture -= 50;
		}
		// ポケモン捕獲イベント
		try {
			// 捕まえたいポケモンの名前を表示
			System.out.println("                             [" + pokemon.getNickname() + "]");
			// 選択したポケモンボールを表示
			System.out.print("  " + ARRAY_BALL[num + 1][1]);
			for (int i = 0; i < 3; i++) {
				// ポケモンをめがけてボールが右へ移動する
				System.out.print("   >>>");
				Thread.sleep(1000);
			}
			if (capture > 100) {
				// 捕獲失敗
				System.out.print("    MISS!");
				System.out.println("\nIt's too bad, " + this.getName() + " failed to catch " + pokemon.getName() + ".");
				Thread.sleep(2000);
				return;
			} else {
				// 捕獲成功
				System.out.print("    SUCCESS!");
				Thread.sleep(1000);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
		// 所有者とボール情報の追加
		pokemon.setOwner(this.getName());
		pokemon.setBall(ball);
		System.out.println("\nCongratulations! " + this.getName() + " caught " + pokemon.getName() + ".");
		// ニックネームをつける
		this.giveNickname(pokemon);
		// ポケットまたはボックスにポケモンをいれる
		if (this.getPocket()[this.getPocket().length - 1] == null) {
			// ポケットに空きがある場合
			for (int i = 0; i < this.getPocket().length; i++) {
				if (this.getPocket()[i] == null) {
					this.setPocket(i, pokemon);
					System.out.println(this.getName() + " put " + pokemon.getNickname() + " in the pocket[" + i + "].");
					return;
				}
			}
		} else {
			// 空きがない場合はボックスに転送する
			for (int i = 0; i < this.box.length; i++) {
				if (this.box[i] == null) {
					this.box[i] = pokemon;
					System.out.println(pokemon.getName() + " has moved into the box.");
					break;
				}
			}
		}

		// 拡張for文は使用不可
		// pはfor文の中で宣言した変数であり、pへの代入はthis.pocket自身の変更にはならない
		// for(Pokemon p : this.pocket) {
		// if(p==null) {
		// p = pokemon;
		// break;
		// }
		// }
	}

	// ポケモンを入れ替えできる条件であるか確認する
	private boolean canPokemon(Pokemon pokemon) {
		if (this.countPokemon() <= 1) {
			// ポケモンの数が不足している場合
			System.out.println("MISS! " + this.getName() + " doesn't have enough Pokemon.");
			return false;
		}
		if (this.countCanBattlePokemon() == 0) {
			// Can Battleのポケモンがいない場合
			System.out.println("MISS! " + this.getName() + " doesn't have Pokemon that can battle.");
			return false;
		}
		if (pokemon.getStuck()) {
			// ポケモンがstuck状態の場合
			System.out.println("MISS! " + this.getName() + " cannot switch Pokemon because " + pokemon.getName()
					+ " is stuck in special move!");
			return false;
		}
		return true;
	}

	// ボールを投げられる条件であるか確認する
	private boolean canThrowPokeBall(Pokemon pokemon) {
		// 既に捕まえられている場合
		if (!pokemon.isWild()) {
			if (pokemon.getOwner().equals(this.getName())) {
				System.out.println("MISS! " + this.getName() + " has already caught " + pokemon.getName() + ".");
				return false;
			} else {
				System.out.println("MISS! " + pokemon.getName() + "'s owner is " + pokemon.getOwner() + ".");
				return false;
			}
		}
		// 各ボールの所持数の合計
		int quantity = this.getBalls()[0] + this.getBalls()[1] + this.getBalls()[2] + this.getBalls()[3];
		if (quantity == 0) {
			//ボールを１つも所持していない場合
			System.out.println("MISS! " + this.getName() + " doesn't have enough PokeBalls.");
			return false;
		}
		return true;
	}

	// ポケモンセンターに行く
	public void visitPokemonCenter() {
		this.setLocation("Pokemon Center");
		System.out.println("\n" + this.getName() + " visited the pokemon center.");
		// ポケモンの数を数える
		int count = 0;
		// 先頭のポケモンの名前の保存用
		String p_name = "";
		for (Pokemon p : this.getPocket()) {
			if (p != null) {
				p.recover();
				count++;
				if (count == 1) {
					p_name = p.getName();
				}
			}
		}
		// 回復完了メッセージの表示
		System.out.print(p_name);
		if (count > 1) {
			// ポケモンが複数いる場合
			System.out.print(" and the rest of your team");
		}
		System.out.println(" should be all better now!");

	}

	// 所持しているポケモンのステータスを見る
	public void viewCurrentParty() {
		System.out.println("\n------------------------------");
		System.out.println("POKEMON STATUS SUMMARY");
		for (Pokemon p : this.getPocket()) {
			if (p != null) {
				System.out.println("------------------------------");
				p.checkStatus();
			}
		}
		System.out.println("------------------------------\n");
	}

	// ポケモンにアイテムを持たせる
	public void giveItem(int num, String item) {
		if (this.getPocket()[num] == null) {
			System.out.println("Miss! There is no Pokemon in the pocket[" + num + "].");
			return;
		}
		if (this.getPocket()[num].getFainted()) {
			System.out.println("Miss! " + this.getName() + " could not give " + item + " because "
					+ this.getPocket()[num].getNickname() + " fainted.");
			return;
		}
		System.out.println("\n" + this.getName() + " gave " + this.getPocket()[num].getNickname() + " " + item + ".");
		this.getPocket()[num].setItem(item);
	}

}
