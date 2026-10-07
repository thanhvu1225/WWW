
package iuh.edu.restdb.resource;

import iuh.edu.restdb.model.Employee;
import iuh.edu.restdb.service.EmployeeService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/employees")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EmployeeResource {

    private final EmployeeService employeeService = new EmployeeService();

    @GET
    public Response getAll(@QueryParam("deptId") Integer deptId){
        List<Employee> list;
        if(deptId != null){
            list = employeeService.getByDepartmentId(deptId);
        }else {
            list = employeeService.getAll();
        }
        return Response.ok(list).build();
    }
    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") int id){
        Employee emp = employeeService.getById(id);
        if(emp != null){
            return Response.ok(emp).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"message\": \"Employee not found\"}")
                .build();
    }
    @POST
    public Response create(Employee emp){
        boolean success = employeeService.save(emp);
        if(success){
            return Response.status(Response.Status.CREATED).entity(emp).build();
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"message\": \"Cannot create employee\"}")
                .build();
    }
    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, Employee emp){
        emp.setId(id);
        boolean success = employeeService.update(emp);
        if(success){
            return Response.ok(emp).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"message\": \"Cannot update employee\"}")
                .build();
    }
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id){
        boolean success = employeeService.delete(id);
        if(success){
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"message\": \"Cannot delete employee\"}")
                .build();
    }
}
