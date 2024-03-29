package time_lapse;

import java.util.ArrayList;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import jig.Entity;
import jig.ResourceManager;


public class Game extends StateBasedGame {
	
	// possible states
	public static final int SPLASHSCREEN = 0;
	public static final int PLAYINGSTATE = 1;
	public static final int TRANSITIONSTATE = 2;
	public static final int GAMEOVERSTATE = 3;
	/*
	 * TODO: Will need to adjust MapSize later
	 * Map will be larger with scrolling
	 * May need to adjust tilesize as needed
	 */
	public static final int TILESIZE = 40;
	public static final int NUM_OF_TILESX = 74;
	public static final int NUM_OF_TILESY = 40;
	
	// resource strings
	public static final String PLAYER_DEFAULT_RSC = "resources/player_default.png";
	public static final String SPLASH_SCREEN_ON_RSC = "resources/light_on.jpg";
	public static final String SPLASH_SCREEN_DIM_RSC = "resources/light_dim.jpg";
	public static final String SPLASH_SCREEN_OFF_RSC = "resources/light_off.jpg";
	public static final String TILE_DIRT_RSC = "resources/dirt_tile.png";	
	public static final String TREE_DIRT_RSC = "resources/tree_tile.png";	
	public static final String LEFT_TREE_DIRT_RSC = "resources/right_tree_tile.png";	
	public static final String RIGHT_TREE_DIRT_RSC = "resources/left_tree_tile.png";
	public static final String BOULDER_RSC = "resources/boulder_tile.png";	
	public static final String LEVEL1_TRANSITION_RSC = "resources/level1_transition.png";
	public static final String LEVEL2_TRANSITION_RSC = "resources/level2_transition.png";
	public static final String arrow = "resources/arrow.png";
	public static final String UI_BG_RSC = "resources/gameUI.png";
	public static final String UI_HEALTHBAR_RSC = "resources/health_box.png";
	public static final String UI_HEALTHPIECE_RSC = "resources/health_piece.png";
	public static final String UI_HEALTHSHIELD_RSC = "resources/shield_piece.png";
	public static final String PROJECTILE_DEFAULT_RSC = "resources/bullet.png";
	public static final String ITEM_HAMMER_RSC = "resources/hammer.png";
	public static final String ITEM_FEATHER_RSC = "resources/feather.png";
	public static final String ITEM_SHIELD_RSC = "resources/shield.png";
	public static final String ITEM_ARROW_RSC = "resources/arrow.png";
	public static final String ITEM_ACCELERATOR_RSC = "resources/accelerator.png";
	public static final String ITEM_FIERY_RSC = "resources/fiery.png";
	public static final String OPEN_DOOR = "resources/dooropen.png";
	public static final String CLOSED_DOOR = "resources/doorclosed.png";
	public static final String DOOR_SWITCH_ON = "resources/switchon.png";
	public static final String DOOR_SWITCH_OFF = "resources/switchoff.png";
	public static final String OPEN_DOOR_VERT = "resources/dooropenvert.png";
	public static final String CLOSED_DOOR_VERT = "resources/doorclosedvert.png";
	public static final String TRANSPARENT_RSC = "resources/transparent.png";
	public static final String GAME_OVER = "resources/gameover.png";
	public static final String TILE_FOG_RSC = "resources/fog.png";
	public static final String MACHINE_PIECE_1_RSC = "resources/machine_piece_1.png";
	public static final String TIME_MACHINE_RSC = "resources/timemachine.png";

	
	// Animation resources
	public static final String PLAYER_DEFAULT_RIGHT_RSC = "resources/player_default_right_ani.png";
	public static final String PLAYER_DEFAULT_LEFT_RSC = "resources/player_default_left_ani.png";
	public static final String PLAYER_DEFAULT_RIGHT_IDLE_RSC = "resources/player_default_right_idle_ani.png";
	public static final String PLAYER_DEFAULT_LEFT_IDLE_RSC = "resources/player_default_left_idle_ani.png";
	public static final String PLAYER_DEFAULT_DOWN_RSC = "resources/player_default_down_ani.png";
	public static final String PLAYER_DEFAULT_UP_RSC = "resources/player_default_up_ani.png";
	public static final String PLAYER_DEFAULT_UP_IDLE_RSC = "resources/player_default_up_idle_ani.png";
	public static final String PLAYER_DEFAULT_DOWN_IDLE_RSC = "resources/player_default_down_idle_ani.png";

