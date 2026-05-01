package com.sotron.systems;

import java.util.Comparator;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.SortedIteratingSystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sotron.GdxGame;
import com.sotron.assets.AssetService;
import com.sotron.components.Transform;
import com.sotron.components.Graphic;

public class RenderSystem extends SortedIteratingSystem implements Disposable {
    private final Batch batch;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final Viewport viewport;
    private final AssetService assetService;
    private final OrthographicCamera camera;

    public RenderSystem (Batch batch, Viewport viewport, AssetService assetService, OrthographicCamera camera) {
        super (
            Family.all(Transform.class, Graphic.class).get(),
            Comparator.comparing(Transform.MAPPER::get)
        );
        this.batch = batch;
        this.camera = camera;
        this.assetService = assetService;
        this.viewport = viewport;
        this.mapRenderer = new OrthogonalTiledMapRenderer(null, GdxGame.UNIT_SCALE, this.batch);
    }

    public void setMap (TiledMap map) {
        this.mapRenderer.setMap(map);
    }
    
    @Override
    public void update(float deltaTime) {
        this.viewport.apply();
        this.batch.setColor(Color.WHITE);
        this.mapRenderer.render();
        this.mapRenderer.setView(this.camera);

        forceSort();
        super.update(deltaTime);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        Transform transform = Transform.MAPPER.get(entity);
        Graphic graphic = Graphic.MAPPER.get(entity);
        if (graphic.getRegion() == null) {
            return;
        }

        Vector2 position = transform.getPosition();
        Vector2 scaling = transform.getScaling();
        Vector2 size = transform.getSize();
        this.batch.setColor(graphic.getColor());
        this.batch.draw(
            graphic.getRegion(),
            position.x - (1f - scaling.x) * size.x * 0.5f,
            position.y - (1f - scaling.y) * size.y * 0.5f,
            size.x * 0.5f, size.y * 0.5f,
            size.x, size.y,
            scaling.x, scaling.y,
            transform.getRotationDeg()
        );
    }
    
    @Override
    public void dispose() {
        this.mapRenderer.dispose();
    }    
}
