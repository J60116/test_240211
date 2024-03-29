package game.pokemon;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Collections;


public class Environment{
	//クラス変数	
    //生息地名リスト
	public static final String ARRAY_HABITATS[] = { "grassland", "river", "hill" };
    // public static final String ARRAY_HABITATS[] = { "grassland", "river", "sea", "rocky mountain", "snowy mountain" , "sand dune"};
	public static final List<String> LIST_HABITATS = Arrays.asList(ARRAY_HABITATS);
    //各生息地に住むポケモン
	public static final Set<Pokemon> SET_0 = new HashSet<>();//grassland
	public static final Pokemon POKEMON_GRASSLAND[] = { new Eevee(), new Leafeon() };
	public static final Set<Pokemon> SET_1 = new HashSet<>();//river
	public static final Pokemon POKEMON_RIVER[] = { new Vaporeon() };
	public static final Set<Pokemon> SET_2 = new HashSet<>();//hill
	public static final Pokemon POKEMON_HILL[] = { new Jolteon(), new Flareon() };
	//生息地マップ
	public static final Map<String, Set<Pokemon>> MAP_HABITATS = new HashMap<String, Set<Pokemon>>();
    
	//クラスメゾット
	//生息地のポケモンをランダムに１種類取得する
    public static Set<String> getPokemon(String habitat){ 
		//配列からセットを作成
		Collections.addAll(SET_0, POKEMON_GRASSLAND);   
		Collections.addAll(SET_1, POKEMON_RIVER);   
		Collections.addAll(SET_2, POKEMON_HILL);   
        
        //生息マップの作成
        MAP_HABITATS.put(ARRAY_HABITATS[0], SET_0);
        MAP_HABITATS.put(ARRAY_HABITATS[1], SET_1);
        MAP_HABITATS.put(ARRAY_HABITATS[2], SET_2);

		//戻り値用
		Set<String> set = new HashSet<>();
		for(Pokemon p : MAP_HABITATS.get(habitat)){
			set.add(p.getName());
		}
		return set;		
    }
	
	//生息地の風景を取得する
	public static String[][] getView(String habitat) {
		//戻り値用
		String[][] view = new String[5][8];
		//乱数用
		Random rand = new Random();
		//生息地別に配列viewを作成
		for(int i = 1; i <= 3; i++){
			//乱数を２つ用意（ポケモンが隠れている可能性のある場所）
			int a = rand.nextInt(2) + 1;
			int b = a + rand.nextInt(2) + i;
			//1~6の数値を代入
			view[i][a] = "(" + (2 * i - 1) + ")";
			view[i][b] = "(" + (2 * i) + ")";
		}
		for(int i = 0; i < view.length; i++){	
			for(int j = 0; j < view[i].length; j++){
				//数値が代入されていない場所
				if(view[i][j] == null){
					//生息地別
					switch(habitat){
						case "grassland" -> {
								view[i][j] = "www";
						}
						case "river" -> {
							if(i==0){
								view[i][j] = "///";
							} else {
								view[i][j] = "~~~";
							}
						} 
						case "hill" ->{
							if((i != 0 || i != 4) && j % 2 == 0){
								view[i][j] = "^^^";
							} else {
								view[i][j] = "   ";
							}
						}
						default -> {
							//それ以外は作成中
						}
					}
				}
			}
		}
		return view;
	}

	//風景の表示
	public static void dispView(String[][] view){
		for(int i = 0; i < view.length; i++){	
			if(i % 2 == 0){
				System.out.print(" ");
			}
			for(int j = 0; j < view[i].length; j++){
				if(j > 0){
					System.out.print(" ");
				}
				System.out.print(view[i][j]);
			}
			System.out.println();
		}
	}
}