import com.badlogic.gdx.ApplicationAdapter; 
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer; 
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Rectangle; 
import com.badlogic.gdx.math.Circle; 
import com.badlogic.gdx.Input.Keys; 
import com.badlogic.gdx.math.Vector2; 
import com.badlogic.gdx.math.MathUtils; 
import com.badlogic.gdx.math.Intersector; 
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.Texture; 
import com.badlogic.gdx.InputProcessor; 
import com.badlogic.gdx.*; 
import com.badlogic.gdx.utils.Array; 
import java.util.*;
import chn.util.*;
import apcslib.*;
public class Sokoban extends ApplicationAdapter
{
    private ShapeRenderer renderer;//used to draw textures
    private OrthographicCamera camera;//the camera to our world
    private Viewport viewport;//maintains the ratios of your world 
    private BitmapFont font; //used to draw fonts (text)
    private SpriteBatch batch; //also needed to draw fonts (text)
    private GlyphLayout layout;
    private int[][] grid;
    private int[][] destinationGrid;
    private Texture brickPicture;
    private Texture unmarkedBoxPicture;
    private Texture markedBoxPicture;
    private Texture spacePicture;
    private Texture sandImage;
    private Texture character;
    private Texture destinationPic;
    private Texture playerdestinationPic;
    private Texture background;
    private Texture help;
    private Texture helpHighlight;
    private Texture helpBackground;
    private Rectangle helpButton;
    private Rectangle helpButtonHighlight;
    private Texture easy;
    private Texture easyHighlight;
    private Rectangle easyButton;
    private Texture medium;
    private Texture mediumHighlight;
    private Texture hard;
    private Texture hardHighlight;
    private Rectangle hardButton;
    private Texture play;
    private Texture playHigh;
    private Texture title;
    private Rectangle titleSquare;
    private Rectangle mediumBut;
    private Rectangle mediumHigBut;
    private Rectangle playBut;
    private Rectangle playButHigh;
    private Texture winner;
    private Rectangle winnerBut;
    private Texture winnerBackground;
    private int PlayerRow;
    private int PlayerCol;
    private ArrayList<Texture> images;
    private Gamestate gamestate;
    private Level level;

    @Override//called once when the game is started (kind of like our constructor)
    public void create(){

        camera = new OrthographicCamera(); 
        viewport = new FitViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT, camera); 