	//story resources
	public static final String story1 = "resources/t1.png";
	public static final String story2 = "resources/t2.png";
	public static final String story3 = "resources/t3.png";
	public static final String story4 = "resources/t4.png";
	public static final String story5 = "resources/t5.png";
	public static final String story6 = "resources/t6.png";
	public static final String story7 = "resources/t7.png";
	public static final String story8 = "resources/t8.png";

	// Enemy resources
	
	// Tree resources
	public static final String ENEMY_DEFAULT_TREE_RSC = "resources/Tree_enemy.png";
	public static final String ENEMY_DEFAULT_TREE_FRONT_ANI_RSC = "resources/Tree_enemy_front_ani.png";
	public static final String ENEMY_DEFAULT_TREE_FRONT_RIGHT_ANI_RSC = "resources/Tree_enemy_front_right_ani.png";
	public static final String ENEMY_DEFAULT_TREE_BACK_ANI_RSC = "resources/Tree_enemy_back_ani.png";
	
	// Wizard resources
	public static final String ENEMY_DEFAULT_WIZARD_ANI_RSC = "resources/Wizard_enemy_front_right_ani.png";
	public static final String ENEMY_DEFAULT_WIZARD_LEFT_ANI_RSC = "resources/Wizard_enemy_front_left_ani.png";
	
	// Mimic resources
	public static final String ENEMY_DEFAULT_MIMIC_ANI_RSC = "resources/mimic.png";
	public static final String ENEMY_DEFAULT_MIMIC_FRONT_ANI_RSC = "resources/mimic_front_ani.png";
	
	// items in the game
	public Player player;
	public Map map;
	public TimeMachine machine;
	public int storyTime;
	public boolean cheatMode = false;
	public int currLevel = 1;
	public int LIVES;
	public ArrayList<Enemy> enemy;
	public ArrayList<Item> items;
	public ArrayList<Door> doors;
	public ArrayList<Projectile> projectiles;
	public ArrayList<DoorSwitch> doorSwitch;
	public ArrayList<Room> rooms;
	public Debug debug;
	public UIHandler UIHandler;
	public ImageManager image_control;
	
	public Game(String title) {
		super(title);
		Entity.setCoarseGrainedCollisionBoundary(Entity.AABB);
	}

