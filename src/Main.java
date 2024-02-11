public class Main {

	public static void main(String[] args) {
		//ポケモントレーナーの生成
		User user = new User();
	
		//いま所持しているポケモン
		user.setPocket(1,new Eevee(user.name, "PokeBall"));
		user.setPocket(2,new Vaporeon(user.name, "SuperBall"));

		//ポケモンが現れた
		Pokemon eevee = new Eevee();
		
		//所持しているポケモンの確認
		user.viewCurrentParty();

		//ポケモンをマスターボールで捕まえる
		user.getPokemon(eevee, "MasterBall");

		//ポケモンセンターに行く
		user.visitPokemonCenter();

		//2番目と3番目のポケモンに「みずのいし」を渡す
		user.giveItem(1 , "WaterStone");
		user.giveItem(2 , "WaterStone");

		//所持しているポケモンのステータスを見る
		user.viewCurrentParty();

	}
}