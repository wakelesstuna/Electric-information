package net.wakelesstuna.electricinformation.repositories;

import net.wakelesstuna.electricinformation.entites.db.SpotPriceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface SpotPriceRepository extends JpaRepository<SpotPriceEntity, String> {

    List<SpotPriceEntity> findAllByTimeStampDay(LocalDate date);

    long countByTimeStampDay(LocalDate date);

}
