package game.player;
import java.util.*;

import game.pokemon.*;

public class User {
	//ボールの種類(0:野生、1:モンスターボール、2:スーパーボール、3:ハイパーボール、4:マスターボール)
	final static String[][] ARRAY_BALL = { { "Wild", " W " }, { "Poke Ball", "(p)" }, { "Super Ball", "(s)" },
			{ "Hyper Ball", "(h)" }, { "Master Ball", "(m)" } };
	// ボールの捕獲補正率
	// final static double[] ARRAY_BALL_RATE = { 0.0, 1.0, 1.5, 2.0, 100.0};
	private String name; //名前
	private String location; //今いる場所
	private Pokemon[] pocket; //ポケモンを格納するポケット
	private Pokemon[] box; //ポケモンを格納するボックス
	Scanner sc; //文字入力用
	private boolean battle; //バトル中かどうか
	
	public User() {
		this("Satoshi", new Eevee("Satoshi", "Poke Ball"));
	}

	public User(String name, Pokemon pokemon) {
		this.name = name;
		this.location = "Masara Town";
		this.pocket = new Pokemon[6];
		this.setPocket(0, pokemon); 
		this.box = new Pokemon[30];
		this.sc = new Scanner(System.in);
		this.battle = false;
		System.out.println(this.getName() + ", welcome to new world!\nLet's go on an adventure with " + pokemon.getName() + ".");
	}

	public static String[][] getArrayBall() {
		return ARRAY_BALL;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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
		//ボールの情報がないまま呼び出した場合
		if (pokemon.getBall() == null) {
			pokemon.setBall(ARRAY_BALL[0][0]);
			return;
		}
		//ポケモンがボールに入っていない場合
		if (pokemon.getBall().equals(ARRAY_BALL[0][1])) {
			System.out.println("Please catch " + pokemon.getName() + " using any of PokeBall.");
			return;
		}
		// for (int i = 0; i < this.getPocket().length; i++) {
		// 	if (this.getPocket()[i] != null ){
		// 		//既にポケットにいるポケモンを指定した場合
		// 		if (this.getPocket()[i].equals(pokemon)) {
		// 			System.out.println("You already set this Pokemon in your pocket.");
		// 			return;
		// 		}
		// 		//指定したポケットが空いていない場合
		// 		if (num == i) {
		// 			System.out.println("The pocket[" + num + "] is unavailable");
		// 			return;
		// 		}
		// 	}
		// }
		this.pocket[num] = pokemon;
	}
	
	public boolean getBattle() {
		return this.battle;
	}
	
	public void trueBattle() {
		this.battle = true;
		//先頭側にいるCan BattleのポケモンをIn Battleにする
		this.getCanBattlePokemon().setStatus(Pokemon.getArrayStatus()[1]);
		// for (Pokemon p : this.getPocket()) {
		// 	if (p != null && !p.getStatus().equals(Pokemon.getArrayStatus()[0])) {
		// 		p.setStatus(Pokemon.getArrayStatus()[1]);
		// 		break;
		// 	}
		// }
	}

	public void falseBattle() {
		this.battle = false;
		//In battleのポケモンをCan battleに変更
		for (Pokemon p : this.getPocket()) {
			if (p != null && p.getStatus().equals(Pokemon.getArrayStatus()[1])) {
				p.setStatus(Pokemon.getArrayStatus()[2]);
			}
		}
	}
	
	//メゾット
	public void goTo(String habitat, Pokemon pokemon){
		this.setLocation(habitat);
		System.out.println("\n" + this.getName() + " went to the " + habitat + ".");
		//行き先がポケモンの生息地かどうか(生息地であってもポケモンに会えるとは限らない)
		if(Environment.LIST_HABITATS.contains(habitat) && pokemon.liveIn(habitat)){
			this.lookFor(pokemon, Environment.getView(habitat));
		} else {
			System.out.println(this.getName() + " had a good time there.");
		}
	}

