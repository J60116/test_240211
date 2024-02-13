public abstract class Move {
    //タイプ
    final static String[] ARRAY_TYPE = { "NORMAL", "FIRE", "WATER", "ELECTRIC", "GRASS", "ICE", "FIGHTING",
			"POISON", "GROUND", "FLYING", "PSYCHIC", "BUG", "ROCK", "GHOST", "DRAGON", "DARK", "STEEL", "FAIRY" };
	//技の効果
	final static String[] ARRAY_EFFECTIVE_MSG = { "×Has no effect", "△Not very effective", "〇Effective",
			"◎Super effective" };
	final static double[] ARRAY_EFFECTIVE_RATE = { 0.0, 0.5, 1.0, 2.0 };
	
    private String name;
    private String type; 
    private int mp;
    private int mp_max;
    private int power; //威力
    private int accuracy; //命中率

    public Move(){
        this.name = null;
        this.type = ARRAY_TYPE[1];
        this.mp_max = 0;
        this.mp = this.mp_max;
        this.power = 0;
        this.accuracy = 100;
    }

    public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getMP_max() {
		return this.mp_max;
	}
	
	public void setMP_max(int mp_max) {
		this.mp_max = mp_max;
	}

    public int getMP() {
		return this.mp;
	}
	
	public void setMP(int mp) {
		this.mp = mp;
	}
	//オーバーロード
	public void setMP() {
		if(this.mp != 0) {
			this.mp -= 1;
		}
	}

    public int getPower() {
		return this.power;
	}

	public void setPower(int power) {
		this.power = power;
	}

    public int getAccuracy() {
		return this.accuracy;
	}

	public void setAccuracy(int accuracy) {
		this.accuracy = accuracy;
	}
    

    @Override
	public String toString() {
        String str = this.name + " " + "(" + this.type + ") MP:" + this.mp + "/" + this.mp_max 
        // + "\nPower:" + this.power + " Accuracy:" + this.accuracy
        ;
        return str;
    }

}