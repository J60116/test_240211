import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

public class Environment{

    //生息地名リスト
    public final static String ARRAY_HABITATS[] = { "Grassland", "River", "sea", "rocky mountain", "snowy mountain" };
    public final static String ARRAY_PARTS[] = { "www", "///", "sea", "rocky mountain", "snowy mountain" };
    
	public final static Map<String, Set<Pokemon>> inhabitMap = new HashMap<String, Set<Pokemon>>();; //生息地マップ
    
    public static String name; //生息地名
    public static Set<Pokemon> inhabit; //生息するポケモン
    
    public static Pokemon getPokemon(String habitat){
        
        //草原のポケモン
        Set<Pokemon> grassland = new HashSet<>();
        grassland.add(new Eevee());
//		grassland.add(new Leafeon());　//Leafia
        
        //川のポケモン
        Set<Pokemon> river = new HashSet<>();
        river.add(new Vaporeon());
        
        //生息マップの作成
        inhabitMap.put(ARRAY_HABITATS[0], grassland);
        inhabitMap.put(ARRAY_HABITATS[1], river);

		//
		Pokemon pokemon = null;
		for(Pokemon p : inhabitMap.get(habitat)){
			pokemon = p;
			break;
		}
		return pokemon;		
    }
	
	public static String[][] getView(String habitat) {
		String[][] view = new String[5][8];
		Random rand = new Random();
		if(habitat.equals(ARRAY_HABITATS[0])){
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

		}
		return view;
	}

}