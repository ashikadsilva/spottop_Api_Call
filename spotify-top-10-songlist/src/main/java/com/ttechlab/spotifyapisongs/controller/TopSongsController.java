package com.ttechlab.spotifyapisongs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ttechlab.spotifyapisongs.service.SpotifyService;

import java.util.List;

@RestController
@RequestMapping("/get")
public class TopSongsController {

    private final SpotifyService spotifyService;

    @Autowired
    public TopSongsController(SpotifyService spotifyService) {
        this.spotifyService = spotifyService;
    }

    @GetMapping("/tracks")
    public ResponseEntity<String> getTopTracks() {
        List<String> topTracks = spotifyService.getTopTracks();
        StringBuilder output = new StringBuilder();
        output.append("Top 10 Songs :").append("<br>");
        for (int i = 0; i < topTracks.size(); i++) {
            String trackName = topTracks.get(i);
            output.append((i + 1)).append(". ").append(trackName).append("<br>");
        }
        return new ResponseEntity<>(output.toString(), HttpStatus.OK);
    }
}
