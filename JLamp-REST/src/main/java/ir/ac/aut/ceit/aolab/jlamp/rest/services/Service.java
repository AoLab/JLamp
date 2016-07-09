/*
 * In The Name Of God
 * ======================================
 * [] Project Name : JLamp 
 * 
 * [] Package Name : ir.ac.aut.ceit.aolab.jlamp.rest.services
 *
 * [] Creation Date : 05-07-2016
 *
 * [] Created By : Parham Alvani (parham.alvani@gmail.com)
 * =======================================
*/

package ir.ac.aut.ceit.aolab.jlamp.rest.services;

import ir.ac.aut.ceit.aolab.jlamp.rest.http.IResponse;
import spark.Request;
import spark.Response;

public interface Service {
    IResponse onBootRequest(Request request, Response response);
}
