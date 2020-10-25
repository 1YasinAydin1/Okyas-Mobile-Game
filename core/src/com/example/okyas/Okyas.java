package com.example.okyas;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import org.w3c.dom.css.Rect;


public class Okyas extends ApplicationAdapter {

	// region Required Variables
	SpriteBatch batch;
	OrthographicCamera moveCamera;
	Texture background;
	Animation player;
	Vector2 playerPlace;
	float passingTime = 0;


	static final float player_start_X_Place = 260;
	static final float player_start_Y_Place = 250;

	enum GameState{Start,Running,GameOver};
	GameState gameState = GameState.Start;
	Vector2 gravity = new Vector2();
	Vector2 playergravity = new Vector2();
	static final float player_jump = 800;
	static final float GRAVITY = -18;
	static final float player_speed_X = 500;

	TextureRegion floorBackground;
	float startFloorPlaceX;

	Animation enemyFloorImageAnim,enemyFlyImageAnim,CoinsAnim;
	TextureRegion enemyFloorImage,enemyFlyImage;
	Array<Enemy> enemyArray = new Array<>();

	TextureRegion readyImage,gameOverImage;
	Sound gameOverSound,jumpSound,coinSound;
	Rectangle playerFrame = new Rectangle();
	Rectangle enemyFrame = new Rectangle();

	OrthographicCamera UICamera;

	//ShapeRenderer shapeRenderer;
	BitmapFont font;
	int point = 0;
	Music music;
	int i;
	int coins = 1000,coinsY = 350,LevelUp = 20,LevelUpCount = 1,highScore;
	Rectangle floorFrame = new Rectangle();
	Rectangle coinsFrame = new Rectangle();
	boolean coinsFirst = false;
	Preferences prefs;


	// region Texture

	Texture
	// coin
			coin1,
			coin2,
			coin3,
			coin4,
			coin5,
			coin6,
			coin7,

	// enemyFlyImage
			enemyFlyImage1,
			enemyFlyImage2,
			enemyFlyImage3,
			enemyFlyImage4,
			enemyFlyImage5,
			enemyFlyImage6,
			enemyFlyImage7,
			enemyFlyImage8,
			enemyFlyImage9,
			enemyFlyImage10,

	// enemyFloorImage
			enemyFloorImage1,
			enemyFloorImage2,

	// player

			player1,
			player2,
			player3,
			player4,
			player5,
			player6,
			player7,
			player8,
			player9,
			player10,
			player11,
			player12,
			player13,
			player14,
			player15,
			player16,
			player17,
			player18,
			player19,
			player20,
			player21,
			player22,
			player23,
			player24,
			player25,
			player26,
			player27,
			player28,
			player29,
			player30,
			player31,
			player32,
			player33,
			player34,
			player35,
			player36,
			player37,
			player38,
			player39,
			player40,
			player41,
			player42,
			player43;
	// endregion

