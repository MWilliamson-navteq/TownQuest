package com.mwilliamson.townquest.app;

import com.jme3.app.SimpleApplication;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.input.ChaseCamera;
import com.jme3.input.KeyInput;
import com.jme3.light.AmbientLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.scene.shape.Quad;
import com.jme3.scene.shape.Sphere;
import com.jme3.system.AppSettings;
import com.jme3.util.SkyFactory;
import com.jme3.water.SimpleWaterProcessor;
import com.mwilliamson.townquest.app.controls.RotateControl;
import com.mwilliamson.townquest.game.character.Character;
import com.mwilliamson.townquest.game.event.EventManager;
import com.mwilliamson.townquest.input.InputHandler;
import com.mwilliamson.townquest.input.InteractableMouse;
import com.mwilliamson.townquest.input.KeyPressListener;

public class GameLoop extends SimpleApplication
{
    private InteractableMouse mouse;
    private final InputHandler inputHandler = new InputHandler();
    private static final int TARGET_FPS = 120;
    private Node pivot;
    private Geometry blueBox;
    private Geometry redBox;
    private ChaseCamera chaseCamera;

    public GameLoop()
    {
        setShowSettings(false);
        setSettings(createDefaultSettings());
    }

    @Override
    public void simpleInitApp()
    {
        Node skyNode = new Node("skyNode");
        Spatial sky = SkyFactory.createSky(assetManager, "Textures/Sky/Bright/BrightSky.dds", false);
        skyNode.attachChild(sky);

        rootNode.attachChild(skyNode);
        inputHandler.setup(inputManager);
        getFlyByCamera().setEnabled(false);
        /** create a blue box at coordinates (1,-1,1) */
        Sphere box1 = new Sphere(180,180,1f);
        blueBox = new Geometry("Box", box1);
        blueBox.setLocalTranslation(new Vector3f(1,-1,1));
        Material mat1 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat1.setColor("Color", ColorRGBA.Blue);
        blueBox.setMaterial(mat1);
        EventManager.createInteractableGeomatry(blueBox);
        blueBox.setUserData("movingUp", true);

        mouse = new InteractableMouse(cam, inputManager, rootNode);

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

        EventManager.createInteractableGeomatry(blueBox);
        EventManager.createInteractableGeomatry(redBox);

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



        Box box = new Box(15, .2f, 15);
        /*Geometry floor = new Geometry("the Floor", box);
        floor.setLocalTranslation(0, -4, -5);
        Material mat11 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat11.setColor("Color", ColorRGBA.Gray);
        floor.setMaterial(mat11);
        rootNode.attachChild(floor);*/

        blueBox.setUserData("isSpinning", true);
        redBox.setUserData("isSpinning", true);

        //CameraNode cameraNode = new CameraNode("cameraNode", cam);
        //cameraNode.setControlDir(CameraControl.ControlDirection.SpatialToCamera);
        com.mwilliamson.townquest.game.character.Character aelis = new Character(assetManager, rootNode, cam);

        //chaseCamera = new ChaseCamera(cam, aelis.getSpatial(), inputManager);
        //rootNode.attachChild(cameraNode);
        //cameraNode.setLocalTranslation(0f, 3f, 0f);

       // jme3tools.optimize.GeometryBatchFactory.optimize(rootNode);
        redBox.addControl(new RotateControl());

        AmbientLight sun = new AmbientLight();
        //sun.setDirection(new Vector3f(-0.1f, -0.7f, -1.0f));
        rootNode.addLight(sun);
        //setupCamera();
        setupWorld();


    }

    private void setupWorld()
    {
        Node worldNode = new Node();
        rootNode.attachChild(worldNode);

        assetManager.registerLocator("C://Projects//TownQuest//assets", FileLocator.class);
        assetManager.registerLocator("C://Projects//TownQuest//assets/Models", FileLocator.class);
        Spatial terrain = assetManager.loadModel("obj.j3o");
        terrain.setLocalScale(3.0f);
        worldNode.attachChild(terrain);


        SimpleWaterProcessor waterProcessor = new SimpleWaterProcessor(assetManager);
        waterProcessor.setReflectionScene(getRootNode().getChild("skyNode"));

        viewPort.addProcessor(waterProcessor);

        waterProcessor.setWaterDepth(1);         // transparency of water
        waterProcessor.setDistortionScale(1f); // strength of waves
        waterProcessor.setWaveSpeed(0.05f);       // speed of waves

        Quad quad = new Quad(40,40);

        Geometry water=new Geometry("water", quad);
        water.setShadowMode(RenderQueue.ShadowMode.Receive);
        water.setMaterial(waterProcessor.getMaterial());
        water.setLocalTranslation(0f, -2f, 40f);
        worldNode.attachChild(water);
    }

    @Override
    public void simpleUpdate(float tpf)
    {
        //pivot.rotate(0.01f, 0, 0);
        if (blueBox.getUserData("isSpinning"))
        {
            float y = blueBox.getLocalTransform().getTranslation().getY();
            if (y < -1f)
                blueBox.setUserData("movingUp", true);
            else if (y > 1f)
                blueBox.setUserData("movingUp", false);

            if (blueBox.getUserData("movingUp"))
                blueBox.move(0f, 1f * tpf, 0f);
            else
                blueBox.move(0f, -1f * tpf, 0f);
        }

/*        if (redBox.getUserData("isSpinning"))
            redBox.rotate(0, 2f * tpf, 0);*/

        //pivot.rotate(0f, 0f, 1f * tpf);
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