	//ポケモンを探す
	private void lookFor(Pokemon pokemon, String[][] view) {
		if(pokemon.getOwner() != null){
			if(pokemon.getOwner().equals(this.name)){
				System.out.println("MISS! " + this.getName() + " already get " + pokemon.getName());
				return;
			}
		}
		//ポケモンが実際に隠れている場所を保存
		int hide = pokemon.getRand().nextInt(6) + 1;
		int input = -1;
		while(input != hide){
			//風景の表示
			System.out.println();
			Environment.dispView(view);
			//探す場所を数字にて選択
			System.out.print("\nSelect number: ");
			try{
				input = sc.nextInt();
			} catch (InputMismatchException e){
				//数値以外が入力された場合
				System.out.println("ERROR >> Please input number.");
				sc.nextLine();
				continue;
			}
			if(input == hide){
				//選択した場所にポケモンが隠れていたらループ抜ける
				System.out.println("\nA wild " + pokemon.getName() + " has appeared!");
				break;
			} else if (input < 1 || input > 6){
				//1～6以外が入力された場合はメゾットを中断する
				System.out.println(this.name + " could not find (" + input + ")");
				System.out.println(this.name + " gave up looking for Pokemon...");
				sc.nextLine();
				return;
			} else {
				//選択した場所にポケモンが隠れていなかった場合
				boolean check = false;
				for(int i = 1; i < view.length - 1; i++){	
					for(int j = 0; j < view[i].length; j++){
						if(view[i][j].equals("(" + input + ")")){
							//選択した場所にたどりついた時
							check = true;
							//数値を非表示にする
							view[i][j] = view[4][7];
							break;
						}
					}
					if(check){
						break;
					}
				}
				System.out.println(this.getName() + " could not find Pokemon there.");
			}
		}
		//バトルを開始する
		startBattle(pokemon);
	}
	
	//相手から声を掛けられる
	public void isCalled(User opponent) {
		// System.out.println("You are challenged by " + opponent.getName());
		// Pokemon pokemon = opponent.getPocket()[0];
		// System.out.println(opponent.getName() + " sent out " + pokemon.getName() + "!");
		// startBattle(pokemon);
	}
	
	//バトル画面の表示
	private void dispBattleScreen(Pokemon enemy, Pokemon friend) {
		System.out.println("\n<<<<<<< Enemy");
		enemy.checkBattleStatus();
		System.out.println("=======");
		friend.checkBattleStatus();
		System.out.println(">>>>>>> Friend\n");
	}

	//数値入力画面の表示
	public int inputInt(int min, int max, String message){
		//入力範囲の文字列を作成
		String str = "between " + min + " and " + max;
		if(max - min == 1){
			str = min + " or " + max;
		} else if (max == min){
			str = String.valueOf(min);
		}
		//戻り値の初期値は-1とする
		int num = -1;
		while(true){
			try{
				//第３引数の文字列を表示
				System.out.print(message);
				//入力値を受け取る
				num = sc.nextInt();
				//min =< num <= max の場合、ループを抜ける
				if(num >= min && num <= max){
					break;
				}
				System.out.println("ERROR >> Please select number " + str);
			} catch (InputMismatchException e){
				//数値以外が入力された場合
				System.out.println("ERROR >> Please input number");
				sc.nextLine();
			}
		}
		//1行読み飛ばし
		sc.nextLine();
		return num;
	}

