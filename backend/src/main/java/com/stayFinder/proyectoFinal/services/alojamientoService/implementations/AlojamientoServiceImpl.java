package com.stayFinder.proyectoFinal.services.alojamientoService.implementations;



import com.stayFinder.proyectoFinal.dto.inputDTO.AlojamientoRequestDTO;
import com.stayFinder.proyectoFinal.dto.outputDTO.AlojamientoResponseDTO;
import com.stayFinder.proyectoFinal.entity.Alojamiento;
import com.stayFinder.proyectoFinal.entity.Usuario;
import com.stayFinder.proyectoFinal.entity.enums.Role;
import com.stayFinder.proyectoFinal.repository.AlojamientoRepository;
import com.stayFinder.proyectoFinal.repository.UsuarioRepository;
import com.stayFinder.proyectoFinal.services.alojamientoService.interfaces.AlojamientoServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AlojamientoServiceImpl implements AlojamientoServiceInterface {

    private final AlojamientoRepository alojamientoRepo;
    private final UsuarioRepository usuarioRepo;

    @Override
    public AlojamientoResponseDTO crear(AlojamientoRequestDTO req, Long ownerId) {
        Usuario owner = usuarioRepo.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Validación correcta con enum
        if (!owner.getRole().equals(Role.OWNER)) {
            throw new RuntimeException("Solo los OWNERS pueden crear alojamientos");
        }

        Alojamiento alojamiento = Alojamiento.builder()
                .nombre(req.nombre())
                .direccion(req.direccion())
                .precio(req.precio())
                .descripcion(req.descripcion())
                .owner(owner)
                .build();

        alojamientoRepo.save(alojamiento);

        return new AlojamientoResponseDTO(
                alojamiento.getId(),
                alojamiento.getNombre(),
                alojamiento.getDireccion(),
                alojamiento.getPrecio(),
                alojamiento.getDescripcion(),
                alojamiento.getOwner().getId()
        );
    }


    @Override
    public AlojamientoResponseDTO editar(Long alojamientoId, AlojamientoRequestDTO req, Long ownerId) {
        Alojamiento alojamiento = alojamientoRepo.findById(alojamientoId)
                .orElseThrow(() -> new RuntimeException("Alojamiento no encontrado"));

        if (!alojamiento.getOwner().getId().equals(ownerId)) {
            throw new RuntimeException("No puedes editar un alojamiento que no es tuyo");
        }

        alojamiento.setNombre(req.nombre());
        alojamiento.setDireccion(req.direccion());
        alojamiento.setPrecio(req.precio());
        alojamiento.setDescripcion(req.descripcion());

        alojamientoRepo.save(alojamiento);

        return new AlojamientoResponseDTO(
                alojamiento.getId(),
                alojamiento.getNombre(),
                alojamiento.getDireccion(),
                alojamiento.getPrecio(),
                alojamiento.getDescripcion(),
                alojamiento.getOwner().getId()
        );
    }

    @Override
    public void eliminar(Long alojamientoId, Long ownerId) {
        Alojamiento alojamiento = alojamientoRepo.findById(alojamientoId)
                .orElseThrow(() -> new RuntimeException("Alojamiento no encontrado"));

        if (!alojamiento.getOwner().getId().equals(ownerId)) {
            throw new RuntimeException("No puedes eliminar un alojamiento que no es tuyo");
        }

        alojamientoRepo.delete(alojamiento);
    }

    @Override
    @Transactional(readOnly = true)
    public AlojamientoResponseDTO obtenerPorId(Long id) {
        Alojamiento alojamiento = alojamientoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Alojamiento no encontrado"));

        return new AlojamientoResponseDTO(
                alojamiento.getId(),
                alojamiento.getNombre(),
                alojamiento.getDireccion(),
                alojamiento.getPrecio(),
                alojamiento.getDescripcion(),
                alojamiento.getOwner().getId()
        );
    }
}
