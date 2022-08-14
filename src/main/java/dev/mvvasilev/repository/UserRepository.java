package dev.mvvasilev.repository;

import dev.mvvasilev.model.entity.User;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Query("""
            SELECT *
            FROM "app"."User"
            WHERE "passwordHash" = :hash
            """)
    Optional<User> findByPasswordHash(@Param("hash") String passwordHash);

    @Query("""
            SELECT *
            FROM "app"."User" AS "u"
            INNER JOIN "app"."UserRefreshToken" AS "urt" ON "urt"."userId" = "u"."id"
            WHERE "urt"."refreshToken" = :token
            LIMIT 1
            """)
    Optional<User> findByRefreshToken(@Param("token") String token);

    @Query("""
            SELECT *
            FROM "app"."User"
            WHERE "registrationCode" = :registrationCode
            """)
    Optional<User> findByRegistrationCode(@Param("registrationCode") String registrationCode);
}