        batch = new SpriteBatch(); 
        layout = new GlyphLayout(); 
        font = new BitmapFont(); 
        renderer = new ShapeRenderer(); 
        title = new Texture(Gdx.files.internal("Sokoban Menu.png"));
        titleSquare = new Rectangle(Constants.WORLD_WIDTH / 2 - 275, 
            Constants.WORLD_HEIGHT / 2 + 96, 550, 128);
        brickPicture = new Texture(Gdx.files.internal("brickimage.png"));
        unmarkedBoxPicture = new Texture(Gdx.files.internal("unmarkedbox.png"));
        markedBoxPicture = new Texture(Gdx.files.internal("markedbox.png"));
        spacePicture = new Texture(Gdx.files.internal("space.png"));
        sandImage = new Texture(Gdx.files.internal("sand.png"));
        character = new Texture(Gdx.files.internal("assets/character.png"));
        destinationPic = new Texture(Gdx.files.internal("assets/destination.png"));
        playerdestinationPic = new Texture(Gdx.files.internal("assets/playeratdestination.png"));
        background = new Texture(Gdx.files.internal("menuBack.jpg"));
        helpBackground = new Texture(Gdx.files.internal("menuBack.jpg"));
        help = new Texture(Gdx.files.internal("Instructionbuttons.png"));
        helpHighlight = new Texture(Gdx.files.internal("InstructionButtonHighlight.png"));
        helpButton = new Rectangle(Constants.WORLD_WIDTH / 2 - 200, 
            Constants.WORLD_HEIGHT / 2 - 300, 400, 128);
        helpButtonHighlight = new Rectangle(Constants.WORLD_WIDTH / 2 - 200, 
            Constants.WORLD_HEIGHT / 2 - 300, 400, 128);
        play = new Texture(Gdx.files.internal("Play Button.png"));
        playHigh = new Texture(Gdx.files.internal("Play Button Highlight.png"));
        playBut = new Rectangle(Constants.WORLD_WIDTH / 2 -250, 
            Constants.WORLD_HEIGHT / 2 -200, 512, 190);
        playButHigh = new Rectangle(Constants.WORLD_WIDTH / 2 -250, 
            Constants.WORLD_HEIGHT / 2 -200, 512, 190);
        easy = new Texture(Gdx.files.internal("Easy.png")); 
        easyHighlight = new Texture(Gdx.files.internal("EasyHighlight.png")); 
        easyButton = new Rectangle(Constants.WORLD_WIDTH / 2 - 128, 
            Constants.WORLD_HEIGHT / 2 + 150, 256, 128);
        medium = new Texture(Gdx.files.internal("Medium.png"));
        mediumHighlight = new Texture(Gdx.files.internal("MediumHighlight.png"));
        mediumBut = new Rectangle(Constants.WORLD_WIDTH / 2 - 128, 
            Constants.WORLD_HEIGHT / 2- 50, 256, 128);
        hard = new Texture(Gdx.files.internal("Hard.png")); 
        hardHighlight = new Texture(Gdx.files.internal("HardHighlight.png")); 
        hardButton = new Rectangle(Constants.WORLD_WIDTH / 2 - 128, 
            Constants.WORLD_HEIGHT / 2 - 250, 256, 128);
        winner = new Texture(Gdx.files.internal("winner.png"));
        winnerBut = new Rectangle(Constants.WORLD_WIDTH / 2 - 128, 
            Constants.WORLD_HEIGHT / 2, 256, 128);
        winnerBackground = new Texture(Gdx.files.internal("winnerBackground.jpg"));
        grid = new int[9][8];
        destinationGrid = new int[9][8];
        destinationGrid[2][1] = Constants.PLAYER_AT_DESTINATION;
        destinationGrid[3][5] = Constants.PLAYER_AT_DESTINATION;
        destinationGrid[4][1] = Constants.PLAYER_AT_DESTINATION;
        destinationGrid[5][4] = Constants.PLAYER_AT_DESTINATION;
        destinationGrid[6][6] = Constants.PLAYER_AT_DESTINATION;
        destinationGrid[7][4] = Constants.PLAYER_AT_DESTINATION;
        destinationGrid[6][3] = Constants.PLAYER_AT_DESTINATION;
        gamestate = Gamestate.MENU;
        level = Level.NONE;
        images = new ArrayList<Texture>();
        grid[0][0] = Constants.BLANK;
        grid[0][1] = Constants.BLANK;
        grid[0][2] = Constants.STONE;
        grid[0][3] = Constants.STONE;
        grid[0][4] = Constants.STONE;
        grid[0][5] = Constants.STONE;
        grid[0][6] = Constants.STONE;
        grid[0][7] = Constants.BLANK;
        grid[1][0] = Constants.STONE;
        grid[1][1] = Constants.STONE;
        grid[1][2] = Constants.STONE;
        grid[1][3] = Constants.SAND;
        grid[1][4] = Constants.SAND;
        grid[1][5] = Constants.SAND;
        grid[1][6] = Constants.STONE;
        grid[1][7] = Constants.BLANK;
        grid[2][0] = Constants.STONE;
        grid[2][1] = Constants.DESTINATION;
        grid[2][2] = Constants.PLAYER;
        grid[2][3] = Constants.UNMARKED_BOX;
        grid[2][4] = Constants.SAND;
        grid[2][5] = Constants.SAND;
        grid[2][6] = Constants.STONE;
        grid[2][7] = Constants.BLANK;
        grid[3][0] = Constants.STONE;
        grid[3][1] = Constants.STONE;
        grid[3][2] = Constants.STONE;
        grid[3][3] = Constants.SAND;
        grid[3][4] = Constants.UNMARKED_BOX;
        grid[3][5] = Constants.DESTINATION;
        grid[3][6] = Constants.STONE;
        grid[3][7] = Constants.BLANK;
        grid[4][0] = Constants.STONE;
        grid[4][1] = Constants.DESTINATION;
        grid[4][2] = Constants.STONE;
        grid[4][3] = Constants.STONE;
        grid[4][4] = Constants.UNMARKED_BOX;
        grid[4][5] = Constants.SAND;
        grid[4][6] = Constants.STONE;
        grid[4][7] = Constants.BLANK;
        grid[5][0] = Constants.STONE;
        grid[5][1] = Constants.SAND;
        grid[5][2] = Constants.STONE;
        grid[5][3] = Constants.SAND;
        grid[5][4] = Constants.DESTINATION;
        grid[5][5] = Constants.SAND;
        grid[5][6] = Constants.STONE;
        grid[5][7] = Constants.STONE;
        grid[6][0] = Constants.STONE;
        grid[6][1] = Constants.UNMARKED_BOX;
        grid[6][2] = Constants.SAND;
        grid[6][3] = Constants.MARKED_BOX;
        grid[6][4] = Constants.UNMARKED_BOX;
        grid[6][5] = Constants.UNMARKED_BOX;
        grid[6][6] = Constants.DESTINATION;
        grid[6][7] = Constants.STONE;
        grid[7][0] = Constants.STONE;
        grid[7][1] = Constants.SAND;
        grid[7][2] = Constants.SAND;
        grid[7][3] = Constants.SAND;
        grid[7][4] = Constants.DESTINATION;
        grid[7][5] = Constants.SAND;
        grid[7][6] = Constants.SAND;
        grid[7][7] = Constants.STONE;
        grid[8][0] = Constants.STONE;
        grid[8][1] = Constants.STONE;
        grid[8][2] = Constants.STONE;
        grid[8][3] = Constants.STONE;
        grid[8][4] = Constants.STONE;
        grid[8][5] = Constants.STONE;
        grid[8][6] = Constants.STONE;
        grid[8][7] = Constants.STONE;
    }

    @Override// called 60 times a second, all the drawing is in here, or helper
    //methods that are called from here
    public void render(){
        viewport.apply();

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        if(gamestate == Gamestate.MENU)
        {
            Vector2 clickLoc = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY())); 
            batch.setProjectionMatrix(viewport.getCamera().combined);
            // backgroundSound.setVolume(1f);
            // backgroundSound.setLooping(true);
            // backgroundSound.play();
            batch.begin();
            batch.draw(background, 0, 0, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
            batch.end();
            batch.begin();
            batch.draw(title,titleSquare.x,titleSquare.y,titleSquare.width,titleSquare.height);
            if(!playBut.contains(clickLoc)){
                batch.draw(play, 
                    playBut.x, 
                    playBut.y, 
                    playBut.width, 
                    playBut.height);
            }
            else
            {
                batch.draw(playHigh, 
                    playButHigh.x, 
                    playButHigh.y, 
                    playButHigh.width, 
                    playButHigh.height);
                if(Gdx.input.justTouched())
                {
                    batch.draw(background, 0, 0, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
                    gamestate = gamestate.GAME_MENU;   
                }
            }

            if(!helpButton.contains(clickLoc)){
                batch.draw(help, 
                    helpButton.x, 
                    helpButton.y, 
                    helpButton.width, 
                    helpButton.height);
            }
            else
            {
                batch.draw(helpHighlight, 
                    helpButtonHighlight.x, 
                    helpButtonHighlight.y, 
                    helpButtonHighlight.width, 
                    helpButtonHighlight.height);
                if(Gdx.input.justTouched())
                {
                    batch.draw(background, 0, 0, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
                    gamestate = gamestate.INSTRUCTIONS;   
                }
            }
            batch.end(); 
        }

        else if(gamestate == Gamestate.INSTRUCTIONS)
        {
            font.getData().setScale(2);
            layout.setText(font, "     Lets Play Sokoban!" + 
                "\n       The objective of this game is to" +
                "\n       move every light brown box" +
                "\n       to the yellow circles which will turn it" +
                "\n       into a brown box. Use arrow keys" +
                "\n       or WASD keys to move the boxes" +
                "\n       with your player.Click the R key" + 
                "\n       to restart each level. If you beat" +
                "\n       the level,then you may proceed back" +
                "\n       to the menu and move onto a new" +
                "\n       level difficulty with the escape button." + 
                "\n       Also clicking escape at any time returns" +
                "\n       you to the menu. Have Fun!");
            batch.setProjectionMatrix(viewport.getCamera().combined);
            batch.begin();
            batch.draw(helpBackground, 0, 0, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
            font.draw(batch, layout,
                Constants.WORLD_WIDTH / 2 - layout.width / 2,
                Constants.WORLD_HEIGHT / 2 + layout.height / 2);

            batch.end();
            if(Gdx.input.isKeyJustPressed(Keys.ESCAPE))
            {
                gamestate = Gamestate.MENU; 
            }
        }
        else if(gamestate == Gamestate.GAME_MENU)
        {
            Vector2 clickLoc = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY())); 
            batch.setProjectionMatrix(viewport.getCamera().combined);
            batch.begin();
            batch.draw(background, 0, 0, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
            if(Gdx.input.isKeyJustPressed(Keys.ESCAPE))
            {
                gamestate = Gamestate.MENU;
            }
            if(!easyButton.contains(clickLoc))
            {
                batch.draw(easy, 
                    easyButton.x, 
                    easyButton.y, 
                    easyButton.width, 
                    easyButton.height);
            }
            else
            {
                batch.draw(easyHighlight, 
                    easyButton.x, 
                    easyButton.y, 
                    easyButton.width, 
                    easyButton.height);

                if(Gdx.input.justTouched())
                {
                    level = Level.EASY;
                    easyLevel();
                    gamestate = Gamestate.GAME; 
                }
            }
            if(!mediumBut.contains(clickLoc))
            {
                batch.draw(medium, 
                    mediumBut.x, 
                    mediumBut.y, 
                    mediumBut.width, 
                    mediumBut.height);
            }
            else
            {
                batch.draw(mediumHighlight, 
                    mediumBut.x, 
                    mediumBut.y, 
                    mediumBut.width, 
                    mediumBut.height);
                if(Gdx.input.justTouched())
                {
                    level = Level.MED;
                    medLevel();
                    gamestate = Gamestate.GAME; 
                }
            }
            if(!hardButton.contains(clickLoc))
            {
                batch.draw(hard, 
                    hardButton.x, 
                    hardButton.y, 
                    hardButton.width, 
                    hardButton.height);
            }
            else
            {
                batch.draw(hardHighlight, 
                    hardButton.x, 
                    hardButton.y, 
                    hardButton.width, 
                    hardButton.height);
                if(Gdx.input.justTouched())
                {
                    level = Level.HARD;
                    hardLevel();
                    gamestate = Gamestate.GAME; 
                }
            }
            batch.end();
        }
        if(gamestate == Gamestate.GAME)
        {
            if(Gdx.input.isKeyJustPressed(Keys.R))
            {
                if(level == Level.EASY)
                {
                    restartEasy();
                }
                if(level == Level.MED)
                {
                    restartMed();
                }
                if(level == Level.HARD)
                {
                    restartHard();
                }
            }
            else if(Gdx.input.isKeyJustPressed(Keys.ESCAPE))
            {
                level = Level.NONE;
                gamestate = Gamestate.GAME_MENU; 
            }
            else if(easyWin())
            {
                gamestate = Gamestate.WINNER;
            }
            else if(medWin())
            {
                gamestate = Gamestate.WINNER;
            }
            else if(hardWin())
            {
                gamestate = Gamestate.WINNER;
            }
            // grid[PlayerRow][PlayerCol] = Constants.PLAYER;
            if(Gdx.input.isKeyJustPressed(Keys.LEFT) || Gdx.input.isKeyJustPressed(Keys.A))
            {
                if(grid[PlayerRow][PlayerCol-1] != Constants.STONE)
                {

                    //Checks for unmarked box on left of player and as long as grid location to left of box is sand then it can move over as well as the player
                    if(grid[PlayerRow][PlayerCol-1] == Constants.UNMARKED_BOX && grid[PlayerRow][PlayerCol-2] == Constants.SAND && grid[PlayerRow][PlayerCol-2] != Constants.STONE && grid[PlayerRow][PlayerCol-2] != Constants.UNMARKED_BOX && grid[PlayerRow][PlayerCol-2] != Constants.MARKED_BOX && grid[PlayerRow][PlayerCol-2] != Constants.DESTINATION  && grid[PlayerRow][PlayerCol] != Constants.PLAYER_AT_DESTINATION)
                    {

                        PlayerCol--;
                        System.out.print("first");

                        grid[PlayerRow][PlayerCol+1] = Constants.SAND;
                        grid[PlayerRow][PlayerCol] = Constants.PLAYER;
                        grid[PlayerRow][PlayerCol-1] = Constants.UNMARKED_BOX;

                    }
                    else if(destinationGrid[PlayerRow][PlayerCol] == Constants.PLAYER_AT_DESTINATION && grid[PlayerRow][PlayerCol-1] == Constants.UNMARKED_BOX && grid[PlayerRow][PlayerCol-2] == Constants.DESTINATION)
                    {
                        grid[PlayerRow][PlayerCol-1] = Constants.PLAYER;
                        grid[PlayerRow][PlayerCol] = Constants.DESTINATION;
                        grid[PlayerRow][PlayerCol-2] = Constants.MARKED_BOX;
                        PlayerCol--;
                    }
                    else if(destinationGrid[PlayerRow][PlayerCol] == Constants.PLAYER_AT_DESTINATION && grid[PlayerRow][PlayerCol-1] == Constants.UNMARKED_BOX && grid[PlayerRow][PlayerCol-2] == Constants.SAND && grid[PlayerRow][PlayerCol-2]!= Constants.DESTINATION && grid[PlayerRow][PlayerCol-2] != Constants.UNMARKED_BOX && grid[PlayerRow][PlayerCol-2] != Constants.MARKED_BOX)
                    {
                        grid[PlayerRow][PlayerCol] = Constants.DESTINATION;
                        grid[PlayerRow][PlayerCol-1] = Constants.PLAYER;
                        grid[PlayerRow][PlayerCol-2] = Constants.UNMARKED_BOX;
                        PlayerCol--;
                    }
                    else if(destinationGrid[PlayerRow][PlayerCol] == Constants.PLAYER_AT_DESTINATION && grid[PlayerRow][PlayerCol-1] == Constants.UNMARKED_BOX && grid[PlayerRow][PlayerCol-2] == Constants.DESTINATION && grid[PlayerRow][PlayerCol-2]!= Constants.SAND && grid[PlayerRow][PlayerCol-2] != Constants.UNMARKED_BOX && grid[PlayerRow][PlayerCol-2] != Constants.MARKED_BOX)
                    {
                        grid[PlayerRow][PlayerCol] = Constants.DESTINATION;
                        grid[PlayerRow][PlayerCol-1] = Constants.PLAYER;
                        grid[PlayerRow][PlayerCol-2] = Constants.MARKED_BOX;
                        PlayerCol--;
                    }
                    //checks same as above but if to the left is marked box, if you move it chnage it back to a normal unmarked box
                    else if(grid[PlayerRow][PlayerCol-1] == Constants.MARKED_BOX && grid[PlayerRow][PlayerCol-2] == Constants.SAND && grid[PlayerRow][PlayerCol-2] != Constants.STONE && grid[PlayerRow][PlayerCol-2] != Constants.UNMARKED_BOX && grid[PlayerRow][PlayerCol-2] != Constants.MARKED_BOX && grid[PlayerRow][PlayerCol-2] != Constants.DESTINATION)
                    {
                        System.out.print("second");
                        PlayerCol--;
                        grid[PlayerRow][PlayerCol+1] = Constants.SAND;
                        grid[PlayerRow][PlayerCol] = Constants.PLAYER_AT_DESTINATION;
                        grid[PlayerRow][PlayerCol-1] = Constants.UNMARKED_BOX;
                    }
                    else if(grid[PlayerRow][PlayerCol-1] == Constants.MARKED_BOX && grid[PlayerRow][PlayerCol-2] == Constants.DESTINATION && grid[PlayerRow][PlayerCol-2] != Constants.SAND && grid[PlayerRow][PlayerCol-2] != Constants.STONE && grid[PlayerRow][PlayerCol-2] != Constants.UNMARKED_BOX && grid[PlayerRow][PlayerCol-2] != Constants.MARKED_BOX)
                    {
                        grid[PlayerRow][PlayerCol] = Constants.SAND;
                        grid[PlayerRow][PlayerCol-1] = Constants.PLAYER_AT_DESTINATION;
                        grid[PlayerRow][PlayerCol-2] = Constants.MARKED_BOX;
                        PlayerCol--;
                    }
                    //checking if space to left of box is destination and if so change it to a marked box
                    else if(grid[PlayerRow][PlayerCol-1] == Constants.UNMARKED_BOX && grid[PlayerRow][PlayerCol-2] == Constants.DESTINATION)
                    {
                        System.out.print("third");
                        PlayerCol--;
                        grid[PlayerRow][PlayerCol+1] = Constants.SAND;
                        grid[PlayerRow][PlayerCol] = Constants.PLAYER;
                        grid[PlayerRow][PlayerCol-1] = Constants.MARKED_BOX;
                    }
                    else if((destinationGrid[PlayerRow][PlayerCol] == Constants.PLAYER_AT_DESTINATION && grid[PlayerRow][PlayerCol-1] == Constants.SAND && grid[PlayerRow][PlayerCol-1] != Constants.DESTINATION && grid[PlayerRow][PlayerCol-1] != Constants.UNMARKED_BOX && grid[PlayerRow][PlayerCol-1] != Constants.MARKED_BOX))
                    {
                        grid[PlayerRow][PlayerCol-1] = Constants.PLAYER;
                        grid[PlayerRow][PlayerCol] = Constants.DESTINATION;
                        PlayerCol--;
                    }
                    else if((destinationGrid[PlayerRow][PlayerCol] == Constants.PLAYER_AT_DESTINATION && grid[PlayerRow][PlayerCol-1] == Constants.DESTINATION && grid[PlayerRow][PlayerCol-1] != Constants.SAND && grid[PlayerRow][PlayerCol-1] != Constants.UNMARKED_BOX && grid[PlayerRow][PlayerCol-1] != Constants.MARKED_BOX))
                    {
                        grid[PlayerRow][PlayerCol-1] = Constants.PLAYER_AT_DESTINATION;
                        grid[PlayerRow][PlayerCol] = Constants.DESTINATION;
                        PlayerCol--;
                    }
                    else if(destinationGrid[PlayerRow][PlayerCol] == Constants.PLAYER_AT_DESTINATION & grid[PlayerRow][PlayerCol-1] == Constants.MARKED_BOX && grid[PlayerRow][PlayerCol-2] == Constants.SAND && grid[PlayerRow][PlayerCol-2]!= Constants.DESTINATION && grid[PlayerRow][PlayerCol-2] != Constants.UNMARKED_BOX && grid[PlayerRow][PlayerCol-2] != Constants.MARKED_BOX)
                    {
                        grid[PlayerRow][PlayerCol-1] = Constants.PLAYER;
                        grid[PlayerRow][PlayerCol] = Constants.DESTINATION;
                        grid[PlayerRow][PlayerCol-2] = Constants.UNMARKED_BOX;
                        PlayerCol--;
                    }
                    //checks if left of player is empty/sand and moves it over
                    else if(grid[PlayerRow][PlayerCol-1] == Constants.SAND)
                    {
                        System.out.print("sand");
                        PlayerCol--;
                        grid[PlayerRow][PlayerCol] = Constants.PLAYER;
                        grid[PlayerRow][PlayerCol+1] = Constants.SAND;
                    }
                    else if(grid[PlayerRow][PlayerCol-1] == Constants.DESTINATION)
                    {
                        System.out.print("destination");
                        grid[PlayerRow][PlayerCol] = Constants.SAND;

                        grid[PlayerRow][PlayerCol-1] = Constants.PLAYER_AT_DESTINATION;
                        PlayerCol--;
                        System.out.print("Hi");
                        //add picture later
                    }
                    //checks if left of player is destination and then you have the desitnation circle over player

                }
                printDebug();
            }
            //right direction
            if(Gdx.input.isKeyJustPressed(Keys.RIGHT) || Gdx.input.isKeyJustPressed(Keys.D))
            {
                if(grid[PlayerRow][PlayerCol+1] != Constants.STONE)
                {

                    //Checks for unmarked box on left of player and as long as grid location to left of box is sand then it can move over as well as the player
                    if(grid[PlayerRow][PlayerCol+1] == Constants.UNMARKED_BOX && grid[PlayerRow][PlayerCol+2] == Constants.SAND && grid[PlayerRow][PlayerCol+2] != Constants.STONE && grid[PlayerRow][PlayerCol+2] != Constants.UNMARKED_BOX && grid[PlayerRow][PlayerCol+2] != Constants.MARKED_BOX && grid[PlayerRow][PlayerCol+2] != Constants.DESTINATION && grid[PlayerRow][PlayerCol] != Constants.PLAYER_AT_DESTINATION)
                    {
                        PlayerCol++;
                        grid[PlayerRow][PlayerCol-1] = Constants.SAND;
                        grid[PlayerRow][PlayerCol] = Constants.PLAYER;
                        grid[PlayerRow][PlayerCol+1] = Constants.UNMARKED_BOX;
                    }
                    else if(destinationGrid[PlayerRow][PlayerCol] == Constants.PLAYER_AT_DESTINATION && grid[PlayerRow][PlayerCol+1] == Constants.UNMARKED_BOX && grid[PlayerRow][PlayerCol+2] == Constants.DESTINATION)
                    {
                        grid[PlayerRow][PlayerCol+1] = Constants.PLAYER;
                        grid[PlayerRow][PlayerCol] = Constants.DESTINATION;
                        grid[PlayerRow][PlayerCol+2] = Constants.MARKED_BOX;
                        PlayerCol++;
                    }
                    else if(destinationGrid[PlayerRow][PlayerCol] == Constants.PLAYER_AT_DESTINATION & grid[PlayerRow][PlayerCol+1] == Constants.MARKED_BOX && grid[PlayerRow][PlayerCol+2] == Constants.SAND && grid[PlayerRow][PlayerCol+2]!= Constants.DESTINATION && grid[PlayerRow][PlayerCol+2] != Constants.UNMARKED_BOX && grid[PlayerRow][PlayerCol+2] != Constants.MARKED_BOX)
                    {
                        grid[PlayerRow][PlayerCol+1] = Constants.PLAYER;
                        grid[PlayerRow][PlayerCol] = Constants.DESTINATION;
                        grid[PlayerRow][PlayerCol+2] = Constants.UNMARKED_BOX;
                        PlayerCol++;
                    }
                    else if(destinationGrid[PlayerRow][PlayerCol] == Constants.PLAYER_AT_DESTINATION & grid[PlayerRow][PlayerCol+1] == Constants.MARKED_BOX && grid[PlayerRow][PlayerCol+2] == Constants.DESTINATION && grid[PlayerRow][PlayerCol+2]!= Constants.SAND && grid[PlayerRow][PlayerCol+2] != Constants.UNMARKED_BOX && grid[PlayerRow][PlayerCol+2] != Constants.MARKED_BOX)
                    {
                        grid[PlayerRow][PlayerCol+1] = Constants.PLAYER;
                        grid[PlayerRow][PlayerCol] = Constants.DESTINATION;
                        grid[PlayerRow][PlayerCol+2] = Constants.MARKED_BOX;
                        PlayerCol++;
                    }
                    else if(destinationGrid[PlayerRow][PlayerCol] == Constants.PLAYER_AT_DESTINATION && grid[PlayerRow][PlayerCol+1] == Constants.UNMARKED_BOX && grid[PlayerRow][PlayerCol+2] == Constants.SAND && grid[PlayerRow][PlayerCol+2]!= Constants.DESTINATION && grid[PlayerRow][PlayerCol+2] != Constants.UNMARKED_BOX && grid[PlayerRow][PlayerCol+2] != Constants.MARKED_BOX)
                    {
                        grid[PlayerRow][PlayerCol] = Constants.DESTINATION;
                        grid[PlayerRow][PlayerCol+1] = Constants.PLAYER;
                        grid[PlayerRow][PlayerCol+2] = Constants.UNMARKED_BOX;
                        PlayerCol++;
                    }
                    //checks same as above but if to the left is marked box, if you move it chnage it back to a normal unmarked box
                    else if(grid[PlayerRow][PlayerCol+1] == Constants.MARKED_BOX && grid[PlayerRow][PlayerCol+2] == Constants.SAND && grid[PlayerRow][PlayerCol+2] != Constants.STONE && grid[PlayerRow][PlayerCol+2] != Constants.UNMARKED_BOX && grid[PlayerRow][PlayerCol+2] != Constants.MARKED_BOX && grid[PlayerRow][PlayerCol+2] != Constants.DESTINATION)
                    {
                        PlayerCol++;
                        grid[PlayerRow][PlayerCol-1] = Constants.SAND;
                        grid[PlayerRow][PlayerCol] = Constants.PLAYER_AT_DESTINATION;
                        grid[PlayerRow][PlayerCol+1] = Constants.UNMARKED_BOX;
                    }
                    else if(grid[PlayerRow][PlayerCol+1] == Constants.MARKED_BOX && grid[PlayerRow][PlayerCol+2] == Constants.DESTINATION && grid[PlayerRow][PlayerCol+2] != Constants.SAND && grid[PlayerRow][PlayerCol+2] != Constants.STONE && grid[PlayerRow][PlayerCol+2] != Constants.UNMARKED_BOX && grid[PlayerRow][PlayerCol+2] != Constants.MARKED_BOX)
                    {
                        grid[PlayerRow][PlayerCol] = Constants.SAND;
                        grid[PlayerRow][PlayerCol+1] = Constants.PLAYER_AT_DESTINATION;
                        grid[PlayerRow][PlayerCol+2] = Constants.MARKED_BOX;
                        PlayerCol++;
                    }
                    //checking if space to left of box is destination and if so change it to a marked box
                    else if(grid[PlayerRow][PlayerCol+1] == Constants.UNMARKED_BOX && grid[PlayerRow][PlayerCol+2] == Constants.DESTINATION)
                    {
                        PlayerCol++;
                        grid[PlayerRow][PlayerCol-1] = Constants.SAND;
                        grid[PlayerRow][PlayerCol] = Constants.PLAYER;
                        grid[PlayerRow][PlayerCol+1] = Constants.MARKED_BOX;
                    }
                    else if((destinationGrid[PlayerRow][PlayerCol] == Constants.PLAYER_AT_DESTINATION && grid[PlayerRow][PlayerCol+1] == Constants.SAND && grid[PlayerRow][PlayerCol+1] != Constants.DESTINATION && grid[PlayerRow][PlayerCol+1] != Constants.UNMARKED_BOX && grid[PlayerRow][PlayerCol+1] != Constants.MARKED_BOX))
                    {
                        grid[PlayerRow][PlayerCol+1] = Constants.PLAYER;
                        grid[PlayerRow][PlayerCol] = Constants.DESTINATION;
                        PlayerCol++;
                    }
                    else if((destinationGrid[PlayerRow][PlayerCol] == Constants.PLAYER_AT_DESTINATION && grid[PlayerRow][PlayerCol+1] == Constants.DESTINATION && grid[PlayerRow][PlayerCol+1] != Constants.SAND && grid[PlayerRow][PlayerCol+1] != Constants.UNMARKED_BOX && grid[PlayerRow][PlayerCol+1] != Constants.MARKED_BOX))
                    {
                        grid[PlayerRow][PlayerCol+1] = Constants.PLAYER_AT_DESTINATION;
                        grid[PlayerRow][PlayerCol] = Constants.DESTINATION;
                        PlayerCol++;
                    }
                    //
                    //checks if left of player is empty/sand and moves it over
                    else if(grid[PlayerRow][PlayerCol+1] == Constants.SAND)
                    {
                        PlayerCol++;
                        System.out.print("Hi");
                        grid[PlayerRow][PlayerCol] = Constants.PLAYER;
                        grid[PlayerRow][PlayerCol-1] = Constants.SAND;
                    }
                    //checks if left of player is destination and then you have the desitnation circle over player
                    else if(grid[PlayerRow][PlayerCol+1] == Constants.DESTINATION)
                    {
                        grid[PlayerRow][PlayerCol] = Constants.SAND;
                        grid[PlayerRow][PlayerCol+1] = Constants.PLAYER_AT_DESTINATION;
                        PlayerCol++;
                        //add picture later
                    }

                }
                printDebug();
            }
            //up
            if(Gdx.input.isKeyJustPressed(Keys.UP) || Gdx.input.isKeyJustPressed(Keys.W))
            {
                if(grid[PlayerRow-1][PlayerCol] != Constants.STONE)
                {

                    //Checks for unmarked box above  player and as long as grid location above box is sand then it can move up as well as the player
                    if(grid[PlayerRow-1][PlayerCol] == Constants.UNMARKED_BOX && grid[PlayerRow-2][PlayerCol] == Constants.SAND && grid[PlayerRow-2][PlayerCol] != Constants.STONE && grid[PlayerRow-2][PlayerCol] != Constants.UNMARKED_BOX && grid[PlayerRow-2][PlayerCol] != Constants.MARKED_BOX && grid[PlayerRow-2][PlayerCol] != Constants.DESTINATION && grid[PlayerRow][PlayerCol] != Constants.PLAYER_AT_DESTINATION)
                    {
                        PlayerRow--;
                        grid[PlayerRow+1][PlayerCol] = Constants.SAND;
                        grid[PlayerRow][PlayerCol] = Constants.PLAYER;
                        grid[PlayerRow-1][PlayerCol] = Constants.UNMARKED_BOX;
                    }
                    else if(destinationGrid[PlayerRow][PlayerCol] == Constants.PLAYER_AT_DESTINATION && grid[PlayerRow-1][PlayerCol] == Constants.UNMARKED_BOX && grid[PlayerRow-2][PlayerCol] == Constants.DESTINATION)
                    {
                        grid[PlayerRow-1][PlayerCol] = Constants.PLAYER;
                        grid[PlayerRow][PlayerCol] = Constants.DESTINATION;
                        grid[PlayerRow-2][PlayerCol] = Constants.MARKED_BOX;
                        PlayerRow--;
                    }
                    else if(destinationGrid[PlayerRow][PlayerCol] == Constants.PLAYER_AT_DESTINATION & grid[PlayerRow-1][PlayerCol] == Constants.MARKED_BOX && grid[PlayerRow-2][PlayerCol] == Constants.SAND && grid[PlayerRow-2][PlayerCol]!= Constants.DESTINATION && grid[PlayerRow-2][PlayerCol] != Constants.UNMARKED_BOX && grid[PlayerRow-2][PlayerCol] != Constants.MARKED_BOX)
                    {
                        grid[PlayerRow-1][PlayerCol] = Constants.PLAYER;
                        grid[PlayerRow][PlayerCol] = Constants.DESTINATION;
                        grid[PlayerRow-2][PlayerCol] = Constants.UNMARKED_BOX;
                        PlayerRow--;
                    }
                    else if(destinationGrid[PlayerRow][PlayerCol] == Constants.PLAYER_AT_DESTINATION & grid[PlayerRow-1][PlayerCol] == Constants.MARKED_BOX && grid[PlayerRow-2][PlayerCol] == Constants.DESTINATION && grid[PlayerRow-2][PlayerCol]!= Constants.SAND && grid[PlayerRow-2][PlayerCol] != Constants.UNMARKED_BOX && grid[PlayerRow-2][PlayerCol] != Constants.MARKED_BOX)
                    {
                        grid[PlayerRow-1][PlayerCol] = Constants.PLAYER;
                        grid[PlayerRow][PlayerCol] = Constants.DESTINATION;
                        grid[PlayerRow-2][PlayerCol] = Constants.MARKED_BOX;
                        PlayerRow--;
                    }
                    else if(destinationGrid[PlayerRow][PlayerCol] == Constants.PLAYER_AT_DESTINATION && grid[PlayerRow-1][PlayerCol] == Constants.UNMARKED_BOX && grid[PlayerRow-2][PlayerCol] == Constants.SAND && grid[PlayerRow-2][PlayerCol]!= Constants.DESTINATION && grid[PlayerRow-2][PlayerCol] != Constants.UNMARKED_BOX && grid[PlayerRow-2][PlayerCol] != Constants.MARKED_BOX)
                    {
                        grid[PlayerRow-1][PlayerCol] = Constants.PLAYER;
                        grid[PlayerRow][PlayerCol] = Constants.DESTINATION;
                        grid[PlayerRow-2][PlayerCol] = Constants.UNMARKED_BOX;
                        PlayerRow--;
                    }
                    //checks same as above but if above is marked box, if you move it chnage it back to a normal unmarked box
                    else if(grid[PlayerRow-1][PlayerCol] == Constants.MARKED_BOX && grid[PlayerRow-2][PlayerCol] == Constants.SAND && grid[PlayerRow-2][PlayerCol] != Constants.STONE && grid[PlayerRow-2][PlayerCol] != Constants.UNMARKED_BOX && grid[PlayerRow-2][PlayerCol] != Constants.MARKED_BOX && grid[PlayerRow-2][PlayerCol] != Constants.DESTINATION)
                    {
                        PlayerRow--;
                        grid[PlayerRow+1][PlayerCol] = Constants.SAND;
                        grid[PlayerRow][PlayerCol] = Constants.PLAYER_AT_DESTINATION;
                        grid[PlayerRow-1][PlayerCol] = Constants.UNMARKED_BOX;
                    }
                    else if(grid[PlayerRow-1][PlayerCol] == Constants.MARKED_BOX && grid[PlayerRow-2][PlayerCol] == Constants.DESTINATION && grid[PlayerRow-2][PlayerCol] != Constants.SAND && grid[PlayerRow-2][PlayerCol] != Constants.STONE && grid[PlayerRow-2][PlayerCol] != Constants.UNMARKED_BOX && grid[PlayerRow-2][PlayerCol] != Constants.MARKED_BOX)
                    {
                        grid[PlayerRow][PlayerCol] = Constants.SAND;
                        grid[PlayerRow-1][PlayerCol] = Constants.PLAYER_AT_DESTINATION;
                        grid[PlayerRow-2][PlayerCol] = Constants.MARKED_BOX;
                        PlayerRow--;
                    }
                    //checking if space above box is destination and if so change it to a marked box
                    else if(grid[PlayerRow-1][PlayerCol] == Constants.UNMARKED_BOX && grid[PlayerRow-2][PlayerCol] == Constants.DESTINATION)
                    {
                        PlayerRow--;
                        grid[PlayerRow+1][PlayerCol] = Constants.SAND;
                        grid[PlayerRow][PlayerCol] = Constants.PLAYER;
                        grid[PlayerRow-1][PlayerCol] = Constants.MARKED_BOX;
                    }

                    else if(destinationGrid[PlayerRow][PlayerCol] == Constants.PLAYER_AT_DESTINATION && grid[PlayerRow-1][PlayerCol] == Constants.SAND && grid[PlayerRow-1][PlayerCol] != Constants.DESTINATION && grid[PlayerRow-1][PlayerCol] != Constants.UNMARKED_BOX && grid[PlayerRow-1][PlayerCol] != Constants.MARKED_BOX)
                    {
                        grid[PlayerRow-1][PlayerCol] = Constants.PLAYER;
                        grid[PlayerRow][PlayerCol] = Constants.DESTINATION;
                        PlayerRow--;
                    }
                    else if(destinationGrid[PlayerRow][PlayerCol] == Constants.PLAYER_AT_DESTINATION && grid[PlayerRow-1][PlayerCol] == Constants.DESTINATION && grid[PlayerRow-1][PlayerCol] != Constants.SAND && grid[PlayerRow-1][PlayerCol] != Constants.UNMARKED_BOX && grid[PlayerRow-1][PlayerCol] != Constants.MARKED_BOX)
                    {
                        grid[PlayerRow-1][PlayerCol] = Constants.PLAYER_AT_DESTINATION;
                        grid[PlayerRow][PlayerCol] = Constants.DESTINATION;
                        PlayerRow--;
                    }

                    //checks if above player is empty/sand and moves it up
                    else if(grid[PlayerRow-1][PlayerCol] == Constants.SAND)
                    {
                        PlayerRow--;
                        grid[PlayerRow][PlayerCol] = Constants.PLAYER;
                        grid[PlayerRow+1][PlayerCol] = Constants.SAND;
                    }
                    //checks if above player is destination and then you have the desitnation circle over player

                    else if(grid[PlayerRow-1][PlayerCol] == Constants.DESTINATION)
                    {
                        grid[PlayerRow][PlayerCol] = Constants.SAND;
                        grid[PlayerRow-1][PlayerCol] = Constants.PLAYER_AT_DESTINATION;
                        PlayerRow--;
                        System.out.print("Hi");
                        //add picture later
                    }

                }
                printDebug();
            }
            //down
            if(Gdx.input.isKeyJustPressed(Keys.DOWN) || Gdx.input.isKeyJustPressed(Keys.S))
            {

                if(grid[PlayerRow+1][PlayerCol] != Constants.STONE)
                {
                    System.out.print("Hi");
                    //Checks for unmarked box below  player and as long as grid location above box is sand then it can move up as well as the player
                    if(grid[PlayerRow+1][PlayerCol] == Constants.UNMARKED_BOX && grid[PlayerRow+2][PlayerCol] == Constants.SAND && grid[PlayerRow+2][PlayerCol] != Constants.STONE && grid[PlayerRow+2][PlayerCol] != Constants.UNMARKED_BOX && grid[PlayerRow+2][PlayerCol] != Constants.MARKED_BOX && grid[PlayerRow+2][PlayerCol] != Constants.DESTINATION && grid[PlayerRow][PlayerCol] != Constants.PLAYER_AT_DESTINATION)
                    {
                        PlayerRow++;
                        grid[PlayerRow-1][PlayerCol] = Constants.SAND;
                        grid[PlayerRow][PlayerCol] = Constants.PLAYER;
                        grid[PlayerRow+1][PlayerCol] = Constants.UNMARKED_BOX;
                    }
                    else if(destinationGrid[PlayerRow][PlayerCol] == Constants.PLAYER_AT_DESTINATION && grid[PlayerRow+1][PlayerCol] == Constants.UNMARKED_BOX && grid[PlayerRow+1][PlayerCol] == Constants.DESTINATION)
                    {
                        grid[PlayerRow+1][PlayerCol] = Constants.PLAYER;
                        grid[PlayerRow][PlayerCol] = Constants.DESTINATION;
                        grid[PlayerRow+2][PlayerCol] = Constants.MARKED_BOX;
                        PlayerRow++;
                    }
                    else if(destinationGrid[PlayerRow][PlayerCol] == Constants.PLAYER_AT_DESTINATION && grid[PlayerRow+1][PlayerCol] == Constants.MARKED_BOX && grid[PlayerRow+2][PlayerCol] == Constants.SAND && grid[PlayerRow+2][PlayerCol]!= Constants.DESTINATION && grid[PlayerRow+2][PlayerCol] != Constants.UNMARKED_BOX && grid[PlayerRow+2][PlayerCol] != Constants.MARKED_BOX)
                    {
                        System.out.print("2");
                        grid[PlayerRow+1][PlayerCol] = Constants.PLAYER;
                        grid[PlayerRow][PlayerCol] = Constants.DESTINATION;
                        grid[PlayerRow+2][PlayerCol] = Constants.UNMARKED_BOX;
                        PlayerRow++;
                    }
                    else if(destinationGrid[PlayerRow][PlayerCol] == Constants.PLAYER_AT_DESTINATION && grid[PlayerRow+1][PlayerCol] == Constants.UNMARKED_BOX && grid[PlayerRow+2][PlayerCol] == Constants.SAND && grid[PlayerRow+2][PlayerCol]!= Constants.DESTINATION && grid[PlayerRow+2][PlayerCol] != Constants.UNMARKED_BOX && grid[PlayerRow+2][PlayerCol] != Constants.MARKED_BOX)
                    {
                        grid[PlayerRow+1][PlayerCol] = Constants.PLAYER;
                        grid[PlayerRow][PlayerCol] = Constants.DESTINATION;
                        grid[PlayerRow+2][PlayerCol] = Constants.UNMARKED_BOX;
                        PlayerRow++;
                    }
                    else if(destinationGrid[PlayerRow][PlayerCol] == Constants.PLAYER_AT_DESTINATION & grid[PlayerRow+1][PlayerCol] == Constants.MARKED_BOX && grid[PlayerRow+2][PlayerCol] == Constants.DESTINATION && grid[PlayerRow+2][PlayerCol]!= Constants.SAND && grid[PlayerRow+2][PlayerCol] != Constants.UNMARKED_BOX && grid[PlayerRow+2][PlayerCol] != Constants.MARKED_BOX)
                    {
                        System.out.print("1");
                        grid[PlayerRow+1][PlayerCol] = Constants.PLAYER;
                        grid[PlayerRow][PlayerCol] = Constants.DESTINATION;
                        grid[PlayerRow+2][PlayerCol] = Constants.MARKED_BOX;
                        PlayerRow++;
                    }
                    //checks same as above but if below it is marked box, if you move it chnage it back to a normal unmarked box
                    else if(grid[PlayerRow+1][PlayerCol] == Constants.MARKED_BOX && grid[PlayerRow+2][PlayerCol] == Constants.SAND && grid[PlayerRow+2][PlayerCol] != Constants.STONE && grid[PlayerRow+2][PlayerCol] != Constants.UNMARKED_BOX && grid[PlayerRow+2][PlayerCol] != Constants.MARKED_BOX && grid[PlayerRow+2][PlayerCol] != Constants.DESTINATION)
                    {
                        System.out.print("Down");
                        PlayerRow++;
                        grid[PlayerRow-1][PlayerCol] = Constants.SAND;
                        grid[PlayerRow][PlayerCol] = Constants.PLAYER_AT_DESTINATION;
                        grid[PlayerRow+1][PlayerCol] = Constants.UNMARKED_BOX;
                    }
                    else if(grid[PlayerRow+1][PlayerCol] == Constants.MARKED_BOX && grid[PlayerRow+2][PlayerCol] == Constants.DESTINATION && grid[PlayerRow+2][PlayerCol] != Constants.SAND && grid[PlayerRow+2][PlayerCol] != Constants.STONE && grid[PlayerRow+2][PlayerCol] != Constants.UNMARKED_BOX && grid[PlayerRow+2][PlayerCol] != Constants.MARKED_BOX)
                    {
                        grid[PlayerRow][PlayerCol] = Constants.SAND;
                        grid[PlayerRow+1][PlayerCol] = Constants.PLAYER_AT_DESTINATION;
                        grid[PlayerRow+2][PlayerCol] = Constants.MARKED_BOX;
                        PlayerRow++;
                    }
                    //checking if space below box is destination and if so change it to a marked box
                    else if(grid[PlayerRow+1][PlayerCol] == Constants.UNMARKED_BOX && grid[PlayerRow+2][PlayerCol] == Constants.DESTINATION)
                    {
                        PlayerRow++;
                        grid[PlayerRow-1][PlayerCol] = Constants.SAND;
                        grid[PlayerRow][PlayerCol] = Constants.PLAYER;
                        grid[PlayerRow+1][PlayerCol] = Constants.MARKED_BOX;
                    }
                    else if((destinationGrid[PlayerRow][PlayerCol] == Constants.PLAYER_AT_DESTINATION && grid[PlayerRow+1][PlayerCol] == Constants.SAND && grid[PlayerRow+1][PlayerCol] != Constants.DESTINATION && grid[PlayerRow+1][PlayerCol] != Constants.UNMARKED_BOX && grid[PlayerRow+1][PlayerCol] != Constants.MARKED_BOX) )
                    {
                        System.out.print("3");
                        grid[PlayerRow+1][PlayerCol] = Constants.PLAYER;
                        grid[PlayerRow][PlayerCol] = Constants.DESTINATION;
                        PlayerRow++;
                    }
                    else if((destinationGrid[PlayerRow][PlayerCol] == Constants.PLAYER_AT_DESTINATION && grid[PlayerRow+1][PlayerCol] == Constants.DESTINATION && grid[PlayerRow+1][PlayerCol] != Constants.SAND && grid[PlayerRow+1][PlayerCol] != Constants.UNMARKED_BOX && grid[PlayerRow+1][PlayerCol] != Constants.MARKED_BOX) )
                    {
                        grid[PlayerRow+1][PlayerCol] = Constants.PLAYER_AT_DESTINATION;
                        grid[PlayerRow][PlayerCol] = Constants.DESTINATION;
                        PlayerRow++;
                    }
                    //checks if below player is empty/sand and moves it up
                    else if(grid[PlayerRow+1][PlayerCol] == Constants.SAND)
                    {
                        System.out.print("4");
                        PlayerRow++;
                        grid[PlayerRow][PlayerCol] = Constants.PLAYER;
                        grid[PlayerRow-1][PlayerCol] = Constants.SAND;
                    }
                    //checks if below player is destination and then you have the desitnation circle over player
                    else if(grid[PlayerRow+1][PlayerCol] == Constants.DESTINATION)
                    {
                        System.out.print("5");
                        grid[PlayerRow][PlayerCol] = Constants.SAND;
                        grid[PlayerRow+1][PlayerCol] = Constants.PLAYER_AT_DESTINATION;
                        PlayerRow++;
                        System.out.print("Hi");
                        //add picture later
                    }

                }
                printDebug();
            }
            //draw
            batch.begin();
            for(int r = 0; r < grid.length ; r++)
            {
                for(int c = 0; c < grid[r].length ; c++)
                {
                    float x = c * Constants.SQUARE_WIDTH;
                    float y = Constants.WORLD_HEIGHT - ((r+1) * Constants.SQUARE_WIDTH); 
                    if(grid[r][c] == Constants.DESTINATION)
                    {
                        batch.draw(destinationPic,x + 150, y - 150, Constants.SQUARE_WIDTH, Constants.SQUARE_WIDTH);
                    }
                    else if(r == PlayerRow && c == PlayerCol && destinationGrid[r][c] == Constants.PLAYER_AT_DESTINATION)
                    {
                        batch.draw(playerdestinationPic,x + 150, y - 150, Constants.SQUARE_WIDTH, Constants.SQUARE_WIDTH);
                    }
                    else if(grid[r][c] == Constants.BLANK)
                    {
                        batch.draw(sandImage,x + 150, y - 150, Constants.SQUARE_WIDTH, Constants.SQUARE_WIDTH);  
                    }

                    else if(grid[r][c] == Constants.SAND)
                    {
                        batch.draw(sandImage,x + 150, y - 150, Constants.SQUARE_WIDTH, Constants.SQUARE_WIDTH); 
                    }
                    else if(grid[r][c] == Constants.STONE)
                    {
                        batch.draw(brickPicture,x + 150, y - 150, Constants.SQUARE_WIDTH, Constants.SQUARE_WIDTH); 
                    }
                    else if(grid[r][c] == Constants.UNMARKED_BOX)
                    {
                        batch.draw(unmarkedBoxPicture,x + 150, y - 150, Constants.SQUARE_WIDTH, Constants.SQUARE_WIDTH);
                    }
                    else if(grid[r][c] == Constants.MARKED_BOX)
                    {
                        batch.draw(markedBoxPicture,x + 150, y - 150, Constants.SQUARE_WIDTH, Constants.SQUARE_WIDTH);
                    }
                    else if(grid[r][c] == Constants.PLAYER)
                    {
                        batch.draw(character,x + 150, y - 150,Constants.SQUARE_WIDTH, Constants.SQUARE_WIDTH);
                    }

                }
            }
            batch.end();

        }
        if(gamestate == Gamestate.WINNER)
        {
            font.getData().setScale(1);
            layout.setText(font,  "Click escape to return to the menu"   +
                "\n and to choose a new level difficulty");
            batch.setProjectionMatrix(viewport.getCamera().combined);
            batch.begin();
            batch.draw(winnerBackground, 0, 0, Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
            font.draw(batch, layout,
                Constants.WORLD_WIDTH / 2 - layout.width / 2,
                Constants.WORLD_HEIGHT / 4 + layout.height / 2);

            batch.end();
            Vector2 clickLoc = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY())); 
            batch.setProjectionMatrix(viewport.getCamera().combined);
            batch.begin();
            batch.draw(winner, 
                winnerBut.x, 
                winnerBut.y, 
                winnerBut.width, 
                winnerBut.height);
            if(Gdx.input.isKeyJustPressed(Keys.ESCAPE))
            {
                gamestate = Gamestate.GAME_MENU; 
            }
            batch.end();
        }
    }

    public void printDebug()
    {
        for(int[] row : grid)
        {
            for(int element : row)
            {
                System.out.print(element + " ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }

    public void easyLevel()
    {
        grid = new int[7][7];
        destinationGrid = new int[7][7];
        destinationGrid[5][1] = Constants.PLAYER_AT_DESTINATION;
        destinationGrid[4][5] = Constants.PLAYER_AT_DESTINATION;
        PlayerRow = 1;
        PlayerCol = 1;
        grid[0][0] = Constants.STONE;
        grid[0][1] = Constants.STONE;
        grid[0][2] = Constants.STONE;
        grid[0][3] = Constants.STONE;
        grid[0][4] = Constants.STONE;
        grid[0][5] = Constants.STONE;
        grid[0][6] = Constants.STONE;
        grid[1][0] = Constants.STONE;
        grid[1][1] = Constants.PLAYER;
        grid[1][2] = Constants.SAND;
        grid[1][3] = Constants.STONE;
        grid[1][4] = Constants.SAND;
        grid[1][5] = Constants.SAND;
        grid[1][6] = Constants.STONE;
        grid[2][0] = Constants.STONE;
        grid[2][1] = Constants.SAND;
        grid[2][2] = Constants.SAND;
        grid[2][3] = Constants.STONE;
        grid[2][4] = Constants.UNMARKED_BOX;
        grid[2][5] = Constants.SAND;
        grid[2][6] = Constants.STONE;
        grid[3][0] = Constants.STONE;
        grid[3][1] = Constants.SAND;
        grid[3][2] = Constants.SAND;
        grid[3][3] = Constants.SAND;
        grid[3][4] = Constants.SAND;
        grid[3][5] = Constants.SAND;
        grid[3][6] = Constants.STONE;
        grid[4][0] = Constants.STONE;
        grid[4][1] = Constants.SAND;
        grid[4][2] = Constants.UNMARKED_BOX;
        grid[4][3] = Constants.SAND;
        grid[4][4] = Constants.SAND;
        grid[4][5] = Constants.DESTINATION;
        grid[4][6] = Constants.STONE;
        grid[5][0] = Constants.STONE;
        grid[5][1] = Constants.DESTINATION;
        grid[5][2] = Constants.SAND;
        grid[5][3] = Constants.SAND;
        grid[5][4] = Constants.SAND;
        grid[5][5] = Constants.SAND;
        grid[5][6] = Constants.STONE;
        grid[6][0] = Constants.STONE;
        grid[6][1] = Constants.STONE;
        grid[6][2] = Constants.STONE;
        grid[6][3] = Constants.STONE;
        grid[6][4] = Constants.STONE;
        grid[6][5] = Constants.STONE;
        grid[6][6] = Constants.STONE;
    }

    public void restartEasy()
    {
        PlayerRow = 1;
        PlayerCol = 1;
        grid[0][0] = Constants.STONE;
        grid[0][1] = Constants.STONE;
        grid[0][2] = Constants.STONE;
        grid[0][3] = Constants.STONE;
        grid[0][4] = Constants.STONE;
        grid[0][5] = Constants.STONE;
        grid[0][6] = Constants.STONE;
        grid[1][0] = Constants.STONE;
        grid[1][1] = Constants.PLAYER;
        grid[1][2] = Constants.SAND;
        grid[1][3] = Constants.STONE;
        grid[1][4] = Constants.SAND;
        grid[1][5] = Constants.SAND;
        grid[1][6] = Constants.STONE;
        grid[2][0] = Constants.STONE;
        grid[2][1] = Constants.SAND;
        grid[2][2] = Constants.SAND;
        grid[2][3] = Constants.STONE;
        grid[2][4] = Constants.UNMARKED_BOX;
        grid[2][5] = Constants.SAND;
        grid[2][6] = Constants.STONE;
        grid[3][0] = Constants.STONE;
        grid[3][1] = Constants.SAND;
        grid[3][2] = Constants.SAND;
        grid[3][3] = Constants.SAND;
        grid[3][4] = Constants.SAND;
        grid[3][5] = Constants.SAND;
        grid[3][6] = Constants.STONE;
        grid[4][0] = Constants.STONE;
        grid[4][1] = Constants.SAND;
        grid[4][2] = Constants.UNMARKED_BOX;
        grid[4][3] = Constants.SAND;
        grid[4][4] = Constants.SAND;
        grid[4][5] = Constants.DESTINATION;
        grid[4][6] = Constants.STONE;
        grid[5][0] = Constants.STONE;
        grid[5][1] = Constants.DESTINATION;
        grid[5][2] = Constants.SAND;
        grid[5][3] = Constants.SAND;
        grid[5][4] = Constants.SAND;
        grid[5][5] = Constants.SAND;
        grid[5][6] = Constants.STONE;
        grid[6][0] = Constants.STONE;
        grid[6][1] = Constants.STONE;
        grid[6][2] = Constants.STONE;
        grid[6][3] = Constants.STONE;
        grid[6][4] = Constants.STONE;
        grid[6][5] = Constants.STONE;
        grid[6][6] = Constants.STONE;
    }

    public void medLevel()
    {
        PlayerRow = 2;
        PlayerCol = 2;
        grid = new int[9][8];
        destinationGrid = new int[9][8];
        destinationGrid[2][1] = Constants.PLAYER_AT_DESTINATION;
        destinationGrid[3][5] = Constants.PLAYER_AT_DESTINATION;
        destinationGrid[4][1] = Constants.PLAYER_AT_DESTINATION;
        destinationGrid[5][4] = Constants.PLAYER_AT_DESTINATION;
        destinationGrid[6][6] = Constants.PLAYER_AT_DESTINATION;
        destinationGrid[7][4] = Constants.PLAYER_AT_DESTINATION;
        destinationGrid[6][3] = Constants.PLAYER_AT_DESTINATION;
        gamestate = Gamestate.MENU;
        images = new ArrayList<Texture>();
        grid[0][0] = Constants.BLANK;
        grid[0][1] = Constants.BLANK;
        grid[0][2] = Constants.STONE;
        grid[0][3] = Constants.STONE;
        grid[0][4] = Constants.STONE;
        grid[0][5] = Constants.STONE;
        grid[0][6] = Constants.STONE;
        grid[0][7] = Constants.BLANK;
        grid[1][0] = Constants.STONE;
        grid[1][1] = Constants.STONE;
        grid[1][2] = Constants.STONE;
        grid[1][3] = Constants.SAND;
        grid[1][4] = Constants.SAND;
        grid[1][5] = Constants.SAND;
        grid[1][6] = Constants.STONE;
        grid[1][7] = Constants.BLANK;
        grid[2][0] = Constants.STONE;
        grid[2][1] = Constants.DESTINATION;
        grid[2][2] = Constants.PLAYER;
        grid[2][3] = Constants.UNMARKED_BOX;
        grid[2][4] = Constants.SAND;
        grid[2][5] = Constants.SAND;
        grid[2][6] = Constants.STONE;
        grid[2][7] = Constants.BLANK;
        grid[3][0] = Constants.STONE;
        grid[3][1] = Constants.STONE;
        grid[3][2] = Constants.STONE;
        grid[3][3] = Constants.SAND;
        grid[3][4] = Constants.UNMARKED_BOX;
        grid[3][5] = Constants.DESTINATION;
        grid[3][6] = Constants.STONE;
        grid[3][7] = Constants.BLANK;
        grid[4][0] = Constants.STONE;
        grid[4][1] = Constants.DESTINATION;
        grid[4][2] = Constants.STONE;
        grid[4][3] = Constants.STONE;
        grid[4][4] = Constants.UNMARKED_BOX;
        grid[4][5] = Constants.SAND;
        grid[4][6] = Constants.STONE;
        grid[4][7] = Constants.BLANK;
        grid[5][0] = Constants.STONE;
        grid[5][1] = Constants.SAND;
        grid[5][2] = Constants.STONE;
        grid[5][3] = Constants.SAND;
        grid[5][4] = Constants.DESTINATION;
        grid[5][5] = Constants.SAND;
        grid[5][6] = Constants.STONE;
        grid[5][7] = Constants.STONE;
        grid[6][0] = Constants.STONE;
        grid[6][1] = Constants.UNMARKED_BOX;
        grid[6][2] = Constants.SAND;
        grid[6][3] = Constants.MARKED_BOX;
        grid[6][4] = Constants.UNMARKED_BOX;
        grid[6][5] = Constants.UNMARKED_BOX;
        grid[6][6] = Constants.DESTINATION;
        grid[6][7] = Constants.STONE;
        grid[7][0] = Constants.STONE;
        grid[7][1] = Constants.SAND;
        grid[7][2] = Constants.SAND;
        grid[7][3] = Constants.SAND;
        grid[7][4] = Constants.DESTINATION;
        grid[7][5] = Constants.SAND;
        grid[7][6] = Constants.SAND;
        grid[7][7] = Constants.STONE;
        grid[8][0] = Constants.STONE;
        grid[8][1] = Constants.STONE;
        grid[8][2] = Constants.STONE;
        grid[8][3] = Constants.STONE;
        grid[8][4] = Constants.STONE;
        grid[8][5] = Constants.STONE;
        grid[8][6] = Constants.STONE;
        grid[8][7] = Constants.STONE;
    }

    public void restartMed()
    {
        PlayerRow = 2;
        PlayerCol = 2;
        grid[0][0] = Constants.BLANK;
        grid[0][1] = Constants.BLANK;
        grid[0][2] = Constants.STONE;
        grid[0][3] = Constants.STONE;
        grid[0][4] = Constants.STONE;
        grid[0][5] = Constants.STONE;
        grid[0][6] = Constants.STONE;
        grid[0][7] = Constants.BLANK;
        grid[1][0] = Constants.STONE;
        grid[1][1] = Constants.STONE;
        grid[1][2] = Constants.STONE;
        grid[1][3] = Constants.SAND;
        grid[1][4] = Constants.SAND;
        grid[1][5] = Constants.SAND;
        grid[1][6] = Constants.STONE;
        grid[1][7] = Constants.BLANK;
        grid[2][0] = Constants.STONE;
        grid[2][1] = Constants.DESTINATION;
        grid[2][2] = Constants.PLAYER;
        grid[2][3] = Constants.UNMARKED_BOX;
        grid[2][4] = Constants.SAND;
        grid[2][5] = Constants.SAND;
        grid[2][6] = Constants.STONE;
        grid[2][7] = Constants.BLANK;
        grid[3][0] = Constants.STONE;
        grid[3][1] = Constants.STONE;
        grid[3][2] = Constants.STONE;
        grid[3][3] = Constants.SAND;
        grid[3][4] = Constants.UNMARKED_BOX;
        grid[3][5] = Constants.DESTINATION;
        grid[3][6] = Constants.STONE;
        grid[3][7] = Constants.BLANK;
        grid[4][0] = Constants.STONE;
        grid[4][1] = Constants.DESTINATION;
        grid[4][2] = Constants.STONE;
        grid[4][3] = Constants.STONE;
        grid[4][4] = Constants.UNMARKED_BOX;
        grid[4][5] = Constants.SAND;
        grid[4][6] = Constants.STONE;
        grid[4][7] = Constants.BLANK;
        grid[5][0] = Constants.STONE;
        grid[5][1] = Constants.SAND;
        grid[5][2] = Constants.STONE;
        grid[5][3] = Constants.SAND;
        grid[5][4] = Constants.DESTINATION;
        grid[5][5] = Constants.SAND;
        grid[5][6] = Constants.STONE;
        grid[5][7] = Constants.STONE;
        grid[6][0] = Constants.STONE;
        grid[6][1] = Constants.UNMARKED_BOX;
        grid[6][2] = Constants.SAND;
        grid[6][3] = Constants.MARKED_BOX;
        grid[6][4] = Constants.UNMARKED_BOX;
        grid[6][5] = Constants.UNMARKED_BOX;
        grid[6][6] = Constants.DESTINATION;
        grid[6][7] = Constants.STONE;
        grid[7][0] = Constants.STONE;
        grid[7][1] = Constants.SAND;
        grid[7][2] = Constants.SAND;
        grid[7][3] = Constants.SAND;
        grid[7][4] = Constants.DESTINATION;
        grid[7][5] = Constants.SAND;
        grid[7][6] = Constants.SAND;
        grid[7][7] = Constants.STONE;
        grid[8][0] = Constants.STONE;
        grid[8][1] = Constants.STONE;
        grid[8][2] = Constants.STONE;
        grid[8][3] = Constants.STONE;
        grid[8][4] = Constants.STONE;
        grid[8][5] = Constants.STONE;
        grid[8][6] = Constants.STONE;
        grid[8][7] = Constants.STONE;
    }

    public void hardLevel()
    {
        PlayerRow = 4;
        PlayerCol = 7;
        grid = new int[10][14];
        destinationGrid = new int[10][14];
        destinationGrid[1][1] = Constants.PLAYER_AT_DESTINATION;
        destinationGrid[1][2] = Constants.PLAYER_AT_DESTINATION;
        destinationGrid[2][1] = Constants.PLAYER_AT_DESTINATION;
        destinationGrid[2][2] = Constants.PLAYER_AT_DESTINATION;
        destinationGrid[3][1] = Constants.PLAYER_AT_DESTINATION;
        destinationGrid[3][2] = Constants.PLAYER_AT_DESTINATION;
        destinationGrid[4][1] = Constants.PLAYER_AT_DESTINATION;
        destinationGrid[4][2] = Constants.PLAYER_AT_DESTINATION;
        destinationGrid[5][1] = Constants.PLAYER_AT_DESTINATION;
        destinationGrid[5][2] = Constants.PLAYER_AT_DESTINATION;
        grid[0][0] = Constants.STONE;
        grid[0][1] = Constants.STONE;
        grid[0][2] = Constants.STONE;
        grid[0][3] = Constants.STONE;
        grid[0][4] = Constants.STONE;
        grid[0][5] = Constants.STONE;
        grid[0][6] = Constants.STONE;
        grid[0][7] = Constants.STONE;
        grid[0][8] = Constants.STONE;
        grid[0][9] = Constants.STONE;
        grid[0][10] = Constants.STONE;
        grid[0][11] = Constants.STONE;
        grid[0][12] = Constants.SAND;
        grid[0][13] = Constants.SAND;
        grid[1][0] = Constants.STONE;
        grid[1][1] = Constants.DESTINATION;
        grid[1][2] = Constants.DESTINATION;
        grid[1][3] = Constants.SAND;
        grid[1][4] = Constants.SAND;
        grid[1][5] = Constants.STONE;
        grid[1][6] = Constants.SAND;
        grid[1][7] = Constants.SAND;
        grid[1][8] = Constants.SAND;
        grid[1][9] = Constants.SAND;
        grid[1][10] = Constants.SAND;
        grid[1][11] = Constants.STONE;
        grid[1][12] = Constants.STONE;
        grid[1][13] = Constants.STONE;
        grid[2][0] = Constants.STONE;
        grid[2][1] = Constants.DESTINATION;
        grid[2][2] = Constants.DESTINATION;
        grid[2][3] = Constants.SAND;
        grid[2][4] = Constants.SAND;
        grid[2][5] = Constants.STONE;
        grid[2][6] = Constants.SAND;
        grid[2][7] = Constants.UNMARKED_BOX;
        grid[2][8] = Constants.SAND;
        grid[2][9] = Constants.SAND;
        grid[2][10] = Constants.UNMARKED_BOX;
        grid[2][11] = Constants.SAND;
        grid[2][12] = Constants.SAND;
        grid[2][13] = Constants.STONE;
        grid[3][0] = Constants.STONE;
        grid[3][1] = Constants.DESTINATION;
        grid[3][2] = Constants.DESTINATION;
        grid[3][3] = Constants.SAND;
        grid[3][4] = Constants.SAND;
        grid[3][5] = Constants.STONE;
        grid[3][6] = Constants.UNMARKED_BOX;
        grid[3][7] = Constants.STONE;
        grid[3][8] = Constants.STONE;
        grid[3][9] = Constants.STONE;
        grid[3][10] = Constants.STONE;
        grid[3][11] = Constants.SAND;
        grid[3][12] = Constants.SAND;
        grid[3][13] = Constants.STONE;
        grid[4][0] = Constants.STONE;
        grid[4][1] = Constants.DESTINATION;
        grid[4][2] = Constants.DESTINATION;
        grid[4][3] = Constants.SAND;
        grid[4][4] = Constants.SAND;
        grid[4][5] = Constants.SAND;
        grid[4][6] = Constants.SAND;
        grid[4][7] = Constants.PLAYER;
        grid[4][8] = Constants.SAND;
        grid[4][9] = Constants.STONE;
        grid[4][10] = Constants.STONE;
        grid[4][11] = Constants.SAND;
        grid[4][12] = Constants.SAND;
        grid[4][13] = Constants.STONE;
        grid[5][0] = Constants.STONE;
        grid[5][1] = Constants.DESTINATION;
        grid[5][2] = Constants.DESTINATION;
        grid[5][3] = Constants.SAND;
        grid[5][4] = Constants.SAND;
        grid[5][5] = Constants.STONE;
        grid[5][6] = Constants.SAND;
        grid[5][7] = Constants.STONE;
        grid[5][8] = Constants.SAND;
        grid[5][9] = Constants.SAND;
        grid[5][10] = Constants.UNMARKED_BOX;
        grid[5][11] = Constants.SAND;
        grid[5][12] = Constants.STONE;
        grid[5][13] = Constants.STONE;
        grid[6][0] = Constants.STONE;
        grid[6][1] = Constants.STONE;
        grid[6][2] = Constants.STONE;
        grid[6][3] = Constants.STONE;
        grid[6][4] = Constants.STONE;
        grid[6][5] = Constants.STONE;
        grid[6][6] = Constants.SAND;
        grid[6][7] = Constants.STONE;
        grid[6][8] = Constants.STONE;
        grid[6][9] = Constants.UNMARKED_BOX;
        grid[6][10] = Constants.SAND;
        grid[6][11] = Constants.UNMARKED_BOX;
        grid[6][12] = Constants.SAND;
        grid[6][13] = Constants.STONE;
        grid[7][0] = Constants.SAND;
        grid[7][1] = Constants.SAND;
        grid[7][2] = Constants.STONE;
        grid[7][3] = Constants.SAND;
        grid[7][4] = Constants.UNMARKED_BOX;
        grid[7][5] = Constants.SAND;
        grid[7][6] = Constants.SAND;
        grid[7][7] = Constants.UNMARKED_BOX;
        grid[7][8] = Constants.SAND;
        grid[7][9] = Constants.UNMARKED_BOX;
        grid[7][10] = Constants.SAND;
        grid[7][11] = Constants.UNMARKED_BOX;
        grid[7][12] = Constants.SAND;
        grid[7][13] = Constants.STONE;
        grid[8][0] = Constants.SAND;
        grid[8][1] = Constants.SAND;
        grid[8][2] = Constants.STONE;
        grid[8][3] = Constants.SAND;
        grid[8][4] = Constants.SAND;
        grid[8][5] = Constants.SAND;
        grid[8][6] = Constants.SAND;
        grid[8][7] = Constants.STONE;
        grid[8][8] = Constants.SAND;
        grid[8][9] = Constants.SAND;
        grid[8][10] = Constants.SAND;
        grid[8][11] = Constants.SAND;
        grid[8][12] = Constants.SAND;
        grid[8][13] = Constants.STONE;
        grid[9][0] = Constants.SAND;
        grid[9][1] = Constants.SAND;
        grid[9][2] = Constants.STONE;
        grid[9][3] = Constants.STONE;
        grid[9][4] = Constants.STONE;
        grid[9][5] = Constants.STONE;
        grid[9][6] = Constants.STONE;
        grid[9][7] = Constants.STONE;
        grid[9][8] = Constants.STONE;
        grid[9][9] = Constants.STONE;
        grid[9][10] = Constants.STONE;
        grid[9][11] = Constants.STONE;
        grid[9][12] = Constants.STONE;
        grid[9][13] = Constants.STONE;
    }

    public void restartHard()
    {
        PlayerRow = 4;
        PlayerCol = 7;
        grid = new int[10][14];
        grid[0][0] = Constants.STONE;
        grid[0][1] = Constants.STONE;
        grid[0][2] = Constants.STONE;
        grid[0][3] = Constants.STONE;
        grid[0][4] = Constants.STONE;
        grid[0][5] = Constants.STONE;
        grid[0][6] = Constants.STONE;
        grid[0][7] = Constants.STONE;
        grid[0][8] = Constants.STONE;
        grid[0][9] = Constants.STONE;
        grid[0][10] = Constants.STONE;
        grid[0][11] = Constants.STONE;
        grid[0][12] = Constants.SAND;
        grid[0][13] = Constants.SAND;
        grid[1][0] = Constants.STONE;
        grid[1][1] = Constants.DESTINATION;
        grid[1][2] = Constants.DESTINATION;
        grid[1][3] = Constants.SAND;
        grid[1][4] = Constants.SAND;
        grid[1][5] = Constants.STONE;
        grid[1][6] = Constants.SAND;
        grid[1][7] = Constants.SAND;
        grid[1][8] = Constants.SAND;
        grid[1][9] = Constants.SAND;
        grid[1][10] = Constants.SAND;
        grid[1][11] = Constants.STONE;
        grid[1][12] = Constants.STONE;
        grid[1][13] = Constants.STONE;
        grid[2][0] = Constants.STONE;
        grid[2][1] = Constants.DESTINATION;
        grid[2][2] = Constants.DESTINATION;
        grid[2][3] = Constants.SAND;
        grid[2][4] = Constants.SAND;
        grid[2][5] = Constants.STONE;
        grid[2][6] = Constants.SAND;
        grid[2][7] = Constants.UNMARKED_BOX;
        grid[2][8] = Constants.SAND;
        grid[2][9] = Constants.SAND;
        grid[2][10] = Constants.UNMARKED_BOX;
        grid[2][11] = Constants.SAND;
        grid[2][12] = Constants.SAND;
        grid[2][13] = Constants.STONE;
        grid[3][0] = Constants.STONE;
        grid[3][1] = Constants.DESTINATION;
        grid[3][2] = Constants.DESTINATION;
        grid[3][3] = Constants.SAND;
        grid[3][4] = Constants.SAND;
        grid[3][5] = Constants.STONE;
        grid[3][6] = Constants.UNMARKED_BOX;
        grid[3][7] = Constants.STONE;
        grid[3][8] = Constants.STONE;
        grid[3][9] = Constants.STONE;
        grid[3][10] = Constants.STONE;
        grid[3][11] = Constants.SAND;
        grid[3][12] = Constants.SAND;
        grid[3][13] = Constants.STONE;
        grid[4][0] = Constants.STONE;
        grid[4][1] = Constants.DESTINATION;
        grid[4][2] = Constants.DESTINATION;
        grid[4][3] = Constants.SAND;
        grid[4][4] = Constants.SAND;
        grid[4][5] = Constants.SAND;
        grid[4][6] = Constants.SAND;
        grid[4][7] = Constants.PLAYER;
        grid[4][8] = Constants.SAND;
        grid[4][9] = Constants.STONE;
        grid[4][10] = Constants.STONE;
        grid[4][11] = Constants.SAND;
        grid[4][12] = Constants.SAND;
        grid[4][13] = Constants.STONE;
        grid[5][0] = Constants.STONE;
        grid[5][1] = Constants.DESTINATION;
        grid[5][2] = Constants.DESTINATION;
        grid[5][3] = Constants.SAND;
        grid[5][4] = Constants.SAND;
        grid[5][5] = Constants.STONE;
        grid[5][6] = Constants.SAND;
        grid[5][7] = Constants.STONE;
        grid[5][8] = Constants.SAND;
        grid[5][9] = Constants.SAND;
        grid[5][10] = Constants.UNMARKED_BOX;
        grid[5][11] = Constants.SAND;
        grid[5][12] = Constants.STONE;
        grid[5][13] = Constants.STONE;
        grid[6][0] = Constants.STONE;
        grid[6][1] = Constants.STONE;
        grid[6][2] = Constants.STONE;
        grid[6][3] = Constants.STONE;
        grid[6][4] = Constants.STONE;
        grid[6][5] = Constants.STONE;
        grid[6][6] = Constants.SAND;
        grid[6][7] = Constants.STONE;
        grid[6][8] = Constants.STONE;
        grid[6][9] = Constants.UNMARKED_BOX;
        grid[6][10] = Constants.SAND;
        grid[6][11] = Constants.UNMARKED_BOX;
        grid[6][12] = Constants.SAND;
        grid[6][13] = Constants.STONE;
        grid[7][0] = Constants.SAND;
        grid[7][1] = Constants.SAND;
        grid[7][2] = Constants.STONE;
        grid[7][3] = Constants.SAND;
        grid[7][4] = Constants.UNMARKED_BOX;
        grid[7][5] = Constants.SAND;
        grid[7][6] = Constants.SAND;
        grid[7][7] = Constants.UNMARKED_BOX;
        grid[7][8] = Constants.SAND;
        grid[7][9] = Constants.UNMARKED_BOX;
        grid[7][10] = Constants.SAND;
        grid[7][11] = Constants.UNMARKED_BOX;
        grid[7][12] = Constants.SAND;
        grid[7][13] = Constants.STONE;
        grid[8][0] = Constants.SAND;
        grid[8][1] = Constants.SAND;
        grid[8][2] = Constants.STONE;
        grid[8][3] = Constants.SAND;
        grid[8][4] = Constants.SAND;
        grid[8][5] = Constants.SAND;
        grid[8][6] = Constants.SAND;
        grid[8][7] = Constants.STONE;
        grid[8][8] = Constants.SAND;
        grid[8][9] = Constants.SAND;
        grid[8][10] = Constants.SAND;
        grid[8][11] = Constants.SAND;
        grid[8][12] = Constants.SAND;
        grid[8][13] = Constants.STONE;
        grid[9][0] = Constants.SAND;
        grid[9][1] = Constants.SAND;
        grid[9][2] = Constants.STONE;
        grid[9][3] = Constants.STONE;
        grid[9][4] = Constants.STONE;
        grid[9][5] = Constants.STONE;
        grid[9][6] = Constants.STONE;
        grid[9][7] = Constants.STONE;
        grid[9][8] = Constants.STONE;
        grid[9][9] = Constants.STONE;
        grid[9][10] = Constants.STONE;
        grid[9][11] = Constants.STONE;
        grid[9][12] = Constants.STONE;
        grid[9][13] = Constants.STONE;
    }

    public boolean easyWin()
    {
        boolean win = false;
        if(grid[5][1] == Constants.MARKED_BOX && grid[4][5] == Constants.MARKED_BOX)
        {
            win = true;
            gamestate = Gamestate.WINNER;
        }
        return win;
    }

    public boolean medWin()
    {
        boolean win = false;
        if(grid[2][1] == Constants.MARKED_BOX && grid[3][5] == Constants.MARKED_BOX && grid[4][1] == Constants.MARKED_BOX && grid[5][4] == Constants.MARKED_BOX
        && grid[6][6] == Constants.MARKED_BOX && grid[7][4] == Constants.MARKED_BOX && grid[6][3] == Constants.MARKED_BOX)
        {
            win = true;
            gamestate = Gamestate.WINNER;
        }
        return win;
    }

    public boolean hardWin()
    {
        boolean win = false;
        if(grid[1][1] == Constants.MARKED_BOX && grid[1][2] == Constants.MARKED_BOX && grid[2][1] == Constants.MARKED_BOX && grid[2][2] == Constants.MARKED_BOX &&
        grid[3][1] == Constants.MARKED_BOX && grid[3][2] == Constants.MARKED_BOX && grid[4][1] == Constants.MARKED_BOX && grid[4][2] == Constants.MARKED_BOX && 
        grid[5][1] == Constants.MARKED_BOX && grid[5][2] == Constants.MARKED_BOX)
        {
            win = true;
            gamestate = Gamestate.WINNER;
        }
        return win;
    }

    @Override
    public void dispose () {
        renderer.dispose();
        batch.dispose();
        font.dispose(); 
    }

    @Override
    public void resize (int width, int height) {
        viewport.update(width, height, true); 
    }

}