	//ポケモンバトル
	private void startBattle(Pokemon enemy){
		//対戦相手がひんし状態の場合
		if(enemy.getFainted()){
			System.out.println("MISS! " + this.getName() + " cannot start battle because Enemy fainted.");
			return;
		}
		// ポケットの中でCanBattleのポケモンを指定する
		Pokemon friend = this.getCanBattlePokemon();
		if(friend == null){
			System.out.println("MISS! " + this.getName() + " don't have Pokemon that can battle.");
			return;
		}
		this.trueBattle();
		System.out.println("Go! " + friend.getNickname() + "!");
		//バトルが終わるまで繰り返す
		while(this.getBattle()){
			if(friend.getFainted()){
				System.out.println("Will you switch your Pokemon?");
				System.out.print("");
				String msg = "[1]Switch Pokemon [2]Run : ";
				int num = this.inputInt(1,2,msg);
				if(num == 1){
					// ポケモンを入れ替える
					Pokemon substitute = this.selectPokemon();
					if(!this.booleanSwitch(friend, substitute)){
						//入れ替えに失敗した場合は敵が逃げる
						enemy.run();
						this.falseBattle();
						break;
					}
					friend = substitute;
					friend.setStatus(Pokemon.getArrayStatus()[1]);
					System.out.println("Go!" + friend.getNickname() + "!");
				} else {
					this.run();
					this.falseBattle();
					break;
				}
			}
			this.dispBattleScreen(enemy, friend);
			String msgMenu = "Menu:\n[1]Battle [2]Pokemon [3]Throw PokeBall [4]Run : ";
			int menu = this.inputInt(1,4,msgMenu);		
			switch(menu){
				case 1:
					//Battle
					//味方の技の選択
					friend.checkMoves(enemy);
					String msgMove = "What number of Move do you use?: ";
					int num_f = this.inputInt(1,4,msgMove);
					//味方の攻撃
					System.out.println("\nFriend -> Enemy");
					this.giveInstructions(friend, num_f, enemy);
					//敵の攻撃
					if(!enemy.getFainted()){
						System.out.println("\nEnemy -> Friend");
						int num_e1 = enemy.getRand().nextInt(4) + 1;
						enemy.useMove(num_e1, friend);
					}
					break;
				case 2:
					//Pokemon
					if(this.countPokemon() <= 1){
						//ポケモンの数が不足している場合
						System.out.println("MISS! " + this.getName() + " don't have enough Pokemon.");
						break;
					}
					if(this.countCanBattlePokemon() == 0){
						//Can Battleのポケモンがいない場合
						System.out.println("MISS! " + this.getName() + " don't have Pokemon that can battle.");
						break;
					}
					//入れ替え予定のポケモンを宣言
					Pokemon substitute = this.selectPokemon();
					//入れ替えが可能か確認する
					if(!this.booleanSwitch(friend, substitute)){
						break;
					}
					//入れ替え前にCan Battleに戻しておく
					friend.setStatus(Pokemon.getArrayStatus()[2]);
					//入れ替え
					friend = substitute;
					//入れ替えたポケモンをIn Battleに変更
					friend.setStatus(Pokemon.getArrayStatus()[1]);
					System.out.println(this.getName() + " sent out " + friend.getNickname() + "!");
					//敵の攻撃を受ける
					System.out.println("\nEnemy -> Friend");
					int num_e2 = enemy.getRand().nextInt(4) + 1;
					System.out.println(enemy.getName() + " is about to use Move[" + num_e2 +"].");
					enemy.useMove(num_e2, friend);
					break;
				case 3:
					//対戦相手が野生のポケモンではない場合
					if(!enemy.getBall().equals(ARRAY_BALL[0][1])){
						System.out.println("MISS! Enemy's owner is " + enemy.getOwner());
						break;
					}
					//Throw PokeBall
					//ボールの種類を入力
					System.out.println("What type of Poke Balls do you use?: ");
					String msgBall = "[1]Poke Ball [2]Super Ball [3]Hyper Ball [4]Master Ball: ";
					//モンスターボールの名前を取得
					int num = inputInt(1, 4, msgBall);
					this.throwPokeBall(enemy, num);
					if(enemy.getBall() != ARRAY_BALL[0][1]){
						//ポケモンをゲットできた場合
						this.falseBattle();;
					} else {
						//ポケモンをゲットできなかった場合
						//敵の攻撃を受ける
						System.out.println("\nEnemy -> Friend");
						int num_e3 = enemy.getRand().nextInt(4) + 1;
						System.out.println(enemy.getName() + " is about to use Move[" + num_e3 +"].");
						enemy.useMove(num_e3, friend);
					}
					break;
				case 4:
					//Run
					int num_e4 = enemy.getRand().nextInt(4) + 1;
					if(num_e4 != 1){
						//75%の確率で逃げる
						this.run();
						this.falseBattle();
					} else {
						//25%の確率で攻撃を受ける(1番目の技)
						System.out.println(this.name + " could not run away!");
						System.out.println("\nEnemy -> Friend");
						enemy.useMove(num_e4, friend);
					}
					break;
			}
			//バトルが継続している場合
			if(this.getBattle()){
				//判定（どちらかが瀕死状態になれはバトルを終わらせる）
				this.judgeBattle(friend, enemy);
			}
		}
		//手持ちのポケモンが全滅した場合
		if(this.countCanBattlePokemon() == 0){
			System.out.println("\n" + this.name + " is out of usable Pokemon.");
			System.out.println(this.name + " blacked out...");
			//ポケモンセンターで回復する
			this.visitPokemonCenter();
		}
	}

	//ポケモンに技の指示を出す
	public void giveInstructions(Pokemon pokemon, int num, Pokemon enemy){
		pokemon.useMove(num, enemy);
	}
	
