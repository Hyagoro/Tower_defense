package towerDef;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;


public class Game extends StateBasedGame {
    private static String title = "TD Serveur";
    private static final int play = 1;
    private static final int menu = 0;
    private static int maxFPS = 90;

    private Game() {
        super(title);
        this.addState(new Menu(menu));
        this.addState(new TowerDef(play));
    }

    public void initStatesList(GameContainer gc) throws SlickException {
        //this.getState(menu).init(gc, this);
        //this.getState(play).init(gc, this);
        this.enterState(play);
    }


    public static void main(String[] args) {
        AppGameContainer application;
        try {
            application = new AppGameContainer(new Game());
            //application.setDisplayMode(application.getScreenWidth(), application.getScreenHeight(), true);
            application.setDisplayMode(800, 500, false);
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