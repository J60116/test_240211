import java.util.*;

class User {
	String name; //名前
	private Pokemon[] pocket; //ポケモンを格納するポケット
	Pokemon[] box; //ポケモンを格納するボックス
	Scanner sc; //文字入力用
	boolean battle;

	public User() {
		this("Satoshi", new Eevee("Satoshi", "PokeBall"));
	}

	public User(String name, Pokemon pokemon) {
		this.name = name;
		this.pocket = new Pokemon[6];
		this.setPocket(0, pokemon); 
		this.box = new Pokemon[30];
		this.sc = new Scanner(System.in);
		this.battle = false;
	}

	public Pokemon[] getPocket() {
		return this.pocket;
	}

	public void setPocket(int num, Pokemon pokemon) {
		//ボールの情報がないまま呼び出した場合
		if (pokemon.getBall() == null) {
			pokemon.setBall(Pokemon.ARRAY_BALL[0][0]);
			return;
		}
		//ポケモンがボールに入っていない場合
		if (pokemon.getBall().equals(Pokemon.ARRAY_BALL[0][1])) {
			System.out.println("Please catch " + pokemon.getName() + " using any of PokeBall.");
			return;
		}
		for (int i = 0; i < this.getPocket().length; i++) {
			if (this.getPocket()[i] != null ){
				//既にポケットにいるポケモンを指定した場合
				if (this.getPocket()[i].equals(pokemon)) {
					System.out.println("You already set this Pokemon in your pocket.");
					return;
				}
				//指定したポケットが空いていない場合
				if (num == i) {
					System.out.println("The pocket[" + num + "] is unavailable");
					return;
				}
			}
		}
		this.pocket[num] = pokemon;
	}

	//ポケモンバトル
	public void startBattle(Pokemon enemy){
		this.battle = true;
		Pokemon friend = this.getPocket()[0];
		System.out.println("Go! " + friend.getNickname() + "!");
		while(battle){
			System.out.print("Enemy)  ");
			enemy.checkHP();
			System.out.print("Friend) ");
			friend.checkHP();
			System.out.print("[1]Battle [2]Pokemon [3]Throw PokeBall [4]Run : ");
			int menu = sc.nextInt();
			switch(menu){
				case 1:
					friend.checkMoves();
					System.out.print("What number of Moves do you use?: ");
					int num = sc.nextInt();
					//味方の攻撃
					friend.useMove(num, enemy);
					//敵の攻撃
					int r = enemy.getRand().nextInt(enemy.getMove().length) + 1;
					enemy.useMove(r, friend);
					if(enemy.fainted){
						battle = false;
						System.out.println(this.name + " won the game!");
					} else if(friend.fainted){
						battle = false;
						System.out.println(this.name + " lose the game...");
						/*ポケモンを入れ替える*/
						// System.out.println("Will you switch your Pokemon?");
						// System.out.print("[1]Switch Pokemon [2]Run : ");
					}
					break;
				case 2:
					/*ポケモンを入れ替える*/
					break;
				case 3:
					System.out.print("What type of Poke Balls do you use?: ");
					String imput = sc.next();
					this.getPokemon(enemy, imput);
					if(enemy.getBall() != Pokemon.ARRAY_BALL[0][1]){
						this.battle = false;
					}
					break;
				case 4:
					friend.run();
					this.battle = false;
					break;
			}
		}
	}

	//ニックネームをつける
	public void giveNickname(Pokemon pokemon) {
		System.out.println("Do you give " + pokemon.getName() + " a nickname?");
		int num = -1;
		while(true){
			try{
				System.out.print("【1】YES 【0】NO : ");
				num = sc.nextInt();
				//0か1が入力された場合、ループを抜ける
				if(num == 0 || num == 1){
					break;
				}
				System.out.println("ERROR >> Please input 0 or 1");
			} catch (InputMismatchException e){
				System.out.println("ERROR >> Please input number");
				sc.nextLine();
			}
		}
		String imputNickname = pokemon.getNickname();
		if (num == 1) {
			while (true) {
				System.out.print("Nickname: ");
				imputNickname = sc.next();
				if(imputNickname.equals(pokemon.getName())){
					System.out.println("ERROR >> Please input nickname except " + pokemon.getName());
					sc.nextLine();
					continue;
				}
				if(imputNickname.matches(Pokemon.FMT_NAME)){
					break;
				}
			}
		}
		pokemon.setNickname(imputNickname);
		System.out.println("Pleasure to meet you, " + pokemon.getNickname() + "!");
	}

	//ポケモンを捕まえる
	private void getPokemon(Pokemon pokemon, String ball) {
		//ボールの名前が正しいか確認
		boolean check = false;
		for (int i = 0; i < Pokemon.ARRAY_BALL.length; i++) {
			if (ball.equals(Pokemon.ARRAY_BALL[i][0])) {
				check = true;
			}
		}
		if(!check) {
			System.out.println(ball + " is not tool to catch Pokemon.");
			return;
		}
		//既に捕まえられている場合
		if(!pokemon.getBall().equals(Pokemon.ARRAY_BALL[0][1])) {
			if(pokemon.getOwner().equals(this.name)) {
				System.out.println(this.name + " has already caught " + pokemon.getName() + ".");
				return;
			} else {
				System.out.println(pokemon.getName() + "'s owner is " + pokemon.getOwner() + ".");
				return;
			}
		}
		pokemon.setOwner(this.name);
		pokemon.setBall(ball);
		System.out.println(this.name + " caught " + pokemon.getName() + "!");
		this.giveNickname(pokemon);		
		//ポケットに空きがある場合
		if(this.getPocket()[this.getPocket().length - 1] == null) {
			for (int i = 0; i < this.getPocket().length; i++) {
				if (this.getPocket()[i] == null) {
					this.setPocket(i, pokemon);
					System.out.println(this.name + " put " + pokemon.getNickname() + " in the pocket["+i+"].");
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
		System.out.println(this.name + " visited the pokemon center.");
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
		if(count>1) {
			System.out.print(" and the rest of your team");
		}
		System.out.println(" should be all better now!\n");
		
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
		if (this.getPocket()[num] != null) {
			System.out.println(this.getPocket()[num].getNickname() + " received " + item + ".");
			this.getPocket()[num].setItem(item);
		} else {
			System.out.println("There is no Pokemon in the pocket[" + num + "].");
		}
	}

}