public class Main {

	public static void main(String[] args) {
		//ポケモントレーナーの生成
		User user = new User();

		//所持しているポケモンの確認
		user.viewCurrentParty();
		
		//冒険に出かける
		user.goTo("Grassland");
		user.goTo("River");

		//所持しているポケモンの確認
		user.viewCurrentParty();

		//ポケモンセンターに行く
		user.visitPokemonCenter();

		//1番目のポケモンに「みずのいし」を渡す
		user.giveItem(0 , "Leaf Stone");
		
		//所持しているポケモンの確認
		user.viewCurrentParty();

	}
}