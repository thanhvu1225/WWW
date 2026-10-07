
package iuh.edu.ongk_05.resource;

import iuh.edu.ongk_05.model.DanhMuc;
import iuh.edu.ongk_05.service.DanhMucService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;
@Path("/danhmuc")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class DanhMucResource {
    private final DanhMucService danhMucService = new DanhMucService();

    @GET
    public Response getAllOrSearch(@QueryParam("name") String name){
        List<DanhMuc> list;
        if(name != null && !name.trim().isEmpty()){
            list = danhMucService.searchByName(name);
        }else {
            list = danhMucService.getAll();
        }
        return Response.ok(list).build();

    }

    @GET
    @Path("/{id}")
    public Response getById(@PathParam("id") int id){
        DanhMuc danhMuc = danhMucService.getById(id);
        if(danhMuc != null){
            return Response.ok(danhMuc).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"message\": \"Không tìm thấy danh mục\"}")
                .build();
    }

    @POST
    public Response create(DanhMuc danhMuc){
        if(danhMuc == null || danhMuc.getTenDanhMuc() == null || danhMuc.getTenDanhMuc().trim().isEmpty()){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\": \"Tên danh mục không được để rỗng\"}")
                    .build();
        }
        boolean ok = danhMucService.save(danhMuc);
        if(ok){
            return Response.status(Response.Status.CREATED)
                    .entity(danhMuc)
                    .build();
        }
        return Response.status(Response.Status.BAD_REQUEST)
                .entity("{\"message\": \"Không thể thêm danh mục\"}")
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") int id, DanhMuc danhMuc){
        danhMuc.setMaDM(id);
        if(danhMuc == null || danhMuc.getTenDanhMuc() == null || danhMuc.getTenDanhMuc().trim().isEmpty()){
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"message\": \"Tên danh mục không được để rỗng\"}")
                    .build();
        }
        boolean ok = danhMucService.update(danhMuc);
        if(ok){
            return Response.ok(danhMuc).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"message\": \"Không thể cập nhật danh mục\"}")
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") int id){
        boolean ok = danhMucService.delete(id);
        if(ok){
            return Response.noContent().build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"message\": \"Không thể xóa danh mục\"}")
                .build();
    }
}
