package com.sotron.screen;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sotron.GdxGame;
import com.sotron.assets.AssetService;
import com.sotron.assets.MapAsset;
import com.sotron.systems.RenderSystem;

public class LoadingScreen extends ScreenAdapter {

    private final GdxGame game;
    private final AssetService assetService;
    private final Viewport viewport;
    private final OrthographicCamera camera;
    private final Batch batch;
    private final Engine engine;

    public LoadingScreen(GdxGame game) {
        this.game = game;
        this.assetService = game.getAssetService();
        this.viewport = game.getViewport();
        this.camera = game.getCamera();
        this.batch = game.getBatch();
        this.engine = new Engine();

        this.engine.addSystem(new RenderSystem(batch, viewport, assetService, camera));
    }

    /**
     * Queues all required assets for loading.
     */
    @Override
    public void show() {
        this.assetService.load(MapAsset.MAIN);
        this.engine.getSystem(RenderSystem.class)
            .setMap(this.assetService.get(MapAsset.MAIN));
    }

    @Override
    public void hide () {
        this.engine.removeAllEntities();
    }

    /**
     * Updates asset loading progress and transitions to menu when complete.
     */
    @Override
    public void render(float delta) {
        delta = Math.min(delta, 1/30f);
        this.engine.update(delta);

        super.render(delta);
    }

    @Override
    public void dispose () {
        for (EntitySystem system : this.engine.getSystems()) {
            if (system instanceof Disposable disposableSystem) {
                disposableSystem.dispose();
            }            
        }
    }
}