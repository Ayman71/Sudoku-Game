/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Backend;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author Ayman
 */
public interface Viewable {

    Catalog getCatalog();

    // Returns a random game with the specified difficulty
    // Note: the Game class is the representation of the soduko game in the controller
    Game getGame(DifficultyEnum level) throws NotFoundException;

    // Gets a sourceSolution and generates three levels of difficulty
    void driveGames(Game source) throws SolutionInvalidException;

    // Given a game, if invalid returns invalid and the locates the invalid duplicates
    // if valid and complete, return a value
    // if valid and incomplete, returns another value
    // The exact repersentation as a string is done as you best see fit
    String verifyGame(Game game) ;
    // returns the correct combination for the missing numbers
    // Hint: So, there are many ways you can approach this, one way is
    // to have a way to map an index in the combination array to its location in the board

    // one other way to to try to encode the location and the answer all in just one int
    int[] solveGame(Game game) throws InvalidGame;

    // Logs the user action
    void logUserAction(String userAction) throws IOException;

}
