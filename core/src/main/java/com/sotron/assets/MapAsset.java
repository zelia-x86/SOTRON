package com.sotron.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public enum MapAsset implements Asset<TiledMap> {
  MAIN("spawn.tmx");

  MapAsset (String mapName) {
    // TmxMapLoader.Parameters params = new TmxMapLoader().
    this.descriptor = new AssetDescriptor<>("maps/" + mapName, TiledMap.class);
  }
  
  private final AssetDescriptor<TiledMap> descriptor;

  @Override
  public AssetDescriptor<TiledMap> getDescriptor () {
    return this.descriptor;
  }
}
