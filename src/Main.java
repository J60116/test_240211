public class Main {

	public static void main(String[] args) {
		//ポケモントレーナーの生成
		User user = new User();
	
		//いま所持しているポケモン
		user.setPocket(1, new Eevee(user.name, "PokeBall"));
		user.setPocket(2, new Vaporeon(user.name, "SuperBall"));

		//所持しているポケモンの確認
		user.viewCurrentParty();

		//ポケモンが現れた
		Pokemon eevee = new Eevee();

		//ポケモンをマスターボールで捕まえる
		user.getPokemon(eevee, "MasterBall");

		//すでに捕まえているポケモンを4番目のポケットに入れようとする
		user.setPocket(3, eevee);

		//ポケモンセンターに行く
		user.visitPokemonCenter();

		//2番目と3番目のポケモンに「みずのいし」を渡す
		user.giveItem(1 , "WaterStone");
		user.giveItem(2 , "WaterStone");

		//所持しているポケモンのステータスを見る
		user.viewCurrentParty();

	}
}