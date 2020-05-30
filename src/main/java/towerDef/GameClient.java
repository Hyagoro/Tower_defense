package towerDef;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class GameClient extends StateBasedGame {
    public static String title = "TD Client";
    public static final int play = 1;
    public static final int menu = 0;
    public static int maxFPS = 90;

    public GameClient() {
        super(title);
        this.addState(new Menu(menu));
        this.addState(new JeuClient(play));
    }

    public void initStatesList(GameContainer gc) throws SlickException {
        //this.getState(menu).init(gc, this);
        //this.getState(play).init(gc, this);
        this.enterState(play);
    }


    public static void main(String[] args) {
        AppGameContainer application;
        try {
            application = new AppGameContainer(new GameClient());
            application.setDisplayMode(500, 500, false);//application.getScreenWidth(),application.getScreenHeight()//option full screen
            application.setShowFPS(true);
            application.setVSync(true);
            application.setMultiSample(4);
            application.setTargetFrameRate(maxFPS);
            application.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }

}