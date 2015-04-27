package com.github.pedrovgs.nox;

/**
 * Interface created to be implemented by NoxView clients. Interface implementations will be
 * notified when a NoxItem be clicked.
 *
 * @author Pedro Vicente Gomez Sanchez.
 */
public interface OnNoxItemClickListener {

  OnNoxItemClickListener EMPTY = new OnNoxItemClickListener() {
    @Override public void onNoxItemClicked(int position, NoxItem noxItem) {
      //Empty
    }
  };

  void onNoxItemClicked(int position, NoxItem noxItem);
}
