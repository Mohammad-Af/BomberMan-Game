package UI;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Images {
    public static BufferedImage bomberManWU[] = new BufferedImage[5];
    public static BufferedImage bomberManWD[] = new BufferedImage[5];
    public static BufferedImage bomberManWR[] = new BufferedImage[5];
    public static BufferedImage bomberManWL[] = new BufferedImage[5];
    public static BufferedImage bomberManDeath[] = new BufferedImage[5];
    private static BufferedImage[] monster1WR = new BufferedImage[4];
    private static BufferedImage[] monster1WL = new BufferedImage[4];
    private static BufferedImage[] monster1WU = new BufferedImage[4];
    public static BufferedImage[] monster1WD = new BufferedImage[4];
    private static BufferedImage[] monster2WR = new BufferedImage[4];
    private static BufferedImage[] monster2WL = new BufferedImage[4];
    private static BufferedImage[] monster2WU = new BufferedImage[4];
    public static BufferedImage[] monster2WD = new BufferedImage[4];
    private static BufferedImage[] monster3WR = new BufferedImage[4];
    private static BufferedImage[] monster3WL = new BufferedImage[4];
    private static BufferedImage[] monster3WU = new BufferedImage[4];
    public static BufferedImage[] monster3WD = new BufferedImage[4];
    private static BufferedImage[] monster4WR = new BufferedImage[4];
    private static BufferedImage[] monster4WL = new BufferedImage[4];
    private static BufferedImage[] monster4WU = new BufferedImage[4];
    public static BufferedImage[] monster4WD = new BufferedImage[4];
    public static BufferedImage[] monsterDeath = new BufferedImage[12];
    public static BufferedImage bomb[] = new BufferedImage[3];
    public static BufferedImage throwBomb;
    public static BufferedImage blockImage;
    public static BufferedImage wallImage;
    public static BufferedImage background;
    public static BufferedImage fire;
    public static BufferedImage wallBurning[] = new BufferedImage[3];
    public static BufferedImage bombIncreaser;
    public static BufferedImage bombDecrease;
    public static BufferedImage bombControl;
    public static BufferedImage pointUp;
    public static BufferedImage pointDown;
    public static BufferedImage radiusUp;
    public static BufferedImage radiusDown;
    public static BufferedImage speedUp;
    public static BufferedImage speedDown;
    public static BufferedImage ghostPower;
    public static BufferedImage openGate;
    public static BufferedImage closeGate;
    public static BufferedImage burning[] = new BufferedImage[3];

    public static BufferedImage monsterWR[][] = new BufferedImage[4][4];
    public static BufferedImage monsterWL[][] = new BufferedImage[4][4];
    public static BufferedImage monsterWU[][] = new BufferedImage[4][4];
    public static BufferedImage monsterWD[][] = new BufferedImage[4][4];


    public Images() throws IOException {

        String fs =File.separator;

        bomberManWU[0] = ImageIO.read(new File("bomberman"+fs+"WU1.png"));
        bomberManWU[1] = ImageIO.read(new File("bomberman"+fs+"WU2.png"));
        bomberManWU[2] = ImageIO.read(new File("bomberman"+fs+"WU3.png"));
        bomberManWU[3] = ImageIO.read(new File("bomberman"+fs+"WU4.png"));
        bomberManWU[4] = ImageIO.read(new File("bomberman"+fs+"WU5.png"));

        bomberManWD[0] = ImageIO.read(new File("bomberman"+fs+"WD1.png"));
        bomberManWD[1] = ImageIO.read(new File("bomberman"+fs+"WD2.png"));
        bomberManWD[2] = ImageIO.read(new File("bomberman"+fs+"WD3.png"));
        bomberManWD[3] = ImageIO.read(new File("bomberman"+fs+"WD4.png"));
        bomberManWD[4] = ImageIO.read(new File("bomberman"+fs+"WD5.png"));

        bomberManWR[0] = ImageIO.read(new File("bomberman"+fs+"WR1.png"));
        bomberManWR[1] = ImageIO.read(new File("bomberman"+fs+"WR2.png"));
        bomberManWR[2] = ImageIO.read(new File("bomberman"+fs+"WR3.png"));
        bomberManWR[3] = ImageIO.read(new File("bomberman"+fs+"WR4.png"));
        bomberManWR[4] = ImageIO.read(new File("bomberman"+fs+"WR5.png"));

        bomberManWL[0] = ImageIO.read(new File("bomberman"+fs+"WL1.png"));
        bomberManWL[1] = ImageIO.read(new File("bomberman"+fs+"WL2.png"));
        bomberManWL[2] = ImageIO.read(new File("bomberman"+fs+"WL3.png"));
        bomberManWL[3] = ImageIO.read(new File("bomberman"+fs+"WL4.png"));
        bomberManWL[4] = ImageIO.read(new File("bomberman"+fs+"WL5.png"));

        bomb[0] = ImageIO.read(new File("bomberman"+fs+"bomb1 (3).png"));
        bomb[1] = ImageIO.read(new File("bomberman"+fs+"bomb2 (3).png"));
        bomb[2] = ImageIO.read(new File("bomberman"+fs+"bomb3 (3).png"));

        bomberManDeath[0] = ImageIO.read(new File("bomberman"+fs+"death1.png"));
        bomberManDeath[1] = ImageIO.read(new File("bomberman"+fs+"death2.png"));
        bomberManDeath[2] = ImageIO.read(new File("bomberman"+fs+"death3.png"));
        bomberManDeath[3] = ImageIO.read(new File("bomberman"+fs+"death4.png"));
        bomberManDeath[4] = ImageIO.read(new File("bomberman"+fs+"death5.png"));

        //monster1..........................................


        monster1WR[0] = ImageIO.read(new File("bomberman"+fs+"ghost1"+fs+"monsterWR1.png"));
        monster1WR[1] = ImageIO.read(new File("bomberman"+fs+"ghost1"+fs+"monsterWR2.png"));
        monster1WR[2] = ImageIO.read(new File("bomberman"+fs+"ghost1"+fs+"monsterWR3.png"));
        monster1WR[3] = ImageIO.read(new File("bomberman"+fs+"ghost1"+fs+"monsterWR4.png"));

        monster1WL[0] = ImageIO.read(new File("bomberman"+fs+"ghost1"+fs+"monsterWL1.png"));
        monster1WL[1] = ImageIO.read(new File("bomberman"+fs+"ghost1"+fs+"monsterWL2.png"));
        monster1WL[2] = ImageIO.read(new File("bomberman"+fs+"ghost1"+fs+"monsterWL3.png"));
        monster1WL[3] = ImageIO.read(new File("bomberman"+fs+"ghost1"+fs+"monsterWL4.png"));

        monster1WU[0] = ImageIO.read(new File("bomberman"+fs+"ghost1"+fs+"monsterWU1.png"));
        monster1WU[1] = ImageIO.read(new File("bomberman"+fs+"ghost1"+fs+"monsterWU2.png"));
        monster1WU[2] = ImageIO.read(new File("bomberman"+fs+"ghost1"+fs+"monsterWU3.png"));
        monster1WU[3] = ImageIO.read(new File("bomberman"+fs+"ghost1"+fs+"monsterWU4.png"));

        monster1WD[0] = ImageIO.read(new File("bomberman"+fs+"ghost1"+fs+"monsterWD1.png"));
        monster1WD[1] = ImageIO.read(new File("bomberman"+fs+"ghost1"+fs+"monsterWD2.png"));
        monster1WD[2] = ImageIO.read(new File("bomberman"+fs+"ghost1"+fs+"monsterWD3.png"));
        monster1WD[3] = ImageIO.read(new File("bomberman"+fs+"ghost1"+fs+"monsterWD4.png"));


        //monster2..........................................

        monster2WR[0] = ImageIO.read(new File("bomberman"+fs+"ghost2"+fs+"WR1.png"));
        monster2WR[1] = ImageIO.read(new File("bomberman"+fs+"ghost2"+fs+"WR2.png"));
        monster2WR[2] = ImageIO.read(new File("bomberman"+fs+"ghost2"+fs+"WR3.png"));
        monster2WR[3] = ImageIO.read(new File("bomberman"+fs+"ghost2"+fs+"WR4.png"));

        monster2WL[0] = ImageIO.read(new File("bomberman"+fs+"ghost2"+fs+"WL1.png"));
        monster2WL[1] = ImageIO.read(new File("bomberman"+fs+"ghost2"+fs+"WL2.png"));
        monster2WL[2] = ImageIO.read(new File("bomberman"+fs+"ghost2"+fs+"WL3.png"));
        monster2WL[3] = ImageIO.read(new File("bomberman"+fs+"ghost2"+fs+"WL4.png"));

        monster2WU[0] = ImageIO.read(new File("bomberman"+fs+"ghost2"+fs+"WU1.png"));
        monster2WU[1] = ImageIO.read(new File("bomberman"+fs+"ghost2"+fs+"WU2.png"));
        monster2WU[2] = ImageIO.read(new File("bomberman"+fs+"ghost2"+fs+"WU3.png"));
        monster2WU[3] = ImageIO.read(new File("bomberman"+fs+"ghost2"+fs+"WU4.png"));

        monster2WD[0] = ImageIO.read(new File("bomberman"+fs+"ghost2"+fs+"WD1.png"));
        monster2WD[1] = ImageIO.read(new File("bomberman"+fs+"ghost2"+fs+"WD2.png"));
        monster2WD[2] = ImageIO.read(new File("bomberman"+fs+"ghost2"+fs+"WD3.png"));
        monster2WD[3] = ImageIO.read(new File("bomberman"+fs+"ghost2"+fs+"WD4.png"));


        //monster3..........................................

        monster3WR[0] = ImageIO.read(new File("bomberman"+fs+"ghost3"+fs+"monsterWR1.png"));
        monster3WR[1] = ImageIO.read(new File("bomberman"+fs+"ghost3"+fs+"monsterWR2.png"));
        monster3WR[2] = ImageIO.read(new File("bomberman"+fs+"ghost3"+fs+"monsterWR3.png"));
        monster3WR[3] = ImageIO.read(new File("bomberman"+fs+"ghost3"+fs+"monsterWR4.png"));

        monster3WL[0] = ImageIO.read(new File("bomberman"+fs+"ghost3"+fs+"monsterWL1.png"));
        monster3WL[1] = ImageIO.read(new File("bomberman"+fs+"ghost3"+fs+"monsterWL2.png"));
        monster3WL[2] = ImageIO.read(new File("bomberman"+fs+"ghost3"+fs+"monsterWL3.png"));
        monster3WL[3] = ImageIO.read(new File("bomberman"+fs+"ghost3"+fs+"monsterWL4.png"));

        monster3WU[0] = ImageIO.read(new File("bomberman"+fs+"ghost3"+fs+"monsterWU1.png"));
        monster3WU[1] = ImageIO.read(new File("bomberman"+fs+"ghost3"+fs+"monsterWU2.png"));
        monster3WU[2] = ImageIO.read(new File("bomberman"+fs+"ghost3"+fs+"monsterWU3.png"));
        monster3WU[3] = ImageIO.read(new File("bomberman"+fs+"ghost3"+fs+"monsterWU4.png"));

        monster3WD[0] = ImageIO.read(new File("bomberman"+fs+"ghost3"+fs+"monsterWD1.png"));
        monster3WD[1] = ImageIO.read(new File("bomberman"+fs+"ghost3"+fs+"monsterWD2.png"));
        monster3WD[2] = ImageIO.read(new File("bomberman"+fs+"ghost3"+fs+"monsterWD3.png"));
        monster3WD[3] = ImageIO.read(new File("bomberman"+fs+"ghost3"+fs+"monsterWD4.png"));


        //monster4..............................
        monster4WR[0] = ImageIO.read(new File("bomberman"+fs+"ghost4"+fs+"monsterWR1.png"));
        monster4WR[1] = ImageIO.read(new File("bomberman"+fs+"ghost4"+fs+"monsterWR2.png"));
        monster4WR[2] = ImageIO.read(new File("bomberman"+fs+"ghost4"+fs+"monsterWR3.png"));
        monster4WR[3] = ImageIO.read(new File("bomberman"+fs+"ghost4"+fs+"monsterWR4.png"));

        monster4WL[0] = ImageIO.read(new File("bomberman"+fs+"ghost4"+fs+"monsterWL1.png"));
        monster4WL[1] = ImageIO.read(new File("bomberman"+fs+"ghost4"+fs+"monsterWL2.png"));
        monster4WL[2] = ImageIO.read(new File("bomberman"+fs+"ghost4"+fs+"monsterWL3.png"));
        monster4WL[3] = ImageIO.read(new File("bomberman"+fs+"ghost4"+fs+"monsterWL4.png"));

        monster4WU[0] = ImageIO.read(new File("bomberman"+fs+"ghost4"+fs+"monsterWU1.png"));
        monster4WU[1] = ImageIO.read(new File("bomberman"+fs+"ghost4"+fs+"monsterWU2.png"));
        monster4WU[2] = ImageIO.read(new File("bomberman"+fs+"ghost4"+fs+"monsterWU3.png"));
        monster4WU[3] = ImageIO.read(new File("bomberman"+fs+"ghost4"+fs+"monsterWU4.png"));

        monster4WD[0] = ImageIO.read(new File("bomberman"+fs+"ghost4"+fs+"monsterWD1.png"));
        monster4WD[1] = ImageIO.read(new File("bomberman"+fs+"ghost4"+fs+"monsterWD2.png"));
        monster4WD[2] = ImageIO.read(new File("bomberman"+fs+"ghost4"+fs+"monsterWD3.png"));
        monster4WD[3] = ImageIO.read(new File("bomberman"+fs+"ghost4"+fs+"monsterWD4.png"));


        monsterDeath[0] = ImageIO.read(new File("bomberman"+fs+"death"+fs+"death1.png"));
        monsterDeath[1] = ImageIO.read(new File("bomberman"+fs+"death"+fs+"death2.png"));
        monsterDeath[2] = ImageIO.read(new File("bomberman"+fs+"death"+fs+"death3.png"));
        monsterDeath[3] = ImageIO.read(new File("bomberman"+fs+"death"+fs+"death4.png"));
        monsterDeath[4] = ImageIO.read(new File("bomberman"+fs+"death"+fs+"death5.png"));
        monsterDeath[5] = ImageIO.read(new File("bomberman"+fs+"death"+fs+"death6.png"));
        monsterDeath[6] = ImageIO.read(new File("bomberman"+fs+"death"+fs+"death7.png"));
        monsterDeath[7] = ImageIO.read(new File("bomberman"+fs+"death"+fs+"death8.png"));
        monsterDeath[8] = ImageIO.read(new File("bomberman"+fs+"death"+fs+"death9.png"));
        monsterDeath[9] = ImageIO.read(new File("bomberman"+fs+"death"+fs+"death10.png"));
        monsterDeath[10] = ImageIO.read(new File("bomberman"+fs+"death"+fs+"death11.png"));
        monsterDeath[11] = ImageIO.read(new File("bomberman"+fs+"death"+fs+"death12.png"));


        //powerChangers................................
        bombIncreaser = ImageIO.read(new File("bomberman"+fs+"power changers"+fs+"BombIncrease.png"));
        bombDecrease = ImageIO.read(new File("bomberman"+fs+"power changers"+fs+"BombDecrease.png"));
        bombControl = ImageIO.read(new File("bomberman"+fs+"power changers"+fs+"BombControl.png"));
        pointUp = ImageIO.read(new File("bomberman"+fs+"power changers"+fs+"PointUp.png"));
        pointDown = ImageIO.read(new File("bomberman"+fs+"power changers"+fs+"PointDown.png"));
        radiusDown = ImageIO.read(new File("bomberman"+fs+"power changers"+fs+"RadiusDown.png"));
        radiusUp = ImageIO.read(new File("bomberman"+fs+"power changers"+fs+"RadiusUp.png"));
        speedDown = ImageIO.read(new File("bomberman"+fs+"power changers"+fs+"SpeedDown.png"));
        speedUp = ImageIO.read(new File("bomberman"+fs+"power changers"+fs+"SpeedUp.png"));
        ghostPower = ImageIO.read(new File("bomberman"+fs+"power changers"+fs+"ghostPower.png"));


        openGate = ImageIO.read(new File("bomberman"+fs+"open.png"));
        closeGate = ImageIO.read(new File("bomberman"+fs+"close.png"));


        throwBomb = ImageIO.read(new File("bomberman"+fs+"throwBomb.png"));

        blockImage = ImageIO.read(new File("bomberman"+fs+"blockGrass.png"));

        background = ImageIO.read(new File("bomberman"+fs+"grass.png"));

        wallImage = ImageIO.read(new File("bomberman"+fs+"wallGrass2.png"));
        wallBurning[0] = ImageIO.read(new File("bomberman"+fs+"wallBurning1.png"));
        wallBurning[1] = ImageIO.read(new File("bomberman"+fs+"wallBurning2.png"));
        wallBurning[2] = ImageIO.read(new File("bomberman"+fs+"wallBurning3.png"));

        burning[0] = ImageIO.read(new File("bomberman"+fs+"burn1.png"));
        burning[1] = ImageIO.read(new File("bomberman"+fs+"burn2.png"));
        burning[2] = ImageIO.read(new File("bomberman"+fs+"burn3.png"));

        fire = ImageIO.read(new File("bomberman"+fs+"fire2.png"));



        monsterWR[0] = monster1WR;
        monsterWR[1] = monster2WR;
        monsterWR[2] = monster3WR;
        monsterWR[3] = monster4WR;

        monsterWD[0] = monster1WD;
        monsterWD[1] = monster2WD;
        monsterWD[2] = monster3WD;
        monsterWD[3] = monster4WD;

        monsterWL[0] = monster1WL;
        monsterWL[1] = monster2WL;
        monsterWL[2] = monster3WL;
        monsterWL[3] = monster4WL;

        monsterWU[0] = monster1WU;
        monsterWU[1] = monster2WU;
        monsterWU[2] = monster3WU;
        monsterWU[3] = monster4WU;


    }


}
