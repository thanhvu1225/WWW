
package iuh.edu.ongk_05.resource;

import iuh.edu.ongk_05.model.TinTuc;
import iuh.edu.ongk_05.service.TinTucService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/tintuc")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TinTucResource {

    private final TinTucService tinTucService = new TinTucService();

    @GET
    public Response getTinTuc(@QueryParam("maDM") Integer maDM){
        List<TinTuc> list;
        if(maDM != null && maDM > 0){
            list = tinTucService.getByMaDM(maDM);
        }else {
            list = tinTucService.getAll();
        }
        return Response.ok(list).build();
    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") int id){
        TinTuc tinTuc = tinTucService.getById(id);
        if(tinTuc != null){
            return Response.ok(tinTuc).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"message\": \"Không tìm thấy tin tức\"}")
                .build();
    }

    @POST
    public Response create(TinTuc tinTuc){
        String error = tinTucService.validate(tinTuc);
        if(error != null){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"" + error + "\"}")
                    .build();
        }
        boolean ok = tinTucService.save(tinTuc);
        if(ok){
            return Response.ok(tinTuc).build();
        }
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("{\"message\": \"Không thể thêm tin tức\"}")
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id){
        boolean ok = tinTucService.delete(id);
        if(ok){
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"message\": \"Không thể xóa tin tức\"}")
                .build();
    }

}
