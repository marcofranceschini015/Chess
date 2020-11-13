package com.chess.engine.board;

import com.chess.engine.pieces.*;
import com.google.common.collect.ImmutableMap;

import java.util.HashMap;
import java.util.Map;

/**
 * This class represents a Tile of the chess board
 * @author Marco Franceschini
 */
public abstract class Tile {

    //final because it can't change
    protected final int tileCoordinate;

    //a map to have all the possibile empty tile
    private static final Map<Integer, EmptyTile> EMPTY_TILE = createAllPossibleEmptyTile();


    //constructor
    private Tile(int tileCoordinate){
        this.tileCoordinate = tileCoordinate;
    }

    /**
     * This is the real method to create a tile from the external
     * we have all the possibile empty tiles, and if the piece is null, we take one of that,
     * if the piece is not null, we create an occupy tile with the passed piece
     * @param tileCoordinate the coordinates of the tile
     * @param piece the piece that occupy the tile (null if anyone)
     * @return an occupied tile, or an empty tile from the map
     */
    public static Tile createTile(final int tileCoordinate, final Piece piece){
        return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILE.get(tileCoordinate);
    }


    /**
     * it creates a map with all the empty tiles at the beginning.
     * Use an external library for the immutable map
     * @return
     */
    private static Map<Integer, EmptyTile> createAllPossibleEmptyTile() {

        final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();

        for(int i = 0; i < 64; i++){
            emptyTileMap.put(i, new EmptyTile(i));
        }
        return ImmutableMap.copyOf(emptyTileMap); //guava library to become immutable
    }


    /**
     * to define if the tile is occupied or not
     * @return true if is occupied, false otherwise
     */
    public abstract boolean isTileOccupied();

    /**
     * to return the piece that occupy a tile
     * @return the piece, or null if the tile is empty
     */
    public abstract Piece getPiece();



    //INNER CLASSES:
    //extends tile in 2, empty or occupied tile
    //static final --> immutability
    /**
     * Inner class that represents an empty tile, it derives from tile
     * we use that to create a map with all the possible empty tiles
     */
    public static final class EmptyTile extends Tile{
        public EmptyTile(final int coordinate){
            super(coordinate);
        }

        @Override
        public boolean isTileOccupied(){
            return false;
        }

        @Override
        public Piece getPiece() {
            return null;
        }
    }

    /**
     * Inner class that represents an occupied tile, it derives from tile
     * if there is a piece in the tile creation method, we use this inner class
     */
    public static final class OccupiedTile extends Tile{

        private final Piece pieceOnTile;

        public OccupiedTile(int coordinate, Piece pieceOnTile){
            super(coordinate);
            this.pieceOnTile = pieceOnTile;
        }

        @Override
        public boolean isTileOccupied() {
            return true;
        }

        @Override
        public Piece getPiece() {
            return this.pieceOnTile;
        }
    }
}
