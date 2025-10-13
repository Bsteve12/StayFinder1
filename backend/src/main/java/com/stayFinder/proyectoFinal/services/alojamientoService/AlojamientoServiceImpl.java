package com.stayFinder.proyectoFinal.services.alojamientoService;

import com.stayFinder.proyectoFinal.dto.inputDTO.AlojamientoRequestDTO;
import com.stayFinder.proyectoFinal.dto.outputDTO.AlojamientoResponseDTO;
import com.stayFinder.proyectoFinal.entity.Alojamiento;
import com.stayFinder.proyectoFinal.entity.Usuario;
import com.stayFinder.proyectoFinal.entity.enums.EstadoReserva;
import com.stayFinder.proyectoFinal.entity.enums.Role;
import com.stayFinder.proyectoFinal.repository.AlojamientoRepository;
import com.stayFinder.proyectoFinal.repository.ReservaRepository;
import com.stayFinder.proyectoFinal.repository.UsuarioRepository;
import com.stayFinder.proyectoFinal.services.alojamientoService.interfaces.AlojamientoServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AlojamientoServiceImpl implements AlojamientoServiceInterface {

    private final AlojamientoRepository alojamientoRepo;
    private final UsuarioRepository usuarioRepo;
    private final ReservaRepository reservaRepo;

    @Override
    public AlojamientoResponseDTO crear(AlojamientoRequestDTO req, Long ownerId) {
        Usuario owner = usuarioRepo.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Validación de roles permitidos
        if (!(owner.getRole().equals(Role.OWNER) || owner.getRole().equals(Role.ADMIN))) {
            throw new RuntimeException("Solo OWNERS o ADMIN pueden crear alojamientos");
        }

        Alojamiento alojamiento = Alojamiento.builder()
                .nombre(req.nombre())
                .direccion(req.direccion())
                .precio(req.precio())
                .descripcion(req.descripcion())
                .capacidadMaxima(req.capacidadMaxima())
                .owner(owner)
                .eliminado(false)
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

        if (alojamiento.isEliminado()) {
            throw new RuntimeException("El alojamiento fue eliminado");
        }

        if (!alojamiento.getOwner().getId().equals(ownerId)) {
            throw new RuntimeException("No puedes editar un alojamiento que no es tuyo");
        }

        alojamiento.setNombre(req.nombre());
        alojamiento.setDireccion(req.direccion());
        alojamiento.setPrecio(req.precio());
        alojamiento.setDescripcion(req.descripcion());
        alojamiento.setCapacidadMaxima(req.capacidadMaxima());

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

    public List<AlojamientoResponseDTO> obtenerAlojamientosDeOwner(Long ownerId) {
        return alojamientoRepo.findByOwnerIdAndEliminadoFalse(ownerId).stream()
                .map(a -> new AlojamientoResponseDTO(
                        a.getId(),
                        a.getNombre(),
                        a.getDireccion(),
                        a.getPrecio(),
                        a.getDescripcion(),
                        a.getOwner().getId()
                ))
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AlojamientoResponseDTO> listarAlojamientosActivos() {
        return alojamientoRepo.findByEliminadoFalse().stream()
                .map(a -> new AlojamientoResponseDTO(
                        a.getId(),
                        a.getNombre(),
                        a.getDireccion(),
                        a.getPrecio(),
                        a.getDescripcion(),
                        a.getOwner().getId()
                ))
                .toList();
    }
    @Override
    public void eliminar(Long alojamientoId, Long ownerId) {
        Alojamiento alojamiento = alojamientoRepo.findById(alojamientoId)
                .orElseThrow(() -> new RuntimeException("Alojamiento no encontrado"));

        if (!alojamiento.getOwner().getId().equals(ownerId)) {
            throw new RuntimeException("No puedes eliminar un alojamiento que no es tuyo");
        }

        // Validar reservas futuras confirmadas antes de marcar como eliminado
        boolean tieneReservasFuturas = !reservaRepo
                .findByAlojamientoIdAndEstado(alojamientoId, EstadoReserva.CONFIRMADA)
                .isEmpty();

        if (tieneReservasFuturas) {
            throw new RuntimeException("No puedes eliminar un alojamiento con reservas confirmadas futuras");
        }

        // Soft delete
        alojamiento.setEliminado(true);
        alojamientoRepo.save(alojamiento);
    }

    @Override
    @Transactional(readOnly = true)
    public AlojamientoResponseDTO obtenerPorId(Long id) {
        Alojamiento alojamiento = alojamientoRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Alojamiento no encontrado"));

        if (alojamiento.isEliminado()) {
            throw new RuntimeException("Este alojamiento está eliminado");
        }

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
