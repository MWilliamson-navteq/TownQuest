package com.mwilliamson.townquest.app;

import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResult;
import com.jme3.collision.CollisionResults;
import com.jme3.input.KeyInput;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.niftygui.NiftyJmeDisplay;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.mwilliamson.townquest.input.InputHandler;
import com.mwilliamson.townquest.input.KeyPressListener;
import com.mwilliamson.townquest.input.MouseListener;
import de.lessvoid.nifty.Nifty;
import de.lessvoid.nifty.builder.ScreenBuilder;
import de.lessvoid.nifty.screen.DefaultScreenController;

public class GameLoop extends SimpleApplication
{
    private final InputHandler inputHandler = new InputHandler();
    private static final int TARGET_FPS = 120;
    private Node pivot;
    private Geometry blueBox;
    private Geometry redBox;
    public GameLoop()
    {
        setShowSettings(false);
        setSettings(createDefaultSettings());
    }

    @Override
    public void simpleInitApp()
    {
        inputHandler.setup(inputManager);
        getFlyByCamera().setEnabled(false);
        /** create a blue box at coordinates (1,-1,1) */
        Box box1 = new Box(1,1,1);
        blueBox = new Geometry("Box", box1);
        blueBox.setLocalTranslation(new Vector3f(1,-1,1));
        Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setColor("Color", ColorRGBA.Blue);
        blueBox.setMaterial(mat1);

        /** create a red box straight above the blue one at (1,3,1) */
        Box box2 = new Box(1,1,1);
        redBox = new Geometry("Box", box2);
        redBox.setLocalTranslation(new Vector3f(1,3,1));
        Material mat2 = new Material(assetManager,  "Common/MatDefs/Misc/Unshaded.j3md");
        mat2.setColor("Color", ColorRGBA.Red);
        redBox.setMaterial(mat2);

        /** Create a pivot node at (0,0,0) and attach it to the root node */
        pivot = new Node("pivot");
        rootNode.attachChild(pivot); // put this node in the scene

        /** Attach the two boxes to the *pivot* node. (And transitively to the root node.) */
        pivot.attachChild(blueBox);
        pivot.attachChild(redBox);

  /*      Material background = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        background.setColor("Color", ColorRGBA.White);
        getRootNode().setMaterial(background);*/

        InputHandler.addKeyPressListener(new KeyPressListener()
        {
            @Override
            public void onKeyDown(int key)
            {
            }

            @Override
            public void onKeyUp(int key)
            {
                if (key == KeyInput.KEY_ESCAPE)
                    stop();
            }
        });

        InputHandler.addMouseListener(new MouseListener()
        {
            @Override
            public void onMove(int endX, int endY)
            {
            }

            @Override
            public void onLeftClickDown()
            {
                for (CollisionResult collisionResults : checkMouseCollision(rootNode))
                {
                    //collisionResults.getGeometry().rotate(1.0f, 0, 0);
                    collisionResults.getGeometry().setUserData("isSpinning", false);
                }
            }

            @Override
            public void onRightCLickDown()
            {
            }

            @Override
            public void onLeftClickUp()
            {
                for (CollisionResult collisionResults : checkMouseCollision(rootNode))
                {
                    //collisionResults.getGeometry().rotate(1.0f, 0, 0);
                    collisionResults.getGeometry().setUserData("isSpinning", true);
                }
            }

            @Override
            public void onRightClickUp()
            {
            }

            @Override
            public void onScrollUp()
            {
            }

            @Override
            public void onScrollDown()
            {
            }
        });

        Box box = new Box(15, .2f, 15);
        Geometry floor = new Geometry("the Floor", box);
        floor.setLocalTranslation(0, -4, -5);
        Material mat11 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat11.setColor("Color", ColorRGBA.Gray);
        floor.setMaterial(mat11);
        rootNode.attachChild(floor);

        blueBox.setUserData("isSpinning", true);
        redBox.setUserData("isSpinning", true);

        NiftyJmeDisplay niftyDisplay = new NiftyJmeDisplay( assetManager, inputManager, audioRenderer, guiViewPort);
        Nifty nifty = niftyDisplay.getNifty();
        nifty.setDebugOptionPanelColors(true);
        guiViewPort.addProcessor(niftyDisplay);

        nifty.addScreen("start", new ScreenBuilder("start")
        {
            {
                controller(new DefaultScreenController());
                // <!-- ... -->
            }
        }.build(nifty));

        nifty.addScreen("hud", new ScreenBuilder("hud")
        {{
                controller(new DefaultScreenController());
                // <!-- ... -->
            }}.build(nifty));



    }

    private CollisionResults checkMouseCollision(Node geometryCollection)
    {
        Vector3f mousePosition = cam.getWorldCoordinates(inputManager.getCursorPosition(), 0f);
        Vector3f direction = cam.getWorldCoordinates(inputManager.getCursorPosition(), 1f); // Add 1f to the Z projection to make sure it's "in front" of the camera
        direction.subtractLocal(mousePosition).normalizeLocal();
        Ray ray = new Ray(mousePosition, direction);
        CollisionResults results = new CollisionResults();

        geometryCollection.collideWith(ray, results);

        return results;
    }

    @Override
    public void simpleUpdate(float tpf)
    {
        //pivot.rotate(0.01f, 0, 0);
        if (blueBox.getUserData("isSpinning"))
            blueBox.rotate(0, 1f * tpf, 0);
        if (redBox.getUserData("isSpinning"))
            redBox.rotate(0, 2f * tpf, 0);
        checkFPS(tpf);
    }

    public AppSettings createDefaultSettings()
    {
        AppSettings settings = new AppSettings(true);
        settings.setFullscreen(false);
        settings.setTitle("TownQuest");
        settings.setFrameRate(TARGET_FPS);
        settings.setRenderer(AppSettings.LWJGL_OPENGL2);


        return settings;
    }

    int frameFailures = 0;
    private static final double FPS_FAILURE_THRESHOLD = 1000 / TARGET_FPS;

    private void checkFPS(float tpf)
    {
        if (tpf > FPS_FAILURE_THRESHOLD)
            frameFailures ++;
        else
            frameFailures = 0;

        if (frameFailures > TARGET_FPS)
            throw new RuntimeException("Poor FPS, killing application");
    }
}
