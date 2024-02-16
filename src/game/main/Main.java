package game.main;

import game.player.User;

public class Main {

	public static void main(String[] args) {
		//ポケモントレーナーの生成
		User user = new User();

		//所持しているポケモンの確認
		user.viewCurrentParty();
		
		//冒険に出かける
		user.goTo("grassland");
		user.goTo("flower garden");
		user.goTo("river");

		//所持しているポケモンの確認
		user.viewCurrentParty();

		//ポケモンセンターに行く
		user.visitPokemonCenter();

		//1番目のポケモンに「みずのいし」を渡す
		user.giveItem(0 , "Water Stone");
		
		//所持しているポケモンの確認
		user.viewCurrentParty();

	}
}