	// endregion
	@Override
	public void create() {

		// region Initialize and Settings
		batch = new SpriteBatch();
		moveCamera = new OrthographicCamera();
		moveCamera.setToOrtho(false,1920,1440);
		background = new Texture("background.png");

		playerPlace = new Vector2();
		floorBackground= new TextureRegion(new Texture("01.png"));

		enemyFlyImage = new TextureRegion(new Texture("enemy/enemy2/skeleton-fly_01.png"));

		UICamera = new OrthographicCamera();
		UICamera.setToOrtho(false,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		UICamera.update();

		readyImage = new TextureRegion(new Texture("tap_to_play.PNG"));
		gameOverImage = new TextureRegion(new Texture("game_over.png"));
		gameOverSound = Gdx.audio.newSound(Gdx.files.internal("gameOver.mp3"));
		jumpSound = Gdx.audio.newSound(Gdx.files.internal("jump.mp3"));
		coinSound = Gdx.audio.newSound(Gdx.files.internal("coins.mp3"));

		//shapeRenderer = new ShapeRenderer();
		font= new BitmapFont(Gdx.files.internal("font.fnt"));
		music = Gdx.audio.newMusic(Gdx.files.internal("loop.wav"));
		music.setLooping(true);

		prefs = Gdx.app.getPreferences("Game");

		// region Texture and Animation Description

		// coinTexture
		coin1 = new Texture("coins/1.png");
		coin2 = new Texture("coins/2.png");
		coin3 = new Texture("coins/3.png");
		coin4 = new Texture("coins/4.png");
		coin5 = new Texture("coins/5.png");
		coin6 = new Texture("coins/6.png");
		coin7 = new Texture("coins/7.png");

		// enemyFlyImageTexture
		enemyFlyImage1 = new Texture("enemy/enemy2/skeleton-fly_01.png");
		enemyFlyImage2 = new Texture("enemy/enemy2/skeleton-fly_02.png");
		enemyFlyImage3 = new Texture("enemy/enemy2/skeleton-fly_03.png");
		enemyFlyImage4 = new Texture("enemy/enemy2/skeleton-fly_04.png");
		enemyFlyImage5 = new Texture("enemy/enemy2/skeleton-fly_05.png");
		enemyFlyImage6 = new Texture("enemy/enemy2/skeleton-fly_06.png");
		enemyFlyImage7 = new Texture("enemy/enemy2/skeleton-fly_07.png");
		enemyFlyImage8 = new Texture("enemy/enemy2/skeleton-fly_08.png");
		enemyFlyImage9 = new Texture("enemy/enemy2/skeleton-fly_09.png");
		enemyFlyImage10 = new Texture("enemy/enemy2/skeleton-fly_10.png");

		// enemyFloorImageTexture
		enemyFloorImage1 = new Texture("enemy/enemy1/frame-1.png");
		enemyFloorImage2 = new Texture("enemy/enemy1/frame-2.png");

		// playerTexture
		player1 = new Texture("character/run/run_000.png");
		player2 = new Texture("character/run/run_001.png");
		player3 = new Texture("character/run/run_002.png");
		player4 = new Texture("character/run/run_003.png");
		player5 = new Texture("character/run/run_004.png");
		player6 = new Texture("character/run/run_005.png");
		player7 = new Texture("character/run/run_006.png");
		player8 = new Texture("character/run/run_007.png");
		player9 = new Texture("character/run/run_008.png");
		player10 = new Texture("character/run/run_009.png");

		player11 = new Texture("character/run/run_010.png");
		player12 = new Texture("character/run/run_011.png");
		player13 = new Texture("character/run/run_012.png");
		player14 = new Texture("character/run/run_013.png");
		player15 = new Texture("character/run/run_014.png");
		player16 = new Texture("character/run/run_015.png");
		player17 = new Texture("character/run/run_016.png");
		player18 = new Texture("character/run/run_017.png");
		player19 = new Texture("character/run/run_018.png");
		player20 = new Texture("character/run/run_019.png");

		player21 = new Texture("character/run/run_020.png");
		player22 = new Texture("character/run/run_021.png");
		player23 = new Texture("character/run/run_022.png");
		player24 = new Texture("character/run/run_023.png");
		player25 = new Texture("character/run/run_024.png");
		player26 = new Texture("character/run/run_025.png");
		player27 = new Texture("character/run/run_026.png");
		player28 = new Texture("character/run/run_027.png");
		player29 = new Texture("character/run/run_028.png");
		player30 = new Texture("character/run/run_029.png");

		player31 = new Texture("character/run/run_030.png");
		player32 = new Texture("character/run/run_031.png");
		player33 = new Texture("character/run/run_032.png");
		player34 = new Texture("character/run/run_033.png");
		player35 = new Texture("character/run/run_034.png");
		player36 = new Texture("character/run/run_035.png");
		player37 = new Texture("character/run/run_036.png");
		player38 = new Texture("character/run/run_037.png");
		player39 = new Texture("character/run/run_038.png");
		player40 = new Texture("character/run/run_039.png");

		player41 = new Texture("character/run/run_040.png");
		player42 = new Texture("character/run/run_041.png");
		player43 = new Texture("character/run/run_042.png");


		// coinAnimation
		CoinsAnim = new Animation(0.12f,new TextureRegion(coin1),new TextureRegion(coin2)
				,new TextureRegion(coin3),new TextureRegion(coin4)
				,new TextureRegion(coin5),new TextureRegion(coin6),new TextureRegion(coin7));

		//enemyFlyImageAnimation
		enemyFlyImageAnim = new Animation(0.05f,new TextureRegion(enemyFlyImage1),new TextureRegion(enemyFlyImage2)
				,new TextureRegion(enemyFlyImage3),new TextureRegion(enemyFlyImage4)
				,new TextureRegion(enemyFlyImage5),new TextureRegion(enemyFlyImage6)
				,new TextureRegion(enemyFlyImage7),new TextureRegion(enemyFlyImage8)
				,new TextureRegion(enemyFlyImage9),new TextureRegion(enemyFlyImage10));

		//enemyFloorImageAnimation
		enemyFloorImageAnim = new Animation(0.5f,new TextureRegion(enemyFloorImage1),new TextureRegion(enemyFloorImage2));

		// playerAnimation
		player = new Animation(0.03f,
				new TextureRegion(player1),
				new TextureRegion(player2),
				new TextureRegion(player3),
				new TextureRegion(player4),
				new TextureRegion(player5),
				new TextureRegion(player6),
				new TextureRegion(player7),
				new TextureRegion(player8),
				new TextureRegion(player9),
				new TextureRegion(player10),
				new TextureRegion(player11),
				new TextureRegion(player12),
				new TextureRegion(player13),
				new TextureRegion(player14),
				new TextureRegion(player15),
				new TextureRegion(player16),
				new TextureRegion(player17),
				new TextureRegion(player18),
				new TextureRegion(player19),
				new TextureRegion(player20),
				new TextureRegion(player21),
				new TextureRegion(player22),
				new TextureRegion(player23),
				new TextureRegion(player24),
				new TextureRegion(player25),
				new TextureRegion(player26),
				new TextureRegion(player27),
				new TextureRegion(player28),
				new TextureRegion(player29),
				new TextureRegion(player30),
				new TextureRegion(player31),
				new TextureRegion(player32),
				new TextureRegion(player33),
				new TextureRegion(player34),
				new TextureRegion(player35),
				new TextureRegion(player36),
				new TextureRegion(player37),
				new TextureRegion(player38),
				new TextureRegion(player39),
				new TextureRegion(player40),
				new TextureRegion(player41),
				new TextureRegion(player42),
				new TextureRegion(player43));

//endregion

		// region Animation setPlayMode
		enemyFlyImageAnim.setPlayMode(Animation.PlayMode.LOOP);
		enemyFloorImageAnim.setPlayMode(Animation.PlayMode.LOOP);
		player.setPlayMode(Animation.PlayMode.REVERSED);
		CoinsAnim.setPlayMode(Animation.PlayMode.LOOP);
		// endregion
		resetTheWorld();

		// endregion

	}

	private void resetTheWorld() {
		playerPlace.set(player_start_X_Place,player_start_Y_Place);
		moveCamera.position.x = 400;
		gravity.set(0,GRAVITY);
		playergravity.set(0,0);
		startFloorPlaceX = 0;
		enemyArray.clear();
		int j = 0;
		for (int i = 0;i<5;i++){
			j++;
			boolean isDown = MathUtils.randomBoolean();
			enemyArray.add(new Enemy(j>=4?2000+i*400:2000+i*300,isDown ? MathUtils.random(650,1050) : 150,isDown ? enemyFlyImageAnim:enemyFloorImageAnim));
		}
		point = 0; i = 0;
		highScore = prefs.getInteger("score",0);

	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(1,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		updateTheWorld();
		drawTheWorld();
	}

	private void updateTheWorld() {

		float deltaTime = Gdx.graphics.getDeltaTime();
		passingTime +=deltaTime;

		if (Gdx.input.justTouched()){
			if (gameState == GameState.Start){
				music.play();
				player.setPlayMode(Animation.PlayMode.LOOP);
				gameState = GameState.Running;
				jumpSound.play();
			}if (gameState == GameState.Running){
				if (i !=2){
					playergravity.set(player_speed_X+LevelUp,player_jump);
					jumpSound.play();
					i++;
				} gravity.set(0,GRAVITY);
			}if (gameState == GameState.GameOver){
				gameState = GameState.Start;
				if (point>highScore){
					prefs.putInteger("score",point);
					prefs.flush();}
				resetTheWorld();
			}
		}


		if (point>20*LevelUpCount){ // the higher the score, the higher the speed
			LevelUpCount++;
			playergravity.mulAdd(gravity,deltaTime);
			LevelUp +=20;
		}

		if (gameState != GameState.Start){
			playergravity.add(gravity);
		}
		playerPlace.mulAdd(playergravity,deltaTime);
		moveCamera.position.x = playerPlace.x+700;

		if (moveCamera.position.x > floorBackground.getRegionWidth()+startFloorPlaceX+950){
			startFloorPlaceX += floorBackground.getRegionWidth();
			CoinsAnim.setPlayMode(Animation.PlayMode.LOOP);
			coinsFrame.set(coins+floorBackground.getRegionWidth(),coinsY,Gdx.graphics.getWidth()/10,Gdx.graphics.getHeight()/4);
		}
		if (!coinsFirst){
			coinsFrame.set(coins+floorBackground.getRegionWidth(),coinsY,Gdx.graphics.getWidth()/10,Gdx.graphics.getHeight()/4);
			coinsFirst=true;
		}

		if (moveCamera.position.x > floorBackground.getRegionWidth()+coins+1050){
			coins+=floorBackground.getRegionWidth()+MathUtils.random(1000,1500);
			coinsY=Gdx.graphics.getHeight()/2+MathUtils.random(-400,400);
		}

		// Rectangle
		playerFrame.set(playerPlace.x+player1.getWidth()/3,playerPlace.y+player1.getHeight()/3,Gdx.graphics.getWidth()/18,Gdx.graphics.getHeight()/7);
		floorFrame.set(moveCamera.position.x-background.getWidth()/3,150,Gdx.graphics.getWidth()/10,50);

		if (playerFrame.overlaps(floorFrame) &&  gameState != gameState.GameOver){
			gravity.set(0,0);
			playergravity.set(player_speed_X+LevelUp,0);
			if (playerPlace.y<200)
				playerPlace.y=205;
			i=0;
		}
		if (playerFrame.overlaps(coinsFrame)){
			coinsFrame.set(0,0,0,0);
			CoinsAnim.setPlayMode(Animation.PlayMode.NORMAL);
			point+=10;
			coinSound.play();
		}

		for (Enemy enemy:enemyArray){

			// Rectangle
			enemyFrame.set(enemy.position.y>650 ? enemy.position.x+(enemyFlyImage1.getWidth()/6) :enemy.position.x+(enemyFlyImage1.getWidth()/14), enemy.position.y>650 ?enemy.position.y+(enemyFlyImage1.getWidth()/9) :enemy.position.y,Gdx.graphics.getHeight()/8, enemy.position.y>650 ?Gdx.graphics.getHeight()/7:Gdx.graphics.getHeight()/4);

			if (moveCamera.position.x-enemy.position.x>1150){
				boolean isDown = MathUtils.randomBoolean();
				enemy.position.x+=2500;
				enemy.position.y=isDown ? MathUtils.random(650,1050):150;
				enemy.anim= isDown ?enemyFlyImageAnim : enemyFloorImageAnim;
				enemy.passOn = false;
			}if (enemy.position.x<playerPlace.x && !enemy.passOn ){
				point++;
				enemy.passOn=true;
			}if (playerFrame.overlaps(enemyFrame)){
				if (gameState != gameState.GameOver)
					gameOverSound.play();
				music.pause();
				gameState = gameState.GameOver;
				playergravity.y-=5;
				playergravity.x=0;
			}
		}
	}

	private void drawTheWorld() {

		// region moveCamera batch draw
		moveCamera.update();
		batch.setProjectionMatrix(moveCamera.combined);
		batch.begin();

		batch.draw(background,moveCamera.position.x-background.getWidth()/2,0); // background draw

		batch.draw(floorBackground,startFloorPlaceX,0);							  // floorBackground draw
		batch.draw(floorBackground,startFloorPlaceX+floorBackground.getRegionWidth(),0);

		// CoinsAnim draw
		batch.draw((TextureRegion) CoinsAnim.getKeyFrame(passingTime),coins+floorBackground.getRegionWidth(),coinsY,Gdx.graphics.getWidth()/10,Gdx.graphics.getHeight()/4);

		// enemy draw
		for (Enemy enemy:enemyArray){
			batch.draw(enemy.position.y>650 ?(TextureRegion) enemyFlyImageAnim.getKeyFrame(passingTime) :(TextureRegion) enemyFloorImageAnim.getKeyFrame(passingTime),
					enemy.position.x,enemy.position.y,enemy.position.y>650 ? Gdx.graphics.getWidth()/5 :Gdx.graphics.getWidth()/9,
					enemy.position.y>650 ?Gdx.graphics.getHeight()/3+20:Gdx.graphics.getHeight()/3);
		}

		// player draw
		batch.draw((TextureRegion) player.getKeyFrame(passingTime),playerPlace.x,playerPlace.y,Gdx.graphics.getWidth()/10,Gdx.graphics.getHeight()/4);
		batch.end();

		// endregion

		// region UICamera batch draw
		batch.setProjectionMatrix(UICamera.combined);
		batch.begin();
		if (gameState == gameState.Start)
			batch.draw(readyImage,Gdx.graphics.getWidth()/2 - readyImage.getRegionWidth()/2,Gdx.graphics.getHeight()/2-readyImage.getRegionHeight()/2);
		if (gameState == gameState.GameOver)
			batch.draw(gameOverImage,Gdx.graphics.getWidth()/2 - gameOverImage.getRegionWidth()/2,Gdx.graphics.getHeight()/2-gameOverImage.getRegionHeight()/2);
		if (gameState == gameState.GameOver || gameState == gameState.Running){
			font.getData().setScale(2);
			font.setColor(1,1,1,1);
			font.draw(batch,"SCORE : " +point,150,Gdx.graphics.getHeight()-50);
		//	font.setColor(1,0,0,1);
			//font.draw(batch,""+,300,Gdx.graphics.getHeight()-50);
			//font.setColor(1,1,1,1);
			font.draw(batch,"HIGH SCORE : "+highScore,Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()-50);
			//font.setColor(1,0,0,1);
			//font.draw(batch,""+highScore,Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()-50);
		}
		batch.end();

		// endregion

		// region Rectangle shapeRenderer draw (to try)

		/*shapeRenderer.setProjectionMatrix(moveCamera.combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.setColor(0,0,1,1);
		for (Enemy enemy:enemyArray){

			shapeRenderer.rect(enemy.position.y>650 ? enemy.position.x+(enemyFlyImage1.getWidth()/6) :enemy.position.x+(enemyFlyImage1.getWidth()/14), enemy.position.y>650 ?enemy.position.y+(enemyFlyImage1.getWidth()/9) :enemy.position.y,Gdx.graphics.getHeight()/8, enemy.position.y>650 ?Gdx.graphics.getHeight()/7:Gdx.graphics.getHeight()/4);

		}
		//shapeRenderer.circle(playerPlace.x+Gdx.graphics.getWidth()/20,playerPlace.y+Gdx.graphics.getHeight()/10,Gdx.graphics.getWidth()/18);
		shapeRenderer.rect(playerPlace.x+player1.getWidth()/3,playerPlace.y+player1.getHeight()/3,Gdx.graphics.getWidth()/18,Gdx.graphics.getHeight()/7);
		shapeRenderer.rect(moveCamera.position.x-background.getWidth()/3,150,Gdx.graphics.getWidth()/10,50);
		shapeRenderer.rect(coins+floorBackground.getRegionWidth(),coinsY,Gdx.graphics.getWidth()/20,Gdx.graphics.getHeight()/4);
		//shapeRenderer.circle(moveCamera.position.x-background.getWidth()/3+player1.getWidth(),150,20);
		shapeRenderer.end();*/

		// endregion
	}

}
