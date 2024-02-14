import java.util.*;

class User {
	//ボールの種類(0:野生、1:モンスターボール、2:スーパーボール、3:マスターボール)
	final static String[][] ARRAY_BALL = { { "Wild", " W " }, { "Poke Ball", "(p)" }, { "Super Ball", "(s)" },
			{ "Hyper Ball", "(h)" }, { "Master Ball", "(m)" } };
	// ボールの捕獲補正率
	// final static double[] ARRAY_BALL_RATE = { 0.0, 1.0, 1.5, 2.0, 100.0};
	private String name; //名前
	private Pokemon[] pocket; //ポケモンを格納するポケット
	private Pokemon[] box; //ポケモンを格納するボックス
	Scanner sc; //文字入力用
	private boolean battle; //バトル中かどうか
	
	public User() {
		this("Satoshi", new Eevee("Satoshi", "Poke Ball"));
	}

	public User(String name, Pokemon pokemon) {
		this.name = name;
		this.pocket = new Pokemon[6];
		this.setPocket(0, pokemon); 
		this.box = new Pokemon[30];
		this.sc = new Scanner(System.in);
		this.battle = false;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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
		//先頭にいるポケモンの戦闘状態をIn Battleにする
		for (Pokemon p : this.getPocket()) {
			if (p != null && !p.getStatus().equals(Pokemon.ARRAY_STATUS[0])) {
				p.setStatus(Pokemon.ARRAY_STATUS[1]);
				break;
			}
		}
	}
	
	public void falseBattle() {
		this.battle = false;
		//In battleのポケモンをCan battleに変更
		for (Pokemon p : this.getPocket()) {
			if (p != null && p.getStatus().equals(Pokemon.ARRAY_STATUS[1])) {
				p.setStatus(Pokemon.ARRAY_STATUS[2]);
			}
		}
	}
	
