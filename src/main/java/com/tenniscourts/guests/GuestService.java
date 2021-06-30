package com.tenniscourts.guests;

import com.tenniscourts.exceptions.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GuestService {

    private final GuestMapper guestMapper;

    private final GuestRepository guestRepository;

    public GuestDTO createGuest(CreateGuestRequestDTO createGuestRequestDTO) {
        return guestMapper.map(guestRepository.save(guestMapper.map(createGuestRequestDTO)));
    }

    public GuestDTO findGuestById(Long guestId) {
        return guestRepository.findById(guestId).map(guestMapper::map).orElseThrow(() ->  new EntityNotFoundException("Guest not found."));
    }

    public List<GuestDTO> findGuestByName(String name) {
        return guestMapper.map(guestRepository.findByName(name));
    }

    public boolean deleteGuest(Long guestId) {
      guestRepository.deleteById(guestId);
      return guestRepository.findById(guestId).map(guestMapper::map).isPresent();
    }

  public List<GuestDTO> findAll() {
      return guestMapper.map(guestRepository.findAll());
  }

  public GuestDTO updateGuest(GuestDTO dto) {
        return guestMapper.map(guestRepository.save(guestMapper.map(dto)));
  }
}
