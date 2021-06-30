package com.tenniscourts.guests;

import com.tenniscourts.config.BaseRestController;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
public class GuestController extends BaseRestController {

    private final GuestService guestService;

    @GetMapping("/guests")
    public ResponseEntity<List<GuestDTO>> findAll() {
        return ResponseEntity.ok(guestService.findAll());
    }

    @PutMapping(value = "/update-guest")
    public ResponseEntity<GuestDTO> updateGuest(@RequestBody GuestDTO dto) {
        GuestDTO gDto = guestService.findGuestById(dto.getId());
        if (gDto.getId() != null) {
            return ResponseEntity.ok(guestService.updateGuest(dto));
        } else {
            return null;
        }
    }

    @PostMapping(value = "/create-guest")
    public ResponseEntity<Void> createGuest(@RequestBody CreateGuestRequestDTO createGuestRequestDTO) {
        return ResponseEntity.created(locationByEntity(guestService.createGuest(createGuestRequestDTO).getId())).build();
    }

    @GetMapping("/guest-id/{guestId}")
    public ResponseEntity<GuestDTO> findGuestById(@PathVariable Long guestId) {
        return ResponseEntity.ok(guestService.findGuestById(guestId));
    }

    @GetMapping("/guest-name/{guestName}")
    public ResponseEntity<List<GuestDTO>> findGuestByName(@PathVariable String guestName) {
        return ResponseEntity.ok(guestService.findGuestByName(guestName));
    }

    @DeleteMapping("/guest/{guestId}")
    public ResponseEntity<Map<String,String>> deleteGuest(@PathVariable Long guestId) {
        Map<String,String> response = new HashMap<>();

        GuestDTO gDTO = guestService.findGuestById(guestId);
        if (gDTO.getId() != null) {
            boolean isRemoved = guestService.deleteGuest(guestId);

            if(!isRemoved) {
                response.put("ok", "Guest is successfully removed");
                return  ResponseEntity.accepted().body(response);
            } else {
                response.put("error", "Guest was not removed");
                return  ResponseEntity.badRequest().body(response);
            }
        }
        response.put("error", "Guest was not found by the given Id=" + guestId);
        return  ResponseEntity.badRequest().body(response);
    }

}
