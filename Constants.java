
/**
 * Write a description of class Constants here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Constants
{
    public static final int WORLD_WIDTH = 850; 
    public static final int WORLD_HEIGHT = 850; 
    public static final int SQUARE_WIDTH = 50;
    public static final int SIZE = 20;
    public static final int BLANK = 0;
    public static final int SAND = 1;
    public static final int STONE = 2;
    public static final int DESTINATION = 3;
    public static final int UNMARKED_BOX = 4;
    public static final int MARKED_BOX = 5;
    public static final int PLAYER = 6;
    public static final int PLAYER_AT_DESTINATION = 7;
    public static final int START_MARKEDBOX = 8;
    
    public static final float SPEED = .08f;//make smaller for snake to go faster
    
   //make a private constructor so nobody can construct a "Constants' object
    private Constants(){}

}