	//バトル結果の判定
	private void judgeBattle(Pokemon friend, Pokemon enemy){
		if(enemy.getFainted()){
			//敵が気絶した場合
			System.out.println(this.getName() + " won the game!");
			//経験値
			int point = 5;
			System.out.println(this.getName() + "'s Party gained Exp. points.");
			for(int i = 0; i < this.getPocket().length; i++){
				if(getPocket()[i]!=null){
					getPocket()[i].setExp(point, point * 1 / 2);
				}
			}
			this.falseBattle();
		} else if(friend.getFainted()){
			//戦闘可能なポケモンを持っていない場合
			if(countCanBattlePokemon()==0){
				this.falseBattle();
			}
		}
	}

	//ポケモンを選ぶ
	private Pokemon selectPokemon() {
		//戻り値となるポケモンの宣言
		Pokemon pokemon = null;
		//所持しているポケモンの確認
		System.out.println("Current Party: ");
		for(int i = 0; i < this.getPocket().length; i++){
			if(this.getPocket()[i] != null){
				System.out.println("[" + (i + 1) + "] " + this.getPocket()[i].getNickname() + "/" + this.getPocket()[i].getName() + " (" + this.getPocket()[i].getStatus() + ")");
			} else {
				System.out.println("[" + (i + 1) + "] null");
			}
		}
		String msgSwitch = "Which Pokemon do you select?: ";
		int num = this.inputInt(1, 6, msgSwitch);
		// 選択したポケモンを戻り値として返す
		pokemon = this.getPocket()[num - 1];
		return pokemon;
	}
	
	//入れ替え可能かの判定
	private boolean booleanSwitch(Pokemon friend, Pokemon substitute){
		//入れ替えが可能か確認する
		if(substitute == null){
			//nullを選択した場合
			System.out.println("MISS! Pokemon is null.");
			return false;
		}
		if(substitute.getFainted()){
			//ひんし状態のポケモンを選択した場合
			System.out.println("MISS! " + substitute.getName() + " fainted.");
			return false;
		}
		if(substitute.equals(friend)){
			//戦闘中のポケモンを選択した場合
			System.out.println("MISS! You cannot select Pokemon in battle.");
			return false;
		}
		return true;
	}

	//戦闘中のポケモンを調べる
	public Pokemon getInBattlePokemon(){
		Pokemon pokemon = null;
		for(Pokemon p : this.getPocket()){
			if(p.getStatus().equals(Pokemon.getArrayStatus()[1])){
				pokemon = p;
				break;
			}
		}
		return pokemon;
	}

	//所持しているポケモンの数を数える
	public int countPokemon(){
		int count = 0;
		for(Pokemon p : this.getPocket()){
			if(p != null){
				count++;
			}
		}
		return count;
	}

	//対戦時に送り出すポケモン
	public Pokemon getCanBattlePokemon(){
		Pokemon pokemon = null;
		for(Pokemon p : this.getPocket()){
			if(p != null && p.getStatus().equals(Pokemon.getArrayStatus()[2])){
				pokemon = p;
				break;
			}
		}
		return pokemon;
	}

	//CanBattleなポケモンの数を数える
	public int countCanBattlePokemon(){
		int count = 0;
		for(Pokemon p : this.getPocket()){
			if(p != null && p.getStatus().equals(Pokemon.getArrayStatus()[2])){
				count++;
			}
		}
		return count;
	}

	//逃げる
	public void run() {
		System.out.println(this.getName() + " could run away smoothly!");
	}

	//ニックネームをつける
	public void giveNickname(Pokemon pokemon) {
		System.out.println("Do you give " + pokemon.getName() + " a nickname?");
		int num = this.inputInt(0, 1, "【1】YES 【0】NO : ");
		String input = pokemon.getNickname();
		if (num == 1) {
			while (true) {
				System.out.print("Nickname: ");
				input = sc.nextLine();
				String str = input.substring(0,1).toUpperCase() + input.substring(1);
				if(str.equals(pokemon.getName())){
					System.out.println("ERROR >> Please input nickname except \"" + pokemon.getName()+"\"");
					continue;
				}
				if(str.matches(Pokemon.getFmtName())){
					break;
				}
			}
		}
		pokemon.setNickname(input);
		System.out.println("Pleasure to meet you, " + pokemon.getNickname() + "!");
	}

