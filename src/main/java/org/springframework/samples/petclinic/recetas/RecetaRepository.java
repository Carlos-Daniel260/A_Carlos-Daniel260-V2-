package org.springframework.samples.petclinic.recetas;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface RecetaRepository extends Repository<Receta, Long> {
    void save(Receta receta);
}
