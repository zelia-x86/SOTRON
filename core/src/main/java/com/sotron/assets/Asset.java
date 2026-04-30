package com.sotron.assets;

import com.badlogic.gdx.assets.AssetDescriptor;

public interface Asset<T> {
  public AssetDescriptor<T> getDescriptor ();
}
