package dev.mvvasilev.repository;

import dev.mvvasilev.model.entity.UserRefreshToken;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRefreshTokenRepository extends CrudRepository<UserRefreshToken, Long> {

    @Query("""
            SELECT *
            FROM "app"."UserRefreshToken"
            WHERE "userId" = :userId
            ORDER BY "issuedOn" DESC
            LIMIT 1
            """)
    Optional<UserRefreshToken> findLatestByUserId(@Param("userId") long userId);


}
