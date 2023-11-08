package com.ttechlab.spotifyapisongs.entity;

import java.util.List;

public class Track {
	private String name;
	private List<Artist> artists;
	private Album album;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Artist> getArtists() {
		return artists;
	}

	public void setArtists(List<Artist> artists) {
		this.artists = artists;
	}

	public Album getAlbum() {
		return album;
	}

	public void setAlbum(Album album) {
		this.album = album;
	}
}
