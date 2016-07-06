/*
 * In The Name Of God
 * ======================================
 * [] Project Name : JLamp 
 * 
 * [] Package Name : ir.ac.aut.ceit.aolab.jlamp.rest.kaa
 *
 * [] Creation Date : 05-07-2016
 *
 * [] Created By : Parham Alvani (parham.alvani@gmail.com)
 * =======================================
*/

package ir.ac.aut.ceit.aolab.jlamp.rest.kaa;

public class KaaTurnRequest {
    private String id;
    private boolean status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
