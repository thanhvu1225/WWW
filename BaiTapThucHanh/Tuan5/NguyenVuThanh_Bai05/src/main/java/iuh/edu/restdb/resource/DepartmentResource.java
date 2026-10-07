
package iuh.edu.restdb.resource;

import iuh.edu.restdb.model.Department;
import iuh.edu.restdb.service.DepartmentService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
@Path("/departments")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DepartmentResource {

    private final DepartmentService departmentService = new DepartmentService();

    @GET
    public Response getAllOrSearch(@QueryParam("name") String name){
        List<Department> list;
        if(name != null && !name.trim().isEmpty()){
            list = departmentService.searchByName(name.trim());
        }else {
            list = departmentService.getAll();
        }
        return Response.ok(list).build();
    }
    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") int id){
        Department dept = departmentService.getById(id);
        if(dept != null){
            return Response.ok(dept).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"message\": \"Department not found\"}")
                .build();
    }
    @POST
    public Response create(Department dept){
        boolean success = departmentService.save(dept);
        if(success){
            return Response.status(Response.Status.CREATED).entity(dept).build();
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"message\": \"Cannot create department\"}")
                .build();
    }
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, Department dept){
        dept.setId(id);
        boolean success = departmentService.update(dept);
        if(success){
            return Response.ok(dept).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"message\": \"Cannot update department\"}")
                .build();
    }
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id){
        boolean success = departmentService.delete(id);
        if(success){
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"message\": \"Cannot delete department\"}")
                .build();
    }
}
