package com.sotron.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sotron.GdxGame;
import com.sotron.assets.AssetService;
// import com.sotron.asset.AtlasAsset;
// import com.sotron.asset.SkinAsset;
// import com.sotron.asset.SoundAsset;
import com.sotron.assets.MapAsset;

public class LoadingScreen extends ScreenAdapter {

    private final GdxGame game;
    private final AssetService assetService;
    private final Viewport viewport;
    private final OrthographicCamera camera;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final Batch batch;

    public LoadingScreen(GdxGame game) {
        this.game = game;
        this.assetService = game.getAssetService();
        this.viewport = game.getViewport();
        this.camera = game.getCamera();
        this.batch = game.getBatch();
        this.mapRenderer = new OrthogonalTiledMapRenderer(null, GdxGame.UNIT_SCALE, this.batch);
    }

    /**
     * Queues all required assets for loading.
     */
    @Override
    public void show() {
        this.assetService.load(MapAsset.MAIN);
        this.mapRenderer.setMap(this.assetService.get(MapAsset.MAIN));
        // for (AtlasAsset atlasAsset : AtlasAsset.values()) {
        //     assetService.queue(atlasAsset);
        // }
        // assetService.queue(SkinAsset.DEFAULT);
        // for (SoundAsset soundAsset : SoundAsset.values()) {
        //     assetService.queue(soundAsset);
        // }
    }

    /**
     * Updates asset loading progress and transitions to menu when complete.
     */
    @Override
    public void render(float delta) {
        this.viewport.apply();
        this.batch.setColor(Color.WHITE);
        this.mapRenderer.render();
        this.mapRenderer.setView(this.camera);
        super.render(delta);
        this.mapRenderer.dispose();
        // if (assetService.update()) {
        //     Gdx.app.debug("LoadingScreen", "Finished loading assets");
        //     createScreens();
        //     this.game.removeScreen(this);
        //     this.dispose();
        //     // this.game.setScreen(MenuScreen.class);
        // }
    }

    private void createScreens() {
        this.mapRenderer.dispose();
        // this.game.addScreen(new GameScreen(this.game));
        // this.game.addScreen(new MenuScreen(this.game));
    }
}