public class Main {

	public static void main(String[] args) {
		//ポケモントレーナーの生成
		User user = new User();

		//所持しているポケモンの確認
		user.viewCurrentParty();

		//ポケモンの生成
		Pokemon eevee = new Eevee();
		Pokemon vaporeon = new Vaporeon();
		
		//ポケモンを探す
		user.lookForPokemon(eevee);
		user.lookForPokemon(vaporeon);

		//所持しているポケモンの確認
		user.viewCurrentParty();

		//ポケモンセンターに行く
		user.visitPokemonCenter();

		//1番目のポケモンに「みずのいし」を渡す
		user.giveItem(0 , "WaterStone");
		
		//所持しているポケモンの確認
		user.viewCurrentParty();

	}
}