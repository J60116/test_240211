package game.moves;
import game.pokemon.Pokemon;

public abstract class Move {
	
	//技のタイプ（0:物理技、1:特殊技、2:変化技）
	final static String[] ARRAY_MOVE_TYPE = { "Physical", "Special", "Status"};
	//タイプ相性表（normal~grass）
	// final int[][] ARRAY_EFFECTIVE_NUM = new int[5][5];
	// final static double[] ARRAY_EFFECTIVE_RATE = { 1.0, 0.0, 0.5, 2.0 };
	
    private int num_type;//要素番号
    private String name;
    private String type; 
    private int mp;
    private int mp_max;
    private String moveType; 
    private int power; //威力
    private int accuracy; //命中率

    public Move(){
		this.num_type = 0;
        this.name = null;
        this.type = Pokemon.getArrayType()[num_type];//Normal
        this.mp_max = 0;
        this.mp = this.mp_max;
		this.moveType = ARRAY_MOVE_TYPE[0];//Physical
        this.power = 0;
        this.accuracy = 100;
		//タイプ相性表の作成（Normal~Grass）
		// this.ARRAY_EFFECTIVE_NUM[2][1] = 3;
		// this.ARRAY_EFFECTIVE_NUM[2][2] = 2;
		// this.ARRAY_EFFECTIVE_NUM[2][4] = 2;
		// this.ARRAY_EFFECTIVE_NUM[3][2] = 3;
		// this.ARRAY_EFFECTIVE_NUM[3][3] = 2;
		// this.ARRAY_EFFECTIVE_NUM[3][4] = 2;
		// this.ARRAY_EFFECTIVE_NUM[4][1] = 2;
		// this.ARRAY_EFFECTIVE_NUM[4][2] = 3;
		// this.ARRAY_EFFECTIVE_NUM[4][4] = 2;
	}

	public static String[] getArrayMoveType() {
		return ARRAY_MOVE_TYPE;
	}

	public int getNum_type() {
		return this.num_type;
	}
	
	public void setNum_type(int num_type) {
		this.num_type = num_type;
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
        String str = String.format("%-13s MP:%2d/%2d", this.name, this.mp, this.mp_max); 
        // + "\nPower:" + this.power + " Accuracy:" + this.accuracy
        ;
        return str;
    }

}