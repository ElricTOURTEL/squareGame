package com.example.squaregame.dto;

public class BoardResponse {
    private String[][] board;
    private boolean[][] allowedMoves;

    public BoardResponse() {
    }

    public BoardResponse(String[][] board, boolean[][] allowedMoves) {
        this.board = board;
        this.allowedMoves = allowedMoves;
    }

    // Getters and Setters
    public String[][] getBoard() {
        return board;
    }

    public void setBoard(String[][] board) {
        this.board = board;
    }

    public boolean[][] getAllowedMoves() {
        return allowedMoves;
    }

    public void setAllowedMoves(boolean[][] allowedMoves) {
        this.allowedMoves = allowedMoves;
    }
}
