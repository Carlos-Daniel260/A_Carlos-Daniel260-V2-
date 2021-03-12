package org.springframework.samples.petclinic.recetas;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface RecetaRepository extends Repository<Receta, Long> {

    @Query("SELECT receta FROM Receta receta")
    @Transactional(readOnly = true)
    Collection<Receta> All();

    void save(Receta receta);
}
