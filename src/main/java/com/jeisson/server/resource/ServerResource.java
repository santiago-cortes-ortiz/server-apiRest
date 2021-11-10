package com.jeisson.server.resource;

import com.jeisson.server.enumeration.Status;
import com.jeisson.server.model.Response;
import com.jeisson.server.model.Server;
import com.jeisson.server.service.ServerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import static java.time.LocalDateTime.now;
import static org.springframework.http.MediaType.IMAGE_PNG_VALUE;

@RestController
@RequestMapping("/server")
@RequiredArgsConstructor
public class ServerResource {

    private final ServerService serverService;

    @GetMapping("/list")
    public ResponseEntity<Response> getServers(){
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .statusCode(HttpStatus.OK.value())
                        .httpStatus(HttpStatus.OK)
                        .data(Map.of("servers",serverService.list(30)))
                        .message("Servers retrieved")
                        .build()
        );
    }

    @GetMapping("/ping/{ipAddress}")
    public ResponseEntity<Response> pingServer(@PathVariable("ipAddress") String ipAddress) throws IOException {
        Server server = serverService.ping(ipAddress);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .statusCode(HttpStatus.OK.value())
                        .httpStatus(HttpStatus.OK)
                        .data(Map.of("server",server))
                        .message(server.getStatus() == Status.SERVER_UP ? "Ping success":"Ping failed")
                        .build()
        );
    }

    @PostMapping("/save")
    public ResponseEntity<Response> saveServer(@RequestBody @Valid Server server) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .statusCode(HttpStatus.CREATED.value())
                        .httpStatus(HttpStatus.CREATED)
                        .data(Map.of("server",serverService.create(server)))
                        .message("Server created")
                        .build()
        );
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Response> getServer(@PathVariable("id") Long id) {
        Server server = serverService.get(id);
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .statusCode(HttpStatus.OK.value())
                        .httpStatus(HttpStatus.OK)
                        .data(Map.of("server",server))
                        .message("Server retrieved")
                        .build()
        );
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Response> deleteServer(@PathVariable("id") Long id) {
        return ResponseEntity.ok(
                Response.builder()
                        .timeStamp(now())
                        .statusCode(HttpStatus.OK.value())
                        .httpStatus(HttpStatus.OK)
                        .data(Map.of("deleted",serverService.delete(id)))
                        .message("Server deleted")
                        .build()
        );
    }

    @GetMapping(path = "/image/{fileName}", produces = IMAGE_PNG_VALUE)
    public byte[] getServerImage(@PathVariable("fileName") String fileName) throws IOException {
        return Files.readAllBytes(Paths.get(System.getProperty("user.home")+ "Descargas/images/"+fileName));
    }


}
