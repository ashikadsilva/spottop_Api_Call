package com.ttechlab.spotifyapisongs.entity;

import java.util.List;

public class TopTracksResponse {
	private List<Track> items;

	public List<Track> getItems() {
		return items;
	}

	public void setItems(List<Track> items) {
		this.items = items;
	}
}
