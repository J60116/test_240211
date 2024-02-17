package game.moves;
import game.pokemon.Pokemon;

public abstract class Move {
	//技の効果
	final static String[] ARRAY_EFFECTIVE_MSG = { "×Has no effect", "△Not very effective", "〇Effective",
			"◎Super effective" };
	final static double[] ARRAY_EFFECTIVE_RATE = { 0.0, 0.5, 1.0, 2.0 };
	//技のタイプ（0:物理技、1:特殊技、2:変化技）
	final static String[] ARRAY_MOVE_TYPE = { "Physical", "Special", "Status"};
	//タイプ
	final static String[] ARRAY_TYPE = { "NORMAL", "FIRE", "WATER", "ELECTRIC", "GRASS", "ICE", "FIGHTING",
			"POISON", "GROUND", "FLYING", "PSYCHIC", "BUG", "ROCK", "GHOST", "DRAGON", "DARK", "STEEL", "FAIRY" };
	//タイプ相性表
	final double[][] ARRAY_TYPE_COMPATIBILITY = new double[ARRAY_TYPE.length][ARRAY_TYPE.length];

    int num_type;//要素番号
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
		this.ARRAY_TYPE_COMPATIBILITY[2][1] = 2.0;
		this.ARRAY_TYPE_COMPATIBILITY[2][2] = 0.5;
		this.ARRAY_TYPE_COMPATIBILITY[2][4] = 0.5;
		this.ARRAY_TYPE_COMPATIBILITY[3][2] = 2.0;
		this.ARRAY_TYPE_COMPATIBILITY[3][3] = 0.5;
		this.ARRAY_TYPE_COMPATIBILITY[3][4] = 0.5;
		this.ARRAY_TYPE_COMPATIBILITY[4][1] = 0.5;
		this.ARRAY_TYPE_COMPATIBILITY[4][2] = 2.0;
		this.ARRAY_TYPE_COMPATIBILITY[4][4] = 0.5;
		for(int i=0; i<5; i++){
			for(int j=0; j<5; j++){
				if(this.ARRAY_TYPE_COMPATIBILITY[i][j]==0.0){
					this.ARRAY_TYPE_COMPATIBILITY[i][j] = 1.0;
				}
			}
		}
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