package game.moves;
import game.pokemon.Pokemon;

public abstract class Move {
	//技の効果
	final static String[] ARRAY_EFFECTIVE_MSG = { "×Has no effect", "△Not very effective", "〇Effective",
			"◎Super effective" };
	final static double[] ARRAY_EFFECTIVE_RATE = { 0.0, 0.5, 1.0, 2.0 };
	//技のタイプ（0:物理技、1:特殊技、2:変化技）
	final static String[] ARRAY_MOVE_TYPE = { "Physical", "Special", "Status"};

    private String name;
    private String type; 
    private int mp;
    private int mp_max;
    private String moveType; 
    private int power; //威力
    private int accuracy; //命中率

    public Move(){
        this.name = null;
        this.type = Pokemon.getArrayType()[0];//Normal
        this.mp_max = 0;
        this.mp = this.mp_max;
		this.moveType = ARRAY_MOVE_TYPE[0];//Physical
        this.power = 0;
        this.accuracy = 100;

	}

	public static String[] getArrayMoveType() {
		return ARRAY_MOVE_TYPE;
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

	public String getMoveType() {
		return this.moveType;
	}

	public void setMoveType(String moveType) {
		this.moveType = moveType;
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