	@Override
	public void initStatesList(GameContainer container) throws SlickException {
		// Load states
		addState(new SplashScreen());
		addState(new PlayingState());
		addState(new TransitionState());
		addState(new GameOverState());
		
		// LOAD RESOURCES
		ResourceManager.loadImage(PLAYER_DEFAULT_RSC); 
		ResourceManager.loadImage(SPLASH_SCREEN_ON_RSC);
		ResourceManager.loadImage(SPLASH_SCREEN_DIM_RSC);
		ResourceManager.loadImage(SPLASH_SCREEN_OFF_RSC);
		ResourceManager.loadImage(TILE_DIRT_RSC);
		ResourceManager.loadImage(BOULDER_RSC);
		ResourceManager.loadImage(TREE_DIRT_RSC);
		ResourceManager.loadImage(LEFT_TREE_DIRT_RSC);
		ResourceManager.loadImage(RIGHT_TREE_DIRT_RSC);
		ResourceManager.loadImage(ITEM_HAMMER_RSC);
		ResourceManager.loadImage(UI_BG_RSC);
		ResourceManager.loadImage(PROJECTILE_DEFAULT_RSC);
		ResourceManager.loadImage(ITEM_FEATHER_RSC);
		ResourceManager.loadImage(ITEM_SHIELD_RSC);
		ResourceManager.loadImage(LEVEL1_TRANSITION_RSC);
		ResourceManager.loadImage(LEVEL2_TRANSITION_RSC);
		ResourceManager.loadImage(UI_HEALTHBAR_RSC);
		ResourceManager.loadImage(UI_HEALTHPIECE_RSC);
		ResourceManager.loadImage(UI_HEALTHSHIELD_RSC);
		ResourceManager.loadImage(OPEN_DOOR);
		ResourceManager.loadImage(CLOSED_DOOR);
		ResourceManager.loadImage(DOOR_SWITCH_ON);
		ResourceManager.loadImage(DOOR_SWITCH_OFF);
		ResourceManager.loadImage(TRANSPARENT_RSC);
		ResourceManager.loadImage(GAME_OVER);
		ResourceManager.loadImage(TILE_FOG_RSC);
		ResourceManager.loadImage(ITEM_ACCELERATOR_RSC);
		ResourceManager.loadImage(ITEM_SHIELD_RSC);
		ResourceManager.loadImage(ITEM_ARROW_RSC);
		ResourceManager.loadImage(ITEM_FIERY_RSC);
		ResourceManager.loadImage(MACHINE_PIECE_1_RSC);
		ResourceManager.loadImage(TIME_MACHINE_RSC);



		
		// LOAD ANIMATIONS FOR PLAYER && DOOR
		ResourceManager.loadImage(PLAYER_DEFAULT_RIGHT_RSC);
		ResourceManager.loadImage(PLAYER_DEFAULT_LEFT_RSC);
		ResourceManager.loadImage(PLAYER_DEFAULT_RIGHT_IDLE_RSC);
		ResourceManager.loadImage(PLAYER_DEFAULT_DOWN_RSC);
		ResourceManager.loadImage(PLAYER_DEFAULT_UP_RSC);
		ResourceManager.loadImage(PLAYER_DEFAULT_UP_IDLE_RSC);
		ResourceManager.loadImage(PLAYER_DEFAULT_DOWN_IDLE_RSC);
		ResourceManager.loadImage(OPEN_DOOR_VERT);
		ResourceManager.loadImage(CLOSED_DOOR_VERT);
		
		// Load storys
		ResourceManager.loadImage(story1);
		ResourceManager.loadImage(story2);
		ResourceManager.loadImage(story3);
		ResourceManager.loadImage(story4);
		ResourceManager.loadImage(story5);
		ResourceManager.loadImage(story6);
		ResourceManager.loadImage(story7);
		ResourceManager.loadImage(story8);
		// LOAD ANIMATIONS FOR ENEMIES
		
		
		
		// Tree animations
		ResourceManager.loadImage(ENEMY_DEFAULT_TREE_RSC);
		ResourceManager.loadImage(ENEMY_DEFAULT_TREE_FRONT_ANI_RSC);
		ResourceManager.loadImage(ENEMY_DEFAULT_TREE_FRONT_RIGHT_ANI_RSC);
		ResourceManager.loadImage(ENEMY_DEFAULT_TREE_BACK_ANI_RSC);
		
		// Wizard animations
		ResourceManager.loadImage(ENEMY_DEFAULT_WIZARD_ANI_RSC);
		ResourceManager.loadImage(ENEMY_DEFAULT_WIZARD_LEFT_ANI_RSC);

		// Mimic animations
		ResourceManager.loadImage(ENEMY_DEFAULT_MIMIC_ANI_RSC);
		ResourceManager.loadImage(ENEMY_DEFAULT_MIMIC_FRONT_ANI_RSC);

		
		// Initialize stuff
		map = new Map(NUM_OF_TILESX, NUM_OF_TILESY, TILESIZE);
		player = new Player(400, 300);
		enemy = new ArrayList<Enemy>();		
		items = new ArrayList<Item>();
		doors = new ArrayList<Door>();
		doorSwitch = new ArrayList<DoorSwitch>();
		projectiles = new ArrayList<Projectile>();
		LIVES = 2;
		storyTime = 1;
		debug = new Debug(10,20,"asdfasdf");
		UIHandler = new UIHandler(ResourceManager.getImage(UI_BG_RSC));
		rooms = new ArrayList<Room>();		
		// load images for all active entities / tiles
		image_control = new ImageManager();
	}
}
