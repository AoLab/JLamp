/*
 * In The Name Of God
 * ======================================
 * [] Project Name : JLamp 
 * 
 * [] Package Name : ir.ac.aut.ceit.aolab.jlamp.rest.helper
 *
 * [] Creation Date : 05-07-2016
 *
 * [] Created By : Parham Alvani (parham.alvani@gmail.com)
 * =======================================
*/

package ir.ac.aut.ceit.aolab.jlamp.rest.http;

import com.google.gson.Gson;

import spark.ResponseTransformer;

public class JsonTransformer implements ResponseTransformer {
    private Gson gson = new Gson();

    @Override
    public String render(Object model) {
        return gson.toJson(model);
    }
}