	//メゾット
	//ポケモンを探す
	public void lookForPokemon(Pokemon pokemon) {
		//野生ポケモンでない場合
		if(!pokemon.getBall().equals(ARRAY_BALL[0][1])) {
			System.out.println("\n" + pokemon.getName() + " has owner.");
			return;
		}
		System.out.println("\n" + this.getName() + " have started looking for " + pokemon.getName() + " in the grass.");
		// 草むらの生成
		String[][] grass = new String[5][8];
		for(int i = 1; i <= 3; i++){
			//ポケモンが隠れている可能性がある場所を保存
			int a = pokemon.getRand().nextInt(2) + 1;
			int b = a + pokemon.getRand().nextInt(3) + 2;
			//草むらの中に数値を表示する
			grass[i][a] = "(" + (2 * i - 1) + ")";
			grass[i][b] = "(" + (2 * i) + ")";
		}
		//それ以外の場所にはwwwを代入
		for(int i = 0; i < grass.length; i++){	
			for(int j = 0; j < grass[i].length; j++){
				if(grass[i][j] == null){
					grass[i][j] = "www";
				}
			}
		}
		//ポケモンが実際に隠れている場所を保存
		int random = pokemon.getRand().nextInt(6) + 1;
		int input = -1;
		while(input != random){
			//草むらの表示
			System.out.println();
			for(int i = 0; i < grass.length; i++){	
				if(i % 2 == 0){
					System.out.print(" ");
				}
				for(int j = 0; j < grass[i].length; j++){
					if(j > 0){
						System.out.print(" ");
					}
					System.out.print(grass[i][j]);
				}
				System.out.println();
			}
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
			if(input == random){
				//randomと一致したらループを抜ける
				System.out.println("\nA wild " + pokemon.getName() + " has appeared!");
				break;
			} else if (input < 1 || input > 6){
				//1～6以外が入力された場合はメゾットを中断する
				System.out.println("MISS! Please select number between 1 and 6.");
				System.out.println(this.name + " gave up looking for " + pokemon.getName() + ".");
				sc.nextLine();
				return;
			} else {
				//randomと不一致の場合
				//草を刈り取ったかどうか
				boolean remove = false;
				for(int i = 1; i < grass.length - 1; i++){	
					for(int j = 0; j < grass[i].length; j++){
						//探した場所の草は刈り取る
						if(grass[i][j].equals("(" + input + ")")){
							grass[i][j] = "   ";
							remove = true;
							break;
						}
					}
					if(remove){
						break;
					}
				}
				System.out.println(pokemon.getName() + " was not there.");
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
		String str = " between " + min + " and " + max;
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
		return num;
	}

	//ポケモンバトル
	public void startBattle(Pokemon enemy){
		//対戦相手がひんし状態の場合
		if(enemy.getFainted()){
			System.out.println(this.getName() + " cannot start battle because Enemy fainted.");
			return;
		}
		//1番目のポケモンを指定する
		Pokemon friend = this.getPocket()[0];
		if(friend.getFainted()){
			System.out.println(this.getName() + " cannot start battle because " + friend.getNickname() + "fainted.");
			return;
		}
		this.trueBattle();
		System.out.println("Go! " + friend.getNickname() + "!");
		//バトルが終わるまで繰り返す
		while(this.getBattle()){
			this.dispBattleScreen(enemy, friend);
			String msgMenu = "Menu:\n[1]Battle [2]Pokemon [3]Throw PokeBall [4]Run : ";
			int menu = this.inputInt(1,4,msgMenu);		
			switch(menu){
				case 1:
					//Battle
					//味方の技の選択
					friend.checkMoves();
					String msgMove = "What number of Move do you use?: ";
					int num_f = this.inputInt(1,4,msgMove);
					//味方の攻撃
					System.out.println("\nFriend -> Enemy");
					this.giveInstructions(friend, num_f, enemy);
					//敵の攻撃
					System.out.println("\nEnemy -> Friend");
					int num_e = enemy.getRand().nextInt(4) + 1;
					enemy.useMove(num_e, friend);
					//判定（どちらかが瀕死状態になれはバトルを終わらせる）
					this.judgeBattle(friend, enemy);
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
					friend.setStatus(Pokemon.ARRAY_STATUS[2]);
					//入れ替え
					friend = substitute;
					//入れ替えたポケモンをIn Battleに変更
					friend.setStatus(Pokemon.ARRAY_STATUS[1]);
					System.out.println(this.getName() + " sent out " + friend.getNickname() + "!");
					//敵の攻撃を受ける
					System.out.println("\nEnemy -> Friend");
					num_e = enemy.getRand().nextInt(4) + 1;
					System.out.println(enemy.getName() + " is about to use Move[" + num_e +"].");
					enemy.useMove(num_e, friend);
					//判定
					this.judgeBattle(friend, enemy);
					break;
				case 3:
					//Throw PokeBall
					//ボールの種類を入力
					System.out.print("What type of Poke Balls do you use?: ");
					//1行読み飛ばし
					sc.nextLine();
					//モンスターボールの名前を取得
					String input = sc.nextLine();
					this.throwPokeBall(enemy, input);
					//判定
					if(enemy.getBall() != ARRAY_BALL[0][1]){
						this.falseBattle();;
					}
					break;
				case 4:
					//Run
					this.run();
					//判定
					this.falseBattle();
					break;
			}
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
			//In Battleのポケモンの経験値を５つ増やす
			friend.setExp(friend.getExp() + 5);
			this.falseBattle();
		} else if(friend.getFainted()){
			//味方が気絶した場合
			System.out.println(this.getName() + " lose the game...");
			this.falseBattle();
			/*ポケモンを入れ替える*/
			// System.out.println("Will you switch your Pokemon?");
			// System.out.print("[1]Switch Pokemon [2]Run : ");
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
			System.out.println("MISS! " + this.getName() + " cannot select fainted Pokemon.");
			return false;
		}
		if(substitute.equals(friend)){
			//戦闘中のポケモンを選択した場合
			System.out.println("MISS! You selected Pokemon in battle.");
			return false;
		}
		return true;
	}

	//戦闘中のポケモンを調べる
	public Pokemon getInBattlePokemon(){
		Pokemon pokemon = null;
		for(Pokemon p : this.getPocket()){
			if(p.getStatus().equals(Pokemon.ARRAY_STATUS[1])){
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

	//CanBattleなポケモンの数を数える
	public int countCanBattlePokemon(){
		int count = 0;
		for(Pokemon p : this.getPocket()){
			if(p != null && p.getStatus().equals(Pokemon.ARRAY_STATUS[2])){
				count++;
			}
		}
		return count;
	}

	//逃げる
	public void run() {
		System.out.println(this.getName() + " run away.");
	}

	//ニックネームをつける
	public void giveNickname(Pokemon pokemon) {
		System.out.println("Do you give " + pokemon.getName() + " a nickname?");
		int num = this.inputInt(0, 1, "【1】YES 【0】NO : ");
		String input = pokemon.getNickname();
		if (num == 1) {
			while (true) {
				System.out.print("Nickname: ");
				input = sc.next();
				String inputName = input.substring(0,1).toUpperCase() + input.substring(1);
				if(inputName.equals(pokemon.getName())){
					System.out.println("ERROR >> Please input nickname except \"" + pokemon.getName()+"\"");
					sc.nextLine();
				}
				if(inputName.matches(Pokemon.FMT_NAME)){
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
		System.out.println("MISS! \"" + ball + "\" is not tool to catch Pokemon.\nPlease input \"Poke Ball\", \"Super Ball\" or \"Master Ball\"");
		return false;
	}
	
	//ポケモンにボールを投げる
	private void throwPokeBall(Pokemon pokemon, String ball) {
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
		pokemon.setOwner(this.getName());
		pokemon.setBall(ball);
		System.out.println(this.getName() + " caught " + pokemon.getName() + "!");
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
		System.out.println("\n" + this.getName() + " visited the pokemon center.");
		//ポケモンの数を数える
		int count = 0;
		String p_name = "";
		for (Pokemon p : this.pocket) {
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
		System.out.println("--------------------------");
		for (Pokemon p : this.pocket) {
			if (p != null) {
				p.checkStatus();
			}
		}
		System.out.println("--------------------------\n");
	}

	//ポケモンにアイテムを持たせる
	public void giveItem(int num, String item) {
		if(this.getPocket()[num].getFainted()){
			System.out.println("Miss! " + this.name + " could not give " + item + " because " + this.getPocket()[num].getNickname() + " fainted.");
			return;
		}
		if (this.getPocket()[num] == null) {
			System.out.println("Miss! There is no Pokemon in the pocket[" + num + "].");
		} else {
			System.out.println("\n" + this.getPocket()[num].getNickname() + " received " + item + ".");
			this.getPocket()[num].setItem(item);
		}
	}

}