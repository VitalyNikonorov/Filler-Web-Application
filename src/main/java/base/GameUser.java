package base;

/**
 * Created by Виталий on 02.04.2015.
 */
public class GameUser {
    private final String myName;
    private String enemyName;
    private GameUser enemy;
    private int myScore = 0;
    private int enemyScore = 0;
    private int color;

    private int player = 0;
    private Integer[][] cells = new Integer[20][15];
    private Integer[][] gameField;

    public GameUser(String myName) {
        this.myName = myName;
    }

    public String getMyName() {
        return myName;
    }

    public String getEnemyName() {
        return enemyName;
    }

    public int getMyScore() {
        return myScore;
    }

    public int getEnemyScore() {
        return enemy.getMyScore();
    }

    public void setColor(int color){
        this.color = color;
    }

    public int getColor(){ return this.color; };

    public void setScore(int score){ this.myScore = score; };

    public void incrementMyScore() {
        myScore++;
    }

    public void incrementEnemyScore() {
        enemyScore++;
    }

    public void setEnemy(GameUser enemy) {
        this.enemy = enemy;
    }

    public GameUser getEnemy() {
        return this.enemy;
    }

    public void setEnemyName(String enemyName) {
        this.enemyName = enemyName;
    }

    public void setEnemyScore(int enemyScore){
        this.enemyScore = enemyScore;
    }

    public Integer[][] getGameField(){ return gameField; };

    public void setPlayer(int player){
        this.player = player;
    }

    public void setGameField(Integer[][] gameField){
        this.gameField = gameField;
    }

    public void setCells(){
        Integer[][] Mask = new Integer[cells.length][cells[0].length];
        for (int i = 0; i <  cells.length; i++){
            for(int j = 0; j <  cells[0].length; j++){
                cells[i][j] = 0;
            }
        }
        if (player == 1){
            cells[cells.length - 1][0] = 2;
        }else{
            cells[0][cells[0].length - 1] = 2;
        }


        makeBinaryMask(Mask);
        calculateCells(Mask, cells);
    }

    public void makeBinaryMask(Integer[][] Mask){
        int myColor;
        if (player == 1){
            myColor = gameField[gameField.length - 1][0];
        }else{
            myColor = gameField[0][gameField[0].length - 1];
        }

        for(int i = 0; i < gameField.length; i++){
            for(int j = 0; j < gameField[0].length; j++){
                if (gameField[i][j] == myColor){
                    Mask[i][j] = 1;
                }else{
                    Mask[i][j] = 0;
                }
            }
        }
    }

    public static void calculateCells(Integer[][] MASK, Integer[][] MYCELLS){
        int counter = 0;
        do{
            counter = 0;

            for (int i = 0; i < MASK.length ; i++){
                for(int j = 0; j < MASK[0].length; j++){
                    if (MYCELLS[i][j] == 2){
                        if( i < MYCELLS.length-1 ){
                            if ((MASK[i+1][j] == 1) && (MYCELLS[i+1][j] != 3)){
                                MYCELLS[i+1][j] = 2;
                                counter++;
                            }
                        }

                        if( i > 0 ){
                            if ((MASK[i-1][j] == 1) && (MYCELLS[i-1][j] != 3)){
                                MYCELLS[i-1][j] = 2;
                                counter++;
                            }
                        }

                        if( j < MYCELLS[0].length-1 ){
                            if ( (MASK[i][j+1] == 1) && (MYCELLS[i][j+1] != 3 ) ){
                                MYCELLS[i][j+1] = 2;
                                counter++;
                            }
                        }

                        if( j > 0 ){
                            if ((MASK[i][j-1] == 1) && (MYCELLS[i][j-1] != 3)){
                                MYCELLS[i][j-1] = 2;
                                counter++;
                            }
                        }
                        MYCELLS[i][j] = 3;
                    }
                }
            }
        }while (counter != 0);
        for (int k = 0; k < MYCELLS.length; k++){
            for(int p = 0; p < MYCELLS[0].length; p++){
                if(MYCELLS[k][p] == 3){
                    MYCELLS[k][p] = 2;
                }
            }
        }
    }

    public void move(int color){
        int counter = 0;
        do {
            counter = 0;
            for (int i = 0; i < cells.length; i++) {
                for (int j = 0; j < cells[0].length; j++) {
                    if (cells[i][j] == 2) {
                        if (i < cells.length - 1) {
                            if ((gameField[i + 1][j] == color) && (cells[i + 1][j] != 3)) {
                                cells[i + 1][j] = 2;
                                counter++;
                            }
                        }

                        if (i > 0) {
                            if ((gameField[i - 1][j] == color) && (cells[i - 1][j] != 3)) {
                                cells[i - 1][j] = 2;
                                counter++;
                            }
                        }

                        if (j < cells[0].length - 1) {
                            if ((gameField[i][j + 1] == color) && (cells[i][j + 1] != 3)) {
                                cells[i][j + 1] = 2;
                                counter++;
                            }
                        }

                        if (j > 0) {
                            if ((gameField[i][j - 1] == color) && (cells[i][j - 1] != 3)) {
                                cells[i][j - 1] = 2;
                                counter++;
                            }
                        }
                        cells[i][j] = 3;
                    }
                }
            }
        }while (counter != 0);
        int score = 0;
        for (int k = 0; k < cells.length; k++){

            for(int p = 0; p < cells[0].length; p++){
                if(cells[k][p] == 3){
                    score++;
                    gameField[k][p] = color;
                    cells[k][p] = 2;
                }
            }
        }
        myScore = score;
    }

/*    public Integer[][] inverceField(Integer[][] field){
        Integer[][] inverce = new Integer[field.length][field[0].length];
        for (int i = 0; i < field.length; i++){
            for (int j = 0; j < field[0].length; j++){
                inverce[i][j] = field[field.length - i - 1][field[0].length - j - 1];
            }
        }
        return inverce;
    }
*/
}
