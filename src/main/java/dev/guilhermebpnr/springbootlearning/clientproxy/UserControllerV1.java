package dev.guilhermebpnr.springbootlearning.clientproxy;

import dev.guilhermebpnr.springbootlearning.model.User;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

public interface UserControllerV1 {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    List<User> fetchUsers(@QueryParam("gender") String gender);

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{userUid}")
    Response fetchUser(@PathParam("userUid") UUID userUid);

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response insertUser(User user);

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    Response updateUser(User user);

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{userUid}")
    Response removeUser(@PathParam("userUid") UUID userUid);
}
