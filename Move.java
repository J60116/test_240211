public abstract class Move {
    //タイプ
    final static String[] ARRAY_TYPE = { null, "NORMAL", "FIRE", "WATER", "ELECTRIC", "GRASS", "ICE", "FIGHTING",
			"POISON", "GROUND", "FLYING", "PSYCHIC", "BUG", "ROCK", "GHOST", "DRAGON", "DARK", "STEEL", "FAIRY" };
	//技の効果
	final static String[] ARRAY_EFFECTIVE_MSG = { "×Has no effect", "△Not very effective", "〇Effective",
			"◎Super effective" };
	final static double[] ARRAY_EFFECTIVE_RATE = { 0.0, 0.5, 1.0, 2.0 };
	
    String name;
    String type; 
    int mp;
    int mp_max;
    int power; //威力
    int accuracy; //命中率

    public Move(){
        this("None");
    }

    public Move(String name){
        this.name = name;
        type = ARRAY_TYPE[0];
        mp_max = 0;
        mp = this.mp_max;
        power = 0;
        accuracy = 100;
    }

    @Override
	public String toString() {
        String str = this.type + " " + this.name + " " + this.mp + "/" + this.mp_max 
        + "\nPower:" + this.power + "Accuracy: " + this.accuracy;
        return str;
    }

}
