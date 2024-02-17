package game.pokemon;
import java.util.ArrayList;
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
	public static final String ARRAY_HABITATS[] = { "grassland", "river" };
    // public static final String ARRAY_HABITATS[] = { "grassland", "river", "sea", "rocky mountain", "snowy mountain" , "sand dune"};
	public static final List<String> LIST_HABITATS = Arrays.asList(ARRAY_HABITATS);
    //各生息地に住むポケモン
	public static final Set<Pokemon> SET_0 = new HashSet<>();//grassland
	public static final Pokemon POKEMON_GRASSLAND[] = { new Eevee(), new Leafeon() };
	public static final Set<Pokemon> SET_1 = new HashSet<>();//river
	public static final Pokemon POKEMON_RIVER[] = { new Vaporeon() };
	//生息地マップ
	public static final Map<String, Set<Pokemon>> MAP_HABITATS = new HashMap<String, Set<Pokemon>>();
    
	//クラスメゾット
	//生息地のポケモンをランダムに１種類取得する
    public static Pokemon getPokemon(String habitat){ 
		//配列からセットを作成
		Collections.addAll(SET_0, POKEMON_GRASSLAND);   
		Collections.addAll(SET_1, POKEMON_RIVER);   
        
        //生息マップの作成
        MAP_HABITATS.put(ARRAY_HABITATS[0], SET_0);
        MAP_HABITATS.put(ARRAY_HABITATS[1], SET_1);

		//戻り値用
		Pokemon pokemon = null;
		for(Pokemon p : MAP_HABITATS.get(habitat)){
			pokemon = p;
			break;
		}
		return pokemon;		
    }
	
	//生息地の風景を取得する
	public static String[][] getView(String habitat) {
		//戻り値用
		String[][] view = new String[5][8];
		//乱数用
		Random rand = new Random();
		//生息地別に配列viewを作成
		if(habitat.equals(ARRAY_HABITATS[0])){
			//Grassland
			for(int i = 1; i <= 3; i++){
				//ポケモンが隠れている可能性がある場所を保存
				int a = rand.nextInt(2) + 1;
				int b = a + rand.nextInt(3) + 2;
				//1~6の数値を代入
				view[i][a] = "(" + (2 * i - 1) + ")";
				view[i][b] = "(" + (2 * i) + ")";
			}
			//それ以外の場所にはwwwを代入
			for(int i = 0; i < view.length; i++){	
				for(int j = 0; j < view[i].length; j++){
					if(view[i][j] == null){
						view[i][j] = "www";
					}
				}
			}
		} else if(habitat.equals(ARRAY_HABITATS[1])){
			//River
			for(int i = 1; i <= 3; i++){
				int a = rand.nextInt(2) + 1;
				int b = a + rand.nextInt(3) + 2;
				view[i][a] = "(" + (2 * i - 1) + ")";
				view[i][b] = "(" + (2 * i) + ")";
			}
			for(int i = 0; i < view.length; i++){	
				for(int j = 0; j < view[i].length; j++){
					if(i==0){
						view[i][j] = "///";
					}
					if(view[i][j] == null){
						view[i][j] = "~~~";
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