package host.integrator.repository;

import java.util.List;
import host.integrator.model.Carro;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CarroRepository extends JpaRepository<Carro, Long> {
    List<Carro> findByDisponivel(boolean disponivel);
    List<Carro> findByMarca(String marca);
}
