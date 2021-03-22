package com.angelangelov.remont_bg.repository;



import com.angelangelov.remont_bg.model.entities.LogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<LogEntity,String> {

    @Query(
            value = "SELECT offer_id -- COUNT(offer_id) \n" +
                    "count FROM remont_db.logs group by offer_id order by count desc LIMIT 3",
            nativeQuery = true)
    List<String> result();
}