	//ボールの名前が正しいか確認
	public boolean booleanBall(String ball){
		for (int i = 0; i < ARRAY_BALL.length; i++) {
			if (ball.equals(ARRAY_BALL[i][0])) {
				return true;
			}
		}
		System.out.println("MISS! \"" + ball + "\" is not tool to catch Pokemon.\nPlease input \"Poke Ball\", \"Super Ball\", \"Hyper Ball\" or \"Master Ball\"");
		return false;
	}
	
	//ポケモンにボールを投げる
	private void throwPokeBall(Pokemon pokemon, int num) {
		String ball = ARRAY_BALL[num][0];
		if(this.booleanBall(ball) == false){
			return;
		}
		//既に捕まえられている場合
		if(!pokemon.getBall().equals(ARRAY_BALL[0][1])) {
			if(pokemon.getOwner().equals(this.getName())) {
				System.out.println("MISS! " + this.getName() + " has already caught " + pokemon.getName() + ".");
				return;
			} else {
				System.out.println("MISS! " + pokemon.getName() + "'s owner is " + pokemon.getOwner() + ".");
				return;
			}
		}
		System.out.println("\n" + this.name + " threw " + ball + "!");
		//捕獲率
		int capture = 0;
		if(ball.equals(ARRAY_BALL[1][0])){
			//モンスターボール
			capture = pokemon.getRand().nextInt(256);
		} else if(ball.equals(ARRAY_BALL[2][0])){
			//スーパーボール
			capture = pokemon.getRand().nextInt(201);
		} else if(ball.equals(ARRAY_BALL[3][0])){
			//ハイパーボール
			capture = pokemon.getRand().nextInt(151);
		}
		if(capture > 100){
			System.out.println("It's too bad, " + this.getName() + " could catch " + pokemon.getName() + ".");
			return;
		}
		pokemon.setOwner(this.getName());
		pokemon.setBall(ball);
		System.out.println("Congratulation! " + this.getName() + " caught " + pokemon.getName() + ".");
		this.giveNickname(pokemon);		
		//ポケットに空きがある場合
		if(this.getPocket()[this.getPocket().length - 1] == null) {
			for (int i = 0; i < this.getPocket().length; i++) {
				if (this.getPocket()[i] == null) {
					this.setPocket(i, pokemon);
					System.out.println(this.getName() + " put " + pokemon.getNickname() + " in the pocket["+i+"].");
					return;
				}
			}
		} else {
			//空きがない場合はボックスに転送する
			for (int i = 0; i < this.box.length; i++) {
				if (this.box[i] == null) {
					this.box[i] = pokemon;
					System.out.println(pokemon.getName() + " has moved into the box.");
					break;
				}
			}
		}
		
		//拡張for文は使用不可
		//pはfor文の中で宣言した変数であり、pへの代入はthis.pocket自身の変更にはならない
		//		for(Pokemon p : this.pocket) {
		//			if(p==null) {
		//				p = pokemon;
		//				break;
		//			}
		//		}
	}

	//ポケモンセンターに行く
	public void visitPokemonCenter() {
		this.setLocation("Pokemon Center");
		System.out.println("\n" + this.getName() + " visited the pokemon center.");
		//ポケモンの数を数える
		int count = 0;
		String p_name = "";
		for (Pokemon p : this.getPocket()) {
			if (p != null) {
				p.recover();
				count++;
				if(count == 1) {
					p_name = p.getName();
				}
			}
		}
		System.out.print(p_name);
		//ポケモンが複数いる場合
		if(count > 1) {
			System.out.print(" and the rest of your team");
		}
		System.out.println(" should be all better now!");
		
	}

	//所持しているポケモンのステータスを見る
	public void viewCurrentParty() {
		System.out.println("\n--------------------------");
		System.out.println("POKEMON STATUS SUMMARY");
		for (Pokemon p : this.getPocket()) {
			if (p != null) {
				System.out.println("--------------------------");
				p.checkStatus();
			}
		}
		System.out.println("--------------------------\n");
	}

	//ポケモンにアイテムを持たせる
	public void giveItem(int num, String item) {
		if (this.getPocket()[num] == null) {
			System.out.println("Miss! There is no Pokemon in the pocket[" + num + "].");
			return;
		}
		if (this.getPocket()[num].getFainted()){
			System.out.println("Miss! " + this.getName() + " could not give " + item + " because " + this.getPocket()[num].getNickname() + " fainted.");
			return;
		}
		System.out.println("\n" + this.getName() + " gave " + this.getPocket()[num].getNickname() + " " + item + ".");
		this.getPocket()[num].setItem(item);
	}

}
