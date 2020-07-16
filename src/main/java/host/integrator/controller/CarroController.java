package host.integrator.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import host.integrator.model.Carro;
import host.integrator.repository.CarroRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class CarroController {

    @Autowired
    CarroRepository carroRepository;

    @GetMapping("/carros")
    public ResponseEntity<List<Carro>> getAllTutorials(@RequestParam(required = false) String marca) {
        try {
            List<Carro> carros = new ArrayList<Carro>();

            if (marca == null)
                carroRepository.findAll().forEach(carros::add);
            else
                carroRepository.findByMarca(marca).forEach(carros::add);

            if (carros.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(carros, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/carros/{id}")
    public ResponseEntity<Carro> getTutorialById(@PathVariable("id") long id) {
        Optional<Carro> carroData = carroRepository.findById(id);

        if (carroData.isPresent()) {
            return new ResponseEntity<>(carroData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/carros")
    public ResponseEntity<Carro> createCarro(@RequestBody Carro carro) {
        try {
            Carro _carro = carroRepository
                    .save(new Carro(carro.getMarca(), carro.getModelo(), false));
            return new ResponseEntity<>(_carro, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.EXPECTATION_FAILED);
        }
    }

    @PutMapping("/carros/{id}")
    public ResponseEntity<Carro> updateTutorial(@PathVariable("id") long id, @RequestBody Carro carro) {
        Optional<Carro> carroData = carroRepository.findById(id);

        if (carroData.isPresent()) {
            Carro _carro = carroData.get();
            _carro.setMarca(carro.getMarca());
            _carro.setModelo(carro.getModelo());
            _carro.setDisponivel(carro.isDisponivel());
            return new ResponseEntity<>(carroRepository.save(_carro), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/carros/{id}")
    public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
        try {
            carroRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }

    @DeleteMapping("/carros")
    public ResponseEntity<HttpStatus> deleteAllTutorials() {
        try {
            carroRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }

    }

    @GetMapping("/carros/disponivel")
    public ResponseEntity<List<Carro>> findByDisponivel() {
        try {
            List<Carro> tutorials = carroRepository.findByDisponivel(true);

            if (tutorials.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(tutorials, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.EXPECTATION_FAILED);
        }
    }


}
