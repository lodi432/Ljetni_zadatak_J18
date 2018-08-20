/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package glavacevic.ljetnizadatak;

/**
 *
 * @author domagoj
 */
public class Pjesme {

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
    
    private String path;
    
      @Override
    public String toString() {
        return getPath();
    }
}
