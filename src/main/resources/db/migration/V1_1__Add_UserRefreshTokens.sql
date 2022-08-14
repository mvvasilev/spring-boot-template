create table if not exists "app"."UserRefreshToken" (
    "id" numeric auto_increment,
    "userId" numeric,
    "refreshToken" varchar(512),
    "issuedOn" datetime,
    "expiresAt" datetime,
    constraint "PK_UserRefreshToken" primary key ("id"),
    constraint "FK_UserRefreshToken_User" foreign key ("userId") references "app"."User"("id")
);