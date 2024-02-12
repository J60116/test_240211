import java.util.*;

class User {
	String name; //名前
	private Pokemon[] pocket; //ポケモンを格納するポケット
	Pokemon[] box; //ポケモンを格納するボックス
	Scanner sc; //文字入力用

	public User() {
		this("Satoshi", new Eevee("Satoshi", "PokeBall"));
	}

	public User(String name, Pokemon pokemon) {
		this.name = name;
		this.pocket = new Pokemon[6];
		this.setPocket(0, pokemon); 
		this.box = new Pokemon[30];
		this.sc = new Scanner(System.in);
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
			System.out.println("Please catch " + pokemon.name + " using any of PokeBall.");
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

	//ニックネームをつける
	public void giveNickname(Pokemon pokemon) {
		System.out.println("Do you give " + pokemon.name + " a nickname?");
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
		String str = pokemon.name;
		if (num == 1) {
			while (true) {
				System.out.print("Nickname: ");
				str = sc.next();
				if(str.equals(pokemon.name)){
					System.out.println("ERROR >> Please input nickname except " + pokemon.name);
					sc.nextLine();
					continue;
				}
				if(str.matches(Pokemon.FMT_NAME)){
					break;
				}
			}
		}
		pokemon.setNickname(str);
		System.out.println("Pleasure to meet you, " + pokemon.getNickname() + "!");
	}

	//ポケモンを捕まえる
	public void getPokemon(Pokemon pokemon, String ball) {
		//ボールの名前に誤りがある場合
		String str = "";
		for (int i = 0; i < Pokemon.ARRAY_BALL.length; i++) {
			if (ball.equals(Pokemon.ARRAY_BALL[i][0])) {
				str = Pokemon.ARRAY_BALL[i][1];
			}
		}
		if(str.isEmpty()) {
			System.out.println(ball + " is not tool to catch Pokemon.");
			return;
		}
		//既に捕まえられている場合
		if(!pokemon.getBall().equals(Pokemon.ARRAY_BALL[0][1])) {
			if(pokemon.getOwner().equals(this.name)) {
				System.out.println(this.name + " has already caught " + pokemon.name + ".");
				return;
			} else {
				System.out.println(pokemon.name + "'s owner is " + pokemon.getOwner() + ".");
				return;
			}
		}
		pokemon.setOwner(this.name);
		pokemon.setBall(ball);
		System.out.println(this.name + " caught " + pokemon.name + "!");
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
					System.out.println(pokemon.name + " has moved into the box.");
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
					p_name = p.name;
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