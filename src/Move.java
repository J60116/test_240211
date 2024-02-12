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
    private int mp;
    int mp_max;
    int power; //威力
    int accuracy; //命中率

    public Move(){
        this.name = null;
        this.type = ARRAY_TYPE[0];
        this.mp_max = 0;
        this.mp = this.mp_max;
        this.power = 0;
        this.accuracy = 100;
    }

    public int getMP() {
		return this.mp;
	}
	
	public void setMP(int mp) {
		this.mp = mp;
		if(this.mp < 0){
			this.mp = 0;
		}
	}


    @Override
	public String toString() {
        String str = this.name + " " + "(" + this.type + ") " + this.mp + "/" + this.mp_max 
        // + "\nPower:" + this.power + " Accuracy:" + this.accuracy
        ;
        return str;
    }

}
