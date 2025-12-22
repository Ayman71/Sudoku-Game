/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Backend;

/**
 *
 * @author Ayman
 */
public interface Command {
    void execute() throws Exception;

    void undo() throws Exception;
}
