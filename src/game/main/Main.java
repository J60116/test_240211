package game.main;

import game.player.User;
import game.pokemon.*;

public class Main {

	public static void main(String[] args) {
		//ポケモントレーナーの生成
		User user = new User();

		//野生ポケモンの生成
		Eevee eevee = new Eevee();
		Leafeon leafeon = new Leafeon();
		Vaporeon vaporeon = new Vaporeon();
		Jolteon jolteon = new Jolteon();
		Flareon flareon = new Flareon();
		
		//所持しているポケモンの確認
		user.viewCurrentParty();
		
		//冒険に出かける
		user.goTo("flower garden", eevee);
		user.goTo("grassland", leafeon);
		user.goTo("river", vaporeon);
		user.goTo("hill", jolteon);
		user.goTo("observatory", flareon);